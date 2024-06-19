package com.assessment.eventbookingsystem.dto.request;

import com.assessment.eventbookingsystem.enums.UsersRole;
import com.assessment.eventbookingsystem.model.Roles;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsersDto {
    @NotBlank(message = "First name is required!")
    @Size(message = "First name must not be more than 6 characters")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    @Size(message = "Last name must not be more than 6 characters")
    private String lastName;

    @NotBlank(message = "Phone number is required!")
    @Size(min = 11, max = 14, message = "Phone number must be {min} or {max} digit")
    private String phoneNumber;

    @NotBlank(message = "Email is required!")
    @Email(message = "Enter valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "Password must contain 8 characters, a number and a symbol")
    private String password;

    @NotBlank(message = "Address name is required!")
    private String address;

    @NotBlank(message = "Date of birth is required!")
    private String dob;

    @NotBlank(message = "Country is required!")
    private String country;

    @NotBlank(message = "State is required!")
    private String state;

    @NotNull(message = "Role is required")
    private Roles role;

    @NotBlank(message = "LGA is required!")
    private String lga;
}
