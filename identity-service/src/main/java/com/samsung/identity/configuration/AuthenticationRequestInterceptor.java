package com.samsung.identity.configuration;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String authHeader = request.getHeader("Authorization");

            log.info("Header Authorization: {}", authHeader);

            if (StringUtils.hasText(authHeader)) {
                template.header("Authorization", authHeader);
            }
        } else {
            // Trường hợp chạy ngoài HTTP request (Kafka, Scheduler, Async…)
            log.debug("Không có ServletRequestAttributes → bỏ qua Authorization header.");
        }
    }
}
