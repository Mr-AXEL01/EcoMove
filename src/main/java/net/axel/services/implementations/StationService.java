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

    @Override
    public void addStation(Station station) {
        stationRepository.addStation(station);
    }

    @Override
    public Station getStationById(UUID id) {
        return stationRepository.getStationById(id);
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepository.getAllStations();
    }

    @Override
    public void updateStation(Station station) {
        stationRepository.updateStation(station);
    }

    @Override
    public void deleteStation(UUID id) {
        stationRepository.deleteStation(id);
    }

}
