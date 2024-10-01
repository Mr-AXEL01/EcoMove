package net.axel.services.implementations;

import net.axel.models.entities.Journey;
import net.axel.models.entities.Station;
import net.axel.models.entities.Ticket;
import net.axel.repositories.implementations.JourneyRepository;
import net.axel.repositories.implementations.StationRepository;
import net.axel.repositories.implementations.TicketRepository;
import net.axel.repositories.interfaces.IJourneyRepository;
import net.axel.repositories.interfaces.IStationRepository;
import net.axel.repositories.interfaces.ITicketRipository;
import net.axel.services.interfaces.IJourneyService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class JourneyService implements IJourneyService {

    private final IJourneyRepository journeyRepository;
    private final ITicketRipository ticketRepository;
    private final IStationRepository stationRepository;

    public JourneyService(JourneyRepository journeyRepository, TicketRepository ticketRepository, StationRepository stationRepository) {
        this.journeyRepository = journeyRepository;
        this.ticketRepository = ticketRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public void addJourney(Journey journey) {
        journeyRepository.addJourney(journey);
    }

    @Override
    public Journey getJourneyById(UUID id) {
        return journeyRepository.getJourneyById(id);
    }

    @Override
    public List<Journey> getAllJourneys() {
        return journeyRepository.getAllJourneys();
    }

    @Override
    public void updateJourney(Journey journey) {
        journeyRepository.updateJourney(journey);
    }

    @Override
    public void deleteJourney(UUID id) {
        journeyRepository.deleteJourney(id);
    }

    private Map<UUID, List<Ticket>> createGraph() throws SQLException {
        Map<UUID, List<Ticket>> graph = new HashMap<>();
        List<Ticket> allTickets = ticketRepository.getAllTickets();

        for (Ticket ticket : allTickets) {
            graph.computeIfAbsent(ticket.getJourney().getStartStation().getId(), k -> new ArrayList<>()).add(ticket);
        }

        return graph;
    }

    @Override
    public List<Ticket> findShortestJourneyByDate(UUID startStationId, UUID endStationId, LocalDate departureDate) {
        Map<UUID, List<Ticket>> graph = null;
        try {
            graph = createGraph();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // BFS queue, holding paths as lists of tickets
        Queue<List<Ticket>> queue = new LinkedList<>();
        Set<UUID> visitedStations = new HashSet<>();

        // Start BFS from the start station, filtering by the departure date
        for (Ticket ticket : graph.getOrDefault(startStationId, Collections.emptyList())) {
            if (ticket.getDepartureTime().toLocalDate().equals(departureDate)) {
                List<Ticket> path = new ArrayList<>();
                path.add(ticket);
                queue.add(path);
            }
        }

        visitedStations.add(startStationId);

        while (!queue.isEmpty()) {
            List<Ticket> currentPath = queue.poll();
            Ticket lastTicket = currentPath.get(currentPath.size() - 1);

            UUID currentStationId = lastTicket.getJourney().getEndStation().getId();

            if (currentStationId.equals(endStationId)) {
                return currentPath;
            }

            if (visitedStations.contains(currentStationId)) {
                continue;
            }

            visitedStations.add(currentStationId);

            // Explore neighboring stations
            for (Ticket nextTicket : graph.getOrDefault(currentStationId, Collections.emptyList())) {
                if (nextTicket.getDepartureTime().toLocalDate().equals(departureDate)) {
                    List<Ticket> newPath = new ArrayList<>(currentPath);
                    newPath.add(nextTicket);
                    queue.add(newPath);
                }
            }
        }

        return null;
    }

    public UUID getStationIdByLocation(String cityName) {
        List<Station> allStations = stationRepository.getAllStations();

        for (Station station : allStations) {
            if (station.getLocation().equalsIgnoreCase(cityName)) {
                return station.getId();
            }
        }

        return null;
    }

}
