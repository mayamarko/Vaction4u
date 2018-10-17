package view;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

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

//package net.sqlitetutorial;

//        import java.sql.Connection;
//        import java.sql.DatabaseMetaData;
//        import java.sql.DriverManager;
//        import java.sql.SQLException;
//
///**
// *
// * @author sqlitetutorial.net
// */
//public class Main {
//
//    /**
//     * Connect to a sample database
//     *
//     * @param fileName the database file name
//     */
//    public static void createNewDatabase(String fileName) {
//
//        //String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
//        String url = "jdbc:sqlite:C:/Users/maya8/Desktop" + fileName;
//
//        try (Connection conn = DriverManager.getConnection(url)) {
//            if (conn != null) {
//                DatabaseMetaData meta = conn.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        createNewDatabase("test.db");
//
//    }
//}

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;
        import java.sql.Statement;

/**
 *
 * @author sqlitetutorial.net
 */
public class Main {

    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable() {

        // SQLite connection string
        //*********************************************************************************8888
        String url = "jdbc:sqlite:C://Users/adi/Desktop/BGU/vacation4u";
        //*************************************************************************************


        // SQL statement for creating a new users table
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	username text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	birthday DATE NOT NULL \n"
                + " fName text NOT NULL \n"
                + " lName text NOT NULL \n"
                + " address text \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createNewTable();

    }

}