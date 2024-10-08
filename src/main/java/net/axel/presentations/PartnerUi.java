package net.axel.presentations;

import net.axel.models.entities.Partner;
import net.axel.models.enums.PartnerStatus;
import net.axel.models.enums.TransportType;
import net.axel.services.interfaces.IPartnerService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PartnerUi {

    private final IPartnerService partnerService;
    private final Scanner scanner;

    public PartnerUi(IPartnerService partnerService) throws SQLException {
        this.partnerService = partnerService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n--- Partner Management Menu ---\n");
            System.out.println("1. List all partners");
            System.out.println("2. View partner details");
            System.out.println("3. Add a new partner");
            System.out.println("4. Update a partner");
            System.out.println("5. Delete a partner");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listAllPartners();
                    break;
                case 2:
                    viewPartnerDetails();
                    break;
                case 3:
                    addNewPartner();
                    break;
                case 4:
                    updatePartner();
                    break;
                case 5:
                    deletePartner();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 6);
    }

    private void listAllPartners() {
        List<Partner> partners = partnerService.getAllPartners();
        System.out.println("\n--- List of Partners ---\n");
        if (partners != null && !partners.isEmpty()) {
            for (Partner partner : partners) {
                System.out.println("|-----------------------------------------------------------|");
                System.out.println("| ID: " + partner.getId() + " | Company: " + partner.getCompanyName());
            }
        } else {
            System.out.println("No partners found for the moment!");
        }
    }

    private void viewPartnerDetails() {
        System.out.print("\nEnter Partner ID: ");
        String id = scanner.nextLine();
        Partner partner = partnerService.getPartnerById(UUID.fromString(id));
        if (partner != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedDate = dateFormat.format(partner.getCreationDate());

            System.out.println("\n************* Partner Details *************");
            System.out.println("ID                : " + partner.getId());
            System.out.println("Company Name      : " + partner.getCompanyName());
            System.out.println("Comercial Contact : " + partner.getComercialContact());
            System.out.println("Transport Type    : " + partner.getTransportType());
            System.out.println("Geographical Area : " + partner.getGeographicalArea());
            System.out.println("Special Conditions: " + partner.getSpecialConditions());
            System.out.println("Partner Status    : " + partner.getPartnerStatus());
            System.out.println("Creation Date     : " + formattedDate);
        } else {
            System.out.println("Partner not found!");
        }
    }

    private void addNewPartner() {
        System.out.print("\nEnter Company Name: ");
        String companyName = scanner.nextLine();
        if (companyName.trim().isEmpty()) {
            System.out.println("Company Name cannot be empty.");
            return;
        }

        System.out.print("Enter Comercial Contact: ");
        String comercialContact = scanner.nextLine();
        if (comercialContact.trim().isEmpty()) {
            System.out.println("Comercial Contact cannot be empty.");
            return;
        }

        System.out.print("Enter Transport Type (PLANE, TRAIN, BUS, TAXI): ");
        String transportTypeStr = scanner.nextLine();
        if (transportTypeStr.trim().isEmpty()) {
            System.out.println("Transport Type cannot be empty.");
            return;
        }
        TransportType transportType;
        try {
            transportType = TransportType.valueOf(transportTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Transport Type. Please enter PLANE, TRAIN, BUS, or TAXI.");
            return;
        }

        System.out.print("Enter Geographical Area: ");
        String geographicalArea = scanner.nextLine();
        if (geographicalArea.trim().isEmpty()) {
            System.out.println("Geographical Area cannot be empty.");
            return;
        }

        System.out.print("Enter Special Conditions: ");
        String specialConditions = scanner.nextLine();
        if (specialConditions.trim().isEmpty()) {
            System.out.println("Special Conditions cannot be empty.");
            return;
        }

        System.out.print("Enter Partner Status (ACTIVE, INACTIVE, SUSPENDED): ");
        String partnerStatusStr = scanner.nextLine();
        if (partnerStatusStr.trim().isEmpty()) {
            System.out.println("Partner Status cannot be empty.");
            return;
        }
        PartnerStatus partnerStatus;
        try {
            partnerStatus = PartnerStatus.valueOf(partnerStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Partner Status. Please enter ACTIVE, INACTIVE, or SUSPENDED.");
            return;
        }

        Partner newPartner = new Partner(UUID.randomUUID(), companyName, comercialContact, transportType, geographicalArea, specialConditions, partnerStatus);
        partnerService.addPartner(newPartner);
        System.out.println("Partner added successfully!");
    }

    private void updatePartner() {
        System.out.print("\nEnter Partner ID: ");
        String idStr = scanner.nextLine();

        if(idStr.trim().isEmpty()) {
            System.out.println("Partner ID can't be empty.");
            return;
        }
        UUID id;
        try {
            id = UUID.fromString(idStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Partner ID format.");
            return;
        }
        Partner partner = partnerService.getPartnerById(id);

        if (partner != null) {
            System.out.print("Enter New Company Name (" + partner.getCompanyName() + "): ");
            String companyName = scanner.nextLine();
            if (!companyName.isEmpty()) partner.setCompanyName(companyName);

            System.out.print("Enter New Commercial Contact (" + partner.getComercialContact() + "): ");
            String comercialContact = scanner.nextLine();
            if (!comercialContact.isEmpty()) partner.setComercialContact(comercialContact);

            System.out.print("Enter New Transport Type (AIR, TRAIN, BUS) (" + partner.getTransportType() + "): ");
            String transportTypeStr = scanner.nextLine();
            if (!transportTypeStr.isEmpty()) {
                TransportType transportType = TransportType.valueOf(transportTypeStr.toUpperCase());
                partner.setTransportType(transportType);
            }

            System.out.print("Enter New Geographical Area (" + partner.getGeographicalArea() + "): ");
            String geographicalArea = scanner.nextLine();
            if (!geographicalArea.isEmpty()) partner.setGeographicalArea(geographicalArea);

            System.out.print("Enter New Special Conditions (" + partner.getSpecialConditions() + "): ");
            String specialConditions = scanner.nextLine();
            if (!specialConditions.isEmpty()) partner.setSpecialConditions(specialConditions);

            System.out.print("Enter New Partner Status (ACTIVE, INACTIVE, SUSPENDED) (" + partner.getPartnerStatus() + "): ");
            String partnerStatusStr = scanner.nextLine();
            if (!partnerStatusStr.isEmpty()) {
                PartnerStatus partnerStatus = PartnerStatus.valueOf(partnerStatusStr.toUpperCase());
                partner.setPartnerStatus(partnerStatus);
            }

            partnerService.updatePartner(partner);
            System.out.println("Partner updated successfully!");
        } else {
            System.out.println("Partner not found!");
        }
    }

    private void deletePartner() {
        System.out.print("\nEnter Partner ID: ");
        String id = scanner.nextLine();
        partnerService.deletePartner(UUID.fromString(id));
        System.out.println("Partner deleted successfully!");
    }
}
