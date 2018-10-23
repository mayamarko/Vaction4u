package controller;

import model.IModel;

import java.util.Observable;
import java.util.Observer;

public class VacationController extends Observable implements Observer{
    private IModel model;

    public VacationController(IModel model) { this.model = model;}


    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
            setChanged();
            notifyObservers();
        }
    }
}
