package com.assessment.eventbookingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event extends BaseEntity{

    private String name;
    private String description;
    private LocalDateTime date;
    private String location;
    private Integer availableTickets;

    @JsonIgnore
    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;
}
