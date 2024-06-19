package com.assessment.eventbookingsystem.controller.auth;

import com.assessment.eventbookingsystem.config.JwtConfig;
import com.assessment.eventbookingsystem.config.UserDetailService;
import com.assessment.eventbookingsystem.dto.request.AuthRequest;
import com.assessment.eventbookingsystem.dto.response.ApiResponse;
import com.assessment.eventbookingsystem.exception.InvalidDetailsExceptions;
import com.assessment.eventbookingsystem.resources.LoginResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.assessment.eventbookingsystem.utils.MessageUtils.INVALID_DETAILS;
import static com.assessment.eventbookingsystem.utils.MessageUtils.LOGIN_SUCCESSFUL;

@Tag(name = "Authentication", description = "Auth Api")
@RestController
@RequestMapping(LoginResource.AUTH)
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailService userDetailsService;

    private  final JwtConfig jwtUtil;
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<String>> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
             authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new InvalidDetailsExceptions(INVALID_DETAILS);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        var data = jwtUtil.generateToken(userDetails);
        var response = ApiResponse.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage(LOGIN_SUCCESSFUL)
                .data(data)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
