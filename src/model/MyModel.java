package model;

import java.util.Observable;

public class MyModel extends Observable implements IModel{
    @Override
    public void create(String username, String password, int dd, int mm, int yy, String fName, String lName, String address) {

    }

    @Override
    public String read(String username) {
        return null;
    }

    @Override
    public void update(String username, int index, String newInfo) {

    }

    @Override
    public void delete(String username) {

    }
}
