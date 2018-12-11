package model;

import java.awt.*;
import java.io.InputStream;
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
    private static boolean isLogged = false;
    private static int vacationId;

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
                //PRAGMA foreign_keys = ON;
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
                + " address text, \n"
                + " mail text, \n"
                + " image BLOB \n"
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
     * @param mail     - email
     */
    @Override
    public boolean createUser(String username, String password, LocalDate birthday, String fName, String lName, String address, String mail, byte[] image) {
        boolean succeed = true;
        String sql = "INSERT INTO users(username, password, birthday, fName, lName, address, mail, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setDate(3, java.sql.Date.valueOf(birthday));
                pstmt.setString(4, fName);
                pstmt.setString(5, lName);
                pstmt.setString(6, address);
                pstmt.setString(7, mail);
                pstmt.setBytes(8, image);
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
        String sql = "SELECT username, fName, lName, birthday, password, address, mail FROM users WHERE username = ?";
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
                    result.put("email", rs.getString("mail"));

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public byte[] readPicture(String username) {
        byte[] result;
        String qu = "select image from users where username = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(qu);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                result = rs.getBytes("image");
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
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
            if (entry.getValue() != null && !entry.getKey().equals("username")) {
                boolean isDateChanged = false;
                Date date = null;
                if (entry.getKey().equals("birthday")) {
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
        if (newInfo.containsKey("username")) {
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

    @Override
    public void updatePicture(String username, byte[] picture) {
        String sql = "UPDATE users SET image = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setBytes(1, picture);
                pstmt.setString(2, username);
                pstmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
                    if (rs.getString("password").equals(password)) {
                        result = true;
                        isLogged = true;
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

    /**
     * create the vacation table.
     */
    public void createNewVacationsTable() {
        // SQL statement for creating a new vacations table
        String sql = "PRAGMA foreign_keys = ON; \n" +
                "CREATE TABLE IF NOT EXISTS vacations (\n"
                + "	username text NOT NULL,\n"
                + "	vacID INTEGER NOT NULL,\n"
                + "	price INTEGER NOT NULL,\n"
                + "	airline text NOT NULL,\n"
                + "	start DATE NOT NULL, \n"
                + "	returnDate DATE NOT NULL, \n"
                + " baggage BOOLEAN NOT NULL, \n"
                + " baggageDescription text, \n"
                + " numberOfTickets INTEGER NOT NULL, \n"
                + " numberOfAdults INTEGER NOT NULL, \n"
                + " numberOfChilds INTEGER NOT NULL, \n"
                + " numberOfInfants INTEGER NOT NULL, \n"
                + " partialPurchase BOOLEAN NOT NULL, \n"
                + " destination text NOT NULL, \n"
                + " flightBack BOOLEAN NOT NULL, \n"
                + " direct BOOLEAN NOT NULL, \n"
                + " vacationType text, \n"
                + " accommodation BOOLEAN, \n"
                + "PRIMARY KEY(username, vacID), \n"
                + "FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE\n"
                //+ "ON DELETE CASCADE \n"
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

        String sql2 = "SELECT * FROM vacations ORDER BY vacID DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql2);
                ResultSet rs = stmt.executeQuery();
                vacationId = rs.getInt("vacID");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    public void createNewTicketsTable() {
        // SQL statement for creating a new tickets table
        String sql = "CREATE TABLE IF NOT EXISTS tickets (\n"
                + "	username text NOT NULL,\n"
                + "	vacID INTEGER NOT NULL,\n"
                + "	ticketID INTEGER NOT NULL,\n"
                + "	ticketType text NOT NULL, \n"
                + "PRIMARY KEY(username, vacID, ticketID), \n"
                + "FOREIGN KEY(username, vacID) REFERENCES vacations(username, vacID) ON DELETE CASCADE\n"
                //+ "ON DELETE CASCADE \n"
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
    }*/

    public void createNewAccommodationTable() {
        // SQL statement for creating a new accommodation table
        String sql = "CREATE TABLE IF NOT EXISTS accommodation (\n"
                + "	username text NOT NULL,\n"
                + "	vacID INTEGER NOT NULL,\n"
                + "	placeName text NOT NULL,\n"
                + "	address text NOT NULL,\n"
                + "	grade INTEGER NOT NULL,\n"
                + "PRIMARY KEY(username, vacID), \n"
                + "FOREIGN KEY(username, vacID) REFERENCES vacations(username, vacID) ON DELETE CASCADE\n"
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

    public boolean createVacation(String username, int price, String airline, LocalDate start, LocalDate returnDate, boolean baggage, String baggageDescription, int numberOfTickets,
                                  int numberOfAdults, int numberOfChilds, int numberOfInfants,
                                  boolean partialPurchase, String destination, boolean flightBack, boolean direct, String vacationType, boolean accommodation) {
        boolean succeed = true;
        String sql = "INSERT INTO vacations(username, vacID, price, airline, start, returnDate, baggage, baggageDescription, numberOfTickets, numberOfAdults, numberOfchilds, numberOfInfants, partialPurchase, destination, flightBack, direct, vacationType, accommodation)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                vacationId++;
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setInt(2, vacationId);
                pstmt.setInt(3, price);
                pstmt.setString(4, airline);
                pstmt.setDate(5, java.sql.Date.valueOf(start));
                pstmt.setDate(6, java.sql.Date.valueOf(returnDate));
                pstmt.setBoolean(7, baggage);
                pstmt.setString(8, baggageDescription);
                pstmt.setInt(9, numberOfTickets);
                pstmt.setInt(10, numberOfAdults);
                pstmt.setInt(11, numberOfChilds);
                pstmt.setInt(12, numberOfInfants);
                pstmt.setBoolean(13, partialPurchase);
                pstmt.setString(14, destination);
                pstmt.setBoolean(15, flightBack);
                pstmt.setBoolean(16, direct);
                pstmt.setString(17, vacationType);
                pstmt.setBoolean(18, accommodation);

                pstmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException e) {
            succeed = false;
            System.out.println(e.getMessage());
        }
        return succeed;
    }

    /*
    public boolean addTickets(String username, int ticketID, String ticketType) {
        boolean succeed = true;
        String sql = "INSERT INTO tickets(username, vacID, ticketID, ticketType)" +
                " VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                vacationId++;
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setInt(2, vacationId);
                pstmt.setInt(3, ticketID);
                pstmt.setString(4, ticketType);

                pstmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException e) {
            succeed = false;
            System.out.println(e.getMessage());
        }
        return succeed;
    }*/

    public boolean addAccommodation(String username, String placeName, String address, int grade) {
        boolean succeed = true;
        String sql = "INSERT INTO accommodation(username, vacID, placeName, address, grade)" +
                " VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                vacationId++;
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setInt(2, vacationId);
                pstmt.setString(3, placeName);
                pstmt.setString(4, address);
                pstmt.setInt(5, grade);

                pstmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException e) {
            succeed = false;
            System.out.println(e.getMessage());
        }
        return succeed;
    }
}
