package net.axel.models.entities;

import java.util.UUID;

public class Journey {

    private UUID id;
    private Station startStation;
    private Station endStation;

    public Journey(UUID id, Station startStation, Station endStation) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }
}
