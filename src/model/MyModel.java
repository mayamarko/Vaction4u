package model;

import java.sql.*;
import java.util.Observable;

public class MyModel extends Observable implements IModel {


    private String path = System.getProperty("user.dir");
    private String url = "jdbc:sqlite:" + path + "\\vacations.db";

    /**
     * Connect to the vacation.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the database
     */
    public void createNewUsersTable() {

        // SQL statement for creating a new users table
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	username text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	birthday DATE NOT NULL, \n"
                + " fName text NOT NULL, \n"
                + " lName text NOT NULL, \n"
                + " address text \n"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        String sql2 = "INSERT INTO users(username, password, birthday, fName, lName, address) VALUES(?, ?, ?, ?, ?, ?)";
////        try (Connection conn = DriverManager.getConnection(url);
////             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
////            pstmt.setString(1, "A&Mmmm");
////            pstmt.setString(2, "123456");
////            pstmt.setDate(3, java.sql.Date.valueOf("1994-10-23"));
////            pstmt.setString(4, "Adi");
////            pstmt.setString(5, "Maya");
////            pstmt.setString(6, "Sofia");
////            pstmt.executeUpdate();
////        } catch (SQLException e) {
////            System.out.println(e.getMessage());
////        }
////
////        String sql3 = "SELECT username, birthday FROM users";
////
////        try (Connection conn = DriverManager.getConnection(url);
////             Statement stmt = conn.createStatement();
////             ResultSet rs = stmt.executeQuery(sql3)) {
////
////            // loop through the result set
////            while (rs.next()) {
////                System.out.println(rs.getString("username") + "\t" +
////                        rs.getDate("birthday"));
////            }
////        } catch (SQLException e) {
////            System.out.println(e.getMessage());
////        }
    }

    @Override
    public void create(String username, String password, int dd, int mm, int yy, String fName, String lName, String address) {
        String sql2 = "INSERT INTO users(username, password, birthday, fName, lName, address) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setString(1, "m");
            pstmt.setString(2, "123456");
            pstmt.setDate(3, java.sql.Date.valueOf("1994-10-23"));
            pstmt.setString(4, "Adi");
            pstmt.setString(5, "Maya");
            pstmt.setString(6, "Sofia");
            pstmt.executeUpdate();
           // conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String read(String username) {
        return null;
    }

    @Override
    public void update(String username, int index, String newInfo) {

    }

    @Override
    public void delete(String username) {

    }


}
