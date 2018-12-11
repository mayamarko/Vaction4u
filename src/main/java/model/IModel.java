package model;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;

public interface IModel {
    //void createNewDatabase(String url);
    boolean createUser(String username, String password, LocalDate birthday, String fName, String lName, String address,String mail, byte[] image);

    Map readUser(String username);

    boolean updateUser(String username, Map<String, String> newInfo);

    void updatePicture(String username, byte[] picture);

    boolean deleteUser(String username);

    boolean isExist(String username);

    boolean logIn(String username, String password);

    byte[] readPicture(String username);

    boolean addAccommodation(String username, String placeName, String address, int grade);

    //boolean addTickets(String username, int ticketID, String ticketType);

    boolean createVacation(String username, int price, String airline, LocalDate start, LocalDate end, boolean baggage, String baggageDescription, int numberOfTickets,
                           int numberOfAdults, int numberOfChilds, int numberOfInfants,
                           boolean partialPurchase, String destination, boolean flightBack, boolean direct, String vacationType, boolean accommodation);
    }
