package net.axel.services;

import net.axel.models.dto.TicketDto;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Ticket;
import net.axel.models.enums.ContractStatus;
import net.axel.repositories.TicketRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TicketService {

    private final TicketRepository ticketRepository;
    private final ContractService contractService;

    public TicketService() throws SQLException {
        this.ticketRepository = new TicketRepository();
        this.contractService = new ContractService();
    }
    public void addTicket(TicketDto dto) {
        try {
            final Contract contract = contractService.getContractById(dto.ContractId());
            if(!contract.getContractStatus().equals(ContractStatus.IN_PROGRESS)) {
                System.out.println("Can't create ticket , check contract status.");
            } else {
            final Ticket ticket = new Ticket(UUID.randomUUID(), dto.transportType(), dto.purchasePrice(), dto.resellPrice(), dto.saleDate(), dto.ticketStatus(), contract);
            ticketRepository.addTicket(ticket);
            }
        } catch (SQLException e) {
            System.err.println("Error adding ticket: " + e.getMessage());
        }
    }

    public List<Ticket> getAllTickets() {
        try {
            return ticketRepository.getAllTickets();
        } catch (SQLException e) {
            System.err.println("Error retrieving all tickets: " + e.getMessage());
            return null;
        }
    }

    public List<Ticket> getTicketsByPartner(UUID partnerId) {
        try {
            return ticketRepository.getTicketsByPartner(partnerId);
        } catch (SQLException e) {
            System.err.println("Error retrieving tickets for partner: " + e.getMessage());
            return null;
        }
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
