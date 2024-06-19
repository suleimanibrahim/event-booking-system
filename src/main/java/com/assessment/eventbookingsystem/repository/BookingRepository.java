package com.assessment.eventbookingsystem.repository;

import com.assessment.eventbookingsystem.model.Booking;
import com.assessment.eventbookingsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,String> {
    Optional<Booking> findBookingById(String id);

    List<Booking> findByEventIdAndUserId(String eventId, String userId);

}
