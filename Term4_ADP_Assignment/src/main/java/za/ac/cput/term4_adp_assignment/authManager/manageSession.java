package za.ac.cput.term4_adp_assignment.authManager;

/**
 * Session management class for handling user authentication sessions.
 */
public class manageSession {
    
    /**this is the Session variables
     * this provides a unique identifier, email address,full name, ID number,role and the session authentication status for the logged-in users.
     */
    private static int userId;          
    private static String email;         
    private static String name;          
    private static String number;        
    private static String role;          
    private static boolean loggedIn = false; 

    /**
     * Initializes a new user session with the provided user details.
     *  this has a unique identifier for the users email,full name, ID number and their role.
     */
    public static void startSession(int uId, String mail, String userName, String idNumber, String userRole) {
        userId = uId;
        email = mail;
        name = userName;
        number = idNumber;
        role = userRole;
        loggedIn = true;
    }

    /**
     * Terminates the current user session and clears all stored user data.
     */
    public static void endSession() {
        userId = 0;
        email = null;
        name = null;
        number = null;
        role = null;
        loggedIn = false;
    }

    /**
     * Retrieves the current user's unique identifier.
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * Retrieves the current user's email address.
     */
    public static String getEmail() {
        return email;
    }

    /**
     * Retrieves the current user's full name.
     */
    public static String getName() {
        return name;
    }
    
    /**
     * Retrieves the current user's identification number.
     */
    public static String getNumber() {
        return number;
    }

    /**
     * Retrieves the current user's permission level.
     */
    public static String getRole() {
        return role;
    }

    /**
     * Checks if a user session is currently active.
     */
    public static boolean isLoggedIn() {
        return loggedIn;
    }
}