package model;

import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
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
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
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

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * creating new user
     *
     * @param username
     * @param password
     * @param fName    - first name
     * @param lName    - last name
     * @param address  - city
     */
    @Override
    public boolean createUser(String username, String password, LocalDate birthday, String fName, String lName, String address) {
        boolean succeed = true;
        String sql = "INSERT INTO users(username, password, birthday, fName, lName, address) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setDate(3, java.sql.Date.valueOf(birthday));
                pstmt.setString(4, fName);
                pstmt.setString(5, lName);
                pstmt.setString(6, address);
                pstmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException e) {
            succeed = false;
            System.out.println(e.getMessage());
        }
        return succeed;
    }

    /**
     * @param username - the username you want to read about
     * @return dictionary with the information (first name, last name, city) about the given username.
     */
    @Override
    public Map readUser(String username) {
        String sql = "SELECT username, fName, lName, birthday, password, address FROM users WHERE username = ?";
        Map<String, String> result = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                // loop through the result set
                while (rs.next()) {
                    result.put("username", rs.getString("username"));
                    result.put("fName", rs.getString("fName"));
                    result.put("lName", rs.getString("lName"));
                    result.put("birthday", rs.getDate("birthday").toString());
                    result.put("password", rs.getString("password"));
                    result.put("city", rs.getString("address"));

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * @param username the username you want to update it's info
     * @param newInfo  the new info of the user
     */
    @Override
    public boolean updateUser(String username, Map<String, String> newInfo) {
        if (!isExist(username)) {
            return false;
        }
        for (Map.Entry<String, String> entry : newInfo.entrySet()) {
            if (entry.getValue() != null && entry.getKey() != "username") {
                boolean isDateChanged = false;
                Date date = null;
                if (entry.getKey() == "birthday") {
                    isDateChanged = true;
                    String tmp = entry.getValue();
                    date = java.sql.Date.valueOf(tmp);
                }
                String sql = "UPDATE users SET " + entry.getKey() + " = ? WHERE username = ?";
                try (Connection conn = DriverManager.getConnection(url)) {
                    if (conn != null) {
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        if (isDateChanged) {
                            pstmt.setDate(1, date);
                        } else {
                            pstmt.setString(1, entry.getValue());
                        }
                        pstmt.setString(2, username);

                        pstmt.executeUpdate();
                        conn.close();
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if(newInfo.containsKey("username")){
            String sql = "UPDATE users SET username = ? WHERE username = ?";
            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, newInfo.get("username"));
                    pstmt.setString(2, username);
                    pstmt.executeUpdate();
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    /**
     * @param username - the user you want to delete.
     */
    @Override
    public boolean deleteUser(String username) {
        boolean succeed = isExist(username);
        if (succeed) {
            String sql = "DELETE FROM users WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {

                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    pstmt.setString(1, username);

                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                succeed = false;
            }
        }
        return succeed;
    }

    /**
     * log in the web with your username and password.
     *
     * @param username
     * @param password
     * @return true if the log in succeed and false otherwise
     */
    public boolean logIn(String username, String password) {
        boolean result = false;
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                // loop through the result set
                if (rs.next()) {
                    if (rs.getString("password") == password) {
                        result = true;
                    }
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public boolean isExist(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    conn.close();
                    return true;
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
