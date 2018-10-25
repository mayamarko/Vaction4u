package view;

import controller.VacationController;
import javafx.fxml.FXML;
import controller.VacationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;

public class SearchUserController {


    @FXML
    public javafx.scene.control.TextField lbl_username;
    public javafx.scene.control.Button btn_search;
    public javafx.scene.control.TextArea lbl_result;

    private MainController mainController;
    private VacationController vacationController;

    public void injectMainController(MainController mainController, VacationController vacationController) {
        this.mainController = mainController;
        this.vacationController = vacationController;
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        alert.setTitle("Please note");
        alert.setHeaderText(alertMessage);
    }

    @FXML
    private void searchUser() {
        if(lbl_username.getText() == null){
            showAlert("Please write the username you want to search");
        }
        String res = vacationController.search(lbl_username.getText());
        lbl_result.setText(res);
    }

}
