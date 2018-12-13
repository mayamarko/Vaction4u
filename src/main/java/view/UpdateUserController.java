package view;

import controller.VacationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserController {

    @FXML
    public javafx.scene.control.TextField lbl_username;
    public javafx.scene.control.Button btn_ok;

    public javafx.scene.control.TextField lbl_fName;
    public javafx.scene.control.TextField lbl_lName;
    public javafx.scene.control.TextField lbl_password;
    public javafx.scene.control.TextField lbl_city;
    public javafx.scene.control.DatePicker lbl_birthday;
    public javafx.scene.control.TextField lbl_fName_title;
    public javafx.scene.control.TextField lbl_lName_title;
    public javafx.scene.control.TextField lbl_password_title;
    public javafx.scene.control.TextField lbl_city_title;
    public javafx.scene.control.TextField lbl_birthday_title;
    public javafx.scene.control.Button btn_saveChanges;
    public javafx.scene.control.TextField lbl_new_username;
    public javafx.scene.control.TextField lbl_new_username_title;
    public javafx.scene.control.TextField lbl_update_message_title;
    public javafx.scene.control.TextField lbl_picture_title;
    public javafx.scene.control.Button btn_selectPicture;

    private MainController mainController;
    private VacationController vacationController;
    private byte[] person_image;

    @FXML
    private void initialize() {
        lbl_fName_title.setVisible(false);
        lbl_birthday_title.setVisible(false);
        lbl_city_title.setVisible(false);
        lbl_password_title.setVisible(false);
        lbl_lName_title.setVisible(false);
        lbl_fName.setVisible(false);
        lbl_birthday.setVisible(false);
        lbl_city.setVisible(false);
        lbl_password.setVisible(false);
        lbl_lName.setVisible(false);
        btn_saveChanges.setVisible(false);
        btn_ok.setDisable(false);
        lbl_new_username.setVisible(false);
        lbl_new_username_title.setVisible(false);
        lbl_update_message_title.setVisible(false);
        lbl_username.setEditable(true);
        btn_selectPicture.setVisible(false);
        lbl_picture_title.setVisible(false);
    }

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
    private void Update() {
        boolean canResume = true;
        Map<String, String> newInfo = new HashMap<>();
        if (!lbl_new_username.getText().trim().isEmpty()) {
            if (vacationController.userExist(lbl_new_username.getText()) || lbl_new_username.getText().equals("admin")) {
                canResume = false;
                showAlert("Username already exist, choose another one. \nNo changes has been made.");
            } else {
                newInfo.put("username", lbl_new_username.getText());
            }
        }
        if (!lbl_fName.getText().trim().isEmpty())
            newInfo.put("fName", lbl_fName.getText());
        if (!lbl_lName.getText().trim().isEmpty())
            newInfo.put("lName", lbl_lName.getText());
        if (!lbl_password.getText().trim().isEmpty())
            newInfo.put("password", lbl_password.getText());
        if (!lbl_city.getText().trim().isEmpty())
            newInfo.put("address", lbl_city.getText());
        if (lbl_birthday.getValue() != null) {
            if (Period.between(lbl_birthday.getValue(), LocalDate.now()).getYears() < 18) {
                showAlert("Users have to be 18 and older");
                canResume = false;
            }
            newInfo.put("birthday", lbl_birthday.getValue().toString());
        }
        if(person_image != null){
            vacationController.updatePicture(lbl_username.getText(), person_image);
        }


        if (canResume) {
            if (vacationController.updateUser(lbl_username.getText(), newInfo)) {
                showAlert("Your Changes saved successfully");
            } else
                showAlert("Username is not exist!");
        }

        this.initialize();
    }

    @FXML
    private void AllowUpdate() {
        if(lbl_username.getText().equals(vacationController.username) || vacationController.username.equals("admin")) {
            if (vacationController.userExist(lbl_username.getText())) {
                lbl_fName_title.setVisible(true);
                lbl_birthday_title.setVisible(true);
                lbl_city_title.setVisible(true);
                lbl_password_title.setVisible(true);
                lbl_lName_title.setVisible(true);
                lbl_fName.setVisible(true);
                lbl_birthday.setVisible(true);
                lbl_city.setVisible(true);
                lbl_password.setVisible(true);
                lbl_lName.setVisible(true);
                btn_saveChanges.setVisible(true);
                btn_ok.setDisable(true);
                lbl_username.setEditable(false);
                lbl_new_username.setVisible(true);
                lbl_new_username_title.setVisible(true);
                lbl_update_message_title.setVisible(true);
                btn_selectPicture.setVisible(true);
                lbl_picture_title.setVisible(true);
            } else {
                showAlert("The user " + lbl_username.getText() + " is not exist, try again.");
            }
        }else{
            showAlert("You can NOT change another profile, just yours..");
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
