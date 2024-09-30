package net.axel.services.implementations;

import net.axel.models.entities.Partner;
import net.axel.repositories.interfaces.IPartnerRepository;
import net.axel.services.interfaces.IPartnerService;

import java.util.List;
import java.util.UUID;

public class PartnerService implements IPartnerService {

    private final IPartnerRepository partnerRepository;

    public PartnerService(IPartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public void addPartner(Partner partner) {
        partnerRepository.addPartner(partner);
    }

    @Override
    public Partner getPartnerById(UUID id) {
        return partnerRepository.getPartnerById(id);
    }

    @Override
    public List<Partner> getAllPartners() {
        return partnerRepository.getAllPartners();
    }

    @Override
    public void updatePartner(Partner partner) {
        partnerRepository.updatePartner(partner);
    }

    @Override
    public void deletePartner(UUID id) {
        partnerRepository.deletePartner(id);
    }
}
