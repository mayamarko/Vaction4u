package view;

import javafx.beans.property.SimpleStringProperty;

import java.awt.*;


public class tradeShow {
    private final SimpleStringProperty vacId;
    private final SimpleStringProperty destination;
    private Checkbox chooseVac;

    public tradeShow(String vacId, String destination) {
        this.vacId = new SimpleStringProperty(vacId);
        this.destination = new SimpleStringProperty(destination);
        chooseVac=new Checkbox();


    }

    public Checkbox getChooseVac() {
        return chooseVac;
    }

    public void setChooseVac(Checkbox chooseVac) {
        this.chooseVac = chooseVac;
    }

    public String getVacId() {
        return vacId.get();
    }

    public SimpleStringProperty vacIdProperty() {
        return vacId;
    }

    public void setVacId(String vacId) {
        this.vacId.set(vacId);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }
}
