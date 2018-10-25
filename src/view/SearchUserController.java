package view;

import controller.VacationController;
import javafx.fxml.FXML;
import controller.VacationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    @FXML
    private void searchUser() {
        String res = vacationController.search(lbl_username.getText());
        lbl_result.setText(res);
    }

}
