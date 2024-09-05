package net.axel.presentations;

import net.axel.models.dto.PromotionDto;
import net.axel.models.entities.Promotion;
import net.axel.models.enums.OfferStatus;
import net.axel.models.enums.ReductionType;
import net.axel.services.PromotionService;

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
            System.out.println("--- Promotion Management Menu ---");
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

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDateStr = scanner.nextLine();
            java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);

            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDateStr = scanner.nextLine();
            java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

            System.out.print("Enter reduction type (PERCENTAGE, FIXED_AMOUNT): ");
            String reductionTypeStr = scanner.nextLine();
            ReductionType reductionType = ReductionType.valueOf(reductionTypeStr.toUpperCase());

            System.out.print("Enter reduction value: ");
            double reductionValue = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter conditions: ");
            String conditions = scanner.nextLine();

            System.out.print("Enter offer status (ACTIVE, INACTIVE, SUSPENDED): ");
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
            java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);

            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDateStr = scanner.nextLine();
            java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

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
