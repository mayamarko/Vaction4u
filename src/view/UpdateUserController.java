package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.time.LocalDate;
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
    public javafx.scene.control.Button btn_saveChanges;

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
    private void Update() {
        Map<String, String> newInfo = new HashMap<>();
        if (!lbl_fName.getText().trim().isEmpty())
            newInfo.put("fName", lbl_fName.getText());
        if (!lbl_lName.getText().trim().isEmpty())
            newInfo.put("lName", lbl_lName.getText());
        if (!lbl_password.getText().trim().isEmpty())
            newInfo.put("password", lbl_password.getText());
        if (!lbl_city.getText().trim().isEmpty())
            newInfo.put("address", lbl_city.getText());
        if (lbl_birthday.getValue() != null)
            newInfo.put("birthday", lbl_birthday.getValue().toString());

        if(vacationController.updateUser(lbl_username.getText(), newInfo)){
              showAlert("Changes saved");
        }
        else
            showAlert("Username is not exist!");
    }
}
