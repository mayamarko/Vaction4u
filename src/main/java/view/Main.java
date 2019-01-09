package view;

import controller.VacationController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.MyModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main extends Application {

    MyModel model;

    @Override
    public void start(Stage primaryStage) throws Exception {

        /**
         * creating the database and the relative tables.
         */
        model = new MyModel();
        model.createNewDatabase();
        model.createNewUsersTable();
        model.createNewVacationsTable();
        model.createNewAccommodationTable();
        model.create_message_box_Table();
        model.showAllVacations();
        model.createNewSalesTable();
        model.createNewVacationsStatusTable();
        //model.createNewTicketsTable();

        VacationController controller = new VacationController(model);
        model.addObserver(controller);

        /**
         * fxml loading
         */
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/sample.fxml").openStream());
        Scene scene = new Scene(root, 700, 500);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Vactaion4U");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(465);
        primaryStage.setMinWidth(580);
        MainController view = fxmlLoader.getController();
        //  view.setResizeEvent(primaryStage);
        view.setViewModel(controller);
        controller.addObserver(view);
        //view.setSubController();
        SetStageCloseEvent(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    private void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit");
                alert.setHeaderText("Exit confirmation");
                alert.setContentText("Are you sure you want to quit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    //model.stopServers();
                    // ... user chose OK
                    // Close program
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}