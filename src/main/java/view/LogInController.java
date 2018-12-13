
package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;


public class LogInController {

    @FXML
    public javafx.scene.control.TextField lbl_username;
    public javafx.scene.control.TextField lbl_password;
    public javafx.scene.control.Button btn_logIn;

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
    private void LogIn() {
        if (lbl_username.getText().trim().isEmpty()||lbl_password.getText().trim().isEmpty()) {
            showAlert("One or more of your details are missing.");
        } else {
            boolean succeed = vacationController.logIn(lbl_username.getText(),lbl_password.getText());
            if (succeed) {
                showAlert("Welcome back, " + lbl_username.getText() + "\nFind your next vacation!");
            } else {
                if(lbl_username.getText().equals("admin") && lbl_password.getText().equals("admin")){
                    vacationController.setLogged(true);
                    vacationController.setUsername("admin");
                    showAlert("You are in admin mode");
                }
                else {
                    showAlert("One or more of your details are incorrect.");
                }
            }
        }
    }
}


