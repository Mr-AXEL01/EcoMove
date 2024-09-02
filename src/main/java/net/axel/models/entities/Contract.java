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
    private Partner partner;

    public Contract(UUID id, Date startDate, Date endDate, double specialTariff, String conditionsAccord, Boolean renewable, ContractStatus contractStatus, Partner partner) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialTariff = specialTariff;
        this.conditionsAccord = conditionsAccord;
        this.renewable = renewable;
        this.contractStatus = contractStatus;
        this.partner = partner;
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

    public double getSpecialTariff() {
        return specialTariff;
    }

    public void setSpecialTariff(double specialTariff) {
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

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
