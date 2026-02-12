package za.ac.cput.term4_adp_assignment.DBManager;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import za.ac.cput.term4_adp_assignment.Connections.DBConnection;
import za.ac.cput.term4_adp_assignment.authManager.Course;

/**
 *  The DAO for managing course enrollments and related operations.
 *
 */
public class EnrollmentDAO {

    /**
     * This retrieves all available courses from the database.
     */
    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(new Course(
                        rs.getString("course_id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("credits"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Enrolls a student in a specific course.
     * This provides the unique identifier of the student and course.
     */
    public static boolean enrollStudent(int userId, String courseId) {
        String sql = "INSERT INTO enrolled (user_id, course_id) VALUES (?, ?)";

        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            pst.setString(2, courseId);
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            if (e.getMessage().toLowerCase().contains("unique")) {
                System.out.println("Student already enrolled in this course.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Gets the number of students enrolled in a specific course.
     * This provides the unique identifier of the course.
     */
    public static int getStudentCount(String courseId) {
        String sql = "SELECT COUNT(*) AS total FROM enrolled WHERE course_id = ?";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, courseId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Checks if a student is already enrolled in a specific course.
     * It does this by checking the unique identifier of the student and course.
     */
    public static boolean isStudentEnrolled(int userId, String courseId) {
        String sql = "SELECT COUNT(*) AS count FROM enrolled WHERE user_id = ? AND course_id = ?";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            pst.setString(2, courseId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the count of core courses a student is enrolled in.
     * It gets the count of core course by using the unique identifier.
     */
    public static int getStudentCoreCount(int userId) {
        String sql = "SELECT COUNT(*) AS total FROM enrolled e "
                + "JOIN courses c ON e.course_id = c.course_id "
                + "WHERE e.user_id = ? AND c.type = 'Core'";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the count of elective courses a student is enrolled in.
     * It does this by using the unique identifier of the student.
     */
    public static int getStudentElectiveCount(int userId) {
        String sql = "SELECT COUNT(*) AS total FROM enrolled e "
                + "JOIN courses c ON e.course_id = c.course_id "
                + "WHERE e.user_id = ? AND c.type = 'Elective'";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the total number of courses a student is enrolled in.
     * It does this by using the unique identifier of the student and returns the number of course the student is enrolled in.
     */
    public static int getStudentEnrolledCount(int userId) {
        String sql = "SELECT COUNT(*) AS total FROM enrolled WHERE user_id = ?";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // This is the Administrative Methods

    /**
     * This gets the total number of students in the system.
     */
    public static int getTotalStudents() {
        String sql = "SELECT COUNT(*) AS total FROM users WHERE role = 'student'";
        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * This gets the total number of courses in the system.
     */
    public static int getTotalCourses() {
        String sql = "SELECT COUNT(*) AS total FROM courses";
        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the total number of enrollments in the system and returns the total count of all the course enrollments.
     */
    public static int getTotalEnrollments() {
        String sql = "SELECT COUNT(*) AS total FROM enrolled";
        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * This retrieves all students with their basic information for administrative purposes and returns the list of object arrays containing student data.
     */
    public static List<Object[]> getAllStudents() {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT user_id, name, student_number, email, reg_date, last_login FROM users WHERE role = 'student'";
        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] student = {
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("student_number"),
                    rs.getString("email"),
                    rs.getTimestamp("reg_date"),
                    rs.getTimestamp("last_login")
                };
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * This deletes a student from the system by using the unique identifier of the student.
     */
    public static boolean deleteStudent(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'student'";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, userId);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates student information using the unique identifier of the student and the new name and email of the the student.
     */
    public static boolean updateStudent(int userId, String name, String email) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE user_id = ? AND role = 'student'";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setInt(3, userId);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This retrieves all courses with their enrollment counts for the administrative view.
     */
    public static List<Object[]> getAllCoursesWithEnrollment() {
        List<Object[]> courses = new ArrayList<>();
        String sql = "SELECT c.course_id, c.name, c.credits, c.type, c.description, "
                + "COUNT(e.user_id) as enrolled_students "
                + "FROM courses c "
                + "LEFT JOIN enrolled e ON c.course_id = e.course_id "
                + "GROUP BY c.course_id, c.name, c.credits, c.type, c.description";

        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] course = {
                    rs.getString("course_id"),
                    rs.getString("name"),
                    rs.getInt("credits"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getInt("enrolled_students")
                };
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * This adds a new course to the system with validation for duplicate course IDs.
     * It does this by using the unique course identifier, name of the course,credit value of the course,the type of course and the course description.
     */
    public static boolean addCourse(String courseId, String name, int credits, String type, String description) {
        // First check if course already exists (double safety)
        if (courseExists(courseId)) {
            return false;
        }

        String sql = "INSERT INTO courses (course_id, name, credits, type, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, courseId);
            pst.setString(2, name);
            pst.setInt(3, credits);
            pst.setString(4, type);

            // Handle description properly
            if (description != null && !description.isEmpty()) {
                pst.setString(5, description);
            } else {
                pst.setNull(5, java.sql.Types.VARCHAR);
            }

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            // Check if it's a duplicate key error
            if (e.getMessage().toLowerCase().contains("unique") || e.getMessage().toLowerCase().contains("duplicate")) {
                System.out.println("Duplicate course code detected: " + courseId);
                return false;
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing course's information by using the unique identifier of the course, new course name, the new credit value,new course type and new course description.
     */
    public static boolean updateCourse(String courseId, String name, int credits, String type, String description) {
        String sql = "UPDATE courses SET name = ?, credits = ?, type = ?, description = ? WHERE course_id = ?";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setInt(2, credits);
            pst.setString(3, type);
            pst.setString(4, description);
            pst.setString(5, courseId);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This deletes a course and all its enrollments from the system.
     */
    public static boolean deleteCourse(String courseId) {
        // First delete enrollments for this course
        String deleteEnrollments = "DELETE FROM enrolled WHERE course_id = ?";
        String deleteCourse = "DELETE FROM courses WHERE course_id = ?";

        try (Connection con = DBConnection.connect()) {
            con.setAutoCommit(false);

            try (PreparedStatement pst1 = con.prepareStatement(deleteEnrollments); 
                 PreparedStatement pst2 = con.prepareStatement(deleteCourse)) {

                pst1.setString(1, courseId);
                pst1.executeUpdate();

                pst2.setString(1, courseId);
                int affectedRows = pst2.executeUpdate();

                con.commit();
                return affectedRows > 0;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This checks if a course with the given ID already exists in the system and it uses the course ID to check.
     */
    public static boolean courseExists(String courseId) {
        String sql = "SELECT COUNT(*) FROM courses WHERE course_id = ?";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, courseId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method retrieves all enrollment records with student and course details.
     */
    public static List<Object[]> getAllEnrollments() {
        List<Object[]> enrollments = new ArrayList<>();
        String sql = "SELECT e.enroll_id, u.user_id, u.student_number, u.name, c.course_id, c.name as course_name, e.enroll_date "
                + "FROM enrolled e "
                + "JOIN users u ON e.user_id = u.user_id "
                + "JOIN courses c ON e.course_id = c.course_id "
                + "ORDER BY e.enroll_date DESC";

        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] enrollment = {
                    rs.getInt("enroll_id"),
                    rs.getString("student_number"),
                    rs.getString("name"),
                    rs.getString("course_id"),
                    rs.getString("course_name"),
                    rs.getTimestamp("enroll_date")
                };
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    /**
     * This deletes a specific enrollment record using the unique course identifier.
     */
    public static boolean deleteEnrollment(int enrollId) {
        String sql = "DELETE FROM enrolled WHERE enroll_id = ?";
        try (Connection con = DBConnection.connect(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, enrollId);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This retrieves all the students for the combo box display in administrative interfaces.
     */
    public static List<Object[]> getAllStudentsForComboBox() {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT user_id, student_number, name FROM users WHERE role = 'student' ORDER BY student_number";
        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] student = {
                    rs.getInt("user_id"),
                    rs.getString("student_number"),
                    rs.getString("name")
                };
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * This retrieves the all the courses for combo box to display in administrative interfaces.
     */
    public static List<Object[]> getAllCoursesForComboBox() {
        List<Object[]> courses = new ArrayList<>();
        String sql = "SELECT course_id, name, type FROM courses ORDER BY course_id";
        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] course = {
                    rs.getString("course_id"),
                    rs.getString("name"),
                    rs.getString("type")
                };
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}