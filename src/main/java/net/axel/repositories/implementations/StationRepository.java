package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Station;
import net.axel.repositories.interfaces.IStationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StationRepository implements IStationRepository {

    private final String tableName = "stations";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public StationRepository() throws SQLException {
    }

    public void addStation(Station station) {
        final String query = "INSERT INTO " + tableName + " (id, start_station, end_station, start_date) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, station.getId());
            stmt.setString(2, station.getStartStation());
            stmt.setString(3, station.getEndStation());
            stmt.setDate(4, Date.valueOf(station.getStartDate()));
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding new station : " + e.getMessage());
        }
    }

    public Station getStationById(UUID id) {
        final String query = "SELECT * FROM "+ tableName + " WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            try(ResultSet rst = stmt.executeQuery()) {
                if(rst.next()) {
                    return mapToStation(rst);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting Station By ID : " + e.getMessage());
        }
        return null;
    }

    public List<Station> getAllStations() {
        final String query = "SELECT * FROM " + tableName ;
        final List<Station> stations = new ArrayList<>();
        try(Statement stmt = connection.createStatement()) {
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {
               stations.add(mapToStation(rst));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting all Stations : " + e.getMessage());
        }
        return stations;
    }

    public void updateStation(Station station) {
        final String query = "UPDATE "+ tableName + " SET start_station = ?, end_station = ?, start_Date = ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, station.getStartStation());
            stmt.setString(2, station.getEndStation());
            stmt.setDate(3, Date.valueOf(station.getStartDate()));
            stmt.setObject(4, station.getId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating Station : " + e.getMessage());
        }
    }

    public void deleteStation(UUID id) {
        final String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting Station : " + e.getMessage());
        }
    }

    private Station mapToStation(ResultSet rst) throws SQLException {
        UUID id = (UUID) rst.getObject("id");
        String startStation = rst.getString("start_station");
        String endStation = rst.getString("end_station");
        LocalDate startDate = rst.getDate("start_date").toLocalDate();

        return new Station(id, startStation, endStation, startDate);

    }
}
