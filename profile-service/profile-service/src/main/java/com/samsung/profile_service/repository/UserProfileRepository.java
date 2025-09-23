package com.samsung.profile_service.repository;


import com.samsung.profile_service.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
}
