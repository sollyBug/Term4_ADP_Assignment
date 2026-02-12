package za.ac.cput.term4_adp_assignment.Network_Handle;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import za.ac.cput.term4_adp_assignment.Connections.DBConnection;
import za.ac.cput.term4_adp_assignment.DBManager.EnrollmentDAO;
import za.ac.cput.term4_adp_assignment.authManager.Course;
import static za.ac.cput.term4_adp_assignment.DBManager.EnrollmentDAO.getAllCourses;

/**
 * This is the multi-threaded server for handling client connections and processing requests.
 * Manages all client-server communication including authentication, course management,
 * enrollment operations, and administrative functions.
 */
public class Server {

    /**
     * Main server entry point that initializes the server socket and accepts client connections.
     * It creates a new thread for each connected client to handle concurrent requests.
     */
    public static void main(String[] args) {
        int port = 5000; 

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            // Continuous loop to accept client connections
            while (true) {
                // Accepts the incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());
                // Starts a new thread for each client connection to handle multiple clients simultaneously
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    /**
     * Handles individual client connections and processes their requests.
     */
    static class ClientHandler implements Runnable {

        private Socket socket; // Client socket connection

        /**
         * Constructs a ClientHandler for a specific client socket.
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Main client handling loop that processes incoming requests.
         */
        @Override
        public void run() {
            // Initializes input and output streams for client communication
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String request;
                // Continuously reads the requests from client until the connection is closed
                while ((request = in.readLine()) != null) {
                    System.out.println("Received request: " + request);

                    if (request.startsWith("LOGIN")) {
                        handleLogin(request, out); // Handles user authentication from the loginForm GUI
                    } else if (request.startsWith("REGISTER")) {
                        handleRegister(request, out); // Handles new user registration from the signForm GUI
                    
                    // Course Management for Students
                    } else if (request.equalsIgnoreCase("GET_COURSES")) {
                        // Retrieves all the available courses for dashboard and registered GUIs
                        String serialized = serializeCourses(getAllCourses());
                        out.println(serialized);
                    } else if (request.startsWith("ENROLL")) {
                        handleEnroll(request, out); // Processes course enrollment from dashboard GUI
                    } else if (request.startsWith("COUNT")) {
                        handleCount(request, out); // Gets student count for specific course
                    } else if (request.startsWith("CHECK_ENROLLED")) {
                        handleCheckEnrolled(request, out); // Checks if student is enrolled in course
                    
                    // Enrollment Statistics for Students
                    } else if (request.startsWith("GET_CORE_COUNT")) {
                        handleGetCoreCount(request, out); // Gets count of core courses enrolled
                    } else if (request.startsWith("GET_ELECTIVE_COUNT")) {
                        handleGetElectiveCount(request, out); // Gets count of elective courses enrolled
                    } else if (request.startsWith("GET_ENROLLED_COUNT")) {
                        handleGetEnrolledCount(request, out); // Gets total enrolled course count
                    
                    // Administrative Statistics
                    } else if (request.equalsIgnoreCase("GET_TOTAL_STUDENTS")) {
                        handleGetTotalStudents(out); // Gets total student count for adminMain GUI
                    } else if (request.equalsIgnoreCase("GET_TOTAL_COURSES")) {
                        handleGetTotalCourses(out); // Gets total course count for adminMain GUI
                    } else if (request.equalsIgnoreCase("GET_TOTAL_ENROLLMENTS")) {
                        handleGetTotalEnrollments(out); // Gets total enrollment count for adminMain GUI
                    
                    // Student Management (Admin)
                    } else if (request.equalsIgnoreCase("GET_ALL_STUDENTS")) {
                        handleGetAllStudents(out); // Gets all students for adminMain GUI table
                    } else if (request.startsWith("DELETE_STUDENT")) {
                        handleDeleteStudent(request, out); // Deletes student from system
                    } else if (request.startsWith("UPDATE_STUDENT")) {
                        handleUpdateStudent(request, out); // Updates student information
                    
                    // Course Management (Admin)
                    } else if (request.equalsIgnoreCase("GET_ALL_COURSES")) {
                        handleGetAllCourses(out); // Gets all courses with enrollment stats
                    } else if (request.startsWith("ADD_COURSE")) {
                        handleAddCourse(request, out); // Adds new course to system
                    } else if (request.startsWith("UPDATE_COURSE")) {
                        handleUpdateCourse(request, out); // Updates existing course
                    } else if (request.startsWith("DELETE_COURSE")) {
                        handleDeleteCourse(request, out); // Deletes course and its enrollments
                    } else if (request.startsWith("CHECK_COURSE_EXISTS")) {
                        handleCheckCourseExists(request, out); // Checks for duplicate course IDs
                    
                    // Enrollment Management (Admin)
                    } else if (request.equalsIgnoreCase("GET_ALL_ENROLLMENTS")) {
                        handleGetAllEnrollments(out); // Gets all enrollment records
                    } else if (request.startsWith("DELETE_ENROLLMENT")) {
                        handleDeleteEnrollment(request, out); // Deletes specific enrollment
                    } else if (request.equalsIgnoreCase("GET_STUDENTS_FOR_COMBO")) {
                        handleGetStudentsForCombo(out); // Gets students for combo box
                    } else if (request.equalsIgnoreCase("GET_COURSES_FOR_COMBO")) {
                        handleGetCoursesForCombo(out); // Gets courses for combo box
                    } else {
                        out.println("UNKNOWN_COMMAND"); // Handle unknown requests
                    }
                }

            } catch (IOException e) {
                System.out.println("Client connection error: " + e.getMessage());
            }
        }

        /**
         * Handles user authentication requests from loginForm GUI.
         */
        private void handleLogin(String request, PrintWriter out) {
            try {
                String[] parts = request.split(",");
                if (parts.length != 3) {
                    out.println("FAIL,Invalid request format");
                    return;
                }

                String email = parts[1];
                String password = parts[2];

                // Database query to check user credentials
                try (Connection conn = DBConnection.connect(); 
                     PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT user_id, name, student_number, role FROM users WHERE email = ? AND password = ?")) {

                    pstmt.setString(1, email);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        // Extracts the user information from result set
                        int userId = rs.getInt("user_id");
                        String name = rs.getString("name");
                        String number = rs.getString("student_number"); // Used by manageSession
                        String role = rs.getString("role");
                        
                        // Update the last login timestamp for user activity tracking
                        updateLastLogin(userId);
                        
                        // Send a success response with all user data needed for session management
                        out.println("SUCCESS," + userId + "," + name + "," + number + "," + role);
                        System.out.println("Login successful for user: " + email);
                    } else {
                        out.println("FAIL,Invalid email or password");
                        System.out.println("Login failed for user: " + email);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database error during login: " + e.getMessage());
                out.println("FAIL,Server error");
            }
        }

        /**
         * Updates the last login timestamp for a user.
         */
        private void updateLastLogin(int userId) {
            try (Connection conn = DBConnection.connect(); 
                 PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE users SET last_login = CURRENT_TIMESTAMP WHERE user_id = ?")) {
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error updating last login: " + e.getMessage());
            }
        }

        /**
         * Serializes course data for transmission to client.
         */
        private String serializeCourses(List<Course> courses) {
            StringBuilder sb = new StringBuilder();
            for (Course c : courses) {
                sb.append(c.getCourseId()).append("|")        
                  .append(c.getName()).append("|")          
                  .append(c.getType()).append("|")            
                  .append(c.getCredits()).append("|")         
                  .append(c.getDescription().replace(",", " ")).append(";"); 
            }
            return sb.toString();
        }

        /**
         * Handles user registration requests from signForm GUI.
         */
        private void handleRegister(String request, PrintWriter out) {
            String[] parts = request.split(",", 6); 
            if (parts.length != 6) {
                out.println("FAIL,Invalid registration format");
                return;
            }

            String name = parts[1];
            String email = parts[2];
            String studentNumber = parts[3];
            String password = parts[4];
            String role = parts[5]; 

            try (Connection conn = DBConnection.connect(); 
                 PreparedStatement pst = conn.prepareStatement(
                    "INSERT INTO users (name, student_number, password, email, role) VALUES (?, ?, ?, ?, ?)")) {

                // Sets the parameters for prepared statement to prevent SQL injection
                pst.setString(1, name);
                pst.setString(2, studentNumber);
                pst.setString(3, password);
                pst.setString(4, email);
                pst.setString(5, role);

                int inserted = pst.executeUpdate();
                if (inserted > 0) {
                    out.println("SUCCESS");
                    System.out.println("New user registered: " + name + " at " + java.time.LocalDateTime.now());
                } else {
                    out.println("FAIL,Could not insert user");
                }

            } catch (SQLException ex) {
                // Handle specific database errors
                if (ex.getMessage().toLowerCase().contains("unique")) {
                    out.println("FAIL,Email already exists");
                } else {
                    out.println("FAIL,Database error");
                }
                ex.printStackTrace();
            }
        }

        /**
         * Handles course enrollment requests from dashboard GUI.
         */
        private void handleEnroll(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 3) {
                out.println("FAIL,Invalid format");
                return;
            }

            int userId = Integer.parseInt(parts[1]);
            String courseId = parts[2];

            //Process enrollment request and send response to client
            boolean success = EnrollmentDAO.enrollStudent(userId, courseId);
            out.println(success ? "SUCCESS" : "FAIL");
        }

        /**
         * Handles requests for student count in a specific course.
         */
        private void handleCount(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 2) {
                out.println("FAIL,Invalid format");
                return;
            }

            String courseId = parts[1];
            int count = EnrollmentDAO.getStudentCount(courseId);
            out.println(count); 
        }

        /**
         * Checks if a student is enrolled in a specific course.
         */
        private void handleCheckEnrolled(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 3) {
                out.println("FAIL,Invalid format");
                return;
            }

            int userId = Integer.parseInt(parts[1]);
            String courseId = parts[2];

            boolean isEnrolled = EnrollmentDAO.isStudentEnrolled(userId, courseId);
            out.println(isEnrolled ? "ENROLLED" : "NOT_ENROLLED");
        }

        /**
         * Gets the total number of courses a student is enrolled in.
         */
        private void handleGetEnrolledCount(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 2) {
                out.println("FAIL,Invalid format");
                return;
            }

            int userId = Integer.parseInt(parts[1]);
            int count = EnrollmentDAO.getStudentEnrolledCount(userId);
            out.println(count);
        }

        /**
         * Gets the count of core courses a student is enrolled in.
         */
        private void handleGetCoreCount(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 2) {
                out.println("FAIL,Invalid format");
                return;
            }

            int userId = Integer.parseInt(parts[1]);
            int count = EnrollmentDAO.getStudentCoreCount(userId);
            out.println(count);
        }

        /**
         * Gets the count of elective courses a student is enrolled in.
         */
        private void handleGetElectiveCount(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 2) {
                out.println("FAIL,Invalid format");
                return;
            }

            int userId = Integer.parseInt(parts[1]);
            int count = EnrollmentDAO.getStudentElectiveCount(userId);
            out.println(count);
        }

        // Administrative Methods

        /**
         * Handles request for total student count in the system.
         */
        private void handleGetTotalStudents(PrintWriter out) {
            int count = EnrollmentDAO.getTotalStudents();
            out.println(count);
        }

        /**
         * Handles request for total course count in the system and used by the adminGUi for statistic displays.
         */
        private void handleGetTotalCourses(PrintWriter out) {
            int count = EnrollmentDAO.getTotalCourses();
            out.println(count);
        }

        /**
         * Handles request for total enrollment count in the system.
         */
        private void handleGetTotalEnrollments(PrintWriter out) {
            int count = EnrollmentDAO.getTotalEnrollments();
            out.println(count);
        }

        /**
         * Handles request for all student data for administrative purposes.
         */
        private void handleGetAllStudents(PrintWriter out) {
            List<Object[]> students = EnrollmentDAO.getAllStudents();
            StringBuilder sb = new StringBuilder();
            for (Object[] student : students) {
                sb.append(student[0]).append("|") 
                  .append(student[1]).append("|") 
                  .append(student[2]).append("|") 
                  .append(student[3]).append("|") 
                  .append(student[4]).append("|") 
                  .append(student[5]).append(";"); 
            }
            out.println(sb.toString());
        }

        /**
         * Handles student deletion requests from adminMain GUI.
         */
        private void handleDeleteStudent(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 2) {
                out.println("FAIL,Invalid format");
                return;
            }

            int userId = Integer.parseInt(parts[1]);
            boolean success = EnrollmentDAO.deleteStudent(userId);
            out.println(success ? "SUCCESS" : "FAIL");
        }

        /**
         * Handles student information updates from adminMain GUI.
         */
        private void handleUpdateStudent(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 4) {
                out.println("FAIL,Invalid format");
                return;
            }

            int userId = Integer.parseInt(parts[1]);
            String name = parts[2];
            String email = parts[3];

            boolean success = EnrollmentDAO.updateStudent(userId, name, email);
            out.println(success ? "SUCCESS" : "FAIL");
        }

        /**
         * Handles request for all course data with enrollment statistics.
         */
        private void handleGetAllCourses(PrintWriter out) {
            List<Object[]> courses = EnrollmentDAO.getAllCoursesWithEnrollment();
            StringBuilder sb = new StringBuilder();
            for (Object[] course : courses) {
                sb.append(course[0]).append("|") 
                  .append(course[1]).append("|")
                  .append(course[2]).append("|") 
                  .append(course[3]).append("|") 
                  .append(course[4]).append("|") 
                  .append(course[5]).append(";"); 
            }
            out.println(sb.toString());
        }

        /**
         * Handles course addition requests from courseManage GUI.
         */
        private void handleAddCourse(String request, PrintWriter out) {
            String[] parts = request.split(",", 6); // Split into exactly 6 parts
            if (parts.length != 6) {
                out.println("FAIL,Invalid format");
                return;
            }

            String courseId = parts[1];
            String name = parts[2];
            int credits = Integer.parseInt(parts[3]);
            String type = parts[4];
            String description = parts[5];

            boolean success = EnrollmentDAO.addCourse(courseId, name, credits, type, description);
            out.println(success ? "SUCCESS" : "FAIL");
        }

        /**
         * Handles course update requests from courseManage GUI.
         */
        private void handleUpdateCourse(String request, PrintWriter out) {
            String[] parts = request.split(",", 6);
            if (parts.length != 6) {
                out.println("FAIL,Invalid format");
                return;
            }

            String courseId = parts[1];
            String name = parts[2];
            int credits = Integer.parseInt(parts[3]);
            String type = parts[4];
            String description = parts[5];

            boolean success = EnrollmentDAO.updateCourse(courseId, name, credits, type, description);
            out.println(success ? "SUCCESS" : "FAIL");
        }

        /**
         * Handles course deletion requests from courseManage GUI.
         */
        private void handleDeleteCourse(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 2) {
                out.println("FAIL,Invalid format");
                return;
            }

            String courseId = parts[1];
            boolean success = EnrollmentDAO.deleteCourse(courseId);
            out.println(success ? "SUCCESS" : "FAIL");
        }

        /**
         * Checks if a course with given ID already exists.
         */
        private void handleCheckCourseExists(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 2) {
                out.println("FAIL,Invalid format");
                return;
            }

            String courseId = parts[1];
            boolean exists = EnrollmentDAO.courseExists(courseId);
            out.println(exists ? "EXISTS" : "NOT_EXISTS");
        }

        /**
         * Handles request for all enrollment records and is used by enrollMan GUI to populate enrollment management table.
         */
        private void handleGetAllEnrollments(PrintWriter out) {
            List<Object[]> enrollments = EnrollmentDAO.getAllEnrollments();
            StringBuilder sb = new StringBuilder();
            for (Object[] enrollment : enrollments) {
                sb.append(enrollment[0]).append("|") 
                  .append(enrollment[1]).append("|") 
                  .append(enrollment[2]).append("|") 
                  .append(enrollment[3]).append("|")
                  .append(enrollment[4]).append("|") 
                  .append(enrollment[5]).append(";"); 
            }
            out.println(sb.toString());
        }

        /**
         * Handles enrollment deletion requests from enrollMan GUI.
         */
        private void handleDeleteEnrollment(String request, PrintWriter out) {
            String[] parts = request.split(",");
            if (parts.length != 2) {
                out.println("FAIL,Invalid format");
                return;
            }

            int enrollId = Integer.parseInt(parts[1]);
            boolean success = EnrollmentDAO.deleteEnrollment(enrollId);
            out.println(success ? "SUCCESS" : "FAIL");
        }

        /**
         * Handles request for student data formatted for combo boxes and is used by enrollMan GUI for student selection in enrollment management.
         */
        private void handleGetStudentsForCombo(PrintWriter out) {
            List<Object[]> students = EnrollmentDAO.getAllStudentsForComboBox();
            StringBuilder sb = new StringBuilder();
            for (Object[] student : students) {
                sb.append(student[0]).append("|") 
                  .append(student[1]).append("|") 
                  .append(student[2]).append(";"); 
            }
            out.println(sb.toString());
        }

        /**
         * Handles request for course data formatted for combo boxes and is used by enrollMan GUI for course selection in enrollment management.
         */
        private void handleGetCoursesForCombo(PrintWriter out) {
            List<Object[]> courses = EnrollmentDAO.getAllCoursesForComboBox();
            StringBuilder sb = new StringBuilder();
            for (Object[] course : courses) {
                sb.append(course[0]).append("|") // course_id
                  .append(course[1]).append("|") // course_name
                  .append(course[2]).append(";"); // course_type which is used for enrollment limit verification
            }
            out.println(sb.toString());
        }
    }
}