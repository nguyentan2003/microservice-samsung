package com.samsung.identity.service;

import java.util.HashSet;
import java.util.List;

import com.samsung.event.dto.DataEvent;
import com.samsung.event.dto.DataUserInfo;
import com.samsung.event.dto.NotificationEvent;
import com.samsung.identity.dto.request.ApiResponse;
import com.samsung.identity.dto.request.ProfileCreationRequest;
import com.samsung.identity.dto.response.UserProfileResponse;
import com.samsung.identity.mapper.RoleMapper;
import com.samsung.identity.repository.httpclient.ProfileClient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samsung.identity.constant.PredefinedRole;
import com.samsung.identity.dto.request.UserCreationRequest;
import com.samsung.identity.dto.request.UserUpdateRequest;
import com.samsung.identity.dto.response.UserResponse;
import com.samsung.identity.entity.Role;
import com.samsung.identity.entity.User;
import com.samsung.identity.exception.AppException;
import com.samsung.identity.exception.ErrorCode;
import com.samsung.identity.mapper.ProfileMapper;
import com.samsung.identity.mapper.UserMapper;
import com.samsung.identity.repository.RoleRepository;
import com.samsung.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    ProfileMapper profileMapper;
    PasswordEncoder passwordEncoder;
    ProfileClient profileClient;
    RoleMapper roleMapper;
//    vì đang cấu hình string string
   KafkaTemplate<String, Object> kafkaTemplate;

//    KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();

        DataEvent dataEvent = DataEvent.builder()
                .email(request.getEmail())
                .id(user.getId())
                .phone(request.getPhone())
                .address(request.getAddress())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .build();

        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        try {
            ProfileCreationRequest profileCreationRequest = profileMapper.toProfileCreationRequest(request);
            profileCreationRequest.setUserId(user.getId());


            ApiResponse<UserProfileResponse> profile =  profileClient.createProfile(profileCreationRequest);

            if(profile.getCode() != 1000){
                throw new AppException(ErrorCode.CREATE_PROFILE_ERROR);

            }
            UserResponse userResponse = UserResponse.builder()
                    .email(request.getEmail())
                    .id(user.getId())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .fullName(request.getFullName())
                    .username(request.getUsername())
                    .roles(roleMapper.toSetRoleResponse(user.getRoles()))
                    .build();


            kafkaTemplate.send("register_user4",dataEvent);
            return userResponse;

        } catch (Exception e) {
            // Rollback thủ công
            userRepository.delete(user);
            kafkaTemplate.send("register_user4",dataEvent);
            throw new AppException(ErrorCode.CREATE_PROFILE_ERROR);
        }



    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");

        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

   // @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        UserResponse userResponse = userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

        ApiResponse<UserProfileResponse> apiProfile =  profileClient.getProfileByUserId(userResponse.getId());
        UserProfileResponse profile = apiProfile.getResult();

        userResponse.setEmail(profile.getEmail());
        userResponse.setAddress(profile.getAddress());
        userResponse.setPhone(profile.getPhone());
        userResponse.setFullName(profile.getFullName());

        return userResponse;
    }

    public DataUserInfo getDataInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        ApiResponse<UserProfileResponse> apiProfile =  profileClient.getProfileByUserId(user.getId());
        UserProfileResponse profile = apiProfile.getResult();
        DataUserInfo dataUserInfo = DataUserInfo.builder()
                .id(user.getId())
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .fullName(profile.getFullName())
                .username(user.getUsername())
                .build();


        return dataUserInfo;
    }
}
