package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }
//
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

public class Main {

    public static void createNewDatabase(String url) {

        //String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        //String url = "jdbc:sqlite:C:/Users/adi/Desktop/BGU/vacation4u/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the database
     */
    public static void createNewTable(String url) {

        // SQLite connection string
        //*********************************************************************************8888
        //String url = "jdbc:sqlite:C:/Users/adi/Desktop/BGU/vacation4u/" + name;
        //*************************************************************************************


        // SQL statement for creating a new users table
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	username text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	birthday DATE NOT NULL, \n"
                + " fName text NOT NULL, \n"
                + " lName text NOT NULL, \n"
                + " address text \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String sql2 = "INSERT INTO users(username, password, birthday, fName, lName, address) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setString(1, "A&Mmmm");
            pstmt.setString(2, "123456");
            pstmt.setDate(3, java.sql.Date.valueOf("1994-10-23"));
            pstmt.setString(4, "Adi");
            pstmt.setString(5, "Maya");
            pstmt.setString(6, "Sofia");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql3 = "SELECT username, birthday FROM users";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql3)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("username") +  "\t" +
                        rs.getDate("birthday"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String path = System.getProperty("user.dir");

        createNewDatabase("jdbc:sqlite:" + path + "\\test.db");
        createNewTable("jdbc:sqlite:" + path + "\\test.db");

    }

}