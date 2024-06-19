package com.assessment.eventbookingsystem;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;
import java.util.Base64;

@SpringBootApplication
public class EventBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBookingSystemApplication.class, args);
    }

}
