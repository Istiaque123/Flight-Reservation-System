package models;

import resource.BookingStatus;
import resource.PaymentStatus;

import java.time.LocalDateTime;

public class Booking {

    private String bookingId;
    private String flightNumber;
    private String passengerId;
    private int seatNumber;
    private LocalDateTime bookingTime;
    private BookingStatus status;
    private double price;
    private PaymentStatus paymentStatus;


    public Booking(String bookingId, String flightNumber, String passengerId,
                   int seatNumber, double price) {
        this.bookingId = bookingId;
        this.flightNumber = flightNumber;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
        this.bookingTime = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
        this.price = price;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public void confirmPayment() {
        this.paymentStatus = PaymentStatus.COMPLETED;
    }


    public String getBookingId() {
        return bookingId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return String.format("Booking ID: %s | Flight: %s | Passenger: %s | Seat: %d\n" +
                        "Status: %s | Payment: %s | Price: $%.2f | Booked on: %s",
                bookingId, flightNumber, passengerId, seatNumber,
                status, paymentStatus, price, bookingTime);
    }
}
