package net.axel.repositories.interfaces;

import net.axel.models.entities.Station;

import java.util.List;
import java.util.UUID;

public interface IStationRepository {
    public void addStation(Station station);
    public Station getStationById(UUID id);
    public List<Station> getAllStations();
    public void updateStation(Station station);
    public void deleteStation(UUID id);
}
