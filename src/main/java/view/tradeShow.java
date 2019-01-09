package view;

import controller.VacationController;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.*;

import static view.VacationShow.addVacs;
import static view.VacationShow.remove;


public class tradeShow {
    private final SimpleStringProperty vacId;
    private final SimpleStringProperty destination;
    private CheckBox chooseVac;
    private VacationController vacationController;

    public tradeShow(String vacId, String destination, VacationController vacationController) {
        this.vacId = new SimpleStringProperty(vacId);
        this.destination = new SimpleStringProperty(destination);
        this.vacationController = vacationController;
        chooseVac = new CheckBox();
        chooseVac.setOnAction((event) -> {
            if (chooseVac.isSelected()) {
                if (vacationController.getVacStatus(Integer.parseInt(vacId)) == 0) {
                    if (!addVacs(vacId)) {
                        chooseVac.setSelected(false);
                        showAlert("You can only choose one vacation to trade!");
                    }
                } else {
                    showAlert("Oops you already traded or sold this vacation.");
                }
            }
            if (!chooseVac.isSelected()) {
                remove(vacId);
            }

        });

    }

    public CheckBox getChooseVac() {
        return chooseVac;
    }

    public void setChooseVac(CheckBox chooseVac) {
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

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.show();
        alert.setHeaderText(alertMessage);
    }
}
