package services;

import core.AVLTree;
import database.FlightDatabase;
import database.UserDatabase;
import models.Booking;
import models.Flight;
import models.Passenger;
import models.Seat;
import resource.BookingStatus;
import resource.SeatClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingService {
    private FlightDatabase flightDatabase;
    private UserDatabase userDatabase;
    private HashMap<String, Booking> bookingHashMap;
    private AVLTree seatManager;


    public BookingService(FlightDatabase flightDatabase, UserDatabase userDatabase){
        this.flightDatabase = flightDatabase;
        this.userDatabase = userDatabase;
        this.bookingHashMap = new HashMap<>();
        this.seatManager = new AVLTree();

    }

    public Booking bookFlight(String flightNumber, String passengerId, SeatClass seatClass){
        Flight flight = flightDatabase.getFlight(flightNumber);
        Passenger passenger = userDatabase.getPassenger(passengerId);

        if (flight == null || passenger == null){
            return null;
        }
        int seatNumber = -1;

        for (Seat seat: flight.getSeats()){
            if (seat.isAvailable() && seat.getSeatClass() == seatClass) {
                seatNumber = seat.getSeatNumber();
                break;
            }
        }
        if (seatNumber == -1 || !flight.bookSeat(seatNumber)) {
            return null;
        }

        seatManager.updateSeatAvailability(seatNumber, false);
        String bookingId = UUID.randomUUID().toString();
        double price = flight.getPrice() * (seatClass == SeatClass.BUSINESS? 1.5 : 1.0);
        Booking booking = new Booking(bookingId, flightNumber, passengerId, seatNumber, price);
        booking.confirmPayment();
        bookingHashMap.put(bookingId, booking);
        return  booking;
    }


    public boolean cancelBooking(String bookingId){
        Booking booking = bookingHashMap.get(bookingId);
        if (booking == null || booking.getStatus() == BookingStatus.CANCELLED){
            return false;
        }
        Flight flight = flightDatabase.getFlight(booking.getFlightNumber());
        if (flight != null) {
            flight.cancelSeat(booking.getSeatNumber());
            seatManager.updateSeatAvailability(booking.getSeatNumber(), true);
            booking.cancel();
            booking.confirmPayment();
            return true;
        }
        return true;
    }

    public ArrayList<Booking> getPassengerBookings(String passengerId){
        return bookingHashMap.values().stream()
                .filter(b -> b.getPassengerId().equals(passengerId) && b.getStatus() == BookingStatus.CONFIRMED)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
