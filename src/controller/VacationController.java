package controller;

import model.IModel;

import java.time.LocalDate;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class VacationController extends Observable implements Observer{
    private IModel model;

    //if user is logged in.
    private boolean logged = false;

    public VacationController(IModel model) { this.model = model;}


    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
            setChanged();
            notifyObservers();
        }
    }

    public boolean signIn(String username, String password, LocalDate birthday, String fName, String lName, String address){
        return model.createUser(username, password, birthday, fName, lName, address);
    }

    public String search(String username){
        Map result = model.readUser(username);
        return "Username " + username + "is " + result.get("fName") + " " + result.get("lName") + " lives in "
                + result.get("city");
    }
    public void updateUser(String username, Map <String,String> newInfo){
        model.updateUser(username,newInfo);
    }
}
