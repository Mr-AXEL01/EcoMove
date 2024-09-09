package net.axel.services.implementations;

import net.axel.models.dto.ContractDto;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Partner;
import net.axel.repositories.implementations.ContractRepository;
import net.axel.services.interfaces.IContractService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ContractService implements IContractService {

    private final ContractRepository contractRepository;
    private final PartnerService partnerService;

    public ContractService() throws SQLException {
        this.contractRepository = new ContractRepository();
        this.partnerService = new PartnerService();
    }

    @Override
    public boolean addContract(ContractDto dto) {
        final Date StartDate = dto.startDate();
        final Date endDate = dto.endDate();
        if(checkDates(StartDate, endDate)) {
            final Partner partner = partnerService.getPartnerById(dto.partnerId());
            final Contract contract = new Contract(UUID.randomUUID(), dto.startDate(), dto.endDate(), dto.specialTariff(), dto.conditionAccord(), dto.renewable(), dto.status(), partner);
            contractRepository.addContract(contract);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Contract getContractById(UUID id) {
        return contractRepository.getContractById(id);

    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.getAllContracts();
    }

    @Override
    public void updateContract(UUID contractId,ContractDto updateDto) {
        final Partner partner = partnerService.getPartnerById(updateDto.partnerId());
        final Contract contract = new Contract(contractId, updateDto.startDate(), updateDto.endDate(), updateDto.specialTariff(), updateDto.conditionAccord(), updateDto.renewable(), updateDto.status(), partner);
        contractRepository.updateContract(contract);
    }

    @Override
    public void deleteContract(UUID id) {
        contractRepository.deleteContract(id);

    }

    private boolean checkDates(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            System.out.println("Start date can't be after end date, try again with logic dates.");
        }
        return false;
    }

}
