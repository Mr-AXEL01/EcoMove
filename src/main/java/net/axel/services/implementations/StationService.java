package net.axel.services.implementations;

import net.axel.models.entities.Station;
import net.axel.repositories.implementations.StationRepository;
import net.axel.repositories.interfaces.IStationRepository;
import net.axel.services.interfaces.IStationService;

import java.util.List;
import java.util.UUID;

public class StationService implements IStationService {

    private final IStationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public void addStation(Station station) {
        stationRepository.addStation(station);
    }
    public Station getStationById(UUID id) {
        return stationRepository.getStationById(id);
    }
    public List<Station> getAllStations() {
        return stationRepository.getAllStations();
    }
    public void updateStation(Station station) {
        stationRepository.updateStation(station);
    }
    public void deleteStation(UUID id) {
        stationRepository.deleteStation(id);
    }
}
