package net.axel.repositories.interfaces;

import net.axel.models.entities.Promotion;

import java.util.List;
import java.util.UUID;

public interface IPromotionRepository {
    public void addPromotion(Promotion promotion);
    public Promotion getPromotionById(UUID id);
    public List<Promotion> getAllPromotions();
    public void updatePromotion(Promotion promotion);
    public void deletePromotion(UUID id);
}
