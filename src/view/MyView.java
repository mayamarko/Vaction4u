package view;

import controller.VacationController;
import javafx.fxml.FXML;

import java.util.Observable;
import java.util.Observer;

public class MyView implements Observer, IView {

    @FXML
    private VacationController vacationController;

    @Override
    public void displayApp(){}

    @Override
    public void update(Observable o, Object arg){

    }

    public void setViewModel(VacationController controller) {
        this.vacationController = controller;
        //bindProperties(controller);
    }


}
