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

    public boolean createUser(String username, String password, LocalDate birthday, String fName, String lName, String address){
        return model.createUser(username, password, birthday, fName, lName, address);
    }

    public String search(String username){
        Map result = model.readUser(username);
        if(result.get("password") == null){
            return "Username is not exist";
        }
        return "Username - " + username + "\n" +
                "Password - " + result.get("password") + "\n" +
                "Name - " + result.get("fName") + " " + result.get("lName") + "\n" +
                "City - " + result.get("city") + "\n" +
                "Birthday - " + result.get("birthday");
    }

    public boolean userExist(String username){
        return model.isExist(username);
    }

    public boolean updateUser(String username, Map <String,String> newInfo){
        return model.updateUser(username,newInfo);
    }

    public boolean deleteUser(String username){
        return model.deleteUser(username);
    }
}
