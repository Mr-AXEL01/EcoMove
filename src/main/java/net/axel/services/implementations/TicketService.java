package net.axel.services.implementations;

import net.axel.models.dto.TicketDto;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Journey;
import net.axel.models.entities.Ticket;
import net.axel.models.enums.ContractStatus;
import net.axel.repositories.implementations.TicketRepository;
import net.axel.repositories.interfaces.ITicketRipository;
import net.axel.services.interfaces.IContractService;
import net.axel.services.interfaces.IJourneyService;
import net.axel.services.interfaces.ITicketService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TicketService implements ITicketService {

    private final ITicketRipository ticketRepository;
    private final IContractService contractService;
    private final IJourneyService journeyService;

    public TicketService(TicketRepository ticketRepository ,ContractService contractService, JourneyService journeyService) throws SQLException {
        this.ticketRepository = ticketRepository;
        this.contractService = contractService;
        this.journeyService = journeyService;
    }

    @Override
    public boolean addTicket(TicketDto dto) {
        final Contract contract = contractService.getContractById(dto.ContractId());
        final Journey journey = journeyService.getJourneyById(dto.JourneyId());
        final double purchasePrice = dto.purchasePrice();
        final double resellPrice = dto.resellPrice();
        if(!checkContractStatus(contract) || checkPrices(purchasePrice, resellPrice)){
            return false;
        } else {
            final Ticket ticket = new Ticket(UUID.randomUUID(), dto.transportType(), dto.purchasePrice(), dto.resellPrice(), dto.saleDate(), dto.departureTime(), dto.arrivalTime(), dto.ticketStatus(), contract, journey);
            ticketRepository.addTicket(ticket);
            return true;
        }
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }

    @Override
    public List<Ticket> getTicketsByPartner(UUID partnerId) {
        return ticketRepository.getTicketsByPartner(partnerId);
    }

    private boolean checkContractStatus(Contract contract) {
        if (contract.getContractStatus().equals(ContractStatus.IN_PROGRESS)) {
            return true;
        } else {
            System.out.println("Can't create ticket, check contract status.\n");
            return false;
        }
    }

    private boolean checkPrices(double purchasePrice, double resellPrice) {
        if(purchasePrice > resellPrice) {
            System.out.println("The purchase price is higher than the resell price, try again with some profit.\n");
        }
        return purchasePrice > resellPrice;
    }


//    public Ticket findTicketById(UUID id) {
//        try {
//            List<Ticket> allTickets = getAllTickets();
//            if (allTickets != null) {
//                for (Ticket ticket : allTickets) {
//                    if (ticket.getId().equals(id)) {
//                        return ticket;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Error finding ticket by ID: " + e.getMessage());
//        }
//        return null;
//    }
//    public void updateTicket(Ticket ticket) {
//        try {
//            ticketRepository.updateTicket(ticket);
//        } catch (SQLException e) {
//            System.err.println("Error updating ticket: " + e.getMessage());
//        }
//    }
//    public void deleteTicket(UUID id) {
//        try {
//            ticketRepository.deleteTicket(id);
//        } catch (SQLException e) {
//            System.err.println("Error deleting ticket: " + e.getMessage());
//        }
//    }
}
