package za.ac.cput.term4_adp_assignment.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import za.ac.cput.term4_adp_assignment.Connections.DBConnection;

/**
 * Handles database initialization including table creation and sample data insertion.
 * This class is responsible for setting up the database schema and populating it
 * with initial data when the application starts.
 */
public class DBStartup {
    
    /**
     * Initializes the database by dropping existing tables, creating new tables,
     * and inserting sample data. 
     */
    public static void initializeDB() {
        try (Connection con = DBConnection.connect(); Statement stmt = con.createStatement()) {
            dropTables(stmt);
            createTables(stmt);
            insert(stmt);

            System.out.println("Database tables created successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    /**
     * Creates all required database tables for the Student Enrollment System.
     */
    private static void createTables(Statement stmt) throws SQLException {
        // Creates users table for both students and administrators
        stmt.execute("CREATE TABLE users (" +
                "user_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "name VARCHAR(100) NOT NULL, " +
                "student_number VARCHAR(20) NOT NULL UNIQUE, " +
                "password VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "last_login TIMESTAMP, " +
                "role VARCHAR(20) NOT NULL" +  // student or admin
                ")");
        System.out.println("User table created");
        
        // Creates courses table to store course information
        stmt.execute("""
            CREATE TABLE courses (
                course_id VARCHAR (10) PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                type VARCHAR(50) NOT NULL,
                credits INT NOT NULL,
                description VARCHAR (1000)
            )
        """);
        System.out.println("Courses table created");
        
        // Creates enrolled table as a junction table for user-course relationships
        stmt.execute("""
            CREATE TABLE enrolled (
                enroll_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                user_id INT NOT NULL,
                course_id VARCHAR(10) NOT NULL,
                enroll_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
                UNIQUE (user_id, course_id)
            )
        """);
        System.out.println("Enrolled table created.");
    }

    /**
     * Drops existing tables in the database to ensure clean initialization.
     */
    private static void dropTables(Statement stmt) {
        String[] tables = {"enrolled", "courses", "users"};
        for (String table : tables) {
            try {
                stmt.execute("DROP TABLE " + table);
                System.out.println("Dropped table: " + table);
            } catch (SQLException e) {
                System.out.println("Table " + table + " doesn't exist or couldn't be dropped: " + e.getMessage());
            }
        }
    }

    /**
     * This inserts sample data into the database tables for testing and demonstration and includes test users and a comprehensive set of courses with descriptions.
     * 
     */
    private static void insert(Statement stmt) throws SQLException {
        // Insert sample user data
        String[] users = {
            "('Lyle Solomons', 'password', '62582', 'lyle@gmail.com','student')"
        };

        for (String u : users) {
            stmt.execute("INSERT INTO users (name, password, student_number, email, role) VALUES " + u);
        }
        
        System.out.println("Inserted test users");
        
        // Insert comprehensive course catalog
        String[] courses = {
            "('C:PRG1', 'Introduction to Programming', 'Core', 4, 'Fundamental programming concepts using Java. Covers variables, control structures, and methods.')",
            "('C:DSA1', 'Data Structures & Algorithms', 'Core', 4, 'Advanced data organization and algorithmic problem-solving including lists, trees, and sorting algorithms.')",
            "('C:DMS1', 'Database Management Systems', 'Core', 3, 'Covers SQL, normalization, transactions, and database design principles.')",
            "('C:OOP1', 'Object-Oriented Programming', 'Core', 4, 'Covers inheritance, polymorphism, and encapsulation in Java.')",
            "('C:WDV1', 'Web Development', 'Core', 3, 'Frontend and backend web development using HTML, CSS, JavaScript, and Node.js.')",
            "('C:SE1', 'Software Engineering', 'Core', 3, 'Covers software lifecycle, design patterns, and testing methodologies.')",
            "('C:CN1', 'Computer Networks', 'Core', 3, 'Study of network protocols, architectures, and communications models.')",
            "('C:OS1', 'Operating Systems', 'Core', 3, 'Covers process management, memory management, and file systems.')",
            "('E:AI1', 'Artificial Intelligence', 'Elective', 4, 'Introduction to AI including machine learning and neural networks.')",
            "('E:CF1', 'Cybersecurity Fundamentals', 'Elective', 3, 'Security concepts, cryptography, and ethical hacking basics.')",
            "('E:MAD1', 'Mobile App Development', 'Elective', 3, 'Development of mobile applications using modern frameworks.')",
            "('E:CC1', 'Cloud Computing', 'Elective', 3, 'Cloud architecture, virtualization, and distributed applications.')",
            "('E:ML1', 'Machine Learning', 'Elective', 4, 'Supervised and unsupervised learning techniques and neural networks.')",
            "('E:STQ', 'Software Testing & QA', 'Elective', 3, 'Testing processes, test-driven development, and QA standards.')",
            "('E:BBa1', 'Big Data Analytics', 'Elective', 4, 'Data analysis using Hadoop and Spark for large-scale datasets.')",
            "('E:HCI1', 'Human-Computer Interaction', 'Elective', 3, 'Designing user interfaces and evaluating usability.')"
        };

        for (String c : courses) {
            stmt.execute("INSERT INTO courses (course_id, name, type, credits, description) VALUES " + c);
        }
        System.out.println("Inserted sample courses");
    }
}