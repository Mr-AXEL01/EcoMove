package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Client;
import net.axel.repositories.interfaces.IClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements IClientRepository {

    private final String tableName = "clients";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public ClientRepository() throws SQLException {

    }

    @Override
    public void addClient(Client client) {
        final String query = "INSERT INTO " + tableName + " (first_name, last_name, email, phone) " +
                "VALUES (?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getMail());
            stmt.setString(4, client.getPhone());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding new client : " + e.getMessage());
        }
    }

    @Override
    public Client getClientByEmail(String email) {
        final String query = "SELECT * FROM " + tableName + " WHERE email = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try(ResultSet rst = stmt.executeQuery()) {
                if (rst.next()) {
                    return mapToClient(rst);
                } else {
                    return null;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error get client By email : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Client> getAllClients() {
        final String query = "SELECT * FROM " + tableName;
        final List<Client> clients = new ArrayList<>();
        try(Statement stmt = connection.createStatement()) {
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {
                 clients.add(mapToClient(rst));
            }
            return clients;
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting all clients : " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateClient(Client client) {
        final String query = "UPDATE " + tableName + " SET first_name = ?, last_name = ?, phone = ? WHERE email = ?" ;
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getMail());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating Client : " + e.getMessage());
        }
    }

    @Override
    public void deleteClient(String email) {
        final String query = "DELETE FROM " + tableName + " WHERE email = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting Client : " +e.getMessage());
        }
    }

    private Client mapToClient(ResultSet rst) throws SQLException {
        String firstName = rst.getString("first_name");
        String lastName = rst.getString("last_name");
        String email = rst.getString("email");
        String phone = rst.getString("phone");

        return new Client(firstName, lastName, email, phone);
    }

}
