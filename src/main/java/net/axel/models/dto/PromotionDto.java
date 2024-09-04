package net.axel.models.dto;

import net.axel.models.enums.OfferStatus;
import net.axel.models.enums.ReductionType;

import java.sql.Date;
import java.util.UUID;

public record PromotionDto (
        String offerName,
        String description,
        Date startDate,
        Date endDate,
        ReductionType reductionType,
        Double reductionValue,
        String conditions,
        OfferStatus offerStatus,
        UUID contractId
){
}
