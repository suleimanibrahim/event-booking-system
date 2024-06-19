package com.assessment.eventbookingsystem.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    @NotBlank(message = "Event name is required!")
    private String name;

    @NotBlank(message = "Description is required!")
    private String description;

    @NotNull(message = "Date is required!")
    private LocalDateTime date;

    @NotBlank(message = "Location is required!")
    private String location;

    @NotNull(message = "Number of tickets is required!")
    @Min(value = 1, message = "There must be at least 1 ticket available!")
    private Integer availableTickets;
}
