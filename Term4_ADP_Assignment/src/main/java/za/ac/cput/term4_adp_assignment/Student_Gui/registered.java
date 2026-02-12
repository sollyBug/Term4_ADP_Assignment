package za.ac.cput.term4_adp_assignment.Student_Gui;

import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import za.ac.cput.term4_adp_assignment.Network_Handle.Client;
import za.ac.cput.term4_adp_assignment.Worker.reusable;
import static za.ac.cput.term4_adp_assignment.Worker.reusable.BG_MAIN;
import static za.ac.cput.term4_adp_assignment.Worker.reusable.BG_SURFACE;
import static za.ac.cput.term4_adp_assignment.Worker.reusable.BORDER_COLOR;
import za.ac.cput.term4_adp_assignment.authManager.manageSession;
import za.ac.cput.term4_adp_assignment.authManager.Course;
import java.util.List;
import java.util.ArrayList;

/**
 * This is the enrolled students courses interface displaying all courses the student is currently enrolled in.
 * This provides a comprehensive view of enrolled courses with detailed information and navigation options.
 */
public class registered extends reusable implements ActionListener {

    // Declares the GUI components
    private JPanel main, header, logo, subHead, option, content, sideBar, mainContent, mainTitlePanel, gridPanel;
    private JLabel subHead1, subHead2, mainTitle, mainSub;
    private JButton op1, op2;
    private JScrollPane scroll;
    String userName = manageSession.getName();
    String studentNumber = manageSession.getNumber();

    /**
     * This constructs the enrolled courses interface with all GUI components and data.
     * It initializes the layout, loads enrolled courses, and sets up event handlers.
     */
    public registered() {
        main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(BG_MAIN);

        // Top header panel with the logo and navigation
        header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 60));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setBackground(BG_SURFACE);
        header.setBorder(new EmptyBorder(6, 12, 6, 0));

        // The application logo panel along with logo image
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

        // This is the application title section
        subHead = new JPanel();
        subHead.setLayout(new BoxLayout(subHead, BoxLayout.X_AXIS));
        subHead.setBorder(new EmptyBorder(0, 3, 0, 0));
        subHead.setBackground(BG_SURFACE);

        subHead1 = new JLabel("Edu");
        subHead1.setForeground(HEAD_TXT);
        subHead1.setForeground(DARK_GREEN);
        subHead1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        subHead2 = new JLabel("Portal - My Courses");
        subHead2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        subHead2.setForeground(TEXT_PRIMARY);

        subHead1.setAlignmentX(subHead1.LEFT_ALIGNMENT);
        subHead.add(subHead1);
        subHead.add(subHead2);

        // This is the navigation options panel
        option = new JPanel();
        option.setLayout(new BoxLayout(option, BoxLayout.X_AXIS));
        option.setBackground(BG_SURFACE);

        op1 = new JButton("Dashboard");
        op1.setBorder(BorderFactory.createEmptyBorder());
        op1.setBackground(BG_SURFACE);
        op1.setContentAreaFilled(false);
        op1.setOpaque(true);
        op1.setForeground(TEXT_PRIMARY);
        op1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        op1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        op1.addActionListener(this);

        op2 = new JButton("My Journey");
        op2.setBorder(BorderFactory.createEmptyBorder());
        op2.setBackground(BG_SURFACE);
        op2.setContentAreaFilled(false);
        op2.setOpaque(true);
        op2.setForeground(GREEN);
        op2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        op2.setFont(new Font("Segoe UI", Font.BOLD, 13));

        option.add(op1);
        option.add(Box.createHorizontalStrut(15));
        option.add(op2);
        option.add(Box.createHorizontalStrut(8));

        // Main content panel containing sidebar and course grid
        content = new JPanel(new BorderLayout(20, 0));
        content.setBackground(BG_MAIN);
        content.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Sidebar with student profile and academic information
        sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(BG_MAIN);
        sideBar.setPreferredSize(new Dimension(250, 600));

        sideBar.add(createBlock("Student Profile", createStudentProfile()));
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));
        sideBar.add(createBlock("Academic information", createAcademicInfo()));

        // Main area for enrolled courses content
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BG_MAIN);

        // This provides the title section of main content
        mainTitlePanel = new JPanel(new BorderLayout());
        mainTitlePanel.setBackground(BG_MAIN);
        mainTitlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        mainTitle = new JLabel("My Enrolled Courses");
        mainTitle.setForeground(TEXT_PRIMARY);
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        mainSub = new JLabel("Year: 2025");
        mainSub.setForeground(SEC_TXT);

        JPanel titleText = new JPanel(new GridLayout(2, 1));
        titleText.setBackground(BG_MAIN);
        titleText.add(mainTitle);
        titleText.add(mainSub);

        mainTitlePanel.add(titleText, BorderLayout.WEST);

        // Create courses grid for displaying enrolled courses
        gridPanel = createCoursesGrid();
        scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(7);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        populateEnrolledCoursesGrid();

        // Component added to the main frame
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
     * This creates the student profile panel displaying user information.
     */
    private JPanel createStudentProfile() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(BG_SURFACE);

        JLabel nameLabel = new JLabel(userName);
        nameLabel.setForeground(TEXT_PRIMARY);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel idLabel = new JLabel("Student ID: " + studentNumber);
        idLabel.setForeground(TEXT_SECONDARY);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        profilePanel.add(Box.createRigidArea(new Dimension(0, 2)));
        profilePanel.add(nameLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        profilePanel.add(idLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 8)));

        return profilePanel;
    }

    /**
     * This creates the academic information panel displaying enrollment statistics.
     */
    private JPanel createAcademicInfo() {
        JPanel academicPanel = new JPanel();
        academicPanel.setLayout(new BoxLayout(academicPanel, BoxLayout.Y_AXIS));
        academicPanel.setBackground(BG_SURFACE);

        // Retrieves enrolled course count for this student
        int enrolledCount = 0;
        try {
            int userId = manageSession.getUserId();
            String response = Client.sendRequest("GET_ENROLLED_COUNT," + userId);
            enrolledCount = Integer.parseInt(response.trim());
        } catch (Exception ex) {
            ex.printStackTrace();
            enrolledCount = 0;
        }

        JLabel creditsLabel = new JLabel("Academic year: 2025");
        creditsLabel.setForeground(TEXT_PRIMARY);
        creditsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel enrolledLabel = new JLabel("Currently Enrolled: " + enrolledCount + " courses");
        enrolledLabel.setForeground(TEXT_SECONDARY);
        enrolledLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        academicPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        academicPanel.add(creditsLabel);
        academicPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        academicPanel.add(enrolledLabel);

        return academicPanel;
    }

    /**
     * This features the creating of the main grid container for course cards.
     */
    private JPanel createCoursesGrid() {
        JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        grid.setBackground(BG_MAIN);
        grid.setBorder(new EmptyBorder(20, 20, 20, 20));
        return grid;
    }

    /**
     * Fetches the list of courses the student is enrolled in from the server and returns a list of course objects which represent the enrolled course.
     */
    private List<Course> fetchEnrolledCourses() {
        List<Course> enrolledCourses = new ArrayList<>();
        try {
            // First get all of the courses
            String response = Client.sendRequest("GET_COURSES");
            if (response != null && !response.isEmpty()) {
                String[] courseEntries = response.split(";");
                int userId = manageSession.getUserId();

                for (String entry : courseEntries) {
                    if (entry.trim().isEmpty()) {
                        continue;
                    }

                    Course course = parseCourse(entry);
                    if (course != null) {
                        // Then it check if student is enrolled in this course
                        String enrollResponse = Client.sendRequest("CHECK_ENROLLED," + userId + "," + course.getCourseId());
                        if ("ENROLLED".equals(enrollResponse)) {
                            enrolledCourses.add(course);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch enrolled courses: " + e.getMessage());
        }
        return enrolledCourses;
    }

    /**
     *This creates a Course object from server response data.
     */
    private Course parseCourse(String serialized) {
        try {
            String[] fields = serialized.split("\\|");
            String courseId = fields[0];
            String name = fields[1];
            String type = fields[2];
            int credits = Integer.parseInt(fields[3]);
            String desc = fields[4];

            return new Course(courseId, name, type, credits, desc);
        } catch (Exception e) {
            System.out.println("Invalid course format: " + serialized);
            return null;
        }
    }

    /**
     * This populates the courses grid with enrolled courses and displays a message if no courses are enrolled.
     */
    private void populateEnrolledCoursesGrid() {
        List<Course> enrolledCourses = fetchEnrolledCourses();
        gridPanel.removeAll();

        if (enrolledCourses.isEmpty()) {
            // Show a message when no courses are enrolled
            JPanel messagePanel = new JPanel(new BorderLayout());
            messagePanel.setBackground(BG_MAIN);
            messagePanel.setBorder(new EmptyBorder(50, 0, 50, 0));

            JLabel message = new JLabel("You haven't enrolled in any courses yet.", SwingConstants.CENTER);
            message.setForeground(TEXT_SECONDARY);
            message.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            messagePanel.add(message, BorderLayout.CENTER);
            gridPanel.setLayout(new BorderLayout());
            gridPanel.add(messagePanel, BorderLayout.CENTER);
        } else {
            // Create a wrapper panel with GridLayout
            JPanel cardsWrapper = new JPanel(new GridLayout(0, 2, 15, 15));
            cardsWrapper.setBackground(BG_MAIN);

            for (Course course : enrolledCourses) {
                JPanel card = createEnrolledCourseCard(course);
                cardsWrapper.add(card);
            }

            // If there is an odd number, then it adds an empty panel for layout consistency
            if (enrolledCourses.size() % 2 != 0) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setBackground(BG_MAIN);
                emptyPanel.setOpaque(true);
                cardsWrapper.add(emptyPanel);
            }

            gridPanel.setLayout(new BorderLayout());
            gridPanel.add(cardsWrapper, BorderLayout.NORTH);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    /**
     * This creates a visual card component for an enrolled course.
     */
    private JPanel createEnrolledCourseCard(Course course) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(400, 220));

        // This is a Header with the course title and type tag
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_SURFACE);

        JLabel title = new JLabel(course.getName());
        title.setForeground(TEXT_PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel meta = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        meta.setBackground(BG_SURFACE);

        String courseType = course.getType();
        JLabel tag = new JLabel(courseType);

        // This Sets the color based on course type
        Color tagColor;
        if (courseType.equals("Core")) {
            tagColor = DARK_GREEN; // Green for Core courses
        } else {
            tagColor = new Color(251, 146, 60); // Orange for Elective courses
        }

        tag.setForeground(tagColor);
        tag.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(tagColor, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        meta.add(tag);
        header.add(title, BorderLayout.WEST);
        header.add(meta, BorderLayout.EAST);

        // Description body of the course
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

        // Footer with action buttons
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        footer.setBackground(BG_SURFACE);

        JButton materialsBtn = createButton("View Materials", ACCENT_BLUE_LIGHT);
        materialsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        focuseBtnBlue(materialsBtn);

        JButton assignBtn = createButton("Assignments", DARK_GREEN);
        assignBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        focuseBtn(assignBtn);

        JPanel insider = new JPanel();
        insider.setBackground(BG_SURFACE);
        insider.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        insider.add(assignBtn);

        footer.add(materialsBtn);
        footer.add(insider);
        infoFooter.add(info);
        infoFooter.add(footer);

        //Adds the card components
        card.add(header, BorderLayout.NORTH);
        card.add(desc, BorderLayout.CENTER);
        card.add(infoFooter, BorderLayout.SOUTH);

        return card;
    }

    /**
     * This creates a styled button with specified text and background color.
     */
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        button.setFont(new Font("Arial", Font.PLAIN, 11));

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);

        return button;
    }

    /**
     * Handles action events from GUI components.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == op1) {
            // This allows you to navigate back to dashboard
            goToNextPage(this, new dashboard());
        }
    }
}