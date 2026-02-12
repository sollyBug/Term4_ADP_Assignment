package za.ac.cput.term4_adp_assignment.Admin_Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import za.ac.cput.term4_adp_assignment.Worker.reusable;
import java.util.List;
import java.util.ArrayList;
import za.ac.cput.term4_adp_assignment.Network_Handle.Client;
import za.ac.cput.term4_adp_assignment.Student_Gui.registered;
import za.ac.cput.term4_adp_assignment.authManager.Course;
import za.ac.cput.term4_adp_assignment.authManager.manageSession;

/**
 * This is the administrator enrollment management interface for managing student course enrollments.
 * It provides functionality to view, add, and delete enrollment records with real-time updates.
 */
public class enrollMan extends reusable implements ActionListener {

    // GUI Component declarations
    private JPanel main, header, logo, subHead, profile, pBox, admin, content, sideBar, mainTitlePanel, mainContent, statsPanel, holder, ban, outban, buts, in, comBox, butin, menuVibe1, menuVibe2, menuVibe3;
    private JLabel subHead1, subHead2, mainTitle, mainSub, adminLbl, adLbl, uLbl;
    private JScrollPane scroll;
    private JButton ed, del, add, studs, studs2, studs3;
    private JComboBox com;
    private Object[][] currentCourseData;
    private Object[][] currentEnrollmentData;
    private String lastEnrollmentData = "";
    
    String userName = manageSession.getName();

    /**
     * Constructs the enrollment management interface with statistics and enrollment table.
     * It initializes all GUI components and provides real-time data updates.
     */
    public enrollMan() {
        currentEnrollmentData = new Object[0][];
        main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(BG_MAIN);

        // Top header panel with user profile
        header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 60));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setBackground(BG_SURFACE);
        header.setBorder(new EmptyBorder(6, 12, 6, 12));

        // Provides the application logo
        logo = new JPanel();
        logo.setPreferredSize(new Dimension(48, 48));
        logo.setMaximumSize(new Dimension(48, 48));
        logo.setLayout(new GridBagLayout());
        logo.setBackground(BG_SURFACE);

        java.net.URL imgURL = Thread.currentThread().getContextClassLoader().getResource("mainlog.png");
        if (imgURL != null) {
            ImageIcon logoIcon = new ImageIcon(imgURL);
            JLabel logoPic = new JLabel(logoIcon);
            logo.add(logoPic);
        } else {
            System.out.println("Could not load logoM.png");
        }

        // Application title section
        subHead = new JPanel();
        subHead.setLayout(new BoxLayout(subHead, BoxLayout.X_AXIS));
        subHead.setBorder(new EmptyBorder(0, 3, 0, 0));
        subHead.setBackground(BG_SURFACE);

        subHead1 = new JLabel("Edu");
        subHead1.setForeground(HEAD_TXT);
        subHead1.setForeground(DARK_GREEN);
        subHead1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        subHead2 = new JLabel("Portal - Admin Dashboard");
        subHead2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        subHead2.setForeground(TEXT_PRIMARY);

        subHead1.setAlignmentX(subHead1.LEFT_ALIGNMENT);
        subHead.add(subHead1);
        subHead.add(subHead2);

        // This is the administrator profile section
        profile = new JPanel(new BorderLayout(5, 0));
        profile.setBackground(BG_SURFACE);
        profile.setBorder(new EmptyBorder(6, 0, 6, 0));

        admin = new JPanel();
        admin.setPreferredSize(new Dimension(28, 28));
        admin.setMaximumSize(new Dimension(28, 28));
        admin.setLayout(new GridBagLayout());
        admin.setBackground(BG_SURFACE);

        java.net.URL imgURL1 = Thread.currentThread().getContextClassLoader().getResource("adm.png");
        if (imgURL1 != null) {
            ImageIcon adminIcon = new ImageIcon(imgURL1);
            JLabel adminPic = new JLabel(adminIcon);
            admin.add(adminPic);
        } else {
            System.out.println("Could not load logoM.png");
        }

        profile.add(admin, BorderLayout.WEST);

        pBox = new JPanel(new GridLayout(2, 1));
        pBox.setBackground(BG_SURFACE);

        adminLbl = new JLabel(userName);
        adminLbl.setBackground(BG_SURFACE);
        adminLbl.setForeground(DARK_GREEN);
        adminLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));

        adLbl = new JLabel("Administrator");
        adLbl.setBackground(BG_SURFACE);
        adLbl.setForeground(SEC_TXT);
        adLbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pBox.add(adminLbl);
        pBox.add(adLbl);

        profile.add(pBox, BorderLayout.EAST);

        // Main panel for the content
        content = new JPanel(new BorderLayout(20, 0));
        content.setBackground(BG_MAIN);
        content.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Navigation sidebar
        sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(BG_SURFACE);
        sideBar.setPreferredSize(new Dimension(250, 600));
        
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // This is the student Management menu items
        menuVibe1 = new JPanel(new BorderLayout());
        menuVibe1.setPreferredSize(new Dimension(getWidth(), 40));
        menuVibe1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        menuVibe1.setBackground(BG_SURFACE);
        
        studs = new JButton("Student Management");
        studs.setPreferredSize(new Dimension(235, getHeight()));
        studs.setBackground(BG_SURFACE);
        studs.setForeground(TEXT_PRIMARY);
        studs.setFocusPainted(false);
        studs.setContentAreaFilled(false);
        studs.setOpaque(true);
        studs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        focuseBtnMenu(studs);
        studs.setBorderPainted(false);
        studs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideBar.add(menuVibe1);
        
        menuVibe1.add(studs, BorderLayout.EAST);
        sideBar.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // This is the course Management menu items
        menuVibe2 = new JPanel(new BorderLayout());
        menuVibe2.setPreferredSize(new Dimension(getWidth(), 40));
        menuVibe2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        menuVibe2.setBackground(BG_SURFACE);
        
        studs2 = new JButton("Course Management");
        studs2.setPreferredSize(new Dimension(235, getHeight()));
        studs2.setBackground(BG_SURFACE);
        studs2.setForeground(TEXT_PRIMARY);
        studs2.setFocusPainted(false);
        studs2.setContentAreaFilled(false);
        studs2.setOpaque(true);
        studs2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        focuseBtnMenu(studs2);
        studs2.setBorderPainted(false);
        studs2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideBar.add(menuVibe2);
        
        menuVibe2.add(studs2, BorderLayout.EAST);
        sideBar.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Thihs is the active enrollment Management menu item
        menuVibe3 = new JPanel(new BorderLayout());
        menuVibe3.setPreferredSize(new Dimension(getWidth(), 40));
        menuVibe3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        menuVibe3.setBackground(BG_SURFACE);
        
        studs3 = new JButton("Enrollment Management");
        studs3.setPreferredSize(new Dimension(235, getHeight()));
        studs3.setBackground(DARK_GREEN);
        studs3.setForeground(TEXT_PRIMARY);
        studs3.setFocusPainted(false);
        studs3.setContentAreaFilled(false);
        studs3.setOpaque(true);
        studs3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        focuseBtn(studs3);
        studs3.setBorderPainted(false);
        studs3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideBar.add(menuVibe3);
        
        menuVibe3.add(studs3, BorderLayout.EAST);
        
        // Main content area
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BG_MAIN);

        // Title section
        mainTitlePanel = new JPanel(new BorderLayout());
        mainTitlePanel.setBackground(BG_MAIN);
        mainTitlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        mainTitle = new JLabel("Summary");
        mainTitle.setForeground(TEXT_PRIMARY);
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        mainSub = new JLabel("Year: 2025");
        mainSub.setForeground(SEC_TXT);

        JPanel titleText = new JPanel(new GridLayout(2, 1));
        titleText.setBackground(BG_MAIN);
        titleText.add(mainTitle);
        titleText.add(mainSub);

        mainTitlePanel.add(titleText, BorderLayout.WEST);

        // Main holder for the content
        holder = new JPanel(new BorderLayout());
        holder.setBackground(BG_MAIN);

        outban = new JPanel(new BorderLayout());
        outban.setBackground(BG_MAIN);

        // This is the bar for the enrollment management title
        
        ban = new JPanel(new BorderLayout());
        ban.setPreferredSize(new Dimension(getWidth(), 40));
        ban.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        ban.setBackground(BG_MAIN);
        ban.setBorder(new EmptyBorder(6, 0, 3, 0));

        uLbl = new JLabel("Enrollment Management");
        uLbl.setForeground(TEXT_PRIMARY);
        uLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));

        ban.add(uLbl, BorderLayout.NORTH);

        // This is the scroll pane for enrollment table
        scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(7);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.setBackground(BG_MAIN);
        scroll.getViewport().setBackground(BG_MAIN);

        outban.add(ban, BorderLayout.NORTH);

        // Panel for the control buttons 
        buts = new JPanel(new BorderLayout());
        buts.setBackground(BG_MAIN);

        comBox = new JPanel(new BorderLayout());
        comBox.setBorder(new EmptyBorder(0, 0, 0, 8));
        comBox.setBackground(BG_MAIN);

        com = new JComboBox();
        com.setPreferredSize(new Dimension(250, 30));

        comBox.add(com);

        in = new JPanel(new BorderLayout());
        in.setBackground(BG_MAIN);

        butin = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        butin.setBackground(BG_MAIN);

        // This is the delete enrollment button providing the administrator the ability to delete a students enrollment
        del = new JButton("Delete");
        del.setBackground(new Color(255, 80, 80));
        del.setForeground(TEXT_PRIMARY);
        del.setFocusPainted(false);
        del.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        del.setPreferredSize(new Dimension(80, 30));
        del.setContentAreaFilled(false);
        del.setOpaque(true);
        focuseBtnRed(del);

        // This is the add enrollment button allowing the administrator to add an enrollment for a student
        add = new JButton("Add Course");
        add.setBackground(DARK_GREEN);
        add.setForeground(TEXT_PRIMARY);
        add.setFocusPainted(false);
        add.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        add.setPreferredSize(new Dimension(110, 30));
        add.setContentAreaFilled(false);
        add.setOpaque(true);
        focuseBtn(add);

        in.add(comBox, BorderLayout.WEST);
        butin.add(del);
        in.add(butin, BorderLayout.CENTER);
        in.setBorder(new EmptyBorder(0, 0, 10, 0));
        in.add(add, BorderLayout.EAST);

        buts.add(in, BorderLayout.NORTH);

        buts.add(scroll, BorderLayout.CENTER);
        outban.add(buts, BorderLayout.CENTER);

        // Event listeners
        del.addActionListener(this);
        add.addActionListener(this);
        
        studs.addActionListener(this);
        studs2.addActionListener(this);
        studs3.addActionListener(this);

        // Adding the Components
        this.add(main);

        main.add(header, BorderLayout.NORTH);
        header.add(logo, BorderLayout.WEST);
        header.add(subHead, BorderLayout.CENTER);
        header.add(profile, BorderLayout.EAST);
        main.add(content);
        content.add(sideBar, BorderLayout.WEST);
        content.add(mainContent, BorderLayout.CENTER);
        mainContent.add(mainTitlePanel, BorderLayout.NORTH);
        createStatsPanel();
        holder.add(statsPanel, BorderLayout.NORTH);
        holder.add(outban, BorderLayout.CENTER);
        mainContent.add(holder, BorderLayout.CENTER);
        startAutoRefresh();
        createEnrollTable();

        // Frame configuration
        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    /**
     * Creates the statistics panel showing system overview.
     */
    private void createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(BG_MAIN);
        statsPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Gets the initial data from  the server
        int totalStudents = getTotalStudentsFromServer();
        int totalCourses = getTotalCoursesFromServer();
        int totalEnrollments = getTotalEnrollmentsFromServer();

        // Create stat cards
        statsPanel.add(createStatCard("Total Students", String.valueOf(totalStudents), DARK_GREEN));
        statsPanel.add(createStatCard("Total Courses", String.valueOf(totalCourses), new Color(147, 51, 234)));
        statsPanel.add(createStatCard("Active Enrollments", String.valueOf(totalEnrollments), new Color(59, 130, 246)));
    }

    /**
     * Creates a statistic card with title, value, and color coding.
     */
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(SEC_TXT);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(color);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BG_SURFACE);
        content.add(titleLabel, BorderLayout.NORTH);
        content.add(valueLabel, BorderLayout.CENTER);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    /**
     * Fetches the total student count from server and returns the total number of students in the server.
     */
    private int getTotalStudentsFromServer() {
        try {
            String response = Client.sendRequest("GET_TOTAL_STUDENTS");
            return Integer.parseInt(response.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Fetches total course count from server and returns the total number of course in the system.
     */
    private int getTotalCoursesFromServer() {
        try {
            String response = Client.sendRequest("GET_TOTAL_COURSES");
            return Integer.parseInt(response.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Fetches total enrollment count from server and returns the total number of active enrollments in the system.
     */
    private int getTotalEnrollmentsFromServer() {
        try {
            String response = Client.sendRequest("GET_TOTAL_ENROLLMENTS");
            return Integer.parseInt(response.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Refreshes the statistics panel with current data from server.
     */
    public void refreshStatistics() {
        int totalStudents = getTotalStudentsFromServer();
        int totalCourses = getTotalCoursesFromServer();
        int totalEnrollments = getTotalEnrollmentsFromServer();

        // Updates the statistics panel
        statsPanel.removeAll();
        statsPanel.add(createStatCard("Total Students", String.valueOf(totalStudents), DARK_GREEN));
        statsPanel.add(createStatCard("Total Courses", String.valueOf(totalCourses), new Color(147, 51, 234)));
        statsPanel.add(createStatCard("Active Enrollments", String.valueOf(totalEnrollments), new Color(59, 130, 246)));

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    /**
     * Starts the automatic refresh timers for statistics and enrollment table.
     */
    public void startAutoRefresh() {
        // Refresh statistics every 7 seconds
        Timer statsTimer = new Timer(7000, e -> refreshStatistics());
        statsTimer.start();

        // Refresh enrollment table every 4 seconds
        Timer tableTimer = new Timer(4000, e -> refreshEnrollTable());
        tableTimer.start();
    }

    /**
     * Creates the initial enrollment table.
     */
    private void createEnrollTable() {
        refreshEnrollTable();
    }

    /**
     * Fetches enrollment data from server.
     */
    private Object[][] getEnrollmentsFromServer() {
        java.util.List<Object[]> enrollments = new java.util.ArrayList<>();
        try {
            String response = Client.sendRequest("GET_ALL_ENROLLMENTS");
            if (response != null && !response.isEmpty() && !response.equals("UNKNOWN_COMMAND")) {
                String[] enrollmentEntries = response.split(";");
                for (String entry : enrollmentEntries) {
                    if (entry.trim().isEmpty()) {
                        continue;
                    }
                    String[] fields = entry.split("\\|");
                    if (fields.length >= 6) {
                        Object[] enrollment = {
                            fields[0], // enroll_id
                            fields[1], // student_number
                            fields[2], // student_name
                            fields[3], // course_id
                            fields[4], // course_name
                            fields[5] // enroll_date
                        };
                        enrollments.add(enrollment);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading enrollment data from server.");
        }

        return enrollments.toArray(new Object[0][]);
    }

    /**
     * Populates the combo box with enrollment selections.
     */
    private void populateComboBox() {
        com.removeAllItems(); // Clear existing items

        com.addItem("Select an Enrollment");

        for (Object[] enrollment : currentEnrollmentData) {
            String displayText = enrollment[1] + " - " + enrollment[3]; 
            com.addItem(displayText);
        }
    }

    /**
     * This refreshes the enrollment table with current data from server.
     * Only updates if data has changed to optimize performance.
     */
    private void refreshEnrollTable() {
        // This stores the current selection
        String currentSelection;
        if (com.getSelectedItem() != null) {
            currentSelection = com.getSelectedItem().toString();
        } else {
            currentSelection = null;
        }

        // Reload fresh data from server
        Object[][] newEnrollmentData = getEnrollmentsFromServer();

        // Convert data to simple string for comparison
        String currentData = java.util.Arrays.deepToString(newEnrollmentData);

        // Check if the data has changed in anyway.If the data is the same,then it does nothing. however if it is changed then it refreshes the table.
        if (currentData.equals(lastEnrollmentData)) {
            return;
        } else {
            currentEnrollmentData = newEnrollmentData;
            lastEnrollmentData = currentData;

            String[] columnNames = {
                "Enrollment ID", "Student Number", "Student Name", "Course Code", "Course Name", "Enrollment Date"
            };

            // This creates a new table model with the updated data
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(currentEnrollmentData, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Creates a new table
            JTable enrollTable = new JTable(model);
            enrollTable.setBackground(BG_SURFACE);
            enrollTable.setForeground(TEXT_PRIMARY);
            enrollTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            enrollTable.setRowHeight(35);
            enrollTable.getTableHeader().setBackground(BG_SURFACE);
            enrollTable.getTableHeader().setForeground(DARK_GREEN);
            enrollTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            enrollTable.setGridColor(BORDER_COLOR);
            enrollTable.setShowGrid(true);
            enrollTable.setFillsViewportHeight(true);

            // Sets the column widths
            enrollTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            enrollTable.getColumnModel().getColumn(1).setPreferredWidth(120);
            enrollTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            enrollTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            enrollTable.getColumnModel().getColumn(4).setPreferredWidth(200);
            enrollTable.getColumnModel().getColumn(5).setPreferredWidth(150);

            // Set the table directly in the viewport
            scroll.setViewportView(enrollTable);

            // Refreshes the combo box
            populateComboBox();

            // Restores the selection
            if (currentSelection != null) {
                com.setSelectedItem(currentSelection);
            }

            // Refreshes the display
            scroll.revalidate();
            scroll.repaint();
        }
    }

    /**
     * Handles action events from GUI components.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == del) {
            handleDeleteEnrollment();
        } else if (e.getSource() == add) {
            handleAddEnrollment();
        }
        
        // This is the navigation handlers
        if (e.getSource() == studs) {
            goToNextPage(this, new adminMain());
        }
        if (e.getSource() == studs2) {
            goToNextPage(this, new courseManage());
        }
        if (e.getSource() == studs3) {
            goToNextPage(this, new enrollMan());
        }
    }

    /**
     * Handles deletion of selected enrollment records.
     */
    private void handleDeleteEnrollment() {
        if (com.getSelectedIndex() == -1 || com.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select an enrollment to delete.");
            return;
        }

        String selected = com.getSelectedItem().toString();
        String[] parts = selected.split(" - ");
        String studentNumber = parts[0];
        String courseCode = parts[1];

        // Find the enrollment ID in current data
        int enrollId = -1;
        for (Object[] enrollment : currentEnrollmentData) {
            if (enrollment[1].toString().equals(studentNumber) && enrollment[3].toString().equals(courseCode)) {
                enrollId = Integer.parseInt(enrollment[0].toString());
                break;
            }
        }

        if (enrollId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this enrollment?\nStudent: " + studentNumber + "\nCourse: " + courseCode,
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String response = Client.sendRequest("DELETE_ENROLLMENT," + enrollId);
                    if ("SUCCESS".equals(response)) {
                        JOptionPane.showMessageDialog(this, "Enrollment deleted successfully!");
                        refreshEnrollTable();
                        refreshStatistics();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete enrollment.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting enrollment.");
                }
            }
        }
    }

    /**
     * Handles addition of new enrollment record with validation.
     */
    private void handleAddEnrollment() {
        try {
            // Get students for combo box
            String studentsResponse = Client.sendRequest("GET_STUDENTS_FOR_COMBO");
            List<Object[]> students = new ArrayList<>();
            if (studentsResponse != null && !studentsResponse.isEmpty()) {
                String[] studentEntries = studentsResponse.split(";");
                for (String entry : studentEntries) {
                    if (entry.trim().isEmpty()) {
                        continue;
                    }
                    String[] fields = entry.split("\\|");
                    if (fields.length >= 3) {
                        Object[] student = {
                            fields[0], // user_id
                            fields[1], // student_number
                            fields[2] // name
                        };
                        students.add(student);
                    }
                }
            }

            // Gets courses for combo box (now including course type)
            String coursesResponse = Client.sendRequest("GET_COURSES_FOR_COMBO");
            List<Object[]> courses = new ArrayList<>();
            if (coursesResponse != null && !coursesResponse.isEmpty()) {
                String[] courseEntries = coursesResponse.split(";");
                for (String entry : courseEntries) {
                    if (entry.trim().isEmpty()) {
                        continue;
                    }
                    String[] fields = entry.split("\\|");
                    if (fields.length >= 3) {
                        Object[] course = {
                            fields[0], // course_id
                            fields[1], // course_name
                            fields[2] // course_type
                        };
                        courses.add(course);
                    }
                }
            }

            // Create combo box options
            String[] studentOptions = new String[students.size()];
            for (int i = 0; i < students.size(); i++) {
                studentOptions[i] = students.get(i)[1] + " - " + students.get(i)[2]; // Student Number - Name
            }

            // Course options now show type as well
            String[] courseOptions = new String[courses.size()];
            for (int i = 0; i < courses.size(); i++) {
                courseOptions[i] = courses.get(i)[0] + " - " + courses.get(i)[1] + " (" + courses.get(i)[2] + ")"; // Course Code - Course Name (Type)
            }

            // Show student selection dialog
            String selectedStudent = (String) JOptionPane.showInputDialog(this,
                    "Select Student:", "Add Enrollment",
                    JOptionPane.PLAIN_MESSAGE, null, studentOptions, studentOptions[0]);

            if (selectedStudent == null) {
                return;
            }

            // Show course selection dialog
            String selectedCourse = (String) JOptionPane.showInputDialog(this,
                    "Select Course:", "Add Enrollment",
                    JOptionPane.PLAIN_MESSAGE, null, courseOptions, courseOptions[0]);

            if (selectedCourse == null) {
                return;
            }

            // Extracts the IDs from selections
            int studentIndex = java.util.Arrays.asList(studentOptions).indexOf(selectedStudent);
            int courseIndex = java.util.Arrays.asList(courseOptions).indexOf(selectedCourse);

            int userId = Integer.parseInt(students.get(studentIndex)[0].toString());
            String courseId = courses.get(courseIndex)[0].toString();
            String studentName = students.get(studentIndex)[2].toString();
            String courseName = courses.get(courseIndex)[1].toString();
            String courseType = courses.get(courseIndex)[2].toString();

            // Check if the student is already enrolled
            String checkResponse = Client.sendRequest("CHECK_ENROLLED," + userId + "," + courseId);
            if ("ENROLLED".equals(checkResponse)) {
                JOptionPane.showMessageDialog(this,
                        "Student " + studentName + " is already enrolled in " + courseName + "!",
                        "Already Enrolled",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Check the enrollment limits based on course type
            if ("Core".equals(courseType)) {
                String coreCountResponse = Client.sendRequest("GET_CORE_COUNT," + userId);
                int coreCount = Integer.parseInt(coreCountResponse.trim());

                if (coreCount >= 5) {
                    JOptionPane.showMessageDialog(this,
                            "Student " + studentName + " has reached the maximum limit of 5 core courses!\n"
                            + "Current core courses: " + coreCount + "/5\n"
                            + "Cannot enroll in: " + courseName + " (" + courseType + ")",
                            "Core Enrollment Limit Reached",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else if ("Elective".equals(courseType)) {
                String electiveCountResponse = Client.sendRequest("GET_ELECTIVE_COUNT," + userId);
                int electiveCount = Integer.parseInt(electiveCountResponse.trim());

                if (electiveCount >= 2) {
                    JOptionPane.showMessageDialog(this,
                            "Student " + studentName + " has reached the maximum limit of 2 elective courses!\n"
                            + "Current elective courses: " + electiveCount + "/2\n"
                            + "Cannot enroll in: " + courseName + " (" + courseType + ")",
                            "Elective Enrollment Limit Reached",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // This enrolls the student
            String enrollResponse = Client.sendRequest("ENROLL," + userId + "," + courseId);
            if ("SUCCESS".equals(enrollResponse)) {
                JOptionPane.showMessageDialog(this,
                        "Enrollment added successfully!\n"
                        + "Student: " + studentName + "\n"
                        + "Course: " + courseName + " (" + courseType + ")",
                        "Enrollment Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshEnrollTable();
                refreshStatistics();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add enrollment.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding enrollment: " + ex.getMessage());
        }
    }
}