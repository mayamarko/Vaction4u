package view;

import controller.VacationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer, IView {

    @FXML
    private CreateUserController cUController;
    @FXML
    private SearchUserController searchController;
    @FXML
    private UpdateUserController updateController;

    @FXML
    public javafx.scene.control.Button btn_createUser;
    public javafx.scene.control.Button btn_searchUser;
    public javafx.scene.control.Button btn_updateUser;
    public javafx.scene.control.Button btn_deleteUser;

    @FXML
    ImageView iv;
    @FXML
    Pane createPane;
    @FXML
    Pane searchPane;

    @FXML
    private VacationController vacationController;

    @Override
    public void displayApp() {
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setViewModel(VacationController controller) {
        this.vacationController = controller;
        //bindProperties(controller);
    }

    @FXML
    private void initialize() {
        System.out.println("here");


//        /**
//         * load the "create user" fxml
//         */
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            AnchorPane pane = fxmlLoader.load(getClass().getResource("/view/CreateUser.fxml").openStream());
//            createPane.getChildren().setAll(pane);
//            cUController = fxmlLoader.getController();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        /**
//         * load the "search user" fxml
//         */
//        try {
//            FXMLLoader fxmlLoader1 = new FXMLLoader();
//            Pane pane1 = fxmlLoader1.load(getClass().getResource("/view/SearchUser.fxml").openStream());
//            searchPane.getChildren().setAll(pane1);
//
//            searchController = fxmlLoader1.getController();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void setSubController(){
        cUController.injectMainController(this, vacationController);
 //       searchController.injectMainController(this, vacationController);
    }

    public void Create(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Create New User");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("CreateUser.fxml").openStream());
            cUController = fxmlLoader.getController();
            cUController.injectMainController(this, vacationController);
            Scene scene = new Scene(root, 600, 400);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void Search(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Search User");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("SearchUser.fxml").openStream());
            searchController = fxmlLoader.getController();
            searchController.injectMainController(this, vacationController);
            Scene scene = new Scene(root, 600, 400);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }


    public void Update(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Update User");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("UpdateUser.fxml").openStream());
            updateController = fxmlLoader.getController();
            updateController.injectMainController(this, vacationController);
            Scene scene = new Scene(root, 600, 400);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("we hava a problem");
        }
    }
}
