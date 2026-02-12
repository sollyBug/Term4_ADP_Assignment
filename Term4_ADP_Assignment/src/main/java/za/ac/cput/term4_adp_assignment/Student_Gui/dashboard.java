package za.ac.cput.term4_adp_assignment.Student_Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import za.ac.cput.term4_adp_assignment.Worker.reusable;
import java.util.List;
import java.util.ArrayList;
import za.ac.cput.term4_adp_assignment.Network_Handle.Client;
import za.ac.cput.term4_adp_assignment.authManager.Course;
import za.ac.cput.term4_adp_assignment.authManager.manageSession;

/**
 * This is the Student dashboard interface for browsing and enrolling in available courses.
 * Provides the courses available with filtering options and real-time enrollment status.
 */
public class dashboard extends reusable implements ActionListener {

    // Declares the GUI Component
    private JPanel main, header, logo, subHead, sideBar, content, content1, mainContent, mainTitlePanel, mainRight, gridPanel, option;
    private JLabel subHead1, subHead2, mainTitle, mainSub, credits, students;
    private JButton allBtn, filteredBtn, enrollBtn, op1, op2;
    private JCheckBox coreCheck, electiveCheck;
    private JScrollPane scroll;
    private String lastCourseData = "";
    private Timer refreshTimer;
    private int lastCourseCount = 0;
    
    /**
     * Constructs the student dashboard with course browsing.
     * It initializes all GUI components and sets up real-time course data updates.
     */
    public dashboard() {
        // This is the main container panel with border spacing
        main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(BG_MAIN);

        // The top header panel with navigation
        header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 60));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setBackground(BG_SURFACE);
        header.setBorder(new EmptyBorder(6, 12, 6, 0));

        // Application logo image
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

        // The application title section
        subHead = new JPanel();
        subHead.setLayout(new BoxLayout(subHead, BoxLayout.X_AXIS));
        subHead.setBorder(new EmptyBorder(0, 3, 0, 0));
        subHead.setBackground(BG_SURFACE);

        subHead1 = new JLabel("Edu");
        subHead1.setForeground(HEAD_TXT);
        subHead1.setForeground(DARK_GREEN);
        subHead1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        subHead2 = new JLabel("Portal - Infomatics and Design");
        subHead2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        subHead2.setForeground(TEXT_PRIMARY);

        subHead1.setAlignmentX(subHead1.LEFT_ALIGNMENT);
        subHead.add(subHead1);
        subHead.add(subHead2);

        // This is the navigation options
        option = new JPanel();
        option.setLayout(new BoxLayout(option, BoxLayout.X_AXIS));
        option.setBackground(BG_SURFACE);

        op1 = new JButton("Dashboard");
        op1.setBorder(BorderFactory.createEmptyBorder());
        op1.setBackground(BG_SURFACE);
        op1.setContentAreaFilled(false);
        op1.setOpaque(true);
        op1.setForeground(GREEN);
        op1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        op1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        op1.addActionListener(this);

        op2 = new JButton("My Journey");
        op2.setBorder(BorderFactory.createEmptyBorder());
        op2.setBackground(BG_SURFACE);
        op2.setContentAreaFilled(false);
        op2.setOpaque(true);
        op2.setForeground(TEXT_PRIMARY);
        op2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        op2.setFont(new Font("Segoe UI", Font.BOLD, 13));

        option.add(op1);
        option.add(Box.createHorizontalStrut(15));
        option.add(op2);
        option.add(Box.createHorizontalStrut(8));

        // This is the main content area
        content = new JPanel(new BorderLayout(20, 0));
        content.setBackground(BG_MAIN);
        content.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Sidebar with department info and course filters
        sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(BG_MAIN);
        sideBar.setPreferredSize(new Dimension(250, 600));

        // IT Department information block
        sideBar.add(createBlock("IT Department", createDepartmentInfo()));
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));

        // Filtering through course types
        sideBar.add(createBlock("Course Types", createCourseTypes()));

        // Main area for course display content
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BG_MAIN);

        // Title section with filtering options
        mainTitlePanel = new JPanel(new BorderLayout());
        mainTitlePanel.setBackground(BG_MAIN);
        mainTitlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        mainTitle = new JLabel("Computer Science Courses");
        mainTitle.setForeground(TEXT_PRIMARY);
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        mainSub = new JLabel("Browse and enroll in Computer Science courses");
        mainSub.setForeground(SEC_TXT);

        JPanel titleText = new JPanel(new GridLayout(2, 1));
        titleText.setBackground(BG_MAIN);
        titleText.add(mainTitle);
        titleText.add(mainSub);

        mainRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mainRight.setBackground(BG_MAIN);

        allBtn = createButton("All");
        allBtn.setBackground(DARK_GREEN);

        filteredBtn = createButton("Filter");
        filteredBtn.setBackground(BG_SURFACE);

        mainRight.add(filteredBtn);
        mainRight.add(allBtn);

        mainTitlePanel.add(titleText, BorderLayout.WEST);
        mainTitlePanel.add(mainRight, BorderLayout.EAST);

        // Course grid with scroll pane included as well
        gridPanel = createCoursesGrid();
        scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(7);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        populateCoursesGrid();

        op2.addActionListener(this);
        
        // This initialize the course data and set up auto-refresh
        List<Course> initialCourses = fetchCoursesFromServer();
        lastCourseCount = initialCourses.size();
        
        // This portion Sets up real-time refresh timer (it only updates if the course count changes)
        refreshTimer = new Timer(5000, e -> {
            List<Course> currentCourses = fetchCoursesFromServer();
            if (currentCourses.size() != lastCourseCount) {
                lastCourseCount = currentCourses.size();
                populateCoursesGrid();
                updateCourseCounts();
            }
        });
        refreshTimer.start();

        // Adds components to the main 
        this.add(main);

        main.add(header, BorderLayout.NORTH);

        header.add(logo, BorderLayout.WEST);
        header.add(subHead, BorderLayout.CENTER);
        header.add(option, BorderLayout.EAST);
        main.add(content);
        content.add(sideBar, BorderLayout.WEST);
        content.add(mainContent, BorderLayout.CENTER);
        mainContent.add(mainTitlePanel, BorderLayout.NORTH);
        mainContent.add(scroll, BorderLayout.CENTER);

        // Frame configuration
        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> scroll.getVerticalScrollBar().setValue(0));
    }

    /**
     * This creates the department information panel with description and specializations.
     */
    private JPanel createDepartmentInfo() {
        JPanel deptPanel = new JPanel();
        deptPanel.setLayout(new BoxLayout(deptPanel, BoxLayout.Y_AXIS));
        deptPanel.setBackground(BG_SURFACE);

        // This is the IT Department description
        JTextArea deptDesc = new JTextArea(
                "The IT Department offers programs in software development, AI, cybersecurity, and data science, "
                + "preparing students for careers in the evolving tech industry."
        );
        deptDesc.setLineWrap(true);
        deptDesc.setWrapStyleWord(true);
        deptDesc.setEditable(false);
        deptDesc.setFocusable(false);
        deptDesc.setBackground(BG_SURFACE);
        deptDesc.setForeground(TEXT_SECONDARY);
        deptDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        deptDesc.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        deptDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        deptPanel.add(deptDesc);

        // This is the header for specializations
        JLabel specLabel = new JLabel("Specializations:");
        specLabel.setForeground(DARK_GREEN);
        specLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        specLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        deptPanel.add(specLabel);
        deptPanel.add(Box.createRigidArea(new Dimension(0, 5))); // spacing below header

        // Specializations list
        String[] specializations = {
            "Software Engineering", "Data Science", "Cybersecurity",
            "Artificial Intelligence", "Web Development", "Mobile Development"
        };

        JPanel specialPanel = new JPanel();
        specialPanel.setLayout(new BoxLayout(specialPanel, BoxLayout.Y_AXIS));
        specialPanel.setBackground(BG_SURFACE);
        specialPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        specialPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        // Add each specialization as bullet points
        for (String spec : specializations) {
            JLabel specItem = new JLabel("• " + spec);
            specItem.setForeground(TEXT_SECONDARY);
            specItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            specItem.setAlignmentX(Component.LEFT_ALIGNMENT);
            specialPanel.add(specItem);
            specialPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        }

        deptPanel.add(specialPanel);

        return deptPanel;
    }

    /**
     * This creates the course type filtering panel with enrollment count display.
     */
    private JPanel createCourseTypes() {
        JPanel typesPanel = new JPanel();
        typesPanel.setLayout(new BoxLayout(typesPanel, BoxLayout.Y_AXIS));
        typesPanel.setBackground(BG_SURFACE);
        typesPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        // Get current enrollment counts from server
        int coreCount = 0;
        int electiveCount = 0;
        try {
            int userId = manageSession.getUserId();
            String coreResponse = Client.sendRequest("GET_CORE_COUNT," + userId);
            String electiveResponse = Client.sendRequest("GET_ELECTIVE_COUNT," + userId);
            coreCount = Integer.parseInt(coreResponse.trim());
            electiveCount = Integer.parseInt(electiveResponse.trim());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // This is the core courses checkbox with count display
        coreCheck = new JCheckBox("Core Courses (" + coreCount + "/5)");
        coreCheck.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // This is the checkbox for elective courses with count display
        electiveCheck = new JCheckBox("Electives (" + electiveCount + "/2)");
        electiveCheck.setCursor(new Cursor(Cursor.HAND_CURSOR));

        coreCheck.setForeground(TEXT_SECONDARY);
        coreCheck.setBackground(BG_SURFACE);
        coreCheck.setFocusable(false);

        electiveCheck.setForeground(TEXT_SECONDARY);
        electiveCheck.setBackground(BG_SURFACE);
        electiveCheck.setFocusable(false);

        // Adds action listeners for filtering
        coreCheck.addActionListener(this);
        electiveCheck.addActionListener(this);

        typesPanel.add(coreCheck);
        typesPanel.add(electiveCheck);

        return typesPanel;
    }

    /**
     * Creates a styled button for the interface.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        button.setPreferredSize(new Dimension(120, 40));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        return button;
    }

    /**
     * Creates the grid layout for course cards.
     */
    private JPanel createCoursesGrid() {
        JPanel grid = new JPanel(new GridLayout(0, 2, 15, 15)); 
        grid.setBackground(BG_MAIN);
        grid.setBorder(new EmptyBorder(20, 0, 20, 0));
        return grid;
    }

    /**
     * Fetches the list of all available courses from the server.
     */
    private List<Course> fetchCoursesFromServer() {
        List<Course> courses = new ArrayList<>();
        try {
            String response = Client.sendRequest("GET_COURSES");
            if (response != null && !response.isEmpty()) {
                String[] courseEntries = response.split(";"); 
                for (String entry : courseEntries) {
                    if (entry.trim().isEmpty()) {
                        continue;
                    }
                    Course course = parseCourse(entry);
                    if (course != null) {
                        courses.add(course);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch courses: " + e.getMessage());
        }
        return courses;
    }

    /**
     * This creates a Course object from a server response string.
     */
    private Course parseCourse(String serialized) {
        try {
            String[] fields = serialized.split("\\|");
            String courseId = fields[0];    // Actual course ID from database
            String name = fields[1];        // Course name
            String type = fields[2];        // Core/Elective
            int credits = Integer.parseInt(fields[3]);
            String desc = fields[4];

            return new Course(courseId, name, type, credits, desc);
        } catch (Exception e) {
            System.out.println("Invalid course format: " + serialized);
            return null;
        }
    }

    /**
     * This populates the courses grid with available courses and applies filters if any are selected.
     */
    private void populateCoursesGrid() {
        List<Course> courses = fetchCoursesFromServer();
        gridPanel.removeAll();

        for (Course c : courses) {
            JPanel card = createCourseCard(c);
            gridPanel.add(card);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    /**
     * Creates a visual card component for a course with enrollment functionality.
     */
    private JPanel createCourseCard(Course course) {
        // Main panel for course card
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // This is the header with course title and status tag
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_SURFACE);

        JLabel title = new JLabel(course.getName());
        title.setForeground(TEXT_PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel meta = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        meta.setBackground(BG_SURFACE);

        // Check the enrollment status for current user
        boolean isEnrolled = false;
        try {
            int userId = manageSession.getUserId();
            String response = Client.sendRequest("CHECK_ENROLLED," + userId + "," + course.getCourseId());
            isEnrolled = "ENROLLED".equals(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // This sets the tag text and color based on enrollment status and course type
        String statusText;
        Color tagColor;

        if (isEnrolled) {
            statusText = "Enrolled";
            tagColor = ACCENT_BLUE_LIGHT;
        } else {
            statusText = course.getType();
            if (course.getType().equals("Core")) {
                tagColor = DARK_GREEN;
            } else {
                tagColor = new Color(251, 146, 60); // Orange for Elective
            }
        }

        JLabel tag = new JLabel(statusText);
        tag.setForeground(tagColor);
        tag.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(tagColor, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        meta.add(tag);
        header.add(title, BorderLayout.WEST);
        header.add(meta, BorderLayout.EAST);

        // Course description body
        JTextArea desc = new JTextArea(course.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setBackground(BG_SURFACE);
        desc.setForeground(TEXT_MUTED);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Info section with credits and student count
        JPanel infoFooter = new JPanel();
        infoFooter.setLayout(new BoxLayout(infoFooter, BoxLayout.Y_AXIS));
        infoFooter.setBackground(BG_SURFACE);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        info.setBackground(BG_SURFACE);

        JLabel credits = new JLabel(course.getCredits() + " Credits");
        credits.setBorder(new EmptyBorder(0, 0, 0, 10));
        credits.setForeground(tagColor);

        // Gets the student count for this course
        int studentCount = 0;
        try {
            String response = Client.sendRequest("COUNT," + course.getCourseId());
            studentCount = Integer.parseInt(response.trim());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JLabel students = new JLabel(studentCount + " Students");
        students.setForeground(SEC_TXT);

        info.add(credits);
        info.add(students);
        info.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Footer with enrollment button
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(BG_SURFACE);

        JButton enrollBtn = createButton(isEnrolled ? "Enrolled" : "Enroll");

        if (isEnrolled) {
            enrollBtn.setBackground(ACCENT_BLUE_LIGHT);
        } else {
            enrollBtn.setBackground(DARK_GREEN);
            focuseBtn(enrollBtn);
            enrollBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // Add action listener only if not enrolled
        if (!isEnrolled) {
            enrollBtn.addActionListener(e -> {
                handleCourseEnrollment(course, card);
            });
        }

        footer.add(enrollBtn);
        infoFooter.add(info);
        infoFooter.add(footer);

        // Adds the card components
        card.add(header, BorderLayout.NORTH);
        card.add(desc, BorderLayout.CENTER);
        card.add(infoFooter, BorderLayout.SOUTH);

        return card;
    }

    /**
     * This handles the course enrollment process with validation and limits checking.
     */
    private void handleCourseEnrollment(Course course, JPanel card) {
        int userId = manageSession.getUserId();
        String courseId = course.getCourseId();
        String courseType = course.getType();

        try {
            // This checks the enrollment limits before enrolling
            String coreResponse = Client.sendRequest("GET_CORE_COUNT," + userId);
            String electiveResponse = Client.sendRequest("GET_ELECTIVE_COUNT," + userId);

            int coreCount = Integer.parseInt(coreResponse.trim());
            int electiveCount = Integer.parseInt(electiveResponse.trim());

            // Verifies the enrollment limits based on course type
            if (courseType.equals("Core") && coreCount >= 5) {
                JOptionPane.showMessageDialog(this,
                        "You can only enroll in 5 core modules maximum!",
                        "Enrollment Limit Reached",
                        JOptionPane.WARNING_MESSAGE);
                return;
            } else if (courseType.equals("Elective") && electiveCount >= 2) {
                JOptionPane.showMessageDialog(this,
                        "You can only enroll in 2 elective modules maximum!",
                        "Enrollment Limit Reached",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Proceed with enrollment if within limits
            String response = Client.sendRequest("ENROLL," + userId + "," + courseId);
            if ("SUCCESS".equalsIgnoreCase(response)) {
                refreshCourseCard(card, course);
                updateCourseCounts();

                JOptionPane.showMessageDialog(this, "You have successfully enrolled!");
            } else {
                JOptionPane.showMessageDialog(this, "Already enrolled or failed to enroll.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Connection error.");
        }
    }

    /**
     * This refreshes the course card to reflect updated enrollment status.
     */
    private void refreshCourseCard(JPanel card, Course course) {
        // Remove all components from the card
        card.removeAll();

        // Create a new card with updated enrollment status
        JPanel newCard = createCourseCard(course);

        // Copy the layout and add the new card components
        card.setLayout(new BorderLayout());
        card.add(newCard, BorderLayout.CENTER);

        // Refresh the display
        card.revalidate();
        card.repaint();
    }

    /**
     * Updates the button styles based on filter selection state.
     */
    private void updateButtonStyles() {
        boolean anySelected = coreCheck.isSelected() || electiveCheck.isSelected();

        if (anySelected) {
            filteredBtn.setBackground(DARK_GREEN);
            allBtn.setBackground(BG_SURFACE);
        } else {
            allBtn.setBackground(DARK_GREEN);
            filteredBtn.setBackground(BG_SURFACE);
        }
    }

    /**
     * Updates the course count displays in the sidebar and fetches the current enrollment counts from the server.
     */
    private void updateCourseCounts() {
        try {
            int userId = manageSession.getUserId();
            String coreResponse = Client.sendRequest("GET_CORE_COUNT," + userId);
            String electiveResponse = Client.sendRequest("GET_ELECTIVE_COUNT," + userId);

            int coreCount = Integer.parseInt(coreResponse.trim());
            int electiveCount = Integer.parseInt(electiveResponse.trim());

            // Update the checkbox labels with new counts
            coreCheck.setText("Core Courses (" + coreCount + "/5)");
            electiveCheck.setText("Electives (" + electiveCount + "/2)");

            // Refresh the sidebar to reflect the changes
            sideBar.revalidate();
            sideBar.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to update course counts: " + ex.getMessage());
        }
    }

    /**
     * Handles action events from GUI components including filtering and navigation.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        List<Course> courses = fetchCoursesFromServer();
        gridPanel.removeAll();

        // Handle course type filtering
        if (e.getSource() == coreCheck && coreCheck.isSelected()) {
            electiveCheck.setSelected(false);
            for (Course c : courses) {
                if (c.getType().equals("Core")) {
                    gridPanel.add(createCourseCard(c));
                }
            }
        } else if (e.getSource() == electiveCheck && electiveCheck.isSelected()) {
            coreCheck.setSelected(false); 
            for (Course c : courses) {
                if (c.getType().equals("Elective")) {
                    gridPanel.add(createCourseCard(c));
                }
            }
        } else {
            // Show all the courses if no filters are selected
            for (Course c : courses) {
                gridPanel.add(createCourseCard(c));
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
        SwingUtilities.invokeLater(() -> scroll.getVerticalScrollBar().setValue(0));

        updateButtonStyles();

        // Updates the filter button text based on selection
        if (coreCheck.isSelected()) {
            filteredBtn.setText("Core");
        } else if (electiveCheck.isSelected()) {
            filteredBtn.setText("Electives");
        } else {
            filteredBtn.setText("Filter");
        }
        
        // Handle navigation to enrolled courses page
        if (e.getSource() == op2) {
            goToNextPage(this, new registered());
        }
    }
}