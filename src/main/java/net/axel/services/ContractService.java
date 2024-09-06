package net.axel.services;

import net.axel.models.dto.ContractDto;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Partner;
import net.axel.repositories.ContractRepository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ContractService {

    private final ContractRepository contractRepository;
    private final PartnerService partnerService;

    public ContractService() throws SQLException {
        this.contractRepository = new ContractRepository();
        this.partnerService = new PartnerService();
    }

    public boolean addContract(ContractDto dto) {
        final Date StartDate = dto.startDate();
        final Date endDate = dto.endDate();
        try {
            if(checkDates(StartDate, endDate)) {
                final Partner partner = partnerService.getPartnerById(dto.partnerId());
                final Contract contract = new Contract(UUID.randomUUID(), dto.startDate(), dto.endDate(), dto.specialTariff(), dto.conditionAccord(), dto.renewable(), dto.status(), partner);
                contractRepository.addContract(contract);
                return true;
            } else {
                return false;
            }
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

    public List<Contract> getAllContracts() {
        try {
            return contractRepository.getAllContracts();
        } catch (SQLException e) {
//            e.printStackTrace();
            throw new RuntimeException("Error retrieving all contracts", e);
        }
    }

    public void updateContract(UUID contractId,ContractDto updateDto) {
        try {
            final Partner partner = partnerService.getPartnerById(updateDto.partnerId());
            final Contract contract = new Contract(contractId, updateDto.startDate(), updateDto.endDate(), updateDto.specialTariff(), updateDto.conditionAccord(), updateDto.renewable(), updateDto.status(), partner);
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

    private boolean checkDates(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            System.out.println("Start date can't be after end date, try again with logic dates.");
        }
        return false;
    }

}
