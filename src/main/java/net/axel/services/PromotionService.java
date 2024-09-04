package net.axel.services;

import net.axel.models.dto.PromotionDto;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Promotion;
import net.axel.repositories.PromotionRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final ContractService contractService;

    public PromotionService() throws SQLException {
        this.promotionRepository = new PromotionRepository();
        this.contractService = new ContractService();
    }

    public void addPromotion(PromotionDto dto) throws SQLException {
        try {
            final Contract contract = contractService.getContractById(dto.contractId());
            final Promotion promotion = new Promotion(UUID.randomUUID(), dto.offerName(), dto.description(), dto.startDate(), dto.endDate(), dto.reductionType(), dto.reductionValue(), dto.conditions(), dto.offerStatus(), contract);
            promotionRepository.addPromotion(promotion);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding promotion", e);
        }
    }

    public Promotion getPromotionById(UUID id) throws SQLException {
        try {
            return promotionRepository.getPromotionById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving promotion by ID", e);
        }
    }

    public List<Promotion> getAllPromotions() throws SQLException {
        try {
            return promotionRepository.getAllPromotions();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all the promotions.", e);
        }
    }

    public void updatePromotion(UUID promotionId, PromotionDto dto) throws SQLException {
        try {
            final Contract contract = contractService.getContractById(dto.contractId());
            final Promotion promotion = new Promotion(promotionId, dto.offerName(), dto.description(), dto.startDate(), dto.endDate(), dto.reductionType(), dto.reductionValue(), dto.conditions(), dto.offerStatus(), contract);
            promotionRepository.updatePromotion(promotion);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating promotion", e);
        }
    }

    public void deletePromotion(UUID id) throws SQLException {
        try {
            promotionRepository.deletePromotion(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting promotion", e);
        }
    }
}
