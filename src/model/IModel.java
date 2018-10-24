package model;

import java.util.Map;

public interface IModel {
    //void createNewDatabase(String url);
    void createUser(String username, String password, int dd, int mm, int yy, String fName, String lName, String address);
    Map readUser(String username);
    //TODO:: check if can change username and bday....
    void updateUser(String username, Map<String, String> newInfo);
    void deleteUser(String username);
}
