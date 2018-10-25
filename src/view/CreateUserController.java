package view;

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

public class CreateUserController {

    @FXML
    public javafx.scene.control.TextField lbl_username;
    public javafx.scene.control.TextField lbl_lName;
    public javafx.scene.control.TextField lbl_fName;
    public javafx.scene.control.TextField lbl_city;
    public javafx.scene.control.DatePicker lbl_birthday;
    public javafx.scene.control.TextField lbl_password;
    public javafx.scene.control.Button btn_signIn;

    private MainController mainController;
    private VacationController vacationController;

    public void injectMainController(MainController mainController, VacationController vacationController){
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
    private void signIn(){

        if (lbl_fName.getText() == null || lbl_lName.getText() == null || lbl_password.getText() == null ||
            lbl_city.getText() == null || lbl_birthday.getValue() == null){
            showAlert("One or more details are missing");
        }

        else {
            boolean succeedLogged = vacationController.signIn(lbl_username.getText(), lbl_password.getText(), lbl_birthday.getValue(), lbl_fName.getText(), lbl_lName.getText(), lbl_city.getText());

            if (succeedLogged) {
                showAlert(lbl_username.getText() + " is registered");
            } else {
                showAlert("The username " + lbl_username.getText() + " is taken, please choose another one.");
            }
        }
    }

}
