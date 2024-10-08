package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.*;
import net.axel.models.enums.ContractStatus;
import net.axel.models.enums.PartnerStatus;
import net.axel.models.enums.TicketStatus;
import net.axel.models.enums.TransportType;
import net.axel.repositories.interfaces.ITicketRipository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketRepository implements ITicketRipository {

    private final String tableName = "tickets";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public TicketRepository() throws SQLException{
    }

    @Override
    public void addTicket(Ticket ticket) {
        String query = "INSERT INTO " + tableName + " (id, transport_type, purchase_price, resell_price, sale_date, departure_time, arrival_time, ticket_status, contract_id, journey_id) " +
                "VALUES(?, ?::TransportType, ?, ?, ?, ?, ?, ?::TicketStatus, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setObject(1, ticket.getId());
            stmt.setString(2, ticket.getTransportType().name());
            stmt.setDouble(3, ticket.getPurchasePrice());
            stmt.setDouble(4, ticket.getResellPrice());
            stmt.setDate(5, new Date(ticket.getSaleDate().getTime()));
            stmt.setTimestamp(6, Timestamp.valueOf(ticket.getDepartureTime()));
            stmt.setTimestamp(7, Timestamp.valueOf(ticket.getArrivalTime()));
            stmt.setString(8, ticket.getTicketStatus().name());
            stmt.setObject(9, ticket.getContract().getId());
            stmt.setObject(10, ticket.getJourney().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding a ticket" + e.getMessage());
        }
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT t.*, j.*, start_s.*, end_s.*, c.*, p.* FROM " + tableName + " t " +
                "JOIN journeys j ON t.journey_id = j.id " +
                "JOIN stations start_s ON j.start_station = start_s.id " +
                "JOIN stations end_s ON j.end_station = end_s.id " +
                "JOIN contracts c ON t.contract_id = c.id " +
                "JOIN partners p ON c.partner_id = p.id";
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()) {
                tickets.add(mapToTicket(resultSet));
            }
            return tickets;
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting all tickets : " + e.getMessage());
            return null;
        }
    }


    @Override
    public List<Ticket> getTicketsByPartner(UUID id) {
        List<Ticket> partnerTickets = new ArrayList<>();
        String query = "SELECT t.*, j.* ,start_s.*, end_s.* c.*, p.* FROM " + tableName + " t " +
                "JOIN journeys j ON t.journey_id = j.id" +
                "JOIN  stations start_s ON j.start_station = start_s.id" +
                "JOIN  stations end_s ON j.end_station = end_s.id" +
                "JOIN contracts c ON t.contract_id = c.id " +
                "JOIN partners p ON c.partner_id = p.id WHERE p.id= ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                partnerTickets.add(mapToTicket(resultSet));
            }
            return partnerTickets;
        } catch(SQLException e ) {
            e.printStackTrace();
            System.err.println("Error get ticket by partner : " + e.getMessage());
            return null;
        }
    }

    private Ticket mapToTicket(ResultSet resultSet) throws SQLException {
        UUID ticketId = UUID.fromString(resultSet.getString("id"));
        TransportType transportType  = TransportType.valueOf(resultSet.getString("transport_type"));
        Double purchasePrice = resultSet.getDouble("purchase_price");
        Double resellPrice = resultSet.getDouble("resell_price");
        Date saleDate = resultSet.getDate("sale_date");
        LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
        LocalDateTime arrivalTime = resultSet.getTimestamp("arrival_time").toLocalDateTime();
        TicketStatus ticketStatus = TicketStatus.valueOf(resultSet.getString("ticket_status"));

        UUID contractId = UUID.fromString(resultSet.getString("contract_id"));
        Date startDate = resultSet.getDate("start_date");
        Date endDate = resultSet.getDate("end_date");
        double specialTariff = resultSet.getDouble("special_tariff");
        String conditionsAccord = resultSet.getString("conditions_accord");
        Boolean renewable = resultSet.getBoolean("renewable");
        ContractStatus contractStatus = ContractStatus.valueOf(resultSet.getString("contract_status"));

        UUID partnerId = UUID.fromString(resultSet.getString("partner_id"));
        String companyName = resultSet.getString("company_name");
        String comercialContact = resultSet.getString("comercial_contact");
        String geographicalArea = resultSet.getString("geographical_area");
        String specialConditions = resultSet.getString("special_conditions");
        TransportType partnerTransportType = TransportType.valueOf(resultSet.getString("transport_type"));
        PartnerStatus partnerStatus = PartnerStatus.valueOf(resultSet.getString("partner_status"));
        Date creationDate = resultSet.getDate("creation_date");

        UUID journeyId = UUID.fromString(resultSet.getString("journey_id"));

        UUID startStationId = UUID.fromString(resultSet.getString("start_station"));
        String startStationName = resultSet.getString("name");
        String startStationLocation = resultSet.getString("location");

        UUID endStationId = UUID.fromString(resultSet.getString("end_station"));
        String endStationName = resultSet.getString("name");
        String endStationLocation = resultSet.getString("location");

        Station startStation = new Station(startStationId, startStationName, startStationLocation);
        Station endStation = new Station(endStationId, endStationName, endStationLocation);

        Journey journey = new Journey(journeyId, startStation, endStation);

        Partner partner = new Partner(partnerId, companyName, comercialContact, partnerTransportType, geographicalArea, specialConditions, partnerStatus, creationDate);

        Contract contract = new Contract(contractId, startDate, endDate, specialTariff, conditionsAccord, renewable, contractStatus, partner);

        return new Ticket(ticketId, transportType, purchasePrice, resellPrice, saleDate, departureTime, arrivalTime, ticketStatus, contract, journey);
    }
}