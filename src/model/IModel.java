package model;

public interface IModel {
    //void createNewDatabase(String url);
    void create(String username, String password, int dd, int mm, int yy, String fName, String lName, String address);
    String read(String username);
    //TODO:: check if can change username and bday....
    void update(String username, int index, String newInfo);
    void delete(String username);
}
