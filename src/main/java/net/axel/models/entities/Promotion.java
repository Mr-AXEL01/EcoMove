package net.axel.models.entities;

import net.axel.models.enums.OfferStatus;
import net.axel.models.enums.ReductionType;

import java.util.Date;
import java.util.UUID;

public class Promotion {
    private UUID id;
    private String offerName;
    private String description;
    private Date startDate;
    private Date endDate;
    private ReductionType reductionType;
    private Double reductionValue;
    private String conditions;
    private OfferStatus offerStatus;
    private Contract contract;

    public Promotion(UUID id, String offerName, String description, Date startDate, Date endDate, ReductionType reductionType, Double reductionValue, String conditions, OfferStatus offerStatus, Contract contract) {
        this.id = id;
        this.offerName = offerName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reductionType = reductionType;
        this.reductionValue = reductionValue;
        this.conditions = conditions;
        this.offerStatus = offerStatus;
        this.contract = contract;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ReductionType getReductionType() {
        return reductionType;
    }

    public void setReductionType(ReductionType reductionType) {
        this.reductionType = reductionType;
    }

    public Double getReductionValue() {
        return reductionValue;
    }

    public void setReductionValue(Double reductionValue) {
        this.reductionValue = reductionValue;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

}
