package net.axel.models.dto;

import net.axel.models.enums.TicketStatus;
import net.axel.models.enums.TransportType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record TicketDto (
        TransportType transportType,
        Double purchasePrice,
        Double resellPrice,
        Date saleDate,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        TicketStatus ticketStatus,
        UUID ContractId,
        UUID JourneyId
) {

}
