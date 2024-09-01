package net.axel.models.entities;

import net.axel.models.enums.TransportType;

import java.util.Date;
import java.util.UUID;

public class Ticket {
    private UUID id;
    private TransportType transportType;
    private float purchasePrice;
    private float resellPrice;
    private Date saleDate;
    private TicketStatus ticketStatus;

    public Ticket(UUID id, TransportType transportType, float purchasePrice, float resellPrice, Date saleDate, TicketStatus ticketStatus) {
        this.id = id;
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.resellPrice = resellPrice;
        this.saleDate = saleDate;
        this.ticketStatus = ticketStatus;
    }
}
