package net.axel.models.entities;

import net.axel.models.enums.TransportType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Favorite {

    private UUID id;
    private String startStation;
    private String endStation;
    private LocalDateTime departureTime;
    private TransportType transportType;
    private Client clientEmail;

    public Favorite(UUID id, String startStation, String endStation, LocalDateTime departureTime, TransportType transportType, Client clientEmail) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.departureTime = departureTime;
        this.transportType = transportType;
        this.clientEmail = clientEmail;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public Client getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(Client clientEmail) {
        this.clientEmail = clientEmail;
    }
}
