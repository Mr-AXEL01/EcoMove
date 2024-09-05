package net.axel.repositories;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Partner;
import net.axel.models.entities.Promotion;
import net.axel.models.entities.Ticket;
import net.axel.models.enums.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PromotionRepository {

    private final String tableName = "promotion";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public PromotionRepository() throws SQLException {

    }

    public void addPromotion(Promotion promotion) throws SQLException {
        String query = "INSERT INTO "+ tableName + " (id, offer_name, description, start_date, end_date, reduction_type, reduction_value, conditions, offer_status, contract_id)" +
                "VALUES(?, ?, ?, ?, ?, ?::RductionType, ?, ?, ?::OfferStatus, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, promotion.getId());
            stmt.setString(2, promotion.getOfferName());
            stmt.setString(3, promotion.getDescription());
            stmt.setDate(4, new Date(promotion.getStartDate().getTime()));
            stmt.setDate(5, new Date(promotion.getEndDate().getTime()));
            stmt.setString(6, promotion.getReductionType().name());
            stmt.setDouble(7, promotion.getReductionValue());
            stmt.setString(8, promotion.getConditions());
            stmt.setString(9, promotion.getOfferStatus().name());
            stmt.setObject(10, promotion.getContract().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding promotion"+ e.getMessage());
        }
    }

    public Promotion getPromotionById(UUID id) throws SQLException {
         String query = "SELECT * FROM " + tableName + " WHERE id = ?";
         try (PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setObject(1, id);
             try(ResultSet resultSet = stmt.executeQuery()) {
                 if(resultSet.next()) {
                     return mapToPromotion(resultSet);
                 }else {
                     return null;
                 }
             }
         }
    }

    public List<Promotion> getAllPromotions() throws SQLException {
        List<Promotion> promotions = new ArrayList<>();
        String query = "SELECT o.*, c.*, p.* FROM " + tableName + " o " +
                "JOIN contract c ON o.contract_id = c.id " +
                "JOIN partner p ON c.partner_id = p.id";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                promotions.add(mapToPromotion(resultSet));
            }
        }
        return promotions;
    }

    public void updatePromotion(Promotion promotion) throws SQLException {
        String query = "UPDATE " + tableName + " SET offer_name = ?, description = ?, start_date = ?, end_date = ?, reduction_type = ?::ReductionType, "+
                "reduction_value = ?, conditions = ?, offer_status = ?::OfferStatus, contract_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, promotion.getOfferName());
            stmt.setString(2, promotion.getDescription());
            stmt.setDate(3, new Date(promotion.getStartDate().getTime()));
            stmt.setDate(4, new Date(promotion.getEndDate().getTime()));
            stmt.setString(5, promotion.getReductionType().name());
            stmt.setDouble(6, promotion.getReductionValue());
            stmt.setString(7, promotion.getConditions());
            stmt.setString(8, promotion.getOfferStatus().name());
            stmt.setObject(9, promotion.getContract().getId());
            stmt.executeUpdate();
        }
    }

    public void deletePromotion(UUID id) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    private Promotion mapToPromotion(ResultSet resultSet) throws SQLException {
        UUID promotionId = UUID.fromString(resultSet.getString("id"));
        String offerName  = resultSet.getString("offer_name");
        String descreption = resultSet.getString("description");
        Date startDate = resultSet.getDate("start_date");
        Date endDate = resultSet.getDate("end_date");
        ReductionType reductionType = ReductionType.valueOf(resultSet.getString("reduction_type"));
        Double reductionValue = resultSet.getDouble("reduction_value");
        String conditions = resultSet.getString("conditions");
        OfferStatus offerStatus = OfferStatus.valueOf(resultSet.getString("offer_status"));

        UUID contractId = UUID.fromString(resultSet.getString("contract_id"));
        Date contractStartDate = resultSet.getDate("start_date");
        Date contractEndDate = resultSet.getDate("end_date");
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

        Partner partner = new Partner(partnerId, companyName, comercialContact, partnerTransportType, geographicalArea, specialConditions, partnerStatus, creationDate);

        Contract contract = new Contract(contractId, contractStartDate, contractEndDate, specialTariff, conditionsAccord, renewable, contractStatus, partner);

        return new Promotion(promotionId, offerName, descreption, startDate, endDate, reductionType, reductionValue, conditions, offerStatus, contract);
    }

}
