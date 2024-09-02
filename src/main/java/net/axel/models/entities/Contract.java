package net.axel.models.entities;

import net.axel.models.enums.ContractStatus;

import java.util.Date;
import java.util.UUID;

public class Contract {
    private UUID id;
    private Date startDate;
    private Date endDate;
    private double specialTariff;
    private String conditionsAccord;
    private Boolean renewable;
    private ContractStatus contractStatus;

    public Contract(UUID id, Date startDate, Date endDate, float specialTariff, String conditionsAccord, Boolean renewable, ContractStatus contractStatus) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialTariff = specialTariff;
        this.conditionsAccord = conditionsAccord;
        this.renewable = renewable;
        this.contractStatus = contractStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public float getSpecialTariff() {
        return specialTariff;
    }

    public void setSpecialTariff(float specialTariff) {
        this.specialTariff = specialTariff;
    }

    public String getConditionsAccord() {
        return conditionsAccord;
    }

    public void setConditionsAccord(String conditionsAccord) {
        this.conditionsAccord = conditionsAccord;
    }

    public Boolean getRenewable() {
        return renewable;
    }

    public void setRenewable(Boolean renewable) {
        this.renewable = renewable;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }
}
