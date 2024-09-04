package net.axel.repositories;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Partner;
import net.axel.models.entities.Promotion;
import net.axel.models.entities.Ticket;
import net.axel.models.enums.*;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class PromotionRepository {

    private final String tableName = "promotion";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public PromotionRepository() throws SQLException {

    }

    public void addPromotion(Promotion promotion) throws SQLException {
        String query = "INSERT INTO "+ tableName + " (id, offer_name, descriprion, start_date, end_date, reduction_type, reduction_value, conditions, offer_status, contract_id)" +
                "VALUES(?, ?, ?, ?, ?, ?::RductionType, ?, ?, ?::OfferStatus, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, promotion.getId());
            stmt.setString(2, promotion.getOfferName());
            stmt.setDate(3, new Date(promotion.getStartDate().getTime()));
            stmt.setDate(4, new Date(promotion.getEndDate().getTime()));
            stmt.setString(5, promotion.getReductionType().name());
            stmt.setDouble(6, promotion.getReductionValue());
            stmt.setString(7, promotion.getConditions());
            stmt.setString(8, promotion.getOfferStatus().name());
            stmt.setObject(9, promotion.getContract().getId());
        }
    }

    public List<Promotion> getAllPromotions() throws SQLException {

    }

    public Promotion getPromotionById(UUID id) throws SQLException {
//         to do
    }

    public void updatePromotion(Promotion promotion) throws SQLException {
//        to do
    }

    public void deletePromotion(UUID id) throws SQLException {
//        to do
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

        return new Ticket(promotionId, offerName, descreption, startDate, endDate, reductionType, reductionValue, conditions, offerStatus, contract);
    }

}
