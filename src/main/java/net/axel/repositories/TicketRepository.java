package net.axel.repositories;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {

    private final String tableName = "ticket";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public TicketRepository() throws SQLException{
    }

    public void addTicket(Ticket ticket) throws SQLException{
        String query = "INSERT INTO " + tableName + " id, transport_type, purchase_price, resell_price, sale_date, ticket_status, contract_id " +
                "VALUES(?, ?::TransportType, ?, ?, ?, ?::TicketStatus, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setObject(1, ticket.getId());
            stmt.setString(2, ticket.getTransportType().name());
            stmt.setDouble(3, ticket.getPurchasePrice());
            stmt.setDouble(4, ticket.getResellPrice());
            stmt.setDate(5, new Date(ticket.getSaleDate().getTime()));
            stmt.setString(6, ticket.getTicketStatus().name());
            stmt.setObject(7, ticket.getContract().getId());
            stmt.executeUpdate();
        }
    }
    public List<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
//        to do
    }
    public List<Ticket> getTicketsByPartner() throws SQLException {
//        to do
    }
}
