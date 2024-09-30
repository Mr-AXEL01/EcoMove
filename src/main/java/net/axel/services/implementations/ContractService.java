package net.axel.services.implementations;

import net.axel.models.dto.ContractDto;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Partner;
import net.axel.repositories.implementations.ContractRepository;
import net.axel.repositories.interfaces.IContractRepository;
import net.axel.services.interfaces.IContractService;
import net.axel.services.interfaces.IPartnerService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ContractService implements IContractService {

    private final IContractRepository contractRepository;
    private final IPartnerService partnerService;

    public ContractService(ContractRepository contractRepository ,PartnerService partnerService) {
        this.contractRepository = contractRepository;
        this.partnerService = partnerService;
    }

    @Override
    public void addContract(ContractDto dto) {
        final Date StartDate = dto.startDate();
        final Date endDate = dto.endDate();
        if(checkDates(StartDate, endDate)) {
            final Partner partner = partnerService.getPartnerById(dto.partnerId());
            final Contract contract = new Contract(UUID.randomUUID(), dto.startDate(), dto.endDate(), dto.specialTariff(), dto.conditionAccord(), dto.renewable(), dto.status(), partner);
            contractRepository.addContract(contract);
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
        return !startDate.after(endDate);
    }

}
