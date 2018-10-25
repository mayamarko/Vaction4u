package model;

import java.time.LocalDate;
import java.util.Map;

public interface IModel {
    //void createNewDatabase(String url);
    boolean createUser(String username, String password, LocalDate birthday, String fName, String lName, String address);
    Map readUser(String username);
    //TODO:: check if can change username and bday....
    void updateUser(String username, Map<String, String> newInfo);
    void deleteUser(String username);
}
