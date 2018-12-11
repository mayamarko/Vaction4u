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
    public javafx.scene.control.TextField lbl_baggageDescription;
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
    public javafx.scene.control.CheckBox cb_partialPurchase;
    public javafx.scene.control.CheckBox cb_family;
    public javafx.scene.control.CheckBox cb_baggageIncluded;
    public javafx.scene.control.Button btn_post;
    public javafx.scene.control.Label lbl_message_red;


    private MainController mainController;
    private VacationController vacationController;

    public void injectMainController(MainController mainController, VacationController vacationController) {
        this.mainController = mainController;
        this.vacationController = vacationController;
        //lbl_message_red.setStyle("-fx-text-fill: Red; -fx-font-family: \"Calibri\";");
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
            if ((cb_flightBack.isSelected() && lbl_end.getValue() != null) || !cb_flightBack.isSelected()) {
                if ((!cb_accoIncluded.isSelected()) || (cb_accoIncluded.isSelected() && !lbl_addressOfAcco.getText().trim().isEmpty() &&
                        !lbl_rank.getText().trim().isEmpty() && !lbl_nameOfAcco.getText().trim().isEmpty())) {
                    try {
                        int numOfTickets = Integer.parseInt(lbl_numberOfTickets.getText());
                        int numOfAdults = Integer.parseInt(lbl_numOfAdult.getText());
                        int numOfInfant = Integer.parseInt(lbl_numOfInfant.getText());
                        int numOfChild = Integer.parseInt(lbl_numOfChild.getText());
                        int price = Integer.parseInt(lbl_price.getText());
                        if (numOfTickets != (numOfAdults + numOfChild + numOfInfant)) {
                            showAlert("There is inconsistent in the number of tickets!");
                        } else {
                            StringBuilder vacType = new StringBuilder();
                            if (cb_exotic.isSelected()) {
                                vacType.append("Exotic ");
                            }
                            if (cb_allIncluded.isSelected()) {
                                vacType.append("All-Included ");
                            }
                            if (cb_extreme.isSelected()) {
                                vacType.append("Extrem ");
                            }
                            if (cb_family.isSelected()) {
                                vacType.append("Family ");
                            }
                            if (cb_goldenAge.isSelected()) {
                                vacType.append("Golden-age ");
                            }
                            if (cb_honeyMoon.isSelected())
                                vacType.append("Honey-moon ");
                            if (cb_spa.isSelected()) {
                                vacType.append("Spa ");
                            }
                            if (cb_urban.isSelected()) {
                                vacType.append("Urban");
                            }
                            if (!cb_flightBack.isSelected()) {
                                lbl_end.setValue(lbl_start.getValue());
                            }

                            boolean succeedAco = true;
                            if (cb_accoIncluded.isSelected()) {
                                if (!lbl_addressOfAcco.getText().trim().isEmpty() && !lbl_rank.getText().trim().isEmpty()
                                        && !lbl_nameOfAcco.getText().trim().isEmpty()) {
                                    try{
                                        int rank = Integer.parseInt(lbl_rank.getText());
                                        succeedAco = vacationController.addAccommodation(vacationController.username, lbl_nameOfAcco.getText(), lbl_addressOfAcco.getText(), rank);
                                    }catch(Exception e){
                                        showAlert("The accommodation rank has to be a number between 1 to 5");
                                        succeedAco = false;
                                    }

                                }else{
                                    succeedAco = false;
                                }
                            }
                            boolean succeedVac = false;
                            if(succeedAco) {
                                succeedVac = vacationController.createVacation(vacationController.username, price, lbl_airLine.getText(), lbl_start.getValue(),
                                        lbl_end.getValue(), cb_baggageIncluded.isSelected(), lbl_baggageDescription.getText(), numOfTickets, numOfAdults, numOfChild,
                                        numOfInfant, cb_partialPurchase.isSelected(), lbl_destination.getText(), cb_flightBack.isSelected(), cb_direct.isSelected(),
                                        vacType.toString(), cb_accoIncluded.isSelected());
                            }
                            if (succeedVac && succeedAco) {
                                showAlert("Congratulations! your vacation posted!");
                            } else {
                                showAlert("Oops... something went wrong");
                            }
                        }
                    } catch (Exception e) {
                        showAlert("Please fill only numbers in tickets amount fields!");
                    }
                } else {
                    showAlert("Please fill the accommodation details!");
                }
            } else {
                showAlert("Please fill the return date!");
            }
        } else {
            showAlert("One or more details are missing");
        }
    }
}

