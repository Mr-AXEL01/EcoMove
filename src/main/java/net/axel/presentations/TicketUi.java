package net.axel.presentations;

import net.axel.models.dto.TicketDto;
import net.axel.models.entities.Partner;
import net.axel.models.entities.Ticket;
import net.axel.models.enums.TicketStatus;
import net.axel.models.enums.TransportType;
import net.axel.services.interfaces.IPartnerService;
import net.axel.services.interfaces.ITicketService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TicketUi {

    private final ITicketService ticketService;
    private final IPartnerService partnerService;
    private final Scanner scanner;

    public TicketUi(ITicketService ticketService, IPartnerService partnerService) throws SQLException {
        this.ticketService = ticketService;
        this.partnerService = partnerService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n--- Ticket Management Menu ---\n");
            System.out.println("1. List all tickets");
            System.out.println("2. List all tickets of a partner");
            System.out.println("3. Add a new ticket");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    listAllTickets();
                    break;
                case 2:
                    listAllTicketsByPartner();
                    break;
                case 3:
                    addNewTicket();
                    break;
                case 4:
                    System.out.println("Exiting Ticket Management...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private void listAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        if (tickets != null && !tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                System.out.println("|-----------------------------------------------------------|");
                System.out.println("| " + ticket.getId() + " | " + ticket.getTransportType() + " | " + ticket.getContract().getPartner().getCompanyName() + " | " + ticket.getTicketStatus()+"\n");
            }
        } else {
            System.out.println("No tickets available.\n");
        }
    }

    private void listAllTicketsByPartner() {
        System.out.print("Enter partner ID: ");
        String partnerIdStr = scanner.nextLine();
        UUID partnerId = UUID.fromString(partnerIdStr);
        List<Ticket> partnerTickets = ticketService.getTicketsByPartner(partnerId);
        if (partnerTickets != null && !partnerTickets.isEmpty()) {
            final Partner partner = partnerService.getPartnerById(partnerId);
            System.out.println("|********************************|");
            System.out.println("| Tickets Of : " + partner.getCompanyName());
            System.out.println("|********************************|\n");
            for (Ticket ticket : partnerTickets) {
                System.out.println("|-------------------------------------------------------------|");
                System.out.println("| " + ticket.getId() + " | " + ticket.getTransportType() + " | " + ticket.getTicketStatus()+"\n");
            }
        } else {
            System.out.println("No tickets available for this partner.");
        }
    }

    private void addNewTicket() {
        System.out.print("Enter transport type (PLANE, TRAIN, BUS): ");
        String transportTypeStr = scanner.nextLine();
        if(transportTypeStr.trim().isEmpty()) {
            System.out.println("Transport type is empty.");
            return;
        }
        TransportType transportType = TransportType.valueOf(transportTypeStr.toUpperCase());

        System.out.print("Enter purchase price: ");
        String purchasePriceStr = scanner.nextLine();
        if(purchasePriceStr.trim().isEmpty()) {
            System.out.println("Purchase price is empty.");
            return;
        }
        double purchasePrice = Double.parseDouble(purchasePriceStr);

        System.out.print("Enter resell price: ");
        String resellPriceStr = scanner.nextLine();
        if(resellPriceStr.trim().isEmpty()) {
            System.out.println("Resell price is empty.");
            return;
        }
        double resellPrice = Double.parseDouble(resellPriceStr);

//        scanner.nextLine();

        System.out.print("Enter sale date (YYYY-MM-DD): ");
        String saleDateStr = scanner.nextLine();
        if(saleDateStr.trim().isEmpty()) {
            System.out.println("The sale date is empty.");
            return;
        }
        Date saleDate = Date.valueOf(saleDateStr);

        System.out.print("Enter ticket status (SOLD, PENDING, CANCELLED): ");
        String ticketStatusStr = scanner.nextLine();
        TicketStatus ticketStatus = TicketStatus.valueOf(ticketStatusStr.toUpperCase());

        System.out.print("Enter contract ID: ");
        String contractIdStr = scanner.nextLine();
        if (contractIdStr.trim().isEmpty()) {
            System.out.println("The contract ID is empty.");
            return;
        }
        UUID contractId = UUID.fromString(contractIdStr);

        final TicketDto dto = new TicketDto(
                transportType,
                purchasePrice,
                resellPrice,
                saleDate,
                ticketStatus,
                contractId
        );
        if(ticketService.addTicket(dto)) System.out.println("Ticket added successfully.\n");
    }

}
