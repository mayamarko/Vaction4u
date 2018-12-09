package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.Period;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;


public class CreateUserController {

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
    private byte[] person_image;

    public void injectMainController(MainController mainController, VacationController vacationController) {
        this.mainController = mainController;
        this.vacationController = vacationController;
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText(alertMessage);
        alert.show();
        alert.setTitle("Please note");
        alert.setHeaderText(alertMessage);
    }

    @FXML
    private void signIn() {

        if (lbl_fName.getText().trim().isEmpty() || lbl_lName.getText().trim().isEmpty() || lbl_password.getText().trim().isEmpty() ||
                lbl_city.getText().trim().isEmpty() || lbl_email.getText().trim().isEmpty() || lbl_birthday.getValue() == null) {
            showAlert("One or more details are missing");
        } else {
            if (Period.between(lbl_birthday.getValue(), LocalDate.now()).getYears() < 18) {
                showAlert("Users have to be 18 and older");
            } else {
                boolean succeedLogged = vacationController.createUser(lbl_username.getText(), lbl_password.getText(), lbl_birthday.getValue(), lbl_fName.getText(), lbl_lName.getText(), lbl_city.getText(), lbl_email.getText(), person_image);

                if (succeedLogged) {
                    showAlert(lbl_username.getText() + " is registered");
                } else {
                    showAlert("The username " + lbl_username.getText() + " is taken, please choose another one.");
                }
            }
        }
    }

    @FXML
    private void selectPicture(){
        FileChooser chooser = new FileChooser();
        File f = chooser.showOpenDialog(null);
        String filename = f.getAbsolutePath();
        try{
            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum; (readNum=fis.read(buf)) != -1; ){
                bos.write(buf, 0, readNum);
            }
            person_image = bos.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
