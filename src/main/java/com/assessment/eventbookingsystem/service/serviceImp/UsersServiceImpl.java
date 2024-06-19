package com.assessment.eventbookingsystem.service.serviceImp;

import com.assessment.eventbookingsystem.config.JwtConfig;
import com.assessment.eventbookingsystem.controller.user.UsersController;
import com.assessment.eventbookingsystem.dto.request.EventDto;
import com.assessment.eventbookingsystem.dto.request.UsersDto;
import com.assessment.eventbookingsystem.dto.request.ValidationUtils;
import com.assessment.eventbookingsystem.dto.response.ApiResponse;
import com.assessment.eventbookingsystem.enums.BookingStatus;
import com.assessment.eventbookingsystem.enums.TicketStatus;
import com.assessment.eventbookingsystem.exception.*;
import com.assessment.eventbookingsystem.model.Booking;
import com.assessment.eventbookingsystem.model.Event;
import com.assessment.eventbookingsystem.model.Ticket;
import com.assessment.eventbookingsystem.model.Users;
import com.assessment.eventbookingsystem.pagination.EventPage;
import com.assessment.eventbookingsystem.pagination.TicketPage;
import com.assessment.eventbookingsystem.repository.BookingRepository;
import com.assessment.eventbookingsystem.repository.EventRepository;
import com.assessment.eventbookingsystem.repository.TicketRepository;
import com.assessment.eventbookingsystem.repository.UsersRepository;
import com.assessment.eventbookingsystem.service.UsersService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.assessment.eventbookingsystem.utils.MessageUtils.*;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    private final JwtConfig securityUtils;
    private final BookingRepository bookingRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    public Users buildUsersDto(UsersDto usersDto){
        return Users.builder()
                .firstName(usersDto.getFirstName())
                .lastName(usersDto.getLastName())
                .email(usersDto.getEmail())
                .address(usersDto.getAddress())
                .dob(usersDto.getDob())
                .country(usersDto.getCountry())
                .state(usersDto.getState())
                .lga(usersDto.getLga())
                .phoneNumber(usersDto.getPhoneNumber())
                .password(passwordEncoder.encode(usersDto.getPassword()))
                .roles(usersDto.getRole())
                .build();
    }

    @Override
    public ResponseEntity<ApiResponse<String>> register(UsersDto usersDto) {
        Set<ConstraintViolation<UsersDto>> violations = ValidationUtils.validateDTO(usersDto);
        var errorMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        if (!violations.isEmpty()) {
            throw new ViolationException(errorMessage);
        }
     boolean existUser = usersRepository.findUsersByEmail(usersDto.getEmail()).isPresent();
     if(existUser){
         throw new UserAlreadyExistExceptions(USER_ALREADY_EXIST);
     }
    var user = buildUsersDto(usersDto);
     usersRepository.save(user);
     var response = ApiResponse.<String>builder()
             .responseCode(HttpStatus.CREATED.value())
             .responseMessage(REGISTERED_SUCCESSFUL)
             .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<String>> bookEvent(String eventId, String userId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RecordNotFoundException(EVENT_NOT_FOUND));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        Ticket ticket = ticketRepository.findFirstByEventIdAndStatus(eventId, TicketStatus.AVAILABLE).orElseThrow(()-> new RecordNotFoundException(NOT_AVAILABLE_TICKET));
        ticket.setStatus(TicketStatus.BOOKED);
        ticket.setUserId(userId);
        ticketRepository.save(ticket);
        Booking booking = Booking.builder()
                .user(user)
                .event(event)
                .ticket(ticket)
                .bookingDate(LocalDateTime.now())
                .status(BookingStatus.BOOKED)
                .build();
        bookingRepository.save(booking);
        var response = ApiResponse.<String>builder()
                .responseMessage(TICKET_BOOKED_SUCCESSFUL)
                .responseCode(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> cancelBooking(String eventId,String userId) {
        List<Booking> bookings = bookingRepository.findByEventIdAndUserId(eventId, userId);
        if (bookings.isEmpty()) {
            throw new RecordNotFoundException(BOOKING_NOT_FOUND);
        }
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.CANCELLED) {
                continue;
            }
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
            Ticket ticket = booking.getTicket();
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setUserId(null);
            ticketRepository.save(ticket);
        }
        var response = ApiResponse.<String>builder()
                .responseMessage(CANCEL_BOOKING_SUCCESSFUL)
                .responseCode(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }


    public Event buildEventDto(EventDto eventDto){
        return Event.builder()
                .name(eventDto.getName())
                .description(eventDto.getDescription())
                .date(eventDto.getDate())
                .location(eventDto.getLocation())
                .availableTickets(eventDto.getAvailableTickets())
                .build();
    }
    @Transactional
    @Override
    public ResponseEntity<ApiResponse<String>> createEvent(EventDto eventDto) {
        Set<ConstraintViolation<EventDto>> violations = ValidationUtils.validateDTO(eventDto);
        var errorMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        if (!violations.isEmpty()) {
            throw new ViolationException(errorMessage);
        }
        var claim = securityUtils.extractClaimsFromToken();
        var userId = MapUtils.getString(claim, "userId");
        boolean existEvent = eventRepository.findEventByName(eventDto.getName()).isPresent();
        if(existEvent){
            throw new DuplicateRecordExceptions(DUPLICATE_EVENT);
        }
        var event = buildEventDto(eventDto);
        event = eventRepository.save(event);
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < eventDto.getAvailableTickets(); i++) {
            var ticket = Ticket.builder()
                    .event(event)
                    .status(TicketStatus.AVAILABLE)
                    .ticketId(UUID.randomUUID().toString())
                    .userId(userId)
                    .build();
            tickets.add(ticket);
        }
        ticketRepository.saveAll(tickets);
        event.setTickets(tickets);
        eventRepository.save(event);
        var response = ApiResponse.<String>builder()
                .responseMessage(EVENT_CREATED_SUCCESSFUL)
                .responseCode(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> updateEvent(EventDto eventDto, String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RecordNotFoundException(EVENT_NOT_FOUND));
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setDate(eventDto.getDate());
        event.setLocation(eventDto.getLocation());
        event.setAvailableTickets(eventDto.getAvailableTickets());
        eventRepository.save(event);
        var response = ApiResponse.<String>builder()
                .responseMessage(EVENT_UPDATED_SUCCESSFUL)
                .responseCode(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteEvent(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RecordNotFoundException(EVENT_NOT_FOUND));
        List<Ticket> bookings = ticketRepository.findByEvent(event);
        if (!bookings.isEmpty()) {
            ticketRepository.deleteAllInBatch(bookings);
        }
        eventRepository.delete(event);
        var response = ApiResponse.<String>builder()
                .responseMessage(EVENT_DELETED_SUCCESSFUL)
                .responseCode(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Event>>> getAllEvents(EventPage eventPage) {
        Sort sort = Sort.by(eventPage.getSortDirection(), eventPage.getSortBy());
        Pageable pageable = PageRequest.of(eventPage.getPageNumber(), eventPage.getPageSize(), sort);
        Page<Event> users = eventRepository.findAll(pageable);
        var response = ApiResponse.<Page<Event>>builder()
                .responseMessage(EVENT_FETCH_SUCCESS)
                .responseCode(HttpStatus.OK.value())
                .data(users)
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Ticket>>> getAllTickets(TicketPage ticketPage) {
        Sort sort = Sort.by(ticketPage.getSortDirection(), ticketPage.getSortBy());
        Pageable pageable = PageRequest.of(ticketPage.getPageNumber(), ticketPage.getPageSize(), sort);
        Page<Ticket> tickets = ticketRepository.findAll(pageable);
        var response = ApiResponse.<Page<Ticket>>builder()
                .responseMessage(TICKET_FETCH_SUCCESS)
                .responseCode(HttpStatus.OK.value())
                .data(tickets)
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }


}
