package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer, IView {

    @FXML
    private CreateUserController cUController;
    @FXML
    private SearchUserController searchController;

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

        /**
         * load the "create user" fxml
         */
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            AnchorPane pane = fxmlLoader.load(getClass().getResource("/view/CreateUser.fxml").openStream());
            createPane.getChildren().setAll(pane);
            cUController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * load the "search user" fxml
         */
        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            Pane pane1 = fxmlLoader1.load(getClass().getResource("/view/SearchUser.fxml").openStream());
            searchPane.getChildren().setAll(pane1);

            searchController = fxmlLoader1.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void setSubController(){
        cUController.injectMainController(this, vacationController);
        searchController.injectMainController(this, vacationController);
    }
}
