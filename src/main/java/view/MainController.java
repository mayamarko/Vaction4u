package view;

import controller.VacationController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    private DeleteUserController deleteController;
    @FXML
    private LogInController logInController;
    @FXML
    private CreateVacationController createVacationController;

    private String loggedUsername;

    @FXML
    public javafx.scene.control.Button btn_createUser;
    public javafx.scene.control.Button btn_searchUser;
    public javafx.scene.control.Button btn_updateUser;
    public javafx.scene.control.Button btn_deleteUser;
    public javafx.scene.control.Button btn_logIn;
    public javafx.scene.control.Button btn_createVacation;
    public javafx.scene.control.TextArea txt_user;


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
        txt_user.setEditable(false);
        txt_user.setDisable(true);
        txt_user.setVisible(false);
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

    public void setSubController() {
        cUController.injectMainController(this, vacationController);
        //       searchController.injectMainController(this, vacationController);
    }

    public void Create(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Create New User");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/CreateUser.fxml").openStream());

            cUController = fxmlLoader.getController();
            cUController.injectMainController(this, vacationController);
            Scene scene = new Scene(root, 400, 400);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void Search(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Read User");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/SearchUser.fxml").openStream());
            searchController = fxmlLoader.getController();
            searchController.injectMainController(this, vacationController);
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
        if (!vacationController.isLogged()) {
            showAlert("You must be logged in to update your profile");
        } else {
            try {
                Stage stage = new Stage();
                stage.setTitle("Update User");
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("/UpdateUser.fxml").openStream());
                updateController = fxmlLoader.getController();
                updateController.injectMainController(this, vacationController);
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
    }


    public void Delete(ActionEvent actionEvent) {
        if (!vacationController.isLogged()) {
            showAlert("You must be logged in to delete your profile");
        } else {
            try {
                Stage stage = new Stage();
                stage.setTitle("Delete User");
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("/DeleteUser.fxml").openStream());
                deleteController = fxmlLoader.getController();
                deleteController.injectMainController(this, vacationController);
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

    public void CreateVacation(ActionEvent actionEvent) {
        if (!vacationController.isLogged()) {
            showAlert("You must be logged in to post a vacation");
        } else {
            try {
                Stage stage = new Stage();
                stage.setTitle("New Vacation");
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("/CreateVacation.fxml").openStream());
                createVacationController = fxmlLoader.getController();
                createVacationController.injectMainController(this, vacationController);
                Scene scene = new Scene(root, 700, 500);
                //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                stage.show();
            } catch (Exception e) {
                System.out.println("we have a problem");
            }
        }
    }

    public void LogIn(ActionEvent actionEvent) {
        if (!vacationController.isLogged()) {
            try {
                Stage stage = new Stage();
                stage.setTitle("Log In User");
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("/LogInUsers.fxml").openStream());
                logInController = fxmlLoader.getController();
                logInController.injectMainController(this, vacationController);
                Scene scene = new Scene(root, 300, 300);
                //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
                stage.setScene(scene);
                SetStageCloseLogInEvent(stage);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                stage.show();

            } catch (Exception e) {
                System.out.println("we hava a problem");
            }

        }
    }

    private void SetStageCloseLogInEvent(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                if (vacationController.isLogged()) {
                    txt_user.setText("Hello, " + vacationController.getUsername());
                    txt_user.setDisable(false);
                    txt_user.setVisible(true);
                    btn_logIn.setDisable(true);
                }
            }
        });
    }


    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        alert.setTitle("Please note");
        alert.setHeaderText(alertMessage);
    }
}
