package database;

import models.Flight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class FlightDatabase {
    private HashMap<String, Flight> flightByNumber;
    private HashMap<String, ArrayList<Flight>> flightByRoute;
    private HashMap<LocalDate, ArrayList<Flight>> flightByDate;

    public FlightDatabase(){
        this.flightByDate = new HashMap<>();
        this.flightByNumber = new HashMap<>();
        this.flightByRoute = new HashMap<>();
    }

    public void addFlight(Flight flight){
        flightByNumber.put(flight.getFlightNumber(), flight);

        String destination = flight.getSource() +"-"+ flight.getDestination();
        flightByRoute.computeIfAbsent(destination, k -> new ArrayList<>() ).add(flight);

        LocalDate date = flight.getDepartureTime().toLocalDate();
        flightByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(flight);

    }

    public Flight getFlight(String flightNumber) {
        return flightByNumber.get(flightNumber);
    }

    public ArrayList<Flight> getFlightByRoute(String source, String destination){
        String routeKey = source + "-" + destination;
        return flightByRoute.getOrDefault(routeKey, new ArrayList<>());
    }

    public ArrayList<Flight> getFlightsByDate(LocalDate date) {
        return flightByDate.getOrDefault(date, new ArrayList<>());
    }

    public ArrayList<Flight> getAllFlights() {
        return new ArrayList<>(flightByNumber.values());
    }

    public boolean removeFlight(String flightNumber) {
        Flight flight = flightByNumber.remove(flightNumber);
        if (flight != null) {
            String routeKey = flight.getSource() + "-" + flight.getDestination();
            flightByRoute.get(routeKey).remove(flight);

            LocalDate date = flight.getDepartureTime().toLocalDate();
            flightByDate.get(date).remove(flight);
            return true;
        }
        return false;
    }

}
