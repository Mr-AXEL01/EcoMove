package net.axel.models.entities;

import net.axel.models.enums.ContractStatus;

import java.util.Date;
import java.util.UUID;

public class Contract {
    private UUID id;
    private Date startDate;
    private Date endDate;
    private float specialTariff;
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
}
