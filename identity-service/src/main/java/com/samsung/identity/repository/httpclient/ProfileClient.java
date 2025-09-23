package com.samsung.identity.repository.httpclient;

import com.samsung.identity.dto.request.ProfileCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


// name dat tuy y
@FeignClient(name = "profile-service",url = "http://localhost:8001/profile/user-profile")
public interface ProfileClient {
    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
    Object createProfile (@RequestBody ProfileCreationRequest request);

}
