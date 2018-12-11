package view;

import controller.VacationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import controller.VacationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
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
    public javafx.scene.control.Label lbl_Image;
    private ImageView format = null;


    private MainController mainController;
    private VacationController vacationController;

    @FXML
    private void initialize() {
        lbl_result.setEditable(false);
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
    private void searchUser() {
        if(lbl_username.getText().trim().isEmpty()){
            showAlert("Please write the username you want to search");
        }
        else {
            String res = vacationController.search(lbl_username.getText());
            byte[] picture = vacationController.getImage(lbl_username.getText());
            if(picture != null) {
                Image image = new Image(new ByteArrayInputStream(picture));
                format = new ImageView(image);
                format.setFitHeight(45);
                format.setFitWidth(45);
                lbl_Image.setGraphic(format);
            }
            lbl_result.setText(res);
        }
    }
}
