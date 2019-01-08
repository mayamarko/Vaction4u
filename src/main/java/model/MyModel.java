package model;

import java.awt.*;
import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

                    delete_all_Vacation_by_user(username);  //clear tabel -tal
                    delete_all_Message_by_user(username);   //clear tabel -tal
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
        String sql = //"PRAGMA foreign_keys = ON; \n" +
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


    /**
     * create the sales table.
     */
    public void createNewSalesTable() {
        // SQL statement for creating a new vacations table
        String sql = //"PRAGMA foreign_keys = ON; \n" +
                "CREATE TABLE IF NOT EXISTS sales (\n"
                        + "	usernameSeller text NOT NULL,\n"
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
                        + " usernameBuyer text NOT NULL, \n"
                        + "PRIMARY KEY(usernameSeller, vacID), \n"
                        + "FOREIGN KEY(usernameSeller,usernameBuyer) REFERENCES users(username,username) ON DELETE CASCADE\n"
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


    public boolean addSale(String usernameSeller, int vacId, int price, String airline, LocalDate start, LocalDate returnDate, boolean baggage, String baggageDescription, int numberOfTickets,
                           int numberOfAdults, int numberOfChilds, int numberOfInfants,
                           boolean partialPurchase, String destination, boolean flightBack, boolean direct, String vacationType, boolean accommodation, String usernameBuyer) {
        boolean succeed = true;
        String sql = "INSERT INTO sales(usernameSeller, vacID, price, airline, start, returnDate, baggage, baggageDescription, numberOfTickets, numberOfAdults, numberOfchilds, numberOfInfants, partialPurchase, destination, flightBack, direct, vacationType, accommodation, usernameBuyer)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, usernameSeller);
                pstmt.setInt(2, vacId);
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
                pstmt.setString(19, usernameBuyer);

                pstmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException e) {
            succeed = false;
            System.out.println(e.getMessage());
        }
        return succeed;
    }

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

    /**
     *
     *
     * @return  hash map the contains piers in stract : <V_ID int, V_INFO String []>  the aryy : {dest, departD, returnD, price, user, airline, baggage, baggageDisc, numOfTicket + "", numOfAdults + "", numOdChilds + "", numOdInfants + "", partial, back, direct, type, acco};
     */
    public HashMap<Integer, String[]> showAllVacations()
    {
        return showAllVacationsgegeneric("");
    }

    /**
     *
     * @param uid the user for filtering by
     * @return  hash map the contains piers in stract : <V_ID int, V_INFO String []>  the aryy : {dest, departD, returnD, price, user, airline, baggage, baggageDisc, numOfTicket + "", numOfAdults + "", numOdChilds + "", numOdInfants + "", partial, back, direct, type, acco};
     */
    public HashMap<Integer, String[]> showAllVacationsge_by_user(String uid) {
        return showAllVacationsgegeneric(uid);
    }


    /**
     *
     * @param vid the vaction ascked
     * @return  V_INFO - aryy : {dest, departD, returnD, price, user, airline, baggage, baggageDisc, numOfTicket + "", numOfAdults + "", numOdChilds + "", numOdInfants + "", partial, back, direct, type, acco};
     */
    public  String[] show_Vacation_by_vid(int vid) {
        HashMap<Integer,String[]> ans =showAllVacationsgegeneric("");
        return ans.get(vid);
    }


    /**
     *
     * @param uid the user for filtering by if  uis is "" so ther is no filtering
     * @return  hash map the contains piers in stract : <V_ID int, V_INFO String []>  the aryy : {dest, departD, returnD, price, user, airline, baggage, baggageDisc, numOfTicket + "", numOfAdults + "", numOdChilds + "", numOdInfants + "", partial, back, direct, type, acco};
     *
     */
    private HashMap<Integer, String[]> showAllVacationsgegeneric(String uid) {
        String sql;
        if(uid.equals(""))
            sql = "SELECT*FROM vacations";
        else
            sql = "SELECT*FROM vacations WHERE username = ? ";
        HashMap<Integer, String[]> result = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                if(! uid.equals(""))
                    stmt.setString(1, uid);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String dest = rs.getString("destination");
                    String departD = rs.getDate("start").toString();
                    String returnD = rs.getDate("returnDate").toString();
                    String price = rs.getString("price");
                    String user = rs.getString("username");
                    String airline = rs.getString("airline");
                    String baggage;
                    if (rs.getBoolean("baggage")) {
                        baggage = "Yes";
                    } else {
                        baggage = "No";
                    }
                    String baggageDisc = rs.getString("baggageDescription");
                    int numOfTicket = rs.getInt("numberOfTickets");
                    int numOfAdults = rs.getInt("numberOfAdults");
                    int numOdChilds = rs.getInt("numberOfChilds");
                    int numOdInfants = rs.getInt("numberOfInfants");
                    String partial;
                    if (rs.getBoolean("partialPurchase")) {
                        partial = "Yes";
                    } else {
                        partial = "No";
                    }
                    String back;
                    if (rs.getBoolean("flightBack")) {
                        back = "Yes";
                    } else {
                        back = "No";
                    }
                    String direct;
                    if (rs.getBoolean("direct")) {
                        direct = "Yes";
                    } else {
                        direct = "No";
                    }
                    String type = rs.getString("vacationType");
                    String acco;
                    if (rs.getBoolean("accommodation")) {
                        acco = "Yes";
                    } else {
                        acco = "No";
                    }


//dest,dDate,rDate,price,username,airline,baggage(bool),baggDisc,numT,numA,numC,numI,partial(bool),back(bool),direct(bool),type,acco(bool)
                    //if(isExist(user)) plaster_ was fix
                    {
                        String[] s = {dest, departD, returnD, price, user, airline, baggage, baggageDisc, numOfTicket + "", numOfAdults + "", numOdChilds + "", numOdInfants + "", partial, back, direct, type, acco};
                        int vacId = rs.getInt("vacID");
                        result.put(vacId, s);
                    }
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * delete the vacation
     *
     * @param username
     * @param vacID
     * @return
     */
    @Override
    public boolean deleteVacation(String username, int vacID) {
        String sql = "DELETE FROM vacations WHERE username = ? AND vacID = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setInt(2, vacID);

                pstmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public Vacation readVacation(int vacationID) {
        String sql = "SELECT username, vacID, price, airline, start, returnDate, baggage, baggageDescription, numberOfTickets, numberOfAdults, numberOfchilds, numberOfInfants, partialPurchase, destination, flightBack, direct, vacationType, accommodation" +
                " FROM vacations WHERE vacID = ?";
        Vacation result = null;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, vacationID);
                ResultSet rs = stmt.executeQuery();

                // loop through the result set
                while (rs.next()) {
                    String username = rs.getString("username");
                    int vacID = rs.getInt("vacID");
                    int price = rs.getInt("price");
                    String airline = rs.getString("airline");
                    LocalDate start = rs.getDate("start").toLocalDate();
                    LocalDate returnDate = rs.getDate("returnDate").toLocalDate();
                    boolean baggage = rs.getBoolean("baggage");
                    String baggageDescription = rs.getString("baggageDescription");
                    int numberOfTickets = rs.getInt("numberOfTickets");
                    int numberOfAdults = rs.getInt("numberOfAdults");
                    int numberOfChilds = rs.getInt("numberOfChilds");
                    int numberOfInfants = rs.getInt("numberOfInfants");
                    boolean partialPurchase = rs.getBoolean("partialPurchase");
                    String destination = rs.getString("destination");
                    boolean flightBack = rs.getBoolean("flightBack");
                    boolean direct = rs.getBoolean("direct");
                    String vacationType = rs.getString("vacationType");
                    boolean accommodation = rs.getBoolean("accommodation");
                    result = new Vacation(username, vacID, price, airline, start, returnDate, baggage, baggageDescription, numberOfTickets, numberOfAdults, numberOfChilds, numberOfInfants, partialPurchase, destination, flightBack,
                            direct, vacationType, accommodation);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * the func crate the  meseg tabel
     */
    public void create_message_box_Table() {

        // SQL statement for creating a new users tabl
        String sql = "CREATE TABLE IF NOT EXISTS messages_box1 (\n"
                + "	message_src text not NULL,\n"
                + "	message_dest text not NULL,\n"
                + "	message_time DATE NOT NULL, \n"
                + " message_text text NOT NULL, \n"
                + " message_type text NOT NULL, \n"
                + " is_read text NOT NULL, \n"
                + "PRIMARY KEY(message_src, message_dest ,message_text) \n"
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
     *
     * @param src_username the sours of the meseg
     * @param dest_username the dest of the meseg
     * @return
     */
    public Stack get_two_Users_messages(String src_username, String dest_username) {
        String sql = "SELECT * FROM messages_box1 WHERE ((message_src = ? AND message_dest = ?) OR (message_src = ? AND message_dest = ?))" +
                "ORDER BY message_time DESC";
        Stack result = new Stack();

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, src_username);
                stmt.setString(2, dest_username);
                stmt.setString(3, dest_username);
                stmt.setString(4, src_username);
                ResultSet rs = stmt.executeQuery();

                // loop through the result set

                while (rs.next()) {

                    String l = rs.getString("message_src") + "%";
                    l = l + rs.getString("message_src") + "%";
                    l = l + rs.getString("message_time") + "%";
                    l = l + rs.getString("message_text") + "%";
                    l = l + rs.getString("message_type");

                    result.push(l.split("%"));

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;

    }

    /**
     *
     * @param dest_username the dest of the masag
     * @return all mesag of the ascked user
     */
    public Stack get_Users_messages(String dest_username) {
        String sql = "SELECT * FROM messages_box1 WHERE  message_dest = ?" +
                "ORDER BY message_time DESC";
        Stack result = new Stack();

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, dest_username);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {

                    String l = rs.getString("message_src") + "%";
                    l = l + rs.getString("message_dest") + "%";
                    l = l + rs.getString("message_time") + "%";
                    l = l + rs.getString("message_text") + "%";
                    l = l + rs.getString("message_type");

                    //if(isExist(rs.getString("message_src"))) //plaster was fix
                    result.push(l.split("%"));

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;

    }

    /**
     *
     * @param dest_username the ascked user
     * @return True if ther is unread msg
     */
    public boolean un_read_messages(String dest_username) {
        String sql = "SELECT * FROM messages_box1 WHERE  message_dest = ?" +
                "ORDER BY message_time DESC";
        boolean resalt = false;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, dest_username);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {

                    if (rs.getString("is_read").equals("0"))
                        resalt = true;

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resalt;

    }

    /**
     * the funck set all user mark as reeaden
     * @param dest_username the ascked user
     */
    public void update_read_messages(String dest_username) {
        String sql = "UPDATE  messages_box1 SET is_read = ? WHERE  message_dest = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "1");
                pstmt.setString(2, dest_username);
                pstmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    /**
     *
     * @param src_username the src of the msseg
     * @param dest_username the dest of the meseg
     * @param message_time  the meseg time
     * @param message_text the content of the meseg
     * @param massage_type the type of the meseg (sys or other user)
     * @return true is the process sucseess
     */
    public boolean add_message(String src_username, String dest_username, String message_time, String message_text, String massage_type) {
        boolean succeed = true;
        String sql = "INSERT INTO messages_box1(message_src, message_dest, message_time, message_text, message_type, is_read)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                vacationId++;
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, src_username);
                pstmt.setString(2, dest_username);
                pstmt.setString(3, message_time);
                pstmt.setString(4, message_text);
                pstmt.setString(5, massage_type);
                pstmt.setString(6, "0");

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
     *
     *
     * @param source the src of the msseg
     * @param destination the dest of the meseg
     * @param message the content of the meseg
     * @return True if the meseg is exisst
     */
    public boolean is_messg_Exist(String source, String destination, String message) {
        String sql = "SELECT * FROM messages_box1 WHERE message_src = ? AND message_dest = ?  AND message_text = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, source);
                stmt.setString(2, destination);
                stmt.setString(3, message);
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
     *
     * @param source the src of the msseg
     * @param destination the dest of the meseg
     * @param message the content of the meseg
     */
    public void deleteMessage(String source, String destination, String message) {
        boolean succeed = is_messg_Exist(source, destination, message);
        if (succeed) {
            String sql = "DELETE FROM messages_box1 WHERE message_src = ? AND message_dest = ?  AND message_text = ?";

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {

                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    pstmt.setString(1, source);
                    pstmt.setString(2, destination);
                    pstmt.setString(3, message);

                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                succeed = false;
            }
        }
    }

    /**
     *
     * @param username user delited         --tal
     * @return True if sucsess
     */
    public boolean delete_all_Vacation_by_user(String username) {
        String sql = "DELETE FROM vacations WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);

                pstmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     *  --tal
     * @param user_name user dellited
     */
    public void delete_all_Message_by_user(String user_name) {


        String sql = "DELETE FROM messages_box1 WHERE message_src = ? OR message_dest = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, user_name);
                pstmt.setString(2, user_name);

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());


        }
    }

    public static void main(String [] args){
        MyModel m= new MyModel();

       for(String i:m.showAllVacationsge_by_user("b").get(1)){
           System.out.print(i+"- ");
       }



        m.add_message("a","q",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()).toString(),"bla1","sys");
        m.add_message("a","q",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()).toString(),"bla2","sys");
        m.add_message("a","a",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()).toString(),"bla3","sys");

        m.delete_all_Message_by_user("q");
        Stack ans = m.get_Users_messages("q");
        while (!ans.empty()){
            for(String i : (String[]) ans.pop())
                System.out.print(i+" - ");
            System.out.println();
        }

        ans = m.get_Users_messages("a");
        while (!ans.empty()){
            for(String i : (String[]) ans.pop())
                System.out.print(i+" - ");
            System.out.println();
        }

    }

}