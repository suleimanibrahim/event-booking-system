package com.assessment.eventbookingsystem.controller.auth;

import com.assessment.eventbookingsystem.dto.request.UsersDto;
import com.assessment.eventbookingsystem.dto.response.ApiResponse;
import com.assessment.eventbookingsystem.resources.LoginResource;
import com.assessment.eventbookingsystem.service.serviceImp.UsersServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping(LoginResource.AUTH)
@RequiredArgsConstructor
public class RegistrationController {

    private final UsersServiceImpl usersService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody UsersDto usersDto){
        try {
            return usersService.register(usersDto);
        }catch (HttpStatusCodeException ex){
            return ResponseEntity.status(ex.getStatusCode().value()).body( new ApiResponse<>(ex.getMessage(),ex.getStatusCode().value(), null));
        }
    }
}
