package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int totalSeats;
    private ArrayList<Seat> seats;
    private double price;
    private String airline;

    public Flight(String flightNumber, String airline, String source, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime,
                  int totalSeats, double price) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.price = price;
        this.seats = new ArrayList<>();
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 1; i <= totalSeats; i++) {
            seats.add(new Seat(i, true));
        }
    }

    // Getters and setters
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public double getPrice() {
        return price;
    }

    public boolean isSeatAvailable(int seatNumber) {
        if (seatNumber < 1 || seatNumber > totalSeats) return false;
        return seats.get(seatNumber - 1).isAvailable();
    }

    public boolean bookSeat(int seatNumber) {
        if (isSeatAvailable(seatNumber)) {
            seats.get(seatNumber - 1).setAvailable(false);
            return true;
        }
        return false;
    }

    public int bookNextAvailableSeat() {
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                seat.setAvailable(false);
                return seat.getSeatNumber();
            }
        }
        return -1;
    }

    public void cancelSeat(int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= totalSeats) {
            seats.get(seatNumber - 1).setAvailable(true);
        }
    }

    public int getAvailableSeatsCount() {
        return (int) seats.stream().filter(Seat::isAvailable).count();
    }

    @Override
    public String toString() {
        return String.format("%s %s: %s to %s | Dep: %s | Arr: %s | Available: %d/%d | Price: $%.2f",
                airline, flightNumber, source, destination,
                departureTime, arrivalTime, getAvailableSeatsCount(), totalSeats, price);
    }
}