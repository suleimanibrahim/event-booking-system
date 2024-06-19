package com.assessment.eventbookingsystem.model;

import com.assessment.eventbookingsystem.enums.TicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket extends BaseEntity{

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String ticketId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private String userId;
}
