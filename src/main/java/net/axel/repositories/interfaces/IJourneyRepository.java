package net.axel.repositories.interfaces;

import net.axel.models.entities.Journey;
import net.axel.models.entities.Station;

import java.util.List;
import java.util.UUID;

public interface IJourneyRepository {

    public void addJourney(Journey journey);
    public Journey getJourneyById(UUID id);
    public List<Journey> getAllJourneys();
    public void updateJourney(Journey journey);
    public void deleteJourney(UUID id);
}
