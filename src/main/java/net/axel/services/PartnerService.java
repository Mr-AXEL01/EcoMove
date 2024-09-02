package net.axel.services;

import net.axel.models.entities.Partner;
import net.axel.repositories.PartnerRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService() throws SQLException {
        this.partnerRepository = new PartnerRepository();
    }

    public void addPartner(Partner partner) {
        try {
            partnerRepository.addPartner(partner);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding partner", e);
        }
    }

    public Partner getPartnerById(UUID id) {
        try {
            return partnerRepository.getPartnerById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving partner by id", e);
        }
    }

    public List<Partner> getAllPartners() {
        try {
            return partnerRepository.getAllPartners();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all partners", e);
        }
    }

    public void updatePartner(Partner partner) {
        try {
            partnerRepository.updatePartner(partner);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating partner", e);
        }
    }

    public void deletePartner(UUID id) {
        try {
            partnerRepository.deletePartner(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting partner", e);
        }
    }
}
