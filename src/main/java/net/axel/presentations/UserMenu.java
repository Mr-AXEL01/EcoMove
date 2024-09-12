package net.axel.presentations;

import net.axel.models.entities.Client;
import net.axel.repositories.implementations.ClientRepository;
import net.axel.repositories.implementations.JourneyRepository;
import net.axel.services.implementations.ClientService;
import net.axel.services.implementations.FavoriteService;
import net.axel.services.implementations.JourneyService;
import net.axel.services.interfaces.IClientService;
import net.axel.services.interfaces.IFavoriteService;
import net.axel.services.interfaces.IJourneyService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserMenu {
    private final Scanner scanner;
    private final IClientService clientService;
    private final IFavoriteService favoriteService;
    private final IJourneyService journeyService;

    public UserMenu() throws SQLException {
        this.clientService = new ClientService(new ClientRepository());
        this.favoriteService = new FavoriteService();
        this.journeyService = new JourneyService(new JourneyRepository());
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n***** Enter your EcoMove Space *****\n");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    signIn();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void signUp() {
        String firstName, lastName, email, phone;
        boolean isValid = true;

        System.out.print("Enter first name: ");
        firstName = scanner.nextLine();
        if (firstName == null || firstName.isEmpty()) {
            System.out.println("First name cannot be empty.");
            isValid = false;
        }

        System.out.print("Enter last name: ");
        lastName = scanner.nextLine();
        if (lastName == null || lastName.isEmpty()) {
            System.out.println("Last name cannot be empty.");
            isValid = false;
        }

        System.out.print("Enter email: ");
        email = scanner.nextLine();
        if (email == null || !email.contains("@")) {
            System.out.println("Invalid email address.");
            isValid = false;
        }

        System.out.print("Enter phone: ");
        phone = scanner.nextLine();
        if (phone == null || phone.isEmpty()) {
            System.out.println("Phone number cannot be empty.");
            isValid = false;
        }

        if (isValid) {
            Client client = new Client(firstName, lastName, email, phone);
            clientService.addClient(client);
            passToClientMenu(client);
        } else {
            System.out.println("Please correct the errors and try again.");
        }
    }

    private void signIn() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Client client = clientService.getClientByEmail(email);
        if (client != null) {
            passToClientMenu(client);
        } else {
            System.out.println("Email not found. Please try signing up.");
        }
    }

    private void passToClientMenu(Client client) {
        System.out.println("Sign in successful.\n");
        ClientMenu clientMenu = new ClientMenu(client, clientService, favoriteService, journeyService);
        clientMenu.displayMenu();
    }
}