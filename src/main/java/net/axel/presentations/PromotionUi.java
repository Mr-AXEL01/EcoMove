package net.axel.presentations;

import net.axel.models.dto.PromotionDto;
import net.axel.models.entities.Promotion;
import net.axel.models.enums.OfferStatus;
import net.axel.models.enums.ReductionType;
import net.axel.services.PromotionService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PromotionUi {

    private final PromotionService promotionService;
    private final Scanner scanner;

    public PromotionUi() throws SQLException {
        this.promotionService = new PromotionService();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n--- Promotion Management Menu ---\n");
            System.out.println("1. List all promotions");
            System.out.println("2. Add a new promotion");
            System.out.println("3. Find promotion by ID");
            System.out.println("4. Update an existing promotion");
            System.out.println("5. Delete a promotion");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listAllPromotions();
                    break;
                case 2:
                    addNewPromotion();
                    break;
                case 3:
                    findPromotionById();
                    break;
                case 4:
                    updatePromotion();
                    break;
                case 5:
                    deletePromotion();
                    break;
                case 6:
                    System.out.println("Exiting Promotion Management...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void listAllPromotions() {
        try {
            List<Promotion> promotions = promotionService.getAllPromotions();
            if (promotions != null && !promotions.isEmpty()) {
                System.out.println("|-----------------------------------------------------------|");
                for (Promotion promotion : promotions) {
                    System.out.println("| " + promotion.getId() + " | " + promotion.getOfferName() + " | " + promotion.getReductionType() + " | " + promotion.getOfferStatus());
                }
            } else {
                System.out.println("No promotions available.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving promotions: " + e.getMessage());
        }
    }

    private void addNewPromotion() {
        try {
            scanner.nextLine();

            System.out.print("Enter offer name: ");
            String offerName = scanner.nextLine();
            if (offerName.trim().isEmpty()) {
                System.out.println("Offer name can't be empty.");
                return;
            }

            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            if (description.trim().isEmpty()) {
                System.out.println("Description can't be empty.");
                return;
            }

            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDateStr = scanner.nextLine();
            if (startDateStr.trim().isEmpty()) {
                System.out.println("Start date can't be empty.");
                return;
            }
            Date startDate;
            try {
                startDate = Date.valueOf(startDateStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid start date format. Please use 'YYYY-MM-DD'.");
                return;
            }

            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDateStr = scanner.nextLine();
            if (endDateStr.trim().isEmpty()) {
                System.out.println("End date can't be empty.");
                return;
            }
            Date endDate;
            try {
                endDate = Date.valueOf(endDateStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid end date format. Please use 'YYYY-MM-DD'.");
                return;
            }

            if (startDate.after(endDate)) {
                System.out.println("Error: Start Date cannot be after End Date.");
                return;
            }

            System.out.print("Enter reduction type (PERCENTAGE, FIXED_AMOUNT): ");
            String reductionTypeStr = scanner.nextLine();
            if (reductionTypeStr.trim().isEmpty()) {
                System.out.println("Reduction type can't be empty.");
                return;
            }
            ReductionType reductionType;
            try {
                reductionType = ReductionType.valueOf(reductionTypeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid reduction type. Please use PERCENTAGE or FIXED_AMOUNT.");
                return;
            }

            System.out.print("Enter reduction value: ");
            String reductionValueStr = scanner.nextLine();
            if (reductionValueStr.trim().isEmpty()) {
                System.out.println("Reduction value can't be empty.");
                return;
            }
            double reductionValue;
            try {
                reductionValue = Double.parseDouble(reductionValueStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid reduction value. Please enter a valid number.");
                return;
            }

            System.out.print("Enter conditions: ");
            String conditions = scanner.nextLine();
            if (conditions.trim().isEmpty()) {
                System.out.println("Conditions can't be empty.");
                return;
            }

            System.out.print("Enter offer status (ACTIVE, INACTIVE, SUSPENDED): ");
            String offerStatusStr = scanner.nextLine();
            if (offerStatusStr.trim().isEmpty()) {
                System.out.println("Offer status can't be empty.");
                return;
            }
            OfferStatus offerStatus;
            try {
                offerStatus = OfferStatus.valueOf(offerStatusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid offer status. Please use ACTIVE, INACTIVE, or SUSPENDED.");
                return;
            }

            System.out.print("Enter contract ID: ");
            String contractIdStr = scanner.nextLine();
            if (contractIdStr.trim().isEmpty()) {
                System.out.println("Contract ID can't be empty.");
                return;
            }
            UUID contractId;
            try {
                contractId = UUID.fromString(contractIdStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid contract ID format.");
                return;
            }

            PromotionDto promotionDto = new PromotionDto(
                    offerName,
                    description,
                    startDate,
                    endDate,
                    reductionType,
                    reductionValue,
                    conditions,
                    offerStatus,
                    contractId
            );

            promotionService.addPromotion(promotionDto);
            System.out.println("Promotion added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding promotion: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    private void findPromotionById() {
        try {
            System.out.print("Enter promotion ID: ");
            String promotionIdStr = scanner.nextLine();
            UUID promotionId = UUID.fromString(promotionIdStr);

            Promotion promotion = promotionService.getPromotionById(promotionId);
            if (promotion != null) {
                System.out.println("|-----------------------------------------------------------|");
                System.out.println("| Promotion ID: " + promotion.getId());
                System.out.println("| Offer Name: " + promotion.getOfferName());
                System.out.println("| Description: " + promotion.getDescription());
                System.out.println("| Start Date: " + promotion.getStartDate());
                System.out.println("| End Date: " + promotion.getEndDate());
                System.out.println("| Reduction Type: " + promotion.getReductionType());
                System.out.println("| Reduction Value: " + promotion.getReductionValue());
                System.out.println("| Conditions: " + promotion.getConditions());
                System.out.println("| Offer Status: " + promotion.getOfferStatus());
                System.out.println("| Contract: " + promotion.getContract().getId());
                System.out.println("| Partner: " + promotion.getContract().getPartner().getCompanyName());
                System.out.println("|-----------------------------------------------------------|");
            } else {
                System.out.println("No promotion found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving promotion by ID: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid promotion ID: " + e.getMessage());
        }
    }

    private void updatePromotion() {
        try {
            System.out.print("Enter promotion ID: ");
            String promotionIdStr = scanner.nextLine();
            UUID promotionId = UUID.fromString(promotionIdStr);

            System.out.print("Enter offer name: ");
            String offerName = scanner.nextLine();

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDateStr = scanner.nextLine();
            Date startDate = Date.valueOf(startDateStr);

            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDateStr = scanner.nextLine();
            Date endDate = Date.valueOf(endDateStr);

            System.out.print("Enter reduction type (PERCENTAGE, FIXED): ");
            String reductionTypeStr = scanner.nextLine();
            ReductionType reductionType = ReductionType.valueOf(reductionTypeStr.toUpperCase());

            System.out.print("Enter reduction value: ");
            double reductionValue = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter conditions: ");
            String conditions = scanner.nextLine();

            System.out.print("Enter offer status (ACTIVE, INACTIVE): ");
            String offerStatusStr = scanner.nextLine();
            OfferStatus offerStatus = OfferStatus.valueOf(offerStatusStr.toUpperCase());

            System.out.print("Enter contract ID: ");
            String contractIdStr = scanner.nextLine();
            UUID contractId = UUID.fromString(contractIdStr);

            PromotionDto promotionDto = new PromotionDto(
                    offerName,
                    description,
                    startDate,
                    endDate,
                    reductionType,
                    reductionValue,
                    conditions,
                    offerStatus,
                    contractId
            );

            promotionService.updatePromotion(promotionId, promotionDto);
            System.out.println("Promotion updated successfully.");

        } catch (SQLException e) {
            System.err.println("Error updating promotion: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    private void deletePromotion() {
        try {
            System.out.print("Enter promotion ID: ");
            String promotionIdStr = scanner.nextLine();
            UUID promotionId = UUID.fromString(promotionIdStr);

            promotionService.deletePromotion(promotionId);
            System.out.println("Promotion deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting promotion: " + e.getMessage());
        }
    }
}
