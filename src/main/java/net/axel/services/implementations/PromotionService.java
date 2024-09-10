package net.axel.services.implementations;

import net.axel.models.dto.PromotionDto;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Promotion;
import net.axel.repositories.implementations.PromotionRepository;
import net.axel.repositories.interfaces.IPromotionRepository;
import net.axel.services.interfaces.IContractService;
import net.axel.services.interfaces.IPromotionService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PromotionService implements IPromotionService {

    private final IPromotionRepository promotionRepository;
    private final IContractService contractService;

    public PromotionService(PromotionRepository promotionRepository ,IContractService contractService) throws SQLException {
        this.promotionRepository = promotionRepository;
        this.contractService = contractService;
    }

    @Override
    public void addPromotion(PromotionDto dto) {
        final Contract contract = contractService.getContractById(dto.contractId());
        final Promotion promotion = new Promotion(UUID.randomUUID(), dto.offerName(), dto.description(), dto.startDate(), dto.endDate(), dto.reductionType(), dto.reductionValue(), dto.conditions(), dto.offerStatus(), contract);
        promotionRepository.addPromotion(promotion);
    }

    @Override
    public Promotion getPromotionById(UUID id) {
        return promotionRepository.getPromotionById(id);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.getAllPromotions();
    }

    @Override
    public void updatePromotion(UUID promotionId, PromotionDto dto) {
        final Contract contract = contractService.getContractById(dto.contractId());
        final Promotion promotion = new Promotion(promotionId, dto.offerName(), dto.description(), dto.startDate(), dto.endDate(), dto.reductionType(), dto.reductionValue(), dto.conditions(), dto.offerStatus(), contract);
        promotionRepository.updatePromotion(promotion);
    }

    @Override
    public void deletePromotion(UUID id) {
        promotionRepository.deletePromotion(id);
    }
}
