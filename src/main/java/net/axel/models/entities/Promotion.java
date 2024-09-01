package net.axel.models.entities;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.UUID;

public class Promotion {
    private UUID id;
    private String offerName;
    private String description;
    private Date startDate;
    private Date endDate;
    private ReductionType reductionType;
    private float reductionValue;
    private String conditions;
    private OfferStatus offerStatus;

    public Promotion(UUID id, String offerName, String description, Date startDate, Date endDate, ReductionType reductionType, float reductionValue, String conditions, OfferStatus offerStatus) {
        this.id = id;
        this.offerName = offerName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reductionType = reductionType;
        this.reductionValue = reductionValue;
        this.conditions = conditions;
        this.offerStatus = offerStatus;
    }
}
