package view;

import controller.VacationController;
import javafx.beans.property.SimpleStringProperty;
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
    private Button acc;
    private Button dec;
    VacationController vacationController;
    SellerPaymentDetailController sellerPaymentDetailController;
    PaymentController paymentController;
    MainController mainController;


    public MessageShow(String from, String dateAndTine, String message, String type, VacationController vacationController1, MainController mainController) {
        this.from = new SimpleStringProperty(from);
        this.dAndT = new SimpleStringProperty(dateAndTine);
        this.message = new SimpleStringProperty(message);
        this.type = new SimpleStringProperty(type);
        if(type.equals("request vacation")) {
            this.acc = new Button("Accept Request");
            this.dec = new Button("Decline Request");
        }else if(type.equals("request approve")){
            this.acc = new Button("Pay!");
        }
        this.vacationController = vacationController1;
        this.mainController = mainController;
        acc.setOnAction(event -> {
            if (type.equals("request vacation")) {
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Account details");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent root = fxmlLoader.load(getClass().getResource("/SellerPaymentDetail.fxml").openStream());
                    sellerPaymentDetailController = fxmlLoader.getController();
                    sellerPaymentDetailController.injectMainController(mainController, vacationController);
                    Scene scene = new Scene(root, 680, 500);
                    //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                vacationController1.add_message(from, "Your request to buy the vacation from " + vacationController1.username + " has been approved, you can transfer to the payment page",
                        "request approve");
            }else if(type.equals("request approve")){
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Payment");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent root = fxmlLoader.load(getClass().getResource("/Payment.fxml").openStream());
                    paymentController = fxmlLoader.getController();
                    paymentController.injectMainController(mainController, vacationController);
                    Scene scene = new Scene(root, 550, 440);
                    //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        dec.setOnAction(event -> {
            showAlert("Such a loss");
        });

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

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        alert.setTitle("Please note");
        alert.setHeaderText(alertMessage);
    }
}
