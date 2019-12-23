package main.java.com.johnlackwilson;

import java.nio.file.FileSystems;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to connect and interface with the database of the app.
 *
 * @author John Lack-Wilson
 */
class DBConnector {

    private static final String TABLE_NAME = "app";
    private static final String DATA_DIR = ".data";
    private String DB_URL = null;

    DBConnector() {
        this.DB_URL = "jdbc:sqlite:" + FileSystems.getDefault().getPath(".").toAbsolutePath().toString() + "/" + DATA_DIR + "/" + TABLE_NAME + ".db";
    }

    /**
     * <p>Connects to the database.</p>
     * @return the open connection or null.
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);

        } catch(SQLException e) {
            System.err.println("Failed to establish connection.");
        }
        return conn;
    }

    /**
     * <p>Returns all items in the app's database.</p>
     * @return ArrayList of all items in the database.
     */
    List<Todo> getAll() {
        Connection conn = null;
        List<Todo> todoItems = new ArrayList<>();

        try {
            conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from " + TABLE_NAME);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                boolean complete = resultSet.getBoolean("complete");
                String dateAddedString = resultSet.getString("date_added");
                String dateDueString = resultSet.getString("date_due");

                // sqlite returns an ISO8601 type string so we need to format for Java
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateAdded = LocalDateTime.parse(dateAddedString, formatter);

                // dateDue could be null so check first
                LocalDateTime dateDue = null;
                if(dateDueString != null) {
                    dateDue = LocalDateTime.parse(dateDueString, formatter);
                }

                todoItems.add(new Todo(id, title, complete, dateAdded, dateDue));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return todoItems;
    }

    /**
     * <p>Adds an item to the database.</p>
     * @param title of the item to add.
     */
    void add(String title) {
        Connection conn = null;
        try {
            conn = this.connect();
            String sql = "insert into " + TABLE_NAME + " (title) values (?)";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, title);

            // Check it works - 0 on failure.
            int success = prepStmt.executeUpdate();
            if(success != 0) {
                System.out.println("[SUCCESS] Item was added to the todo list.");
            } else {
                System.err.println("[ERROR] Failed to add item to the todo list.");
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * <p>Deletes an item from the database with a particular ID.</p>
     * @param itemNumber the ID of the item.
     */
    void delete(int itemNumber) {
        Connection conn = null;
        try {
            conn = this.connect();
            String sql = "delete from " + TABLE_NAME + " where id=?";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, itemNumber);

            // Check it works - 0 on failure.
            int success = prepStmt.executeUpdate();
            if(success != 0) {
                System.out.println("[SUCCESS] Item was deleted from the todo list.");
            } else {
                System.err.println("[ERROR] Item does not exist");
            }
        } catch(SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }
}
