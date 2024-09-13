package net.axel.presentations;

import net.axel.models.entities.Journey;
import net.axel.models.entities.Station;
import net.axel.services.interfaces.IJourneyService;
import net.axel.services.interfaces.IStationService;
import net.axel.services.implementations.JourneyService;
import net.axel.services.implementations.StationService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class JourneyUi {

    private final IJourneyService journeyService;
    private final IStationService stationService;
    private final Scanner scanner;

    public JourneyUi(JourneyService journeyService, StationService stationService) throws SQLException {
        this.journeyService = journeyService;
        this.stationService = stationService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Journey Management Menu ---");
            System.out.println("1. Add Journey");
            System.out.println("2. View All Journeys");
            System.out.println("3. Get Journey by ID");
            System.out.println("4. Update Journey");
            System.out.println("5. Delete Journey");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addJourney();
                    break;
                case 2:
                    viewAllJourneys();
                    break;
                case 3:
                    getJourneyById();
                    break;
                case 4:
                    updateJourney();
                    break;
                case 5:
                    deleteJourney();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    private void addJourney() {
        System.out.println("\n--- Add New Journey ---\n");
            List<Station> stations = stationService.getAllStations();
            if (stations.isEmpty()) {
                System.out.println("No stations available. Please add stations first.");
                return;
            }

            listAllStations(stations);

            System.out.print("Enter the ID of the start station: ");
            UUID startStationId = UUID.fromString(scanner.nextLine());
            Station startStation = stationService.getStationById(startStationId);
            if (startStation == null) {
                System.out.println("Invalid start station ID. Operation canceled.");
                return;
            }

            System.out.print("Enter the ID of the end station: ");
            UUID endStationId = UUID.fromString(scanner.nextLine());
            Station endStation = stationService.getStationById(endStationId);
            if (endStation == null) {
                System.out.println("Invalid end station ID. Operation canceled.");
                return;
            }

            Journey journey = new Journey(UUID.randomUUID(), startStation, endStation);
            journeyService.addJourney(journey);
            System.out.println("\nJourney added successfully!\n");
    }


    private void viewAllJourneys() {
        System.out.println("\n--- All Journeys ---\n");
        List<Journey> journeys = journeyService.getAllJourneys();
        if (journeys.isEmpty()) {
            System.out.println("\nNo journeys found.");
        } else {
            System.out.println("***************************************");
            for (Journey journey : journeys) {
                System.out.println("|----------------------------------------------------");
                System.out.println("| ID            : " + journey.getId());
                System.out.println("| Start Station : " + journey.getStartStation().getName() + " (ID: " + journey.getStartStation().getId() + ")" + " | " + journey.getStartStation().getLocation());
                System.out.println("| End Station   : " + journey.getEndStation().getName() + " (ID: " + journey.getEndStation().getId() + ")"+ " | " + journey.getEndStation().getLocation());
                System.out.println("|----------------------------------------------------\n");
            }
        }
    }

    private void getJourneyById() {
        System.out.print("Enter journey ID: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Journey journey = journeyService.getJourneyById(id);
        if (journey != null) {
            System.out.println("Journey found: " + journey);
        } else {
            System.out.println("\nJourney not found.");
        }
    }

    private void updateJourney() {
        System.out.print("Enter journey ID to update: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Journey existingJourney = journeyService.getJourneyById(id);
        if (existingJourney == null) {
            System.out.println("Journey not found.");
            return;
        }

        List<Station> stations = stationService.getAllStations();
        if (stations.isEmpty()) {
            System.out.println("No stations available. Please add stations first.");
            return;
        }

        listAllStations(stations);

        System.out.print("Enter the ID of the new start station: ");
        UUID newStartStationId = UUID.fromString(scanner.nextLine());
        Station newStartStation = stationService.getStationById(newStartStationId);
        if (newStartStation == null) {
            System.out.println("Invalid start station ID. Operation canceled.");
            return;
        }

        System.out.print("Enter the ID of the new end station: ");
        UUID newEndStationId = UUID.fromString(scanner.nextLine());
        Station newEndStation = stationService.getStationById(newEndStationId);
        if (newEndStation == null) {
            System.out.println("Invalid end station ID. Operation canceled.");
            return;
        }

        existingJourney.setStartStation(newStartStation);
        existingJourney.setEndStation(newEndStation);

        journeyService.updateJourney(existingJourney);
        System.out.println("\nJourney updated successfully!\n");
    }


    private void deleteJourney() {
        System.out.print("Enter journey ID to delete: ");
        UUID id = UUID.fromString(scanner.nextLine());
        journeyService.deleteJourney(id);
        System.out.println("\nJourney deleted successfully!\n");
    }

    private void listAllStations(List<Station> stations) {
        System.out.println("\n--- List of Available Stations ---");
        for (Station station : stations) {
            System.out.println("ID: " + station.getId());
            System.out.println("Name: " + station.getName());
            System.out.println("Location: " + station.getLocation());
            System.out.println("-----------------------------");
        }
    }

}
