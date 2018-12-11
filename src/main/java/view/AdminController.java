package view;

import controller.VacationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AdminController {
    @FXML
    private SearchUserController searchController;
    @FXML
    private UpdateUserController updateController;
    @FXML
    private DeleteUserController deleteController;

    @FXML
    public javafx.scene.control.TextField lbl_username;
    public javafx.scene.control.TextField lbl_lName;
    public javafx.scene.control.TextField lbl_fName;
    public javafx.scene.control.TextField lbl_city;
    public javafx.scene.control.DatePicker lbl_birthday;
    public javafx.scene.control.TextField lbl_password;
    public javafx.scene.control.TextField lbl_email;
    public javafx.scene.control.Button btn_signIn;
    public javafx.scene.control.Button btn_chosePicture;

    private MainController mainController;
    private VacationController vacationController;


    public void injectMainController(MainController mainController, VacationController vacationController) {
        this.mainController = mainController;
        this.vacationController = vacationController;
    }

    public void Search(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Read User");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/SearchUser.fxml").openStream());
            searchController = fxmlLoader.getController();
            searchController.injectMainController(mainController, vacationController);
            Scene scene = new Scene(root, 300, 340);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
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
            Parent root = fxmlLoader.load(getClass().getResource("/UpdateUser.fxml").openStream());
            updateController = fxmlLoader.getController();
            updateController.injectMainController(mainController, vacationController);
            Scene scene = new Scene(root, 445, 500);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("we hava a problem");
        }
    }


    public void Delete(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Delete User");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/DeleteUser.fxml").openStream());
            deleteController = fxmlLoader.getController();
            deleteController.injectMainController(mainController, vacationController);
            Scene scene = new Scene(root, 300, 300);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("we hava a problem");
        }
    }

}
