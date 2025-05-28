import core.CityTrie;
import core.FlightRouteGraph;
import database.FlightDatabase;
import database.UserDatabase;
import models.Booking;
import models.Flight;
import models.Passenger;
import resource.SeatClass;
import services.AdminService;
import services.BookingService;
import services.FlightSearchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    private static FlightDatabase FLIGHT_DATABASE = new FlightDatabase();
    private static UserDatabase USER_DATABASE = new UserDatabase();
    private static CityTrie CITY_TRIE = new CityTrie();
    private static FlightRouteGraph ROUTE_GRAPH = new FlightRouteGraph();
    private static FlightSearchService FLIGHT_SEARCH_SERVICE = new FlightSearchService(FLIGHT_DATABASE, CITY_TRIE, ROUTE_GRAPH);

    private static BookingService BOOKING_SERVICE = new BookingService(FLIGHT_DATABASE, USER_DATABASE);
    private static AdminService ADMIN_SERVICE = new AdminService(FLIGHT_DATABASE, CITY_TRIE, ROUTE_GRAPH);
    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        initializeSampleData();
        while (true){
            System.out.println("\n=== Flight Reservation System ===");
            System.out.println("1. User Login");
            System.out.println("2. Register");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    userMenu();
                    break;
                case 2:
                    registerUser();
                    break;
                case 3:
                    adminMenu();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

    }


    private static void initializeSampleData() {

        // Sample cities
        CITY_TRIE.insert("New York");
        CITY_TRIE.insert("Los Angeles");
        CITY_TRIE.insert("Chicago");
        CITY_TRIE.insert("Miami");

        // Sample routes
        ROUTE_GRAPH.addRoute("New York", "Los Angeles", 1);
        ROUTE_GRAPH.addRoute("New York", "Chicago", 1);
        ROUTE_GRAPH.addRoute("Chicago", "Los Angeles", 1);
        ROUTE_GRAPH.addRoute("Miami", "New York", 1);

        // Sample flights
        ADMIN_SERVICE.addFlight("AA101", "American Airlines", "New York", "Los Angeles",
                LocalDateTime.of(2025, 6, 1, 8, 0), LocalDateTime.of(2025, 6, 1, 11, 0), 100, 200.0);
        ADMIN_SERVICE.addFlight("AA102", "American Airlines", "Chicago", "Los Angeles",
                LocalDateTime.of(2025, 6, 1, 9, 0), LocalDateTime.of(2025, 6, 1, 11, 30), 80, 180.0);
        ADMIN_SERVICE.addFlight("AA103", "American Airlines", "New York", "Chicago",
                LocalDateTime.of(2025, 6, 1, 7, 0), LocalDateTime.of(2025, 6, 1, 8, 30), 120, 150.0);



    }

    private static void registerUser() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        if (USER_DATABASE.emailExit(email)) {
            System.out.println("Email already exists.");
            return;
        }
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter Passport Number: ");
        String passport = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        Passenger passenger = new Passenger(UUID.randomUUID().toString(), name, email, phone, passport, address);
        USER_DATABASE.addPassenger(passenger, password);
        System.out.println("Registration successful! Passenger ID: " + passenger.getID());
    }

    private static void userMenu() {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        Passenger passenger = USER_DATABASE.authenticate(email, password);
        if (passenger == null) {
            System.out.println("Invalid credentials.");
            return;
        }

        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Search Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    searchFlights();
                    break;
                case 2:
                    bookFlight(passenger);
                    break;
                case 3:
                    cancelBooking(passenger);
                    break;
                case 4:
                    viewBookings(passenger);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

    }

    private static void searchFlights() {
        System.out.print("Enter Source City (or prefix): ");
        String sourcePrefix = sc.nextLine();
        System.out.println("Suggested cities: " + FLIGHT_SEARCH_SERVICE.suggestCities(sourcePrefix));
        System.out.print("Enter Source City: ");
        String source = sc.nextLine();
        System.out.print("Enter Destination City: ");
        String destination = sc.nextLine();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        ArrayList<Flight> directFlights = FLIGHT_SEARCH_SERVICE.searchDirectFlights(source, destination, date);
        System.out.println("\nDirect Flights:");
        directFlights.forEach(System.out::println);

        ArrayList<ArrayList<Flight>> connectingFlights = FLIGHT_SEARCH_SERVICE.searchConnectingFlights(source, destination, date);
        System.out.println("\nConnecting Flights:");
        for (ArrayList<Flight> leg : connectingFlights) {
            System.out.println("Connection: " + leg);
        }
    }

    private static void bookFlight(Passenger passenger) {
        System.out.print("Enter Flight Number: ");
        String flightNumber = sc.nextLine();
        System.out.print("Enter Seat Class (ECONOMY/BUSINESS): ");
        SeatClass seatClass = SeatClass.valueOf(sc.nextLine().toUpperCase());

        Booking booking = BOOKING_SERVICE.bookFlight(flightNumber, passenger.getID(), seatClass);
        if (booking != null) {
            System.out.println("Booking successful: " + booking);
        } else {
            System.out.println("Booking failed. No available seats or invalid flight.");
        }
    }

    private static void cancelBooking(Passenger passenger) {
        System.out.print("Enter Booking ID: ");
        String bookingId = sc.nextLine();
        if (BOOKING_SERVICE.cancelBooking(bookingId)) {
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Cancellation failed. Invalid booking ID or already cancelled.");
        }
    }

    private static void viewBookings(Passenger passenger) {
        ArrayList<Booking> bookings = BOOKING_SERVICE.getPassengerBookings(passenger.getID());
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            bookings.forEach(System.out::println);
        }
    }

    private static void adminMenu() {
        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine();
        if (!password.equals("admin123")) {
            System.out.println("Invalid admin password.");
            return;
        }

        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Flight");
            System.out.println("2. Modify Flight");
            System.out.println("3. Remove Flight");
            System.out.println("4. Generate Seat Report");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addFlight();
                    break;
                case 2:
                    modifyFlight();
                    break;
                case 3:
                    removeFlight();
                    break;
                case 4:
                    generateSeatReport();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addFlight() {
        System.out.print("Enter Flight Number: ");
        String flightNumber = sc.nextLine();
        System.out.print("Enter Airline: ");
        String airline = sc.nextLine();
        System.out.print("Enter Source City: ");
        String source = sc.nextLine();
        System.out.print("Enter Destination City: ");
        String destination = sc.nextLine();
        System.out.print("Enter Departure Time (yyyy-MM-dd HH:mm): ");
        LocalDateTime departureTime = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.print("Enter Arrival Time (yyyy-MM-dd HH:mm): ");
        LocalDateTime arrivalTime = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.print("Enter Total Seats: ");
        int totalSeats = sc.nextInt();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        ADMIN_SERVICE.addFlight(flightNumber, airline, source, destination, departureTime, arrivalTime, totalSeats, price);
        System.out.println("Flight added successfully.");
    }

    private static void modifyFlight() {
        System.out.print("Enter Flight Number: ");
        String flightNumber = sc.nextLine();
        System.out.print("Enter New Departure Time (yyyy-MM-dd HH:mm): ");
        LocalDateTime departureTime = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.print("Enter New Arrival Time (yyyy-MM-dd HH:mm): ");
        LocalDateTime arrivalTime = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.print("Enter New Price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        if (ADMIN_SERVICE.modifyFlight(flightNumber, departureTime, arrivalTime, price)) {
            System.out.println("Flight modified successfully.");
        } else {
            System.out.println("Flight modification failed.");
        }
    }

    private static void removeFlight() {
        System.out.print("Enter Flight Number: ");
        String flightNumber = sc.nextLine();
        if (ADMIN_SERVICE.removeFlight(flightNumber)) {
            System.out.println("Flight removed successfully.");
        } else {
            System.out.println("Flight removal failed.");
        }
    }
    private static void generateSeatReport() {
        System.out.print("Enter Flight Number: ");
        String flightNumber = sc.nextLine();
        System.out.println(ADMIN_SERVICE.generateSeatReport(flightNumber));
    }

}