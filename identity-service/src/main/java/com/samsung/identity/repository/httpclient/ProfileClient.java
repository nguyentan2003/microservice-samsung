package com.samsung.identity.repository.httpclient;

import com.samsung.identity.configuration.AuthenticationRequestInterceptor;
import com.samsung.identity.dto.request.ApiResponse;
import com.samsung.identity.dto.request.ProfileCreationRequest;
import com.samsung.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


// name dat tuy y
@FeignClient(name = "profile-service",url = "${app.services.profile}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface ProfileClient {

    @PostMapping(value = "/internal/create",produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile (@RequestBody ProfileCreationRequest request);

    @GetMapping(value = "/user-profile/get-by-user-id/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> getProfileByUserId (@PathVariable String userId);

}
