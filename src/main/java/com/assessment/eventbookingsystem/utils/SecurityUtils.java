package com.assessment.eventbookingsystem.utils;

import com.assessment.eventbookingsystem.controller.user.UsersController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class SecurityUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);

    public Map<String, Object> getAuthenticatedClaim() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> claims = new HashMap<>();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            Object details = authentication.getDetails();
            if (details instanceof Map) {
                claims = (Map<String, Object>) details;
            }
        }
        LOGGER.info("claims {}", claims);
        return claims;
    }
}
