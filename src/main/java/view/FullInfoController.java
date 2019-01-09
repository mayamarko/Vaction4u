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
    private MessageShow messageShow;
    private boolean isMessageShow;
    @FXML
    public javafx.scene.control.TextArea txt_Info;
    public javafx.scene.control.Button btn_requestPurchase;

    @FXML
    private void initialize() {
        if(!isMessageShow) {
            btn_requestPurchase.setDisable(false);
            btn_requestPurchase.setVisible(false);
        }
    }

    public void injectMainController(MainController mainController, VacationController vacationController, VacationShow vacationShow) {
        this.mainController = mainController;
        this.vacationController = vacationController;
        this.vacationShow = vacationShow;
        isMessageShow = true;
        Show();
    }

    public void injectMainController(MainController mainController, VacationController vacationController, MessageShow messageShow) {
        this.mainController = mainController;
        this.vacationController = vacationController;
        this.messageShow = messageShow;
        isMessageShow = false;
        Show();
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
        if(isMessageShow) {
            txt_Info.setText(vacationShow.getSaveInfo());
        }
        else{
            txt_Info.setText(messageShow.getSaveInfo());
        }
    }

    public void requestPurchase() {
        if(!isMessageShow) {
            btn_requestPurchase.setDisable(false);
            btn_requestPurchase.setVisible(false);
        }
        else{
            vacationShow.requestPurchase();
        }

    }

    public void fullSeller() {
        showAlert("This part is under construction, you will see it in the nexy part");
    }

}
