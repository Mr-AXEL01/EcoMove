package net.axel.repositories.interfaces;

import net.axel.models.entities.Partner;

import java.util.List;
import java.util.UUID;

public interface IPartnerRepository {
    public void addPartner(Partner partner);
    public Partner getPartnerById(UUID id);
    public List<Partner> getAllPartners();
    public void updatePartner(Partner partner);
    public void deletePartner(UUID id);
}
