package za.ac.cput.term4_adp_assignment.authManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.ac.cput.term4_adp_assignment.Connections.DBConnection;

/**
 * Authentication class for handling user login operations.
 * Provides methods to validate user credentials and manage login sessions.
 */
public class auth_log {

    /**
     * This authenticates the user credentials against the database and updates last login timestamp.
     * This method performs user validation and session initialization by checking the users email and password upon successful login.
     */
    public static String handleLogin(String email, String password) {
        // These SQL queries for user validation and login timestamp update
        String checkUser = "SELECT user_id, role FROM users WHERE email = ? AND password = ?";
        String updateLogin = "UPDATE users SET last_login = CURRENT_TIMESTAMP WHERE user_id = ?";

        try (Connection con = DBConnection.connect()) {
            // This checks and validate database connection
            if (con == null || con.isClosed()) {
                System.out.println("Database connection failed");
                return "SERVER_DOWN";
            }
            
            // This executes the user validation query
            try (PreparedStatement pstmt = con.prepareStatement(checkUser)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // This gets the users details from result set
                    int userId = rs.getInt("user_id");
                    String role = rs.getString("role");

                    // This updates the last login timestamp
                    try (PreparedStatement uPstmt = con.prepareStatement(updateLogin)) {
                        uPstmt.setInt(1, userId);
                        uPstmt.executeUpdate();
                    }

                    return "SUCCESS," + userId + "," + role;
                } else {
                    return "FAIL";
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error in handleLogin: " + e.getMessage());
            return "SERVER_DOWN";
        }
    }
}