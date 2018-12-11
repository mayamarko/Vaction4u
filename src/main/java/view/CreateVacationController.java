package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.Period;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class CreateVacationController {

    @FXML
    public javafx.scene.control.TextField lbl_destination;
    public javafx.scene.control.TextField lbl_numberOfTickets;
    public javafx.scene.control.TextField lbl_price;
    public javafx.scene.control.TextField lbl_airLine;
    public javafx.scene.control.TextField lbl_nameOfAcco;
    public javafx.scene.control.TextField lbl_addressOfAcco;
    public javafx.scene.control.TextField lbl_rank;
    public javafx.scene.control.TextField lbl_numOfAdult;
    public javafx.scene.control.TextField lbl_numOfChild;
    public javafx.scene.control.TextField lbl_numOfInfant;
    public javafx.scene.control.DatePicker lbl_start;
    public javafx.scene.control.DatePicker lbl_end;
    public javafx.scene.control.CheckBox cb_flightBack;
    public javafx.scene.control.CheckBox cb_direct;
    public javafx.scene.control.CheckBox cb_accoIncluded;
    public javafx.scene.control.CheckBox cb_spa;
    public javafx.scene.control.CheckBox cb_honeyMoon;
    public javafx.scene.control.CheckBox cb_urban;
    public javafx.scene.control.CheckBox cb_exotic;
    public javafx.scene.control.CheckBox cb_allIncluded;
    public javafx.scene.control.CheckBox cb_extreme;
    public javafx.scene.control.CheckBox cb_goldenAge;
    public javafx.scene.control.CheckBox cb_family;
    public javafx.scene.control.Button btn_post;


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
    private void post() {
        if (lbl_destination.getText().trim().isEmpty() || lbl_numberOfTickets.getText().trim().isEmpty() || lbl_price.getText().trim().isEmpty() ||
                lbl_airLine.getText().trim().isEmpty() || lbl_numOfAdult.getText().trim().isEmpty() || lbl_numOfInfant.getText().trim().isEmpty()
                || lbl_numOfChild.getText().trim().isEmpty() || lbl_start.getValue() != null) {
            if(cb_flightBack.isSelected() && lbl_end.getValue() != null){
                if(cb_accoIncluded.isSelected() && !lbl_addressOfAcco.getText().trim().isEmpty() && !lbl_rank.getText().trim().isEmpty() && !lbl_nameOfAcco.getText().trim().isEmpty()){
                    try {
                        int numOfTickets = Integer.parseInt(lbl_numberOfTickets.getText());
                        int numOfAdults = Integer.parseInt(lbl_numOfAdult.getText());
                        int numOfInfant = Integer.parseInt(lbl_numOfInfant.getText());
                        int numOfChild = Integer.parseInt(lbl_numOfChild.getText());
                        if(numOfTickets != (numOfAdults+numOfChild+numOfInfant)){
                            showAlert("There is inconsistent in the number of tickets!");
                        }
                        else{
                            //boolean succeed = vacationController
                            //TODO make a new vacation here after all the checks!!!!!!!! adding to the tables.
                        }
                    }catch(Exception e){
                        showAlert("Please fill only numbers in tickets amount fields!");
                    }
                }
                else{
                    showAlert("Please fill the accommodation details!");
                }
            }
            else{
                showAlert("Please fill the return date!");
            }
            showAlert("One or more details are missing");
        } else {

        }
    }
}

