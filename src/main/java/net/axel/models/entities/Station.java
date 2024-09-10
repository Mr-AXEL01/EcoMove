package net.axel.models.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Station {
    private UUID id;
    private String startStation;
    private String endStation;
    private Date startDate;
    private List<Ticket> tickets;

    public Station(UUID id, String startStation, String endStation, Date startDate, List<Ticket> tickets) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.startDate = startDate;
        this.tickets = tickets;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
