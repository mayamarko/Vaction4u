package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.Vacation;

import java.util.HashMap;
import java.util.Map;

public class FullInfoController {

    private MainController mainController;
    private VacationController vacationController;
    private VacationShow vacationShow;
    @FXML
    public javafx.scene.control.TextArea txt_Info;

    @FXML
    private void initialize() {
        //text.set("Enter username");
    }

    public void injectMainController(MainController mainController, VacationController vacationController,VacationShow vacationShow) {
        this.mainController = mainController;
        this.vacationController = vacationController;
        this.vacationShow=vacationShow;
    }


    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        alert.setTitle("Please note");
        alert.setHeaderText(alertMessage);
    }

    @FXML
    private void Show() {
        txt_Info.setText(vacationShow.getSaveInfo());
    }

}
