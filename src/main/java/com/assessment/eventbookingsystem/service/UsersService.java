package com.assessment.eventbookingsystem.service;

import com.assessment.eventbookingsystem.dto.request.EventDto;
import com.assessment.eventbookingsystem.dto.request.UsersDto;
import com.assessment.eventbookingsystem.dto.response.ApiResponse;
import com.assessment.eventbookingsystem.model.Event;
import com.assessment.eventbookingsystem.model.Ticket;
import com.assessment.eventbookingsystem.pagination.EventPage;
import com.assessment.eventbookingsystem.pagination.TicketPage;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface UsersService {
    ResponseEntity<ApiResponse<String>> register(UsersDto usersDto);
    ResponseEntity<ApiResponse<String>> bookEvent(String eventId, String userId);
    ResponseEntity<ApiResponse<String>> cancelBooking(String eventId, String userId);
    ResponseEntity<ApiResponse<String>> createEvent(EventDto eventDto);
    ResponseEntity<ApiResponse<String>> updateEvent(EventDto eventDto, String eventId);
    ResponseEntity<ApiResponse<String>> deleteEvent(String eventId);
    ResponseEntity<ApiResponse<Page<Event>>> getAllEvents(EventPage eventPage);
    ResponseEntity<ApiResponse<Page<Ticket>>> getAllTickets(TicketPage ticketPage);
}
