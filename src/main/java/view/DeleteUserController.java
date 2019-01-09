package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.Map;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DeleteUserController {

    @FXML
    public javafx.scene.control.TextField lbl_username;
    public javafx.scene.control.Button btn_delete;

    private MainController mainController;
    private VacationController vacationController;

    @FXML
    private void initialize() {
        //text.set("Enter username");
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
    private void Delete() {
        Map<String, String> newInfo = new HashMap<>();
        if (lbl_username.getText().trim().isEmpty()){
            showAlert("Please enter the username you want to delete.");
        }
        else if(lbl_username.getText().equals(vacationController.username) || vacationController.username.equals("admin")){
            boolean succeed = vacationController.deleteUser(lbl_username.getText());
            if(succeed){
                showAlert("The username " + lbl_username.getText() + " is deleted from the system successfully!");
                if(lbl_username.getText().equals(vacationController.username)) {
                    vacationController.setUsername("");
                    vacationController.setLogged(false);
                    mainController.setDelete();
                }
            }
            else{
                showAlert("The username " + lbl_username.getText() + " is not exist, try again.");
            }
        }else{
            showAlert("You can NOT delete another user, just yours...");
        }
    }


}


