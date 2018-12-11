package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;


public class SellerPaymentDetailController {

    @FXML
    public javafx.scene.control.TextField lbl_numberAccount;
    public javafx.scene.control.TextField lbl_bank;
    public javafx.scene.control.TextField lbl_bankBranch;
    public javafx.scene.control.TextField lbl_fullName;
    public javafx.scene.control.TextField lbl_payPalUsername;
    public javafx.scene.control.Button btn_OK;

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
    private void saveDetails() {

    }
}


