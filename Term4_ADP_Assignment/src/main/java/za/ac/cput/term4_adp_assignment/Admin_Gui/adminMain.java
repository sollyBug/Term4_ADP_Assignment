package za.ac.cput.term4_adp_assignment.Admin_Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import za.ac.cput.term4_adp_assignment.Network_Handle.Client;
import za.ac.cput.term4_adp_assignment.Worker.reusable;
import static za.ac.cput.term4_adp_assignment.Worker.reusable.BG_MAIN;
import za.ac.cput.term4_adp_assignment.authManager.manageSession;

/**
 * Administrator main dashboard interface for student management.
 * Provides comprehensive student management functionality with real-time data updates.
 */
public class adminMain extends reusable implements ActionListener {

    // GUI Component declarations
    private JPanel main, header, logo, subHead, profile, pBox, admin, content, sideBar, mainTitlePanel, mainContent, statsPanel, holder, ban, outban, buts, in, comBox, butin, menuVibe1, menuVibe2, menuVibe3;
    private JLabel subHead1, subHead2, mainTitle, mainSub, adminLbl, adLbl, uLbl;
    private JScrollPane scroll;
    private JButton ed, del, studs, studs2, studs3;
    private JComboBox com;
    private Object[][] currentStudentData;
    private String lastStudentData = "";
    String userName = manageSession.getName();

    /**
     * Constructs the administrator main dashboard with student management capabilities.
     * this initializes all GUI components and sets up real-time data updates.
     */
    public adminMain() {
        main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(BG_MAIN);

        // Top header panel with user profile
        header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 60));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setBackground(BG_SURFACE);
        header.setBorder(new EmptyBorder(6, 12, 6, 12));

        // Initializes and imports the application logo
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

        // Initializes the administrators profile section
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

        // Main panel for content
        content = new JPanel(new BorderLayout(20, 0));
        content.setBackground(BG_MAIN);
        content.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Navigation sidebar
        sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(BG_SURFACE);
        sideBar.setPreferredSize(new Dimension(250, 600));
        
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // This is the active Student Management menu item 
        menuVibe1 = new JPanel(new BorderLayout());
        menuVibe1.setPreferredSize(new Dimension(getWidth(), 40));
        menuVibe1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        menuVibe1.setBackground(BG_SURFACE);
        
        studs = new JButton("Student Management");
        studs.setPreferredSize(new Dimension(235, getHeight()));
        studs.setBackground(DARK_GREEN);
        studs.setForeground(TEXT_PRIMARY);
        studs.setFocusPainted(false);
        studs.setContentAreaFilled(false);
        studs.setOpaque(true);
        studs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        focuseBtn(studs);
        studs.setBorderPainted(false);
        studs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideBar.add(menuVibe1);
        
        menuVibe1.add(studs, BorderLayout.EAST);
        sideBar.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // This is the Course Management menu item
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
        
        // This is the Enrollment Management menu item
        menuVibe3 = new JPanel(new BorderLayout());
        menuVibe3.setPreferredSize(new Dimension(getWidth(), 40));
        menuVibe3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        menuVibe3.setBackground(BG_SURFACE);
        
        studs3 = new JButton("Enrollment Management");
        studs3.setPreferredSize(new Dimension(235, getHeight()));
        studs3.setBackground(BG_SURFACE);
        studs3.setForeground(TEXT_PRIMARY);
        studs3.setFocusPainted(false);
        studs3.setContentAreaFilled(false);
        studs3.setOpaque(true);
        studs3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        focuseBtnMenu(studs3);
        studs3.setBorderPainted(false);
        studs3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideBar.add(menuVibe3);
        
        menuVibe3.add(studs3, BorderLayout.EAST);

        // Main content area
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BG_MAIN);

        // This initializes the title section
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

        // The title bar for Student management
        ban = new JPanel(new BorderLayout());
        ban.setPreferredSize(new Dimension(getWidth(), 40));
        ban.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        ban.setBackground(BG_MAIN);
        ban.setBorder(new EmptyBorder(6, 0, 3, 0));

        uLbl = new JLabel("Student Management");
        uLbl.setForeground(TEXT_PRIMARY);
        uLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));

        ban.add(uLbl, BorderLayout.NORTH);

        // Scroll pane for student table
        scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(7);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.setBackground(BG_MAIN);
        scroll.getViewport().setBackground(BG_MAIN);

        outban.add(ban, BorderLayout.NORTH);

        // Control buttons panel
        buts = new JPanel(new BorderLayout());
        buts.setBackground(BG_MAIN);

        comBox = new JPanel(new BorderLayout());
        comBox.setBorder(new EmptyBorder(0, 0, 0, 8));
        comBox.setBackground(BG_MAIN);

        com = new JComboBox();
        com.setPreferredSize(new Dimension(250, 30));

        comBox.add(com);

        in = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        in.setBackground(BG_MAIN);

        butin = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        butin.setBackground(BG_MAIN);

        // Edit student button
        ed = new JButton("Edit");
        ed.setBackground(DARK_GREEN);
        ed.setForeground(TEXT_PRIMARY);
        ed.setFocusPainted(false);
        ed.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        ed.setPreferredSize(new Dimension(80, 30));
        ed.setContentAreaFilled(false);
        ed.setOpaque(true);
        focuseBtn(ed);

        // Delete student button
        del = new JButton("Delete");
        del.setBackground(new Color(255, 80, 80));
        del.setForeground(TEXT_PRIMARY);
        del.setFocusPainted(false);
        del.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        del.setPreferredSize(new Dimension(80, 30));
        del.setContentAreaFilled(false);
        del.setOpaque(true);
        focuseBtnRed(del);

        in.add(comBox);
        butin.add(ed);
        butin.add(del);
        in.add(butin);
        in.setBorder(new EmptyBorder(0, 0, 10, 0));

        buts.add(in, BorderLayout.NORTH);

        buts.add(scroll, BorderLayout.CENTER);
        outban.add(buts, BorderLayout.CENTER);

        // Event listeners
        ed.addActionListener(this);
        del.addActionListener(this);
        
        studs.addActionListener(this);
        studs2.addActionListener(this);
        studs3.addActionListener(this);

        // Adding the components
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
        createStudentTable();

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

        // Gets the initial data from server
        int totalStudents = getTotalStudentsFromServer();
        int totalCourses = getTotalCoursesFromServer();
        int totalEnrollments = getTotalEnrollmentsFromServer();

        // Create the stat cards
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
     * Fetches total student count from server and returns the total number of students in the system.
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

        // Update the statistics panel
        statsPanel.removeAll();
        statsPanel.add(createStatCard("Total Students", String.valueOf(totalStudents), DARK_GREEN));
        statsPanel.add(createStatCard("Total Courses", String.valueOf(totalCourses), new Color(147, 51, 234)));
        statsPanel.add(createStatCard("Active Enrollments", String.valueOf(totalEnrollments), new Color(59, 130, 246)));

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    /**
     * Starts automatic refresh timers for statistics and student table.
     */
    public void startAutoRefresh() {
        // Refresh statistics every 7 seconds
        Timer statsTimer = new Timer(7000, e -> refreshStatistics());
        statsTimer.start();

        // Refresh student table every 10 seconds for real-time updates
        Timer tableTimer = new Timer(4000, e -> refreshStudentTable());
        tableTimer.start();
    }

    /**
     * Creates the initial student table.
     */
    private void createStudentTable() {
        refreshStudentTable(); // Just call refresh to create the initial table
    }

    /**
     * Fetches student data from server.
     */
    private Object[][] getStudentsFromServer() {
        java.util.List<Object[]> students = new java.util.ArrayList<>();
        try {
            String response = Client.sendRequest("GET_ALL_STUDENTS");
            if (response != null && !response.isEmpty() && !response.equals("UNKNOWN_COMMAND")) {
                String[] studentEntries = response.split(";");
                for (String entry : studentEntries) {
                    if (entry.trim().isEmpty()) {
                        continue;
                    }
                    String[] fields = entry.split("\\|");
                    if (fields.length >= 6) {
                        Object[] student = {
                            fields[0], // user_id
                            fields[1], // name
                            fields[2], // student_number
                            fields[3], // email
                            fields[4], // reg_date
                            fields[5] // last_login
                        };
                        students.add(student);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading student data from server.");
        }

        return students.toArray(new Object[0][]);
    }

    /**
     * Populates the combo box with student selections.
     */
    private void populateComboBox() {
        com.removeAllItems(); // Clear existing items

        com.addItem("Select a student");

        for (Object[] student : currentStudentData) {
            String displayText = student[2] + " - " + student[1]; // Student Number - Name
            com.addItem(displayText);
        }
    }

    /**
     * Refreshes the student table with current data from server.
     */
    private void refreshStudentTable() {
        // Store current selection using an if-else statement
        String currentSelection;
        if (com.getSelectedItem() != null) {
            currentSelection = com.getSelectedItem().toString();
        } else {
            currentSelection = null;
        }

        // Reload fresh data from server
        Object[][] newStudentData = getStudentsFromServer();

        // Convert data to simple string for comparison
        String currentData = java.util.Arrays.deepToString(newStudentData);

        // Check if the data has changed in anyway.If the data is the same,then it does nothing. however if it is changed then it refreshes the table.
        
        if (currentData.equals(lastStudentData)) {
            return;
        } else {
            currentStudentData = newStudentData;
            lastStudentData = currentData;

            String[] columnNames = {
                "Student ID", "Name", "Student Number", "Email",
                "Registration Date", "Last Login"
            };

            // Creates a new table model with updated data
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(currentStudentData, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Creates a new table
            JTable studentTable = new JTable(model);
            studentTable.setBackground(BG_SURFACE);
            studentTable.setForeground(TEXT_PRIMARY);
            studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            studentTable.setRowHeight(35);
            studentTable.getTableHeader().setBackground(BG_SURFACE);
            studentTable.getTableHeader().setForeground(DARK_GREEN);
            studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            studentTable.setGridColor(BORDER_COLOR);
            studentTable.setShowGrid(true);
            studentTable.setFillsViewportHeight(true);

            studentTable.getColumnModel().getColumn(0).setPreferredWidth(80);
            studentTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            studentTable.getColumnModel().getColumn(2).setPreferredWidth(120);
            studentTable.getColumnModel().getColumn(3).setPreferredWidth(200);
            studentTable.getColumnModel().getColumn(4).setPreferredWidth(150);
            studentTable.getColumnModel().getColumn(5).setPreferredWidth(150);

            // Updates the scroll pane
            scroll.setViewportView(new JScrollPane(studentTable));

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
        if (e.getSource() == ed) {
            handleEditStudent();
        } else if (e.getSource() == del) {
            handleDeleteStudent();
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
     * Handles editing of student information.
     */
    private void handleEditStudent() {
        if (com.getSelectedIndex() == -1 || com.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }

        String selected = com.getSelectedItem().toString();
        String studentNumber = selected.split(" - ")[0];

        // Finds the student in the CURRENT data
        Object[] studentToEdit = null;
        for (Object[] student : currentStudentData) {
            if (student[2].toString().equals(studentNumber)) {
                studentToEdit = student;
                break;
            }
        }

        if (studentToEdit != null) {
            String studentId = studentToEdit[0].toString();
            String currentName = studentToEdit[1].toString();
            String currentEmail = studentToEdit[3].toString();

            //Replaces name with current value as default
            String newName = (String) JOptionPane.showInputDialog(this,
                    "Enter new name:", "Edit Student Name",
                    JOptionPane.PLAIN_MESSAGE, null, null, currentName);

            if (newName == null || newName.trim().isEmpty()) {
                return;
            }

            // replaces email with current value as default
            String newEmail = (String) JOptionPane.showInputDialog(this,
                    "Enter new email:", "Edit Student Email",
                    JOptionPane.PLAIN_MESSAGE, null, null, currentEmail);

            if (newEmail == null || newEmail.trim().isEmpty()) {
                return;
            }

            try {
                String response = Client.sendRequest("UPDATE_STUDENT," + studentId + "," + newName + "," + newEmail);
                if ("SUCCESS".equals(response)) {
                    JOptionPane.showMessageDialog(this, "Student updated successfully!");
                    // Forces a refresh from server
                    refreshStudentTable();
                    refreshStatistics();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update student.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating student.");
            }
        }
    }

    /**
     * Handles deletion of student with a confirmation.
     */
    private void handleDeleteStudent() {
        if (com.getSelectedIndex() == -1 || com.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        String selected = com.getSelectedItem().toString();
        String studentNumber = selected.split(" - ")[0];
        String studentName = selected.split(" - ")[1];

        // Finds the student ID in CURRENT data
        String studentId = null;
        for (Object[] student : currentStudentData) {
            if (student[2].toString().equals(studentNumber)) {
                studentId = student[0].toString();
                break;
            }
        }

        if (studentId != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete student: " + studentName + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String response = Client.sendRequest("DELETE_STUDENT," + studentId);
                    if ("SUCCESS".equals(response)) {
                        JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                        // Forces a refresh from server
                        refreshStudentTable();
                        refreshStatistics();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete student.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting student.");
                }
            }
        }
    }
}