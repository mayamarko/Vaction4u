package model;

import java.io.InputStream;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public interface IModel {
    //void createNewDatabase(String url);
    boolean createUser(String username, String password, LocalDate birthday, String fName, String lName, String address, String mail, byte[] image);

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

    boolean addSale(String usernameSeller, int vacId ,int price, String airline, LocalDate start, LocalDate returnDate, boolean baggage, String baggageDescription, int numberOfTickets,
                           int numberOfAdults, int numberOfChilds, int numberOfInfants,
                           boolean partialPurchase, String destination, boolean flightBack, boolean direct, String vacationType, boolean accommodation, String usernameBuyer);

    boolean deleteVacation(String username, int vacID);

    HashMap<Integer,String[]> showAllVacations();

    boolean add_message(String src_username, String dest_username, String message_time, String message_text,String massage_type);

    void update_read_messages(String dest_username);

    boolean un_read_messages(String dest_username);

    Stack get_Users_messages(String dest_username);

    Stack get_two_Users_messages(String src_username,String dest_username);

    Vacation readVacation(int vacationID);

    void deleteMessage(String source, String destination, String message);

    boolean is_messg_Exist(String source, String destination, String message);
    boolean delete_all_Vacation_by_user(String username) ;
    void delete_all_Message_by_user(String user_name) ;
    String[] show_Vacation_by_vid(int vid) ;









    }


