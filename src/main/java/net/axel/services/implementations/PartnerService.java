package net.axel.services.implementations;

import net.axel.models.entities.Partner;
import net.axel.repositories.PartnerRepository;
import net.axel.services.interfaces.IPartnerService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PartnerService implements IPartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService() throws SQLException {
        this.partnerRepository = new PartnerRepository();
    }

    @Override
    public void addPartner(Partner partner) {
        try {
            partnerRepository.addPartner(partner);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding partner", e);
        }
    }

    @Override
    public Partner getPartnerById(UUID id) {
        try {
            return partnerRepository.getPartnerById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving partner by id", e);
        }
    }

    @Override
    public List<Partner> getAllPartners() {
        try {
            return partnerRepository.getAllPartners();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all partners", e);
        }
    }

    @Override
    public void updatePartner(Partner partner) {
        try {
            partnerRepository.updatePartner(partner);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating partner", e);
        }
    }

    @Override
    public void deletePartner(UUID id) {
        try {
            partnerRepository.deletePartner(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting partner", e);
        }
    }
}
