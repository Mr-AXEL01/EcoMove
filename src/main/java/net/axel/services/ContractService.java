package net.axel.services;

import net.axel.models.entities.Contract;
import net.axel.repositories.ContractRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService() throws SQLException {
        this.contractRepository = new ContractRepository();
    }
    public void addContract(Contract contract) {
        try {
            contractRepository.addContract(contract);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding contract", e);
        }
    }
    public Contract getContractById(UUID id) {
        try {
            return contractRepository.getContractById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving contract by id", e);
        }
    }
    public List<Contract> getAllContract() {
        try {
            return contractRepository.getAllContracts();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all contracts", e);
        }
    }
    public void updateContract(Contract contract) {
        try {
            contractRepository.updateContract(contract);
        } catch (SQLException e) {
            throw new RuntimeException("Eroor updating contract", e);
        }
    }
    public void deleteContract(UUID id) {
        try {
            contractRepository.deleteContract(id);
        } catch ( SQLException e) {
            throw new RuntimeException("Error deleting contract", e);
        }
    }

}
