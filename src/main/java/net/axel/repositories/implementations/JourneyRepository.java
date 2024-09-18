package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Journey;
import net.axel.models.entities.Station;
import net.axel.repositories.interfaces.IJourneyRepository;
import net.axel.repositories.interfaces.IStationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JourneyRepository implements IJourneyRepository {

    private final String tableName = "journeys";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    private final IStationRepository stationRepository;

    public JourneyRepository(StationRepository stationRepository) throws SQLException {
        this.stationRepository = stationRepository;
    }

    @Override
    public void addJourney(Journey journey) {
        final String query = "INSERT INTO " + tableName + " (id, start_station, end_station) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, journey.getId());
            stmt.setObject(2, journey.getStartStation().getId());
            stmt.setObject(3, journey.getEndStation().getId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding new station : " + e.getMessage());
        }
    }

    @Override
    public Journey getJourneyById(UUID id) {
        final String query = "SELECT j.* , start_s.* , end_s.* FROM "+ tableName + " j " +
                "JOIN stations start_s ON j.start_station = start_s.id " +
                "JOIN stations end_s ON j.end_station = end_s.id WHERE j.id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            try(ResultSet rst = stmt.executeQuery()) {
                if(rst.next()) {
                    return mapToJourney(rst);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting Journey By ID : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Journey> getAllJourneys() {
        final String query = "SELECT j.* , start_s.* , end_s.* FROM "+ tableName + " j " +
                "JOIN stations start_s ON j.start_station = start_s.id " +
                "JOIN stations end_s ON j.end_station = end_s.id";
        final List<Journey> journeys = new ArrayList<>();
        try(Statement stmt = connection.createStatement()) {
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {
                journeys.add(mapToJourney(rst));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting all Journeys : " + e.getMessage());
        }
        return journeys;
    }

    @Override
    public void updateJourney(Journey journey) {
        final String query = "UPDATE "+ tableName + " SET start_station = ? , end_station= ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, journey.getStartStation());
            stmt.setObject(2, journey.getEndStation());
            stmt.setObject(4, journey.getId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating Station : " + e.getMessage());
        }
    }

    @Override
    public void deleteJourney(UUID id) {
        final String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting Station : " + e.getMessage());
        }
    }

    private Journey mapToJourney(ResultSet rst) throws SQLException {
        UUID journeyId = (UUID) rst.getObject("id");

        UUID startStationId = UUID.fromString(rst.getString("start_station"));
        UUID endStationId = UUID.fromString(rst.getString("end_station"));

        Station startStation = stationRepository.getStationById(startStationId);
        Station endStation = stationRepository.getStationById(endStationId);

        return new Journey(journeyId, startStation, endStation);

    }
}
