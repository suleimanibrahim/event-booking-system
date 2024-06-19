package com.assessment.eventbookingsystem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @NotBlank(message = "Email is required!")
    @Email(message = "Enter valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "Password must contain 8 characters, a number and a symbol")
    private String password;
}
