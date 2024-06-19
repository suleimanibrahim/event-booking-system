package com.assessment.eventbookingsystem.repository;

import com.assessment.eventbookingsystem.enums.TicketStatus;
import com.assessment.eventbookingsystem.model.Booking;
import com.assessment.eventbookingsystem.model.Event;
import com.assessment.eventbookingsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,String> {
    Optional<Ticket> findTicketByTicketId(String id);
    Optional<Ticket> findTicketByUserId(String id);
    Optional<Ticket> findTicketByStatus(TicketStatus status);
    List<Ticket> findByEvent(Event event);

    Optional<Ticket> findFirstByEventIdAndStatus(String eventId, TicketStatus ticketStatus);
}
