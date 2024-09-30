package net.axel.presentations;

import net.axel.repositories.implementations.*;
import net.axel.services.implementations.*;

import java.sql.SQLException;
import java.util.Scanner;

public class AdminMenu {

    private final PartnerUi partnerUi;
    private final ContractUi contractUi;
    private final PromotionUi promotionUi;
    private final TicketUi ticketUi;
    private final StationUi stationUi;
    private final JourneyUi jorneyUi;
    private final Scanner scanner;

    public AdminMenu() throws SQLException {
        this.partnerUi = new PartnerUi(
                new PartnerService(
                        new PartnerRepository()
                )
        );

        this.contractUi = new ContractUi();

        this.promotionUi = new PromotionUi(
                new PromotionService(
                        new PromotionRepository(),
                        new ContractService(
                                new ContractRepository(),
                                new PartnerService(
                                        new PartnerRepository()
                                )
                        )
                )
        );

        this.stationUi = new StationUi(
                new StationService(
                        new StationRepository()
                )
        );

        this.jorneyUi = new JourneyUi(
                new JourneyService(
                        new JourneyRepository(
                                new StationRepository()
                        ),
                        new TicketRepository(),
                        new StationRepository()
                ),
                new StationService(
                        new StationRepository()
                )
        );

        this.ticketUi = new TicketUi(
                new TicketService(
                        new TicketRepository(),
                        new ContractService(
                                new ContractRepository(),
                                new PartnerService(
                                        new PartnerRepository()
                                )
                        ),
                        new JourneyService(
                                new JourneyRepository(
                                        new StationRepository()
                                ),
                                new TicketRepository(),
                                new StationRepository()
                        )
                ),
                new PartnerService(
                        new PartnerRepository()
                )
        );

        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        int choice;
        do {
            System.out.println("\n--- Admin Management Menu ---\n");
            System.out.println("1. Partner Management");
            System.out.println("2. Contract Management");
            System.out.println("3. Promotion Management");
            System.out.println("4. Ticket Management");
            System.out.println("5. Station Management");
            System.out.println("6. Journey Management");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> partnerUi.showMenu();
                case 2 -> contractUi.showMenu();
                case 3 -> promotionUi.showMenu();
                case 4 -> ticketUi.showMenu();
                case 5 -> stationUi.showMenu();
                case 6 -> jorneyUi.showMenu();
                case 7 -> System.out.println("Exiting the system...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }
}
