package net.axel.presentations;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() throws SQLException {
        while (true) {
            System.out.println("Welcome to the System!");
            System.out.println("1. Enter as Admin");
            System.out.println("2. Enter as User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    enterAsAdmin();
                    break;
                case 2:
                    enterAsUser();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    private void enterAsAdmin() throws SQLException {
        // Initialize AdminMenu
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.showMainMenu(); // Display Admin Menu
    }

    private void enterAsUser() throws SQLException {
        // Initialize UserMenu
        UserMenu userMenu = new UserMenu();
        userMenu.displayMenu(); // Display User Menu
    }
}
