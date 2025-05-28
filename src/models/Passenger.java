package models;

public class Passenger {

    private final String ID;
    private final String NAME;
    private  String EMAIL;
    private  String PHONE;
    private final String PASSPORTNUMBER;
    private  String ADDRESS;


    public Passenger(String ID, String NAME, String EMAIL, String PHONE, String PASSPORTNUMBER, String ADDRESS) {
        this.ID = ID;
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.PHONE = PHONE;
        this.PASSPORTNUMBER = PASSPORTNUMBER;
        this.ADDRESS = ADDRESS;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }



    public String getPassportNumber() {
        return PASSPORTNUMBER;
    }

    @Override
    public String toString() {
        return String.format("Passenger ID: %s\nName: %s\nEmail: %s\nPhone: %s\nPassport: %s\nAddress: %s",
                ID, NAME, EMAIL, PHONE, PASSPORTNUMBER, ADDRESS);
    }
}
