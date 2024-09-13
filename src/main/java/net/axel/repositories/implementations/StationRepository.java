package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Station;
import net.axel.repositories.interfaces.IStationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StationRepository implements IStationRepository {

    private final String tableName = "stations";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public StationRepository() throws SQLException {
    }

    @Override
    public void addStation(Station station) {
        final String query = "INSERT INTO " + tableName + " (id, name, location) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, station.getId());
            stmt.setString(2, station.getName());
            stmt.setString(3, station.getLocation());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding new station : " + e.getMessage());
        }
    }

    @Override
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

    @Override
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

    @Override
    public void updateStation(Station station) {
        final String query = "UPDATE "+ tableName + " SET name = ? , location = ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, station.getName());
            stmt.setString(2, station.getLocation());
            stmt.setObject(3, station.getId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating Station : " + e.getMessage());
        }
    }

    @Override
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
        String name = rst.getString("name");
        String location = rst.getString("location");

        return new Station(id, name, location);

    }
}
