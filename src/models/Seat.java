package models;

import resource.SeatClass;

public class Seat {
    private int seatNumber;
    private boolean available;
    private SeatClass seatClass;

    public Seat(int seatNumber, boolean available) {
        this.seatNumber = seatNumber;
        this.available = available;
        // Assign seat class based on seat number (business class typically first 20 seats)
        this.seatClass = seatNumber <= 20 ? SeatClass.BUSINESS : SeatClass.ECONOMY;
    }

    // Getters and setters
    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
