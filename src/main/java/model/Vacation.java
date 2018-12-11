package model;

import java.time.LocalDate;

public class Vacation {
    private String userName;
    private int vacId;
    private int price;
    private String airline;
    private LocalDate fDeparture;
    private LocalDate fReturn;
    private boolean baggage;
    private  String baggageDescruption;
    private int numOfTickets;
    private int numAdult;
    private int numChild;
    private int numInfant;
    private boolean partialPurchse;
    private String destination;
    private boolean flightBack;
    private boolean directFlight;
    private String vacationType;
    private boolean accomodation;

    public Vacation(String user,int vId,int price,String airline,LocalDate dept,LocalDate rtrn,boolean b,String bInfo,int numT, int numA, int numC, int numI, boolean partial,
                    String dest, boolean back,boolean direct,String type,boolean acco){
        this.userName=user;
        this.vacId=vId;
        this.price=price;
        this.airline=airline;
        this.fDeparture=dept;
        this.fReturn=rtrn;
        this.baggage=b;
        this.baggageDescruption=bInfo;
        this.numOfTickets=numT;
        this.numAdult=numA;
        this.numChild=numC;
        this.numInfant=numI;
        this.partialPurchse=partial;
        this.destination=dest;
        this.flightBack=back;
        this.directFlight=direct;
        this.vacationType=type;
        this.accomodation=acco;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVacId() {
        return vacId;
    }

    public void setVacId(int vacId) {
        this.vacId = vacId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public LocalDate getfDeparture() {
        return fDeparture;
    }

    public void setfDeparture(LocalDate fDeparture) {
        this.fDeparture = fDeparture;
    }

    public LocalDate getfReturn() {
        return fReturn;
    }

    public void setfReturn(LocalDate fReturn) {
        this.fReturn = fReturn;
    }

    public boolean isBaggage() {
        return baggage;
    }

    public void setBaggage(boolean baggage) {
        this.baggage = baggage;
    }

    public String getBaggageDescruption() {
        return baggageDescruption;
    }

    public void setBaggageDescruption(String baggageDescruption) {
        this.baggageDescruption = baggageDescruption;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public void setNumOfTickets(int numOfTickets) {
        this.numOfTickets = numOfTickets;
    }

    public int getNumAdult() {
        return numAdult;
    }

    public void setNumAdult(int numAdult) {
        this.numAdult = numAdult;
    }

    public int getNumChild() {
        return numChild;
    }

    public void setNumChild(int numChild) {
        this.numChild = numChild;
    }

    public int getNumInfant() {
        return numInfant;
    }

    public void setNumInfant(int numInfant) {
        this.numInfant = numInfant;
    }

    public boolean isPartialPurchse() {
        return partialPurchse;
    }

    public void setPartialPurchse(boolean partialPurchse) {
        this.partialPurchse = partialPurchse;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isFlightBack() {
        return flightBack;
    }

    public void setFlightBack(boolean flightBack) {
        this.flightBack = flightBack;
    }

    public boolean isDirectFlight() {
        return directFlight;
    }

    public void setDirectFlight(boolean directFlight) {
        this.directFlight = directFlight;
    }

    public String getVacationType() {
        return vacationType;
    }

    public void setVacationType(String vacationType) {
        this.vacationType = vacationType;
    }

    public boolean isAccomodation() {
        return accomodation;
    }

    public void setAccomodation(boolean accomodation) {
        this.accomodation = accomodation;
    }
}


