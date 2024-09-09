package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Partner;
import net.axel.models.enums.PartnerStatus;
import net.axel.models.enums.TransportType;
import net.axel.repositories.interfaces.IPartnerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PartnerRepository implements IPartnerRepository {
    private final String tableName = "partners";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public PartnerRepository() throws SQLException {
    }

    @Override
    public void addPartner(Partner partner){
        String query = "INSERT INTO "+tableName+ " (id, company_name, comercial_contact, transport_type, geographical_area, special_conditions, partner_status) " +
                "VALUES (?, ?, ?, ?::TransportType, ?, ?, ?::PartnerStatus)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, partner.getId());
            statement.setString(2, partner.getCompanyName());
            statement.setString(3, partner.getComercialContact());
            statement.setString(4, partner.getTransportType().name());
            statement.setString(5, partner.getGeographicalArea());
            statement.setString(6, partner.getSpecialConditions());
            statement.setString(7, partner.getPartnerStatus().name());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding partner:" + e.getMessage());
        }
    }

    @Override
    public Partner getPartnerById(UUID id) {
        String query = "SELECT * FROM "+tableName+ " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPartner(resultSet);
                } else {
                    return null;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error find partner by ID:" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Partner> getAllPartners(){
        String query = "SELECT * FROM " +tableName;
        List<Partner> partners = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                partners.add(mapResultSetToPartner(resultSet));
            }
            return partners;
        }catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error find partner by ID:" + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void updatePartner(Partner partner) {
        String query = "UPDATE "+tableName+" SET company_name = ?, comercial_contact = ?, transport_type = ?::TransportType, geographical_area = ?, special_conditions = ?, partner_status = ?::PartnerStatus WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, partner.getCompanyName());
            statement.setString(2, partner.getComercialContact());
            statement.setString(3, partner.getTransportType().name());
            statement.setString(4, partner.getGeographicalArea());
            statement.setString(5, partner.getSpecialConditions());
            statement.setString(6, partner.getPartnerStatus().name());
            statement.setObject(7, partner.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating partner :" + e.getMessage());
        }
    }

    @Override
    public void deletePartner(UUID id) {
        String query = "DELETE FROM "+tableName+" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting partner : " + e.getMessage());
        }
    }

    private Partner mapResultSetToPartner(ResultSet resultSet) throws SQLException {
        UUID id = (UUID) resultSet.getObject("id");
        String companyName = resultSet.getString("company_name");
        String comercialContact = resultSet.getString("comercial_contact");
        TransportType transportType = TransportType.valueOf(resultSet.getString("transport_type"));
        String geographicalArea = resultSet.getString("geographical_area");
        String specialConditions = resultSet.getString("special_conditions");
        PartnerStatus partnerStatus = PartnerStatus.valueOf(resultSet.getString("partner_status"));
        Date creationDate = resultSet.getDate("creation_date");

        return new Partner(id, companyName, comercialContact, transportType, geographicalArea, specialConditions, partnerStatus, creationDate);

    }
}
