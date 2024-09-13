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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TicketUi {

    private final ITicketService ticketService;
    private final IPartnerService partnerService;
    private final Scanner scanner;
    private final DateTimeFormatter dateTimeFormatter;

    public TicketUi(ITicketService ticketService, IPartnerService partnerService) throws SQLException {
        this.ticketService = ticketService;
        this.partnerService = partnerService;
        this.scanner = new Scanner(System.in);
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
        System.out.print("Enter transport type (PLANE, TRAIN, BUS, TAXI): ");
        String transportTypeStr = scanner.nextLine();
        if(transportTypeStr.trim().isEmpty()) {
            System.out.println("Transport type is empty.");
            return;
        }
        TransportType transportType;
        try {
            transportType = TransportType.valueOf(transportTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid transport type. Please enter PLANE, TRAIN, BUS, TAXI.");
            return;
        }

        System.out.print("Enter purchase price: ");
        String purchasePriceStr = scanner.nextLine();
        if (purchasePriceStr.trim().isEmpty()) {
            System.out.println("Purchase price is empty.");
            return;
        }
        double purchasePrice;
        try {
            purchasePrice = Double.parseDouble(purchasePriceStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid purchase price. Please enter a valid number.");
            return;
        }

        System.out.print("Enter resell price: ");
        String resellPriceStr = scanner.nextLine();
        if (resellPriceStr.trim().isEmpty()) {
            System.out.println("Resell price is empty.");
            return;
        }
        double resellPrice;
        try {
            resellPrice = Double.parseDouble(resellPriceStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid resell price. Please enter a valid number.");
            return;
        }

        System.out.print("Enter sale date (YYYY-MM-DD): ");
        String saleDateStr = scanner.nextLine();
        if (saleDateStr.trim().isEmpty()) {
            System.out.println("The sale date is empty.");
            return;
        }
        Date saleDate;
        try {
            saleDate = Date.valueOf(saleDateStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        System.out.print("Enter departure time (YYYY-MM-DD HH:MM): ");
        String departureTimeStr = scanner.nextLine();
        LocalDateTime departureTime;
        try {
            departureTime = LocalDateTime.parse(departureTimeStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid departure time format. Please use YYYY-MM-DD HH:MM.");
            return;
        }

        System.out.print("Enter arrival time (YYYY-MM-DD HH:MM): ");
        String arrivalTimeStr = scanner.nextLine();
        LocalDateTime arrivalTime;
        try {
            arrivalTime = LocalDateTime.parse(arrivalTimeStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid arrival time format. Please use YYYY-MM-DD HH:MM.");
            return;
        }

        System.out.print("Enter ticket status (SOLD, PENDING, CANCELLED): ");
        String ticketStatusStr = scanner.nextLine();
        TicketStatus ticketStatus;
        try {
            ticketStatus = TicketStatus.valueOf(ticketStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ticket status. Please enter SOLD, PENDING, or CANCELLED.");
            return;
        }

        System.out.print("Enter contract ID: ");
        String contractIdStr = scanner.nextLine();
        if (contractIdStr.trim().isEmpty()) {
            System.out.println("The contract ID is empty.");
            return;
        }
        UUID contractId;
        try {
            contractId = UUID.fromString(contractIdStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid contract ID format.");
            return;
        }

        System.out.print("Enter journey ID: ");
        String journeyIdStr = scanner.nextLine();
        if (journeyIdStr.trim().isEmpty()) {
            System.out.println("The journey ID is empty.");
            return;
        }
        UUID journeyId;
        try {
            journeyId = UUID.fromString(journeyIdStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid journey ID format.");
            return;
        }

        final TicketDto dto = new TicketDto(
                transportType,
                purchasePrice,
                resellPrice,
                saleDate,
                departureTime,
                arrivalTime,
                ticketStatus,
                contractId,
                journeyId
        );

        if (ticketService.addTicket(dto)) {
            System.out.println("Ticket added successfully.\n");
        } else {
            System.out.println("Error adding the ticket.\n");
        }
    }

}
