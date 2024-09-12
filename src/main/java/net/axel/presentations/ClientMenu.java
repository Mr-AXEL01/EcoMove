package net.axel.presentations;

import net.axel.models.entities.Client;
import net.axel.services.interfaces.IClientService;
import net.axel.services.interfaces.IFavoriteService;
import net.axel.services.interfaces.IJourneyService;

import java.util.Scanner;

public class ClientMenu {
    private final IClientService clientService;
    private final IFavoriteService favoriteService;
    private final IJourneyService journeyService;
    private final Scanner scanner;
    private Client client;

    public ClientMenu(Client client, IClientService clientService, IFavoriteService favoriteService, IJourneyService journeyService) {
        this.client = client;
        this.clientService = clientService;
        this.favoriteService = favoriteService;
        this.journeyService = journeyService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (client != null) {
            System.out.println("Hi, " + client.getFirstName() );
            System.out.println("1. View Profile");
            System.out.println("2. My Favorite Searches");
            System.out.println("3. Search for Journeys");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    viewFavoriteSearches();
                    break;
                case 3:
                    searchJourneys();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("\nProfile Information:");
        System.out.println("First Name: " + client.getFirstName());
        System.out.println("Last Name: " + client.getLastName());
        System.out.println("Email: " + client.getMail());
        System.out.println("Phone: " + client.getPhone());

        System.out.println("1. Update Information");
        System.out.println("2. Delete Account");
        System.out.println("3. Back to Menu");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                updateProfile();
                break;
            case 2:
                deleteAccount();
                return; // Exit to be reloaded
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void updateProfile() {
        System.out.print("\nEnter new first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter new phone: ");
        String phone = scanner.nextLine();

        if (!firstName.isEmpty()) client.setFirstName(firstName);
        if (!lastName.isEmpty()) client.setLastName(lastName);
        if (!phone.isEmpty()) client.setPhone(phone);

        clientService.updateClient(client);
        System.out.println("Profile updated successfully.\n");
    }

    private void deleteAccount() {
        System.out.print("Are you sure you want to delete your account? (yes/no): ");
        String confirmation = scanner.nextLine();
        if ("yes".equalsIgnoreCase(confirmation)) {
            clientService.deleteClient(client.getMail());
            System.out.println("Account deleted successfully. Logging out...");
            client = null; // Clear the client object
            return;
        } else {
            System.out.println("Account deletion cancelled.");
        }
    }

    private void viewFavoriteSearches() {
//        List<Favorite> favorites = favoriteService.getFavoritesByClientEmail(client.getMail());
//        if (favorites.isEmpty()) {
//            System.out.println("No favorite searches yet.");
//        } else {
//            for (int i = 0; i < favorites.size(); i++) {
//                Favorite favorite = favorites.get(i);
//                System.out.println((i + 1) + ". Start Station: " + favorite.getStartStation() +
//                        ", End Station: " + favorite.getEndStation() +
//                        ", Departure Time: " + favorite.getDepartureTime() +
//                        ", Transport Type: " + favorite.getTransportType());
//            }
//        }
    }

    private void searchJourneys() {
//        System.out.print("Enter start city: ");
//        String startCity = scanner.nextLine();
//        System.out.print("Enter end city: ");
//        String endCity = scanner.nextLine();
//        System.out.print("Enter departure time (yyyy-MM-dd HH:mm:ss): ");
//        String departureTime = scanner.nextLine();
//
//        List<Journey> journeys = journeyService.searchJourneys(startCity, endCity, departureTime);
//
//        if (journeys.isEmpty()) {
//            System.out.println("No journeys found.");
//        } else {
//            for (int i = 0; i < journeys.size(); i++) {
//                Journey journey = journeys.get(i);
//                System.out.println((i + 1) + ". Journey ID: " + journey.getId() +
//                        ", Start Station: " + journey.getStartStation() +
//                        ", End Station: " + journey.getEndStation() +
//                        ", Departure Time: " + journey.getDepartureTime() +
//                        ", Price: " + journey.getPrice());
//            }
//
//            System.out.print("Enter the number of the journey to book: ");
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            if (choice > 0 && choice <= journeys.size()) {
//                Journey selectedJourney = journeys.get(choice - 1);
//                // Process the booking
//                System.out.println("Journey booked successfully.");
//            } else {
//                System.out.println("Invalid choice.");
//            }
//        }
    }
}
