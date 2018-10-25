package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer, IView {

    @FXML
    private CreateUserController cUController;

    @FXML
    ImageView iv;
    @FXML
    Pane secPane;

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
        //cUController = new CreateUserController();
        /*
        try {
            Image image = new Image(new FileInputStream("resources/home.jpg"));
            iv.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/


        Pane newLoadedPane = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            newLoadedPane = fxmlLoader.load(getClass().getResource("/view/CreateUser.fxml").openStream());
            cUController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        secPane.getChildren().add(newLoadedPane);
    }

    public void setSubController(){
        cUController.injectMainController(this, vacationController);
    }
}
