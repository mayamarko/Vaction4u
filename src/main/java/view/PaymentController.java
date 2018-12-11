
package view;

import controller.VacationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;


public class PaymentController {

    @FXML
    public javafx.scene.control.TextField lbl_creditCard;
    public javafx.scene.control.TextField lbl_Id;
    public javafx.scene.control.TextField lbl_cvv;
    public javafx.scene.control.TextField lbl_paypalUsername;
    public javafx.scene.control.TextField lbl_paypalPassword;
    public javafx.scene.control.Button btn_pay;
    public javafx.scene.control.ComboBox cbox_month;
    public javafx.scene.control.ComboBox cbox_year;


    private MainController mainController;
    private VacationController vacationController;
    private byte[] person_image;

    @FXML
    private void initialize() {
        setData();
    }
    public void injectMainController(MainController mainController, VacationController vacationController) {
        this.mainController = mainController;
        this.vacationController = vacationController;
    }

    public void setData(){

        ObservableList<String> monthes = FXCollections.observableArrayList("January", "February", "March","April","May", "June", "July", "August", "September", "October","November", "December");
        cbox_month.setItems(monthes);
        cbox_month.setValue("Month");
        ObservableList<String> years = FXCollections.observableArrayList("2018", "2019", "2020","2021","2022", "2023", "2024");
        cbox_year.setItems(years);
        cbox_year.setValue("Year");

    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        alert.setTitle("Please note");
        alert.setHeaderText(alertMessage);
    }


}
