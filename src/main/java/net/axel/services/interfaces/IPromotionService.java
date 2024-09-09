package net.axel.services.interfaces;

import net.axel.models.dto.PromotionDto;
import net.axel.models.entities.Promotion;

import java.util.List;
import java.util.UUID;

public interface IPromotionService {
    public void addPromotion(PromotionDto dto);
    public Promotion getPromotionById(UUID id);
    public List<Promotion> getAllPromotions();
    public void updatePromotion(UUID promotionId, PromotionDto dto);
    public void deletePromotion(UUID id);
}
