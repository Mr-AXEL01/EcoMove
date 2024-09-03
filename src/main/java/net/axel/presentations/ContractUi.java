package net.axel.presentations;

import net.axel.models.entities.Contract;
import net.axel.models.entities.Partner;
import net.axel.services.ContractService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ContractUi {

    private final ContractService contractService;
    private final Scanner scanner;

    public ContractUi() throws SQLException {
        this.contractService = new ContractService();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n--- Contract Management Menu ---");
            System.out.println("1. List all contracts");
            System.out.println("2. View contract details");
            System.out.println("3. Add a new contract");
            System.out.println("4. Update a contract");
            System.out.println("5. Delete a contract");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listAllContracts();
                    break;
                case 2:
                    viewContractDetails();
                    break;
                case 3:
                    addNewContract();
                    break;
                case 4:
                    updateContract();
                    break;
                case 5:
                    deleteContract();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 6);
    }

    private void listAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();
        System.out.println("\n--- List of Contracts ---");
        System.out.println("");
        if (contracts != null && !contracts.isEmpty()) {
            for (Contract contract : contracts) {
                System.out.println("|-----------------------------------------------------------|");
                System.out.println("| ID: " + contract.getId() + " | Partner: " + contract.getPartner().getCompanyName());
            }
        } else {
            System.out.println("No contracts found for the moment!");
        }
    }
    private void viewContractDetails() {
        System.out.print("\nEnter Contract ID: ");
        String id = scanner.nextLine();
        Contract contract = contractService.getContractById(UUID.fromString(id));
        if (contract != null) {

            System.out.println("\n************* Contract Details *************");
            System.out.println("ID                : " + contract.getId());
            System.out.println("Start Date        : " + contract.getStartDate());
            System.out.println("End Date          : " + contract.getEndDate());
            System.out.println("Special Tariff    : " + contract.getSpecialTariff());
            System.out.println("Conditions Accord : " + contract.getConditionsAccord()+"\n");
            System.out.println("Renewable         : " + contract.getRenewable());
            System.out.println("Contract Status   : " + contract.getContractStatus());
            System.out.println("Partner Name      : " + contract.getPartner().getCompanyName());
        } else {
            System.out.println("Partner not found!");
        }
    }
}
