package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.Period;

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
    private void signIn() {

        if (lbl_fName.getText().trim().isEmpty() || lbl_lName.getText().trim().isEmpty() || lbl_password.getText().trim().isEmpty() ||
                lbl_city.getText().trim().isEmpty() || lbl_birthday.getValue() == null) {
            showAlert("One or more details are missing");
        } else {
            if (Period.between(lbl_birthday.getValue(), LocalDate.now()).getYears() < 18) {
                showAlert("Users have to be 18 and older");
            } else {
                boolean succeedLogged = vacationController.createUser(lbl_username.getText(), lbl_password.getText(), lbl_birthday.getValue(), lbl_fName.getText(), lbl_lName.getText(), lbl_city.getText());

                if (succeedLogged) {
                    showAlert(lbl_username.getText() + " is registered");
                } else {
                    showAlert("The username " + lbl_username.getText() + " is taken, please choose another one.");
                }
            }
        }
    }

}
