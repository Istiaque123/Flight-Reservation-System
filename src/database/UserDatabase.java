package database;

import models.Passenger;

import java.util.HashMap;

public class UserDatabase {

    private HashMap<String, Passenger> passengerById;
    private HashMap<String, String> credentials;

    public UserDatabase() {
        this.passengerById = new HashMap<>();
        this.credentials = new HashMap<>();
    }

    public void addPassenger(Passenger passenger, String password){
        passengerById.put(passenger.getID(), passenger);
        credentials.put(passenger.getEMAIL(), password);
    }

    public Passenger getPassenger(String passengerId){
        return passengerById.get(passengerId);
    }

    public Passenger authenticate(String email, String password){
        String tempPassword = credentials.get(email);
        if (tempPassword != null && tempPassword.equals(password)) {
            return passengerById.values().stream()
                    .filter(p -> p.getEMAIL().equals(email))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean emailExit(String email){
        return credentials.containsKey(email);
    }
}
