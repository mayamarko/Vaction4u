package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MessageShow {
    private final SimpleStringProperty from;
    private final SimpleStringProperty dAndT;
    private final SimpleStringProperty message;
    private final SimpleStringProperty type;
    private Button acc;
    private Button dec;


    public MessageShow(String from, String dateAndTine, String message, String type) {
        this.from = new SimpleStringProperty(from);
        this.dAndT = new SimpleStringProperty(dateAndTine);
        this.message = new SimpleStringProperty(message);
        this.type = new SimpleStringProperty(type);
        this.acc = new Button("Accept Request");
        this.dec = new Button("Decline Request");
        acc.setOnAction(event -> {
            showAlert("Great! The money will transfer to your account");

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
