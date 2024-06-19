package com.assessment.eventbookingsystem.controller.user;

import com.assessment.eventbookingsystem.dto.response.ApiResponse;
import com.assessment.eventbookingsystem.model.Event;
import com.assessment.eventbookingsystem.model.Ticket;
import com.assessment.eventbookingsystem.pagination.EventPage;
import com.assessment.eventbookingsystem.pagination.TicketPage;
import com.assessment.eventbookingsystem.resources.BaseResource;
import com.assessment.eventbookingsystem.resources.EventResources;
import com.assessment.eventbookingsystem.resources.UserResources;
import com.assessment.eventbookingsystem.service.serviceImp.UsersServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping(UserResources.USER+ EventResources.EVENT)
@RequiredArgsConstructor
public class UsersController {

    private final UsersServiceImpl usersService;

    @RateLimiter(name = "bookEventRateLimiter")
    @PostMapping(value=EventResources.BOOK+ BaseResource.PATH_ID_PATHVARIABLE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> bookEvent(@PathVariable String id, @PathParam("eventId") String eventId) {
        try {
            return usersService.bookEvent(eventId, id);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode().value()).body(new ApiResponse<>(ex.getMessage(), ex.getStatusCode().value(), null));
        }
    }

    @PostMapping(value=EventResources.BOOK+ EventResources.CANCEL+BaseResource.PATH_ID_PATHVARIABLE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> cancelBooking(@PathVariable String id, @PathParam("eventId") String eventId) {
        try {
            return usersService.cancelBooking(eventId, id);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode().value()).body(new ApiResponse<>(ex.getMessage(), ex.getStatusCode().value(), null));
        }
    }
    @GetMapping(value=EventResources.ALL_EVENT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Page<Event>>> getAllEvent(EventPage eventPage) {
        try {
            return usersService.getAllEvents(eventPage);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode().value()).body(new ApiResponse<>(ex.getMessage(), ex.getStatusCode().value(), null));
        }
    }

    @GetMapping(value=EventResources.ALL_TICKET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Page<Ticket>>> getAllTicket(TicketPage ticketPage) {
        try {
            return usersService.getAllTickets(ticketPage);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode().value()).body(new ApiResponse<>(ex.getMessage(), ex.getStatusCode().value(), null));
        }
    }




}
