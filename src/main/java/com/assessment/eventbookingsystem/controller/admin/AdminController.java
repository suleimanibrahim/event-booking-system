package com.assessment.eventbookingsystem.controller.admin;

import com.assessment.eventbookingsystem.dto.request.EventDto;
import com.assessment.eventbookingsystem.dto.response.ApiResponse;
import com.assessment.eventbookingsystem.resources.BaseResource;
import com.assessment.eventbookingsystem.resources.EventResources;
import com.assessment.eventbookingsystem.resources.UserResources;
import com.assessment.eventbookingsystem.service.serviceImp.UsersServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping(UserResources.ADMIN+ EventResources.EVENT)
@RequiredArgsConstructor
public class AdminController {

    private final UsersServiceImpl usersService;

    @RateLimiter(name = "createEventRateLimiter")
    @PostMapping(value = EventResources.CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> createEvent(@RequestBody EventDto eventDto) {
        try {
            return usersService.createEvent(eventDto);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode().value()).body(new ApiResponse<>(ex.getMessage(), ex.getStatusCode().value(), null));
        }
    }

    @PutMapping(value = EventResources.UPDATE+ BaseResource.PATH_ID_PATHVARIABLE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> updateEvent(@RequestBody EventDto eventDto, @PathVariable String id) {
        try {
            return usersService.updateEvent(eventDto, id);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode().value()).body(new ApiResponse<>(ex.getMessage(), ex.getStatusCode().value(), null));
        }
    }

    @DeleteMapping(value = EventResources.DELETE+ BaseResource.PATH_ID_PATHVARIABLE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> deleteEvent(@PathVariable String id) {
        try {
            return usersService.deleteEvent(id);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode().value()).body(new ApiResponse<>(ex.getMessage(), ex.getStatusCode().value(), null));
        }
    }
}
