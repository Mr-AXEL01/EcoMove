package net.axel.services.interfaces;

import net.axel.models.dto.ContractDto;
import net.axel.models.entities.Contract;

import java.util.List;
import java.util.UUID;

public interface IContractService {
    public boolean addContract(ContractDto dto);
    public Contract getContractById(UUID id);
    public List<Contract> getAllContracts();
    public void updateContract(UUID contractId,ContractDto updateDto);
    public void deleteContract(UUID id);
}
