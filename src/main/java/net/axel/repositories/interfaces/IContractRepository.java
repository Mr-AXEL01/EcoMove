package net.axel.repositories.interfaces;

import net.axel.models.entities.Contract;
import net.axel.models.entities.Partner;

import java.util.List;
import java.util.UUID;

public interface IContractRepository {
    public void addContract(Contract contract);
    public Contract getContractById(UUID id);
    public List<Contract> getAllContracts();
    public void updateContract(Contract contract);
    public void deleteContract(UUID id);
}
