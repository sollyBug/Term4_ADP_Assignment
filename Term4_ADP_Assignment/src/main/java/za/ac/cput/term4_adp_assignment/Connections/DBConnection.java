package za.ac.cput.term4_adp_assignment.Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection connect() {
        Connection con = null;
        try {
            // Database connection parameters
            String URL = "jdbc:derby://localhost:1527/StudentEnrolmentDB";
            String Username = "Enrollify";
            String Password = "1234";
            
            // Attempting to the establish database connection
            con = DriverManager.getConnection(URL, Username, Password);
            System.out.println("Connected successfully");
        }
        catch (SQLException e) {
            // Logs connection failure and returns a null
            System.out.println("Connection error: " + e.getMessage());
            return null;
        }
        return con;
    }

}
