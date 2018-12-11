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

public class VacationShow {
    private final SimpleStringProperty vacId;
    private final SimpleStringProperty destanation;
    private final SimpleStringProperty departDay;
    private final SimpleStringProperty returnDay;
    private final SimpleStringProperty price;
    private Button allInfo;
    private Button request;
    VacationController vacationController;
     private String saveInfo;
     private FullInfoController fullInfoController;
     private MainController mainController;


    //docId, //dest,dDate,rDate,price,username,airline,baggage(bool),baggDisc,numT,numA,numC,numI,partial(bool),back(bool),direct(bool),type,acco(bool)
    public VacationShow(MainController mainController,VacationController vac, int id, String d, String departD, String returnD, String price, String user, String airline, String baggage, String baggageDisc, String numT, String numA, String numC, String numI, String part, String back, String direct, String type, String accomendation) {
        this.vacationController = vac;
        this.mainController=mainController;
        this.vacId = new SimpleStringProperty(id + "");
        this.destanation = new SimpleStringProperty(d);
        this.departDay = new SimpleStringProperty(departD);
        this.returnDay = new SimpleStringProperty(returnD);
        this.price = new SimpleStringProperty(price);
        this.allInfo = new Button("full info");
        this.request = new Button("purchase request");
        allInfo.setOnAction(event -> {
                        saveInfo(d,departD,returnD,price,user,airline,baggage,baggageDisc,numT,numA,numC,numI,part,back,direct,type,accomendation);
            fullDiscription();
        });
        request.setOnAction(event -> {
            //showAlert("hey2");
            if (vacationController.isLogged()) {
                vacationController.add_message(user, "*" + id + "* Your vacation to " + d + " has been requested to buy, by " + vacationController.username + ".", "request vacation");
                showAlert("The request sent! You soon will see the approve in the message box");
            } else {
                showAlert("You must be logged in to buy vacation!");
            }
        });

    }

    private void saveInfo(String d, String departD, String returnD, String price, String user, String airline, String baggage, String baggageDisc, String numT,String numA, String numC, String numI, String part, String back, String direct,String type, String accomendation){
        saveInfo= "Full information: \n" +
                    "Destanation: " + d +
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

    public String getSaveInfo() {
        return saveInfo;
    }

    private void fullDiscription() {
        try{
        Stage stage = new Stage();
        stage.setTitle("Update User");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/FullInfo.fxml").openStream());
        fullInfoController = fxmlLoader.getController();
       fullInfoController.injectMainController(mainController, vacationController,this);
        Scene scene = new Scene(root, 445, 500);
        //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        stage.show();
    } catch(Exception e){
        System.out.println("we hava a problem");
    }

}

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        //alert.setTitle("Please note");
        alert.setHeight(400);
        alert.setHeaderText(alertMessage);
    }

    public String getVacId() {
        return vacId.get();
    }

    public SimpleStringProperty vacIdProperty() {
        return vacId;
    }

    public String getDestanation() {
        return destanation.get();
    }

    public SimpleStringProperty destanationProperty() {
        return destanation;
    }

    public String getDepartDay() {
        return departDay.get();
    }

    public SimpleStringProperty departDayProperty() {
        return departDay;
    }

    public String getReturnDay() {
        return returnDay.get();
    }

    public SimpleStringProperty returnDayProperty() {
        return returnDay;
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public Button getAllInfo() {
        return allInfo;
    }

    public void setAllInfo(Button allInfo) {
        this.allInfo = allInfo;
    }

    public Button getRequest() {
        return request;
    }

    public void setRequest(Button request) {
        this.request = request;
    }
}
