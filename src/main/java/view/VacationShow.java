package view;

import controller.VacationController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class VacationShow {
    private final SimpleStringProperty vacId;
    private final SimpleStringProperty destanation;
    private final SimpleStringProperty departDay;
    private final SimpleStringProperty returnDay;
    private final SimpleStringProperty price;
    private Button allInfo;
    private Button request;
    VacationController vacationController;
   // private String username;



    //docId, //dest,dDate,rDate,price,username,airline,baggage(bool),baggDisc,numT,numA,numC,numI,partial(bool),back(bool),direct(bool),type,acco(bool)
    public VacationShow(VacationController vac,int id, String d, String departD, String returnD, String price,String user,String airline,String baggage,String baggageDisc,String numT,String numA,String numC, String numI,String part,String back, String direct,String type,String accomendation ){
        this.vacationController=vac;
        this.vacId=new SimpleStringProperty(id+"");
        this.destanation=new SimpleStringProperty(d);
        this.departDay=new SimpleStringProperty(departD);
        this.returnDay=new SimpleStringProperty(returnD);
        this.price=new SimpleStringProperty(price);
        this.allInfo=new Button("full info");
        this.request=new Button("purchase request");
        allInfo.setOnAction(event -> {
            showAlert("hey " +d );
        });
        request.setOnAction(event -> {
            //showAlert("hey2");
            //vacationController.add_message()
        });

    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        alert.setTitle("Please note");
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
