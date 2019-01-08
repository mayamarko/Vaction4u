package view;

import controller.VacationController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageShow {
    private final SimpleStringProperty from;
    private final SimpleStringProperty dAndT;
    private final SimpleStringProperty message;
    private final SimpleStringProperty type;
    private String saveInfo;
    private Button acc;
    private Button dec;
    private Button vacDescription;
    @FXML
    private FullInfoController fullInfoController;
    VacationController vacationController;
    SellerPaymentDetailController sellerPaymentDetailController;
    PaymentController paymentController;
    MainController mainController;


    public MessageShow(String from, String dateAndTine, String messageI, String type, VacationController vacationController1, MainController mainController) {
        this.from = new SimpleStringProperty(from);
        this.dAndT = new SimpleStringProperty(dateAndTine);
        this.message = new SimpleStringProperty(messageI);
        this.type = new SimpleStringProperty(type);
        this.vacDescription = new Button("Full information");
        vacDescription.setVisible(false);
        if (type.equals("request vacation")) {
            this.acc = new Button("Accept Request");
            this.dec = new Button("Decline Request");
        } else if (type.equals("request approve")) {
            this.acc = new Button("Start a chat with the seller to set up the payment");
            this.dec = new Button("");
            this.dec.setVisible(false);
        } else if (type.equals("successful purchase")) {
            this.acc = new Button("");
            this.dec = new Button("");
            this.dec.setVisible(false);
            this.acc.setVisible(false);
        } else if (type.equals("trade request vacation")) {
            vacDescription.setVisible(true);
            this.acc = new Button("Accept Trade Request");
            this.dec = new Button("Decline Trade Request");
        }
        this.vacationController = vacationController1;
        this.mainController = mainController;
        acc.setOnAction(event -> {
            if (type.equals("request vacation")) {
                showAlert("You accepted the request, the buyer will contact you to arrange the payment.");
//                /*
//                try {
//                    Stage stage = new Stage();
//                    stage.setTitle("Account details");
//                    FXMLLoader fxmlLoader = new FXMLLoader();
//                    Parent root = fxmlLoader.load(getClass().getResource("/SellerPaymentDetail.fxml").openStream());
//                    sellerPaymentDetailController = fxmlLoader.getController();
//                    sellerPaymentDetailController.injectMainController(mainController, vacationController);
//                    Scene scene = new Scene(root, 680, 500);
//                    //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
//                    stage.setScene(scene);
//                    stage.setResizable(false);
//                    stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
//                    stage.showAndWait();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }*/
                int sign = messageI.indexOf(" ");
                String vacIdString = messageI.substring(0, sign);
                try {
                    vacationController1.add_message(from, vacIdString + " Your request to buy the vacation from " + vacationController1.username + " has been approved, you can start a chat with the seller to set up the payment",
                            "request approve");
                    acc.setDisable(true);
                    dec.setDisable(true);
                } catch (Exception e) {
                    showAlert("Ooops you have already accepted this request");
                }
                vacationController1.deleteMessage(from, vacationController1.username, messageI);
            } else if (type.equals("request approve")) {
                showAlert("Sorry... The site is under construction");

//                try {
//                    int sign = messageI.indexOf(" ");
//                    String vacIdString = messageI.substring(1, sign - 1);
//                    int vacId = Integer.parseInt(vacIdString);
//                    Stage stage = new Stage();
//                    stage.setTitle("Payment");
//                    FXMLLoader fxmlLoader = new FXMLLoader();
//                    Parent root = fxmlLoader.load(getClass().getResource("/Payment.fxml").openStream());
//                    paymentController = fxmlLoader.getController();
//                    paymentController.injectMainController(mainController, vacationController, vacId);
//                    Scene scene = new Scene(root, 550, 440);
//                    //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
//                    stage.setScene(scene);
//                    stage.setResizable(false);
//                    stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
//                    stage.showAndWait();
//                    if (paymentController.isPaid()) {
//                        vacationController1.deleteMessage(from, vacationController1.username, messageI);
//                    }
//                    acc.setDisable(true);
//                    dec.setDisable(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            } else if (type.equals("trade request vacation")) {
                showAlert("You accepted the trade request.");
                int sign = messageI.indexOf(" ");
                String vacIdString = messageI.substring(0, sign);
                String[] sellerVac = getVacationDetails(vacIdString);
                try {
                    vacationController1.add_message(from, vacIdString + " Your request to trade the vacation from " + vacationController1.username + "to " + sellerVac[0] + " has been approved.",
                            "trade request approve");
                    acc.setDisable(true);
                    dec.setDisable(true);
                } catch (Exception e) {
                    showAlert("Ooops you have already accepted this request");
                }
                vacationController1.deleteMessage(from, vacationController1.username, messageI);
            }

        });
        dec.setOnAction(event -> {
            showAlert("Such a loss");
            acc.setDisable(true);
            dec.setDisable(true);
        });
        vacDescription.setOnAction(event -> {
            if (type.equals("trade request vacation")) {
                int hashTag = messageI.indexOf("#");
                String vacIDnew = messageI.substring(hashTag + 1);
                int spaceIndex = vacIDnew.indexOf(" ");
                vacIDnew = vacIDnew.substring(0, spaceIndex);
                String[] toTradeVac = getVacationDetails(vacIDnew);
                saveInfo = saveInfo(toTradeVac[0], toTradeVac[1], toTradeVac[2], toTradeVac[3], toTradeVac[4],
                        toTradeVac[5], toTradeVac[6], toTradeVac[7], toTradeVac[8], toTradeVac[9], toTradeVac[10], toTradeVac[11]
                        , toTradeVac[12], toTradeVac[13], toTradeVac[14], toTradeVac[15], toTradeVac[16]);
                fullDiscription();
            }
        });

    }

    private String saveInfo(String d, String departD, String returnD, String price, String user, String airline, String baggage, String baggageDisc, String numT, String numA, String numC, String numI, String part, String back, String direct, String type, String accomendation) {
        if (back.equals("No")) {
            returnD = "Return flight is not included";
        }
        return "Destanation: " + d +
                " \nDeparture date: " + departD +
                "\nReturn date: " + returnD +
                "\nPrice: " + price +
                "\nPublished by: " + user +
                "\nAirline: " + airline +
                "\nBaggage included: " + baggage +
                "\nBaggage discription: " + baggageDisc +
                "\nNumber of Tickets: " + numT +
                "\nOf them: Adult tickets: " + numA + " Child tickets: " + numC + " Infant tickets " + numI +
                "\nCan purchase part of tickets: " + part +
                "\nIncluding flight back: " + back +
                "\nFlight is direct: " + direct +
                "\nVacation type: " + type +
                "\nAccommodation included: " + accomendation;
    }

    private void fullDiscription() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Full Information");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/FullInfo.fxml").openStream());
            fullInfoController = fxmlLoader.getController();
            fullInfoController.injectMainController(mainController, vacationController, this);
            Scene scene = new Scene(root, 415, 436);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            //System.out.println("we hava a problem");
            e.printStackTrace();
        }

    }

    private String[] getVacationDetails(String vacID) {
        int id = 0;
        try {
            id = Integer.parseInt(vacID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String[] vacDetails = vacationController.getVacationDescriptionByID(id);
        return vacDetails;
    }

    public String getSaveInfo() {
        return saveInfo;
    }

    public String getFrom() {
        return from.get();
    }

    public SimpleStringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getdAndT() {
        return dAndT.get();
    }

    public SimpleStringProperty dAndTProperty() {
        return dAndT;
    }

    public void setdAndT(String dAndT) {
        this.dAndT.set(dAndT);
    }

    public String getMessage() {
        return message.get();
    }

    public SimpleStringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Button getAcc() {
        return acc;
    }

    public void setAcc(Button acc) {
        this.acc = acc;
    }

    public Button getDec() {
        return dec;
    }

    public void setDec(Button dec) {
        this.dec = dec;
    }

    public Button getVacDescription() {
        return vacDescription;
    }

    public void setVacDescription(Button vacDescription) {
        this.vacDescription = vacDescription;
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        alert.setTitle("Please note");
        alert.setHeaderText(alertMessage);
    }
}
