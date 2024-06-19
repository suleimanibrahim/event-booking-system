package com.assessment.eventbookingsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Users implements UserDetails{
    @Id
//    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @NotBlank(message = "First name is required!")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    private String lastName;

    @NotBlank(message = "Phone number is required!")
    @Size(min = 11, max = 14)
    private String phoneNumber;

    @NotBlank(message = "Email is required!")
    @Email
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank(message = "DOB is required!")
    private String dob;

    @NotBlank(message = "Country is required!")
    private String country;

    @NotBlank(message = "State is required!")
    private String state;

    @NotBlank(message = "Lga is required!")
    private String lga;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;


    private static String generateRandomId() {
        UUID uuid = UUID.randomUUID();
        String hex = uuid.toString().replace("-", "");
        return hex.substring(0, 24);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
