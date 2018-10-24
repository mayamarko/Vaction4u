package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CreateUserController {

    private VacationController vacationController;

    @FXML
    public javafx.scene.control.TextField lbl_username;
    public javafx.scene.control.TextField lbl_lName;
    public javafx.scene.control.TextField lbl_fName;
    public javafx.scene.control.TextField lbl_city;
    public javafx.scene.control.TextField lbl_birthday;
    public javafx.scene.control.TextField lbl_password;
    public javafx.scene.control.Button btn_signIn;

    public void setViewModel(VacationController controller) {
        this.vacationController = controller;
        //bindProperties(controller);
    }

    public void signIn(){

    }

}
