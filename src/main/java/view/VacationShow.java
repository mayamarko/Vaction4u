package view;

import javafx.beans.property.SimpleStringProperty;

public class VacationShow {
    private final SimpleStringProperty vacId;
    private final SimpleStringProperty destanation;
    private final SimpleStringProperty departDay;
    private final SimpleStringProperty returnDay;
    private final SimpleStringProperty price;

    public VacationShow(int id, String d,String departD,String returnD,String price){
        this.vacId=new SimpleStringProperty(id+"");
        this.destanation=new SimpleStringProperty(d);
        this.departDay=new SimpleStringProperty(departD);
        this.returnDay=new SimpleStringProperty(returnD);
        this.price=new SimpleStringProperty(price);
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
}
