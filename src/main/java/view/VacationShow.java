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
     private String userName;

     @FXML
     private FullInfoController fullInfoController;
    @FXML
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
        this.userName=user;
        this.allInfo = new Button("full info");
        this.request = new Button("purchase request");
        allInfo.setOnAction(event -> {
            saveInfo=saveInfo(d,departD,returnD,price,user,airline,baggage,baggageDisc,numT,numA,numC,numI,part,back,direct,type,accomendation);
            fullDiscription();
        });
        request.setOnAction(event -> {
            //showAlert("hey2");
           requestPurchase();
//            if (vacationController.isLogged()) {
//                if(vacationController.username.equals(user)){
//                    showAlert("You can NOT buy your own vacation!!!!");
//                }else {
//                    vacationController.add_message(user, "*" + id + "* Your vacation to " + d + " has been requested to buy, by " + vacationController.username + ".", "request vacation");
//                    showAlert("The request sent! You soon will see the approve in the message box");
//                }
//            } else {
//                showAlert("You must be logged in to buy vacation!");
//            }
        });

    }

    public void  requestPurchase(){
        if (vacationController.isLogged()) {
            if(vacationController.username.equals(userName)){
                showAlert("You can NOT buy your own vacation!!!!");
            }else {
                vacationController.add_message(userName, "*" + vacId.get() + "* Your vacation to " + destanation.get() + " has been requested to buy, by " + vacationController.username + ".", "request vacation");
                showAlert("The request sent! You soon will see the approve in the message box");
            }
        } else {
            showAlert("You must be logged in to buy vacation!");
        }
    }
    private String saveInfo(String d, String departD, String returnD, String price, String user, String airline, String baggage, String baggageDisc, String numT,String numA, String numC, String numI, String part, String back, String direct,String type, String accomendation){
       return       "Destanation: " + d +
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
        //System.out.println("we hava a problem");
            e.printStackTrace();
    }

}

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        //alert.setTitle("Please note");
        //alert.setHeight(200);
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
