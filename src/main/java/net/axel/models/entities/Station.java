package net.axel.models.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Station {
    private UUID id;
    private String startStation;
    private String endStation;
    private LocalDate startDate;
    private List<Ticket> tickets;

    public Station(UUID id, String startStation, String endStation, LocalDate startDate) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.startDate = startDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
