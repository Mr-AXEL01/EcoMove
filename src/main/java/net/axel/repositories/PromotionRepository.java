package net.axel.repositories;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Promotion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
//        to do
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

    private Promotion mapToPromotion() throws SQLException {
//        to do
    }

}
