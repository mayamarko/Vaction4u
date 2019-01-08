package view;

import controller.VacationController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class VacationShow {
    private final SimpleStringProperty vacId;
    private final SimpleStringProperty destanation;
    private final SimpleStringProperty departDay;
    private final SimpleStringProperty returnDay;
    private final SimpleStringProperty price;
    private Button allInfo;
    private Button request;
    private Button trade;
    private ChoiceBox vacationsOptions;
    VacationController vacationController;
    public static ArrayList<String> vacationChose;
    private String saveInfo;
    private String userName;
    private TableView table;
    @FXML
    private FullInfoController fullInfoController;
    @FXML
    private MainController mainController;


    //docId, //dest,dDate,rDate,price,username,airline,baggage(bool),baggDisc,numT,numA,numC,numI,partial(bool),back(bool),direct(bool),type,acco(bool)
    public VacationShow(MainController mainController, VacationController vac, int id, String d, String departD, String returnD, String price, String user, String airline, String baggage, String baggageDisc, String numT, String numA, String numC, String numI, String part, String back, String direct, String type, String accomendation) {
        this.vacationController = vac;
        this.mainController = mainController;
        this.vacId = new SimpleStringProperty(id + "");
        this.destanation = new SimpleStringProperty(d);
        this.departDay = new SimpleStringProperty(departD);
        if (back.equals("Yes")) {
            this.returnDay = new SimpleStringProperty(returnD);
        } else {
            this.returnDay = new SimpleStringProperty("Return flight is not included");
        }
        this.price = new SimpleStringProperty(price);
        this.userName = user;
        this.allInfo = new Button("full info");
        this.request = new Button("purchase request");
        this.trade = new Button("trade request");
        vacationsOptions = new ChoiceBox();
        vacationsOptions.setDisable(true);
        vacationsOptions.setVisible(false);
        vacationChose = new ArrayList<>();
        //vacationsOptions.setValue("Select Vacation");
        allInfo.setOnAction(event -> {
            saveInfo = saveInfo(d, departD, returnD, price, user, airline, baggage, baggageDisc, numT, numA, numC, numI, part, back, direct, type, accomendation);
            fullDiscription();
        });
        request.setOnAction(event -> {
            requestPurchase();
        });
        trade.setOnAction(event -> {
            requestTrade();
        });

    }

    public void requestPurchase() {
        if (vacationController.isLogged()) {
            if (vacationController.username.equals(userName)) {
                showAlert("You can NOT buy your own vacation!!!!");
            } else {
                if (!vacationController.is_messg_Exist(vacationController.username, userName, "*" + vacId.get() + "* Your vacation to " + destanation.get() + " has been requested to buy,\n by " + vacationController.username + ".")) {
                    vacationController.add_message(userName, "*" + vacId.get() + "* Your vacation to " + destanation.get() + " has been requested to buy,\n by " + vacationController.username + ".", "request vacation");
                    showAlert("The request sent! You soon will see the approve in the message box");
                    request.setDisable(true);
                } else {
                    showAlert("You have already asked to purchase this vacation..");
                    request.setDisable(true);
                }

            }
        } else {
            showAlert("You must be logged in to buy vacation!");
        }
    }

    public void requestTrade() {
        if (vacationController.isLogged()) {
            if (vacationController.username.equals(userName)) {
                showAlert("You can NOT trade your own vacation!!!!");
            } else {
                showUsersVacations();
            }
        } else {
            showAlert("You must be logged in to buy vacation!");
        }
    }

    private ObservableList<tradeShow> getMyVacations() {
        ObservableList<tradeShow> data = FXCollections.observableArrayList();
        HashMap<Integer, String[]> set = vacationController.showAllVacationsge_by_user(userName);
        for (Map.Entry<Integer, String[]> entry : set.entrySet()) {
            String[] info = entry.getValue();
            data.add(new tradeShow(entry.getKey().toString(), info[0]));
        }
        return data;
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

    public String getSaveInfo() {
        return saveInfo;
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

    public Button getTrade() {
        return trade;
    }

    public void setTrade(Button trade) {
        this.trade = (Button) trade;
    }

    public ChoiceBox getVacationsOptions() {
        return vacationsOptions;
    }

    public void setVacationsOptions(ChoiceBox vacationsOptions) {
        this.vacationsOptions = vacationsOptions;
    }


    public void showUsersVacations() {
        try {
            table = new TableView();
            Stage stage = new Stage();
            Scene scene = new Scene(new Group());
            stage.setTitle("Your vacations");
            stage.setWidth(550);
            stage.setHeight(700);
            final Label label = new Label("Your vacations");
            label.setFont(new Font("Calibri Light", 22));
            final TextArea textArea = new TextArea("Here you can choose your vacation that you want to trade for. \nYou can only choose one vacation! ");
            textArea.setFont(new Font("Calibri Light", 16));
            textArea.setMaxSize(500, 70);
            textArea.setEditable(false);
            final Button button = new Button("Send trade request");
            button.setMaxSize(500, 50);
            button.setOnAction((event) -> {
                if (!vacationController.is_messg_Exist(vacationController.username, userName, "*" + vacId.get() + "* Your vacation to " + destanation.get() + " has been requested to trade for a vacation to " + "#" + vacationChose.get(0) + " by " + vacationController.username + ".")) {
                    vacationController.add_message(userName, "*" + vacId.get() + "* Your vacation to " + destanation.get() + " has been requested to trade for a vacation to " + "#" + vacationChose.get(0) + " by " + vacationController.username + ".", "trade request vacation");
                    showAlert("The request sent! You soon will see the approve in the message box");
                    trade.setDisable(true);
                    button.setDisable(true);

                } else {
                    showAlert("You have already asked to trade this vacation..");
                    trade.setDisable(true);
                }

            });
            table.setEditable(false);

            TableColumn dest = new TableColumn("Destination");
            dest.setMinWidth(150);
            dest.setCellValueFactory(new PropertyValueFactory<VacationShow, String>("destination"));

            TableColumn vacId = new TableColumn("Vacation ID");
            vacId.setMinWidth(150);
            vacId.setCellValueFactory(new PropertyValueFactory<VacationShow, String>("vacId"));

            TableColumn checkbox = new TableColumn("You choice");
            checkbox.setMinWidth(100);
            checkbox.setCellValueFactory(new PropertyValueFactory<VacationShow, CheckBox>("chooseVac"));


            //table.setItems(getData());
            table.setItems(getMyVacations());
            table.getColumns().addAll(dest, vacId, checkbox);
            table.setMinHeight(200);
            table.setMaxHeight(900);

            final VBox vbox = new VBox();
            vbox.setSpacing(20);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, textArea, table, button);

            ((Group) scene.getRoot()).getChildren().addAll(vbox);
            stage.setScene(scene);

            table.setStyle("-fx-selection-bar: #c6ecc6; -fx-selection-bar-non-focused: #66cc66;  -fx-background-color: #66cc66;");
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();

        } catch (Exception e) {
            //System.out.println("not opening");
        }
    }

    public static boolean addVacs(String vacid) {
        if (vacationChose.size() >= 1)
            return false;
        vacationChose.add(vacid);
        return true;
    }

    public static void remove(String vacid) {
        if (vacationChose.contains(vacid))
            vacationChose.remove(vacid);
    }

}
