package net.axel.models.entities;

import net.axel.models.enums.TicketStatus;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public float getResellPrice() {
        return resellPrice;
    }

    public void setResellPrice(float resellPrice) {
        this.resellPrice = resellPrice;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
