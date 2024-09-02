package net.axel.models.entities;

import net.axel.models.enums.PartnerStatus;
import net.axel.models.enums.TransportType;

import java.util.Date;
import java.util.UUID;

public class Partner {
    private UUID id;
    private String companyName;
    private String comercialContact;
    private TransportType transportType;
    private String geographicalArea;
    private String specialConditions;
    private PartnerStatus partnerStatus;
    private Date creationDate;

    public Partner(UUID id, String companyName, String comercialContact, TransportType transportType, String geographicalArea, String specialConditions, PartnerStatus partnerStatus) {
        this.id = id;
        this.companyName = companyName;
        this.comercialContact = comercialContact;
        this.transportType = transportType;
        this.geographicalArea = geographicalArea;
        this.specialConditions = specialConditions;
        this.partnerStatus = partnerStatus;

    }

    public Partner(UUID id, String companyName, String comercialContact, TransportType transportType, String geographicalArea, String specialConditions, PartnerStatus partnerStatus, Date creationDate) {
        this.id = id;
        this.companyName = companyName;
        this.comercialContact = comercialContact;
        this.transportType = transportType;
        this.geographicalArea = geographicalArea;
        this.specialConditions = specialConditions;
        this.partnerStatus = partnerStatus;
        this.creationDate = creationDate;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getComercialContact() {
        return comercialContact;
    }

    public void setComercialContact(String comercialContact) {
        this.comercialContact = comercialContact;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getGeographicalArea() {
        return geographicalArea;
    }

    public void setGeographicalArea(String geographicalArea) {
        this.geographicalArea = geographicalArea;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public PartnerStatus getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(PartnerStatus partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
