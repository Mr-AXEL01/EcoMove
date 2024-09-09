package net.axel.presentations;

import net.axel.models.entities.Partner;
import net.axel.models.entities.Ticket;
import net.axel.services.PartnerService;
import net.axel.services.TicketService;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    private final PartnerUi partnerUi;
    private final ContractUi contractUi;
    private final PromotionUi promotionUi;
    private final TicketUi ticketUi;
    private final Scanner scanner;

    public Menu() throws SQLException {
        this.partnerUi = new PartnerUi();
        this.contractUi = new ContractUi();
        this.promotionUi = new PromotionUi();
        this.ticketUi = new TicketUi(new TicketService(), new PartnerService());
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        int choice;
        do {
            System.out.println("\n--- Main Management Menu ---\n");
            System.out.println("1. Partner Management");
            System.out.println("2. Contract Management");
            System.out.println("3. Promotion Management");
            System.out.println("4. Ticket Management");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    partnerUi.showMenu();
                    break;
                case 2:
                    contractUi.showMenu();
                    break;
                case 3:
                    promotionUi.showMenu();
                    break;
                case 4:
                    ticketUi.showMenu();
                    break;
                case 5:
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
}
