package services;

import core.CityTrie;
import core.FlightRouteGraph;
import database.FlightDatabase;
import models.Flight;
import resource.SeatClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class FlightSearchService {
    private FlightDatabase flightDatabase;
    private CityTrie cityTrie;
    private FlightRouteGraph routeGraph;

    public FlightSearchService(FlightDatabase flightDatabase, CityTrie cityTrie, FlightRouteGraph routeGraph) {
        this.flightDatabase = flightDatabase;
        this.cityTrie = cityTrie;
        this.routeGraph = routeGraph;
    }

    public ArrayList<Flight> searchDirectFlights(String source, String destination, LocalDate date) {
        return (ArrayList<Flight>) flightDatabase.getFlightByRoute(source, destination).stream()
                .filter(flight -> flight.getDepartureTime().toLocalDate().equals(date))
                .filter(flight -> flight.getAvailableSeatsCount() > 0)
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .collect(Collectors.toList());
    }

    public ArrayList<String> suggestCities(String prefix) {
        return cityTrie.searchPrefix(prefix);
    }

    public ArrayList<ArrayList<Flight>> searchConnectingFlights(String source, String destination, LocalDate date) {
        ArrayList<ArrayList<Flight>> connectingFlights = new ArrayList<>();
        ArrayList<String> path = routeGraph.getShortestPaths(source, destination);

        if (path.size() <= 2) {
            return connectingFlights;
        }

        ArrayList<Flight> currentLeg = new ArrayList<>();
        findConnectingFlights(source, destination, date, path, 1, currentLeg, connectingFlights);
        return connectingFlights;
    }

    private void findConnectingFlights(String source, String destination, LocalDate date, ArrayList<String> path,
                                       int index, ArrayList<Flight> currentLeg, ArrayList<ArrayList<Flight>> result) {
        if (index == path.size() - 1) {
            ArrayList<Flight> flights = searchDirectFlights(source, destination, date);
            for (Flight flight : flights) {
                if (isValidConnection(currentLeg, flight)) {
                    ArrayList<Flight> newLeg = new ArrayList<>(currentLeg);
                    newLeg.add(flight);
                    result.add(newLeg);
                }
            }
            return;
        }

        String nextCity = path.get(index);
        ArrayList<Flight> flights = searchDirectFlights(source, nextCity, date);
        for (Flight flight : flights) {
            if (isValidConnection(currentLeg, flight)) {
                ArrayList<Flight> newLeg = new ArrayList<>(currentLeg);
                newLeg.add(flight);
                findConnectingFlights(nextCity, destination, date, path, index + 1, newLeg, result);
            }
        }
    }

    private boolean isValidConnection(ArrayList<Flight> currentLeg, Flight nextFlight) {
        if (currentLeg.isEmpty()) {
            return true;
        }
        Flight lastFlight = currentLeg.get(currentLeg.size() - 1);
        return nextFlight.getDepartureTime().isAfter(lastFlight.getArrivalTime().plusHours(1)) &&
                nextFlight.getDepartureTime().isBefore(lastFlight.getArrivalTime().plusHours(6));
    }

    public ArrayList<Flight> searchFlightsByCriteria(String source, String destination,
                                                     LocalDate date, int minSeats,
                                                     double maxPrice, SeatClass seatClass) {
        return (ArrayList<Flight>) flightDatabase.getFlightByRoute(source, destination).stream()
                .filter(flight -> flight.getDepartureTime().toLocalDate().equals(date))
                .filter(flight -> flight.getAvailableSeatsCount() >= minSeats)
                .filter(flight -> flight.getPrice() <= maxPrice)
                .filter(flight -> hasAvailableSeatClass(flight, seatClass))
                .sorted(Comparator.comparing(Flight::getPrice))
                .collect(Collectors.toList());
    }

    private boolean hasAvailableSeatClass(Flight flight, SeatClass seatClass) {
        return flight.getSeats().stream()
                .anyMatch(seat -> seat.getSeatClass() == seatClass && seat.isAvailable());
    }

    // New method for flight details
    public String getFlightDetails(String flightNumber) {
        Flight flight = flightDatabase.getFlight(flightNumber);
        if (flight == null) {
            return "Flight not found.";
        }
        long economySeats = flight.getSeats().stream()
                .filter(seat -> seat.getSeatClass() == SeatClass.ECONOMY)
                .count();
        long businessSeats = flight.getSeats().stream()
                .filter(seat -> seat.getSeatClass() == SeatClass.BUSINESS)
                .count();
        long availableEconomy = flight.getSeats().stream()
                .filter(seat -> seat.getSeatClass() == SeatClass.ECONOMY && seat.isAvailable())
                .count();
        long availableBusiness = flight.getSeats().stream()
                .filter(seat -> seat.getSeatClass() == SeatClass.BUSINESS && seat.isAvailable())
                .count();
        return String.format(
                "Flight Number: %s\nAirline: %s\nRoute: %s to %s\nDeparture: %s\nArrival: %s\nTotal Seats: %d\n" +
                        "Available Seats: %d\nEconomy Seats: %d (Available: %d)\nBusiness Seats: %d (Available: %d)\nPrice: $%.2f",
                flight.getFlightNumber(), flight.getAirline(), flight.getSource(), flight.getDestination(),
                flight.getDepartureTime(), flight.getArrivalTime(), flight.getTotalSeats(),
                flight.getAvailableSeatsCount(), economySeats, availableEconomy, businessSeats, availableBusiness,
                flight.getPrice());
    }
}