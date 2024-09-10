package net.axel.presentations;

import net.axel.models.entities.Station;
import net.axel.services.implementations.StationService;
import net.axel.services.interfaces.IStationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class StationUi {

    private final IStationService stationService;
    private final Scanner scanner;

    public StationUi(StationService stationService) {
        this.stationService = stationService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n--- Station Management ---");
            System.out.println("1. Add Station");
            System.out.println("2. Get Station Details");
            System.out.println("3. Get All Stations");
            System.out.println("4. Update Station");
            System.out.println("5. Delete Station");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addStation();
                case 2 -> getStationById();
                case 3 -> getAllStations();
                case 4 -> updateStation();
                case 5 -> deleteStation();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);
    }

    public void addStation() {
        System.out.println("\n--- Add New Station ---");

        System.out.print("Start Station: ");
        String startStation = scanner.nextLine();
        if (startStation.trim().isEmpty()) {
            System.out.println("Start Station cannot be empty.");
            return;
        }

        System.out.print("End Station: ");
        String endStation = scanner.nextLine();
        if (endStation.trim().isEmpty()) {
            System.out.println("End Station cannot be empty.");
            return;
        }

        System.out.print("Start Date (YYYY-MM-DD): ");
        String startDateInput = scanner.nextLine();
        if (startDateInput.trim().isEmpty()) {
            System.out.println("Start Date cannot be empty.");
            return;
        }
        LocalDate startDate;
        try {
            startDate = LocalDate.parse(startDateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        Station station = new Station(UUID.randomUUID(), startStation, endStation, startDate);
        stationService.addStation(station);

        System.out.println("Station added successfully!");
    }

    public void getStationById() {
        System.out.println("\n--- Get Station by ID ---");
        System.out.print("Enter Station ID: ");
        String idInput = scanner.nextLine();
        if (idInput.trim().isEmpty()) {
            System.out.println("Station ID cannot be empty.");
            return;
        }

        UUID id;
        try {
            id = UUID.fromString(idInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Station ID format.");
            return;
        }

        Station station = stationService.getStationById(id);
        if (station != null) {
            System.out.println("Station Details: " + station);
        } else {
            System.out.println("Station not found.");
        }
    }

    public void getAllStations() {
        System.out.println("\n--- All Stations ---");
        List<Station> stations = stationService.getAllStations();
        if (stations.isEmpty()) {
            System.out.println("No stations found.");
        } else {
            stations.forEach(System.out::println);
        }
    }

    public void updateStation() {
        System.out.println("\n--- Update Station ---");

        System.out.print("Enter Station ID to update: ");
        String idInput = scanner.nextLine();
        if (idInput.trim().isEmpty()) {
            System.out.println("Station ID cannot be empty.");
            return;
        }

        UUID id;
        try {
            id = UUID.fromString(idInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Station ID format.");
            return;
        }

        Station station = stationService.getStationById(id);
        if (station != null) {
            System.out.print("New Start Station: ");
            String newStartStation = scanner.nextLine();
            if (newStartStation.trim().isEmpty()) {
                System.out.println("Start Station cannot be empty.");
                return;
            }

            System.out.print("New End Station: ");
            String newEndStation = scanner.nextLine();
            if (newEndStation.trim().isEmpty()) {
                System.out.println("End Station cannot be empty.");
                return;
            }

            System.out.print("New Start Date (YYYY-MM-DD): ");
            String newStartDateInput = scanner.nextLine();
            if (newStartDateInput.trim().isEmpty()) {
                System.out.println("Start Date cannot be empty.");
                return;
            }
            LocalDate newStartDate;
            try {
                newStartDate = LocalDate.parse(newStartDateInput);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                return;
            }

            station.setStartStation(newStartStation);
            station.setEndStation(newEndStation);
            station.setStartDate(newStartDate);

            stationService.updateStation(station);
            System.out.println("Station updated successfully!");
        } else {
            System.out.println("Station not found.");
        }
    }

    public void deleteStation() {
        System.out.println("\n--- Delete Station ---");
        System.out.print("Enter Station ID to delete: ");
        String idInput = scanner.nextLine();
        if (idInput.trim().isEmpty()) {
            System.out.println("Station ID cannot be empty.");
            return;
        }

        UUID id;
        try {
            id = UUID.fromString(idInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Station ID format.");
            return;
        }

        stationService.deleteStation(id);
        System.out.println("Station deleted successfully!");
    }
}