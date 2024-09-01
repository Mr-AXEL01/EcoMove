package net.axel.models.entities;

import net.axel.models.enums.PartnerStatus;
import net.axel.models.enums.TransportType;

import java.util.Date;
import java.util.UUID;

public class Partner {
    private UUID id;
    private String companyName;
    private String commercialContact;
    private TransportType transportType;
    private String geographicalArea;
    private String specialConditions;
    private PartnerStatus partnerStatus;
    private Date creationDate;

    public Partner(UUID id, String companyName, String commercialContact, TransportType transportType, String geographicalArea, String specialConditions, PartnerStatus partnerStatus) {
        this.id = id;
        this.companyName = companyName;
        this.commercialContact = commercialContact;
        this.transportType = transportType;
        this.geographicalArea = geographicalArea;
        this.specialConditions = specialConditions;
        this.partnerStatus = partnerStatus;
    }

}
