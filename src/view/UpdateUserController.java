package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserController {

    @FXML
    public javafx.scene.control.TextField lbl_username;
    public javafx.scene.control.Button btn_ok;

    public javafx.scene.control.TextField lbl_fName;
    public javafx.scene.control.TextField lbl_lName;
    public javafx.scene.control.TextField lbl_password;
    public javafx.scene.control.TextField lbl_city;
    public javafx.scene.control.DatePicker lbl_birthday;
    public javafx.scene.control.TextField lbl_fName_title;
    public javafx.scene.control.TextField lbl_lName_title;
    public javafx.scene.control.TextField lbl_password_title;
    public javafx.scene.control.TextField lbl_city_title;
    public javafx.scene.control.TextField lbl_birthday_title;
    public javafx.scene.control.Button btn_saveChanges;
    public javafx.scene.control.TextField lbl_new_username;
    public javafx.scene.control.TextField lbl_new_username_title;
    public javafx.scene.control.TextField lbl_update_message_title;

    private MainController mainController;
    private VacationController vacationController;

    @FXML
    private void initialize() {
        lbl_fName_title.setVisible(false);
        lbl_birthday_title.setVisible(false);
        lbl_city_title.setVisible(false);
        lbl_password_title.setVisible(false);
        lbl_lName_title.setVisible(false);
        lbl_fName.setVisible(false);
        lbl_birthday.setVisible(false);
        lbl_city.setVisible(false);
        lbl_password.setVisible(false);
        lbl_lName.setVisible(false);
        btn_saveChanges.setVisible(false);
        btn_ok.setDisable(false);
        lbl_new_username.setVisible(false);
        lbl_new_username_title.setVisible(false);
        lbl_update_message_title.setVisible(false);
        lbl_username.setEditable(true);
    }

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
    private void Update() {
        boolean canResume = true;
        Map<String, String> newInfo = new HashMap<>();
        if (!lbl_new_username.getText().trim().isEmpty()) {
            if (vacationController.userExist(lbl_new_username.getText())) {
                canResume = false;
                showAlert("Username already exist, choose another one. \nNo changes has been made.");
            } else {
                newInfo.put("username", lbl_new_username.getText());
            }
        }
        if (!lbl_fName.getText().trim().isEmpty())
            newInfo.put("fName", lbl_fName.getText());
        if (!lbl_lName.getText().trim().isEmpty())
            newInfo.put("lName", lbl_lName.getText());
        if (!lbl_password.getText().trim().isEmpty())
            newInfo.put("password", lbl_password.getText());
        if (!lbl_city.getText().trim().isEmpty())
            newInfo.put("address", lbl_city.getText());
        if (lbl_birthday.getValue() != null) {
            if (Period.between(lbl_birthday.getValue(), LocalDate.now()).getYears() < 18) {
                showAlert("Users have to be 18 and older");
                canResume = false;
            }
            newInfo.put("birthday", lbl_birthday.getValue().toString());
        }


        if (canResume) {
            if (vacationController.updateUser(lbl_username.getText(), newInfo)) {
                showAlert("Changes saved");
            } else
                showAlert("Username is not exist!");
        }

        this.initialize();
    }

    @FXML
    private void AllowUpdate() {
        if (vacationController.userExist(lbl_username.getText())) {
            lbl_fName_title.setVisible(true);
            lbl_birthday_title.setVisible(true);
            lbl_city_title.setVisible(true);
            lbl_password_title.setVisible(true);
            lbl_lName_title.setVisible(true);
            lbl_fName.setVisible(true);
            lbl_birthday.setVisible(true);
            lbl_city.setVisible(true);
            lbl_password.setVisible(true);
            lbl_lName.setVisible(true);
            btn_saveChanges.setVisible(true);
            btn_ok.setDisable(true);
            lbl_username.setEditable(false);
            lbl_new_username.setVisible(true);
            lbl_new_username_title.setVisible(true);
            lbl_update_message_title.setVisible(true);
        } else {
            showAlert("The user " + lbl_username.getText() + " is not exist, try again.");
        }
    }
}
