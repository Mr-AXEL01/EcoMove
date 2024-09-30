package net.axel.services.interfaces;

import net.axel.models.entities.Journey;
import net.axel.models.entities.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IJourneyService {
    public void addJourney(Journey journey);
    public Journey getJourneyById(UUID id);
    public List<Journey> getAllJourneys();
    public void updateJourney(Journey journey);
    public void deleteJourney(UUID id);

    public UUID getStationIdByLocation(String departureCity);

    // This is the method you're implementing, it should match here:
    public List<Ticket> findShortestJourneyByDate(UUID startStationId, UUID endStationId, LocalDate departureDate);
}
