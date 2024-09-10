package net.axel.models.entities;

import net.axel.models.enums.TicketStatus;
import net.axel.models.enums.TransportType;

import java.util.Date;
import java.util.UUID;

public class Ticket {
    private UUID id;
    private TransportType transportType;
    private Double purchasePrice;
    private Double resellPrice;
    private Date saleDate;
    private TicketStatus ticketStatus;
    private Contract contract;
    private Station station;

    public Ticket(UUID id, TransportType transportType, Double purchasePrice, Double resellPrice, Date saleDate, TicketStatus ticketStatus, Contract contract, Station station) {
        this.id = id;
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.resellPrice = resellPrice;
        this.saleDate = saleDate;
        this.ticketStatus = ticketStatus;
        this.contract = contract;
        this.station= station;
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

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getResellPrice() {
        return resellPrice;
    }

    public void setResellPrice(Double resellPrice) {
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

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
