package controller;

import model.IModel;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class VacationController extends Observable implements Observer {
    private IModel model;

    //if user is logged in.
    private boolean logged = false;
    public String username = "";

    public VacationController(IModel model) {
        this.model = model;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            setChanged();
            notifyObservers();
        }
    }

    public boolean createUser(String username, String password, LocalDate birthday, String fName, String lName, String address, String mail, byte[] image) {
        return model.createUser(username, password, birthday, fName, lName, address, mail, image);
    }

    public String search(String username) {
        Map result = model.readUser(username);
        if (result.get("password") == null) {
            return "Username is not exist";
        }
        return "Username - " + username + "\n" +
                "Password - " + result.get("password") + "\n" +
                "Name - " + result.get("fName") + " " + result.get("lName") + "\n" +
                "City - " + result.get("city") + "\n" +
                "Birthday - " + result.get("birthday") + "\n" +
                "Email - " + result.get("email");
    }

    public byte[] getImage(String username) {
        if (userExist(username)) {
            return model.readPicture(username);
        } else {
            return null;
        }
    }

    public ResultSet showAllVacations(){
        return model.showAllVacations();
    }

    public boolean userExist(String username) {
        return model.isExist(username);
    }

    public boolean updateUser(String username, Map<String, String> newInfo) {
        return model.updateUser(username, newInfo);
    }

    public void updatePicture(String username, byte[] picture) {
        model.updatePicture(username, picture);
    }

    public boolean isLogged() {
        return logged;
    }

    public String getUsername() {
        return username;
    }

    public boolean logIn(String username, String password) {
        logged = model.logIn(username, password);
        if (logged)
            this.username = username;
        return logged;
    }

    public boolean deleteUser(String username) {
        return model.deleteUser(username);
    }

    public boolean createVacation(String username, int price, String airline, LocalDate start, LocalDate end, boolean baggage, String baggageDescription, int numberOfTickets,
                                  int numberOfAdults, int numberOfChilds, int numberOfInfants, boolean partialPurchase, String destination, boolean flightBack, boolean direct, String vacationType, boolean accommodation) {
        return model.createVacation(username, price, airline, start, end, baggage, baggageDescription, numberOfTickets,
                numberOfAdults, numberOfChilds, numberOfInfants, partialPurchase, destination, flightBack, direct, vacationType, accommodation);
    }

    /*public boolean addTickets(String username, int ticketID, String ticketType){
        return model.addTickets(username, ticketID, ticketType);
    }*/

    public  boolean addAccommodation(String username, String placeName, String address, int grade){
        return model.addAccommodation(username, placeName, address, grade);
    }
}
