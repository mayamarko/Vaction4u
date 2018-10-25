package view;

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

    @FXML
    private void signIn(){
        LocalDate d = lbl_birthday.getValue();
        System.out.println(d);

        vacationController.signIn(lbl_username.getText(), lbl_password.getText(), d, lbl_fName.getText(), lbl_lName.getText(), lbl_city.getText());
    }

}
