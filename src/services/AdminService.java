package services;

import core.CityTrie;
import core.FlightRouteGraph;
import database.FlightDatabase;
import models.Flight;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AdminService {
    private FlightDatabase flightDatabase;
    private CityTrie cityTrie;
    private FlightRouteGraph routeGraph;

    public AdminService(FlightDatabase flightDatabase, CityTrie cityTrie, FlightRouteGraph routeGraph) {
        this.flightDatabase = flightDatabase;
        this.cityTrie = cityTrie;
        this.routeGraph = routeGraph;
    }

    public void addFlight(String flightNumber, String airline, String source, String destination,
                          LocalDateTime departureTime, LocalDateTime arrivalTime, int totalSeats, double price) {
        Flight flight = new Flight(flightNumber, airline, source, destination, departureTime, arrivalTime, totalSeats, price);
        flightDatabase.addFlight(flight);
        cityTrie.insert(source);
        cityTrie.insert(destination);
        routeGraph.addRoute(source, destination, 1);
    }

    public boolean modifyFlight(String flightNumber, LocalDateTime newDepartureTime, LocalDateTime newArrivalTime, double newPrice) {
        Flight flight = flightDatabase.getFlight(flightNumber);
        if (flight != null) {
            flightDatabase.removeFlight(flightNumber);
            Flight newFlight = new Flight(flightNumber, flight.getAirline(), flight.getSource(), flight.getDestination(),
                    newDepartureTime, newArrivalTime, flight.getTotalSeats(), newPrice);
            flightDatabase.addFlight(newFlight);
            return true;
        }
        return false;
    }

    public boolean removeFlight(String flightNumber) {
        return flightDatabase.removeFlight(flightNumber);
    }

    public String generateSeatReport(String flightNumber) {
        Flight flight = flightDatabase.getFlight(flightNumber);
        if (flight == null) {
            return "Flight not found.";
        }
        int available = flight.getAvailableSeatsCount();
        int booked = flight.getTotalSeats() - available;
        return String.format("Flight %s: %d seats booked, %d seats available.", flightNumber, booked, available);
    }

    public void viewAllFlights() {
        ArrayList<Flight> flights = flightDatabase.getAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            flights.forEach(System.out::println);
        }
    }
}