package net.axel.presentations;

import net.axel.models.dto.ContractDto;
import net.axel.models.entities.Contract;
import net.axel.models.enums.ContractStatus;
import net.axel.services.implementations.ContractService;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ContractUi {

    private final ContractService contractService;
    private final Scanner scanner;
    private final SimpleDateFormat dateFormat;

    public ContractUi() throws SQLException {
        this.contractService = new ContractService();
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n--- Contract Management Menu ---\n");
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
        if (contracts != null && !contracts.isEmpty()) {
            for (Contract contract : contracts) {
                System.out.println("|-----------------------------------------------------------|");
                System.out.println("| ID: " + contract.getId() + " | Partner: " + contract.getPartner().getCompanyName());
            }
        } else {
            System.out.println("No contracts found at the moment!");
        }
    }

    private void viewContractDetails() {
        System.out.print("\nEnter Contract ID: ");
        String id = scanner.nextLine();
        Contract contract = contractService.getContractById(UUID.fromString(id));
        if (contract != null) {
            System.out.println("\n************* Contract Details *************");
            System.out.println("ID                : " + contract.getId());
            System.out.println("Start Date        : " + dateFormat.format(contract.getStartDate()));
            System.out.println("End Date          : " + dateFormat.format(contract.getEndDate()));
            System.out.println("Special Tariff    : " + contract.getSpecialTariff());
            System.out.println("Conditions Accord : " + contract.getConditionsAccord());
            System.out.println("Renewable         : " + contract.getRenewable());
            System.out.println("Contract Status   : " + contract.getContractStatus());
            System.out.println("Partner Name      : " + contract.getPartner().getCompanyName());
        } else {
            System.out.println("Contract not found!");
        }
    }

    private void addNewContract() {
        try {
            System.out.print("\nEnter The Start Date (yyyy-MM-dd): ");
            String startDateStr = scanner.nextLine();
            if (startDateStr.trim().isEmpty()) {
                System.out.println("Start Date can't be empty.");
                return;
            }
            Date startDate = dateFormat.parse(startDateStr);

            System.out.print("Enter The End Date (yyyy-MM-dd): ");
            String endDateStr = scanner.nextLine();
            if (endDateStr.trim().isEmpty()) {
                System.out.println("End Date can't be empty.");
                return;
            }
            Date endDate = dateFormat.parse(endDateStr);


            System.out.print("Enter The Special Tariff ($.$$ or $%): ");
            String specialTariffStr = scanner.nextLine();
            if (specialTariffStr.trim().isEmpty()) {
                System.out.println("Special Tariff can't be empty.");
                return;
            }
            double specialTariff;
            try {
                specialTariff = Double.parseDouble(specialTariffStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Special Tariff. Please enter a valid number.");
                return;
            }

            System.out.print("Enter The Conditions Accord: ");
            String conditionsAccord = scanner.nextLine();
            if (conditionsAccord.trim().isEmpty()) {
                System.out.println("Conditions Accord can't be empty.");
                return;
            }

            System.out.print("Is The Contract Renewable? (true or false): ");
            String renewableStr = scanner.nextLine();
            if (renewableStr.trim().isEmpty()) {
                System.out.println("Renewable field can't be empty.");
                return;
            }
            boolean renewable;
            try {
                renewable = Boolean.parseBoolean(renewableStr);
            } catch (Exception e) {
                System.out.println("Invalid input for Renewable. Please enter 'true' or 'false'.");
                return;
            }

            System.out.print("Enter Contract Status (IN_PROGRESS, COMPLETE, SUSPENDED): ");
            String contractStatusStr = scanner.nextLine();
            if (contractStatusStr.trim().isEmpty()) {
                System.out.println("Contract Status can't be empty.");
                return;
            }
            ContractStatus contractStatus;
            try {
                contractStatus = ContractStatus.valueOf(contractStatusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Contract Status. Please enter IN_PROGRESS, COMPLETE, or SUSPENDED.");
                return;
            }

            System.out.print("Enter Partner ID: ");
            String partnerIdStr = scanner.nextLine();
            if (partnerIdStr.trim().isEmpty()) {
                System.out.println("Partner ID can't be empty.");
                return;
            }
            UUID partnerId;
            try {
                partnerId = UUID.fromString(partnerIdStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Partner ID format.");
                return;
            }

            final ContractDto dto = new ContractDto(
                    startDate,
                    endDate,
                    specialTariff,
                    conditionsAccord,
                    renewable,
                    contractStatus,
                    partnerId
            );
            if(contractService.addContract(dto)) System.out.println("Contract added successfully!");

        } catch (ParseException e) {
            System.out.println("Error: Invalid date format. Please use 'yyyy-MM-dd'.");
        } catch (Exception e) {
            System.out.println("Error adding contract: " + e.getMessage());
        }
    }

    private void updateContract() {
        try {
            System.out.print("\nEnter Contract ID to update: ");
            UUID contractId = UUID.fromString(scanner.nextLine());
            Contract existingContract = contractService.getContractById(contractId);
            if (existingContract == null) {
                System.out.println("Contract not found!");
                return;
            }

            System.out.print("Enter The Start Date (yyyy-MM-dd) [" + dateFormat.format(existingContract.getStartDate()) + "]: ");
            String startDateInput = scanner.nextLine();
            Date startDate = startDateInput.isEmpty() ? existingContract.getStartDate() : dateFormat.parse(startDateInput);

            System.out.print("Enter The End Date (yyyy-MM-dd) [" + dateFormat.format(existingContract.getEndDate()) + "]: ");
            String endDateInput = scanner.nextLine();
            Date endDate = endDateInput.isEmpty() ? existingContract.getEndDate() : dateFormat.parse(endDateInput);

            System.out.print("Enter The Special Tariff ($.$$ or $%) [" + existingContract.getSpecialTariff() + "]: ");
            String specialTariffInput = scanner.nextLine();
            double specialTariff = specialTariffInput.isEmpty() ? existingContract.getSpecialTariff() : Double.parseDouble(specialTariffInput);

            System.out.print("Enter The Conditions Accord [" + existingContract.getConditionsAccord() + "]: ");
            String conditionsAccord = scanner.nextLine();
            conditionsAccord = conditionsAccord.isEmpty() ? existingContract.getConditionsAccord() : conditionsAccord;

            System.out.print("Is The Contract Renewable? (true or false) [" + existingContract.getRenewable() + "]: ");
            String renewableInput = scanner.nextLine();
            boolean renewable = renewableInput.isEmpty() ? existingContract.getRenewable() : Boolean.parseBoolean(renewableInput);

            System.out.print("Enter Contract Status (IN_PROGRESS, COMPLETE, SUSPENDED) [" + existingContract.getContractStatus() + "]: ");
            String contractStatusStr = scanner.nextLine();
            ContractStatus contractStatus = contractStatusStr.isEmpty() ? existingContract.getContractStatus() : ContractStatus.valueOf(contractStatusStr.toUpperCase());

            System.out.print("Enter Partner ID [" + existingContract.getPartner().getId() + "]: ");
            String partnerIdInput = scanner.nextLine();
            UUID partnerId = partnerIdInput.isEmpty() ? existingContract.getPartner().getId() : UUID.fromString(partnerIdInput);

            final ContractDto updatedDto = new ContractDto(
                    startDate,
                    endDate,
                    specialTariff,
                    conditionsAccord,
                    renewable,
                    contractStatus,
                    partnerId
            );

            contractService.updateContract(contractId, updatedDto);
            System.out.println("Contract updated successfully!");

        } catch (ParseException e) {
            System.out.println("Error: Invalid date format. Please use 'yyyy-MM-dd'.");
        } catch (Exception e) {
            System.out.println("Error updating contract: " + e.getMessage());
        }
    }

    private void deleteContract() {
        System.out.println("\nEnter Contract ID :");
        String idStr = scanner.nextLine();
        if (idStr.trim().isEmpty()) {
            System.out.println("Contract Id can't be empty.");
            return;
        }
        UUID id;
        try {
            id = UUID.fromString(idStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Partner ID format.");
            return;
        }
        contractService.deleteContract(id);
    }

}
