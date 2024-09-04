package net.axel.models.dto;

import net.axel.models.enums.TicketStatus;
import net.axel.models.enums.TransportType;

import java.util.Date;
import java.util.UUID;

public record TicketDto (
        TransportType transportType,
        Double purchasePrice,
        Double resellPrice,
        Date saleDate,
        TicketStatus ticketStatus,
        UUID ContractId
) {

}
