package za.ac.cput.term4_adp_assignment.Log_Sign_Guis;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import za.ac.cput.term4_adp_assignment.Network_Handle.Client;
import za.ac.cput.term4_adp_assignment.Student_Gui.dashboard;
import za.ac.cput.term4_adp_assignment.Worker.reusable;
import static za.ac.cput.term4_adp_assignment.Worker.reusable.BG_MAIN;
import static za.ac.cput.term4_adp_assignment.Worker.reusable.goToNextPage;

public class signForm extends reusable implements ActionListener {

    private JPanel main, left, right, headerPanel, eduBox, leftMain, joinBox, infoBox, cenHead, cenCon, button;
    private JLabel accentLine, edu, port, enr, joinLabel, descLabel, footerLabel, cenTitle;
    private JScrollPane scroll;
    private JTextField txtFullName, txtEmail, txtStudentNumber;
    private JPasswordField txtPassword, txtConfirmPassword;
    private ButtonGroup group;
    private JRadioButton student, admin;
    private JButton signUpButton, loginLink;

    public signForm() {
        //creating the main with 10 borderspacing
        main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(25, 70, 25, 70));
        main.setBackground(BG_MAIN);

        //----------------------------------------------------
        //initialize left panel with the side content
        left = new JPanel(new BorderLayout());
        left.setBackground(DARK_GREEN);
        left.setPreferredSize(new Dimension(470, getHeight()));
        left.setBorder(new EmptyBorder(20, 35, 20, 35));

        //handles the header panels
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_GREEN);
        headerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 58));

        eduBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        eduBox.setBackground(DARK_GREEN);
        edu = new JLabel("Edu");
        edu.setFont(new Font("Segoe UI", Font.BOLD, 24));
        edu.setForeground(BG_MAIN);
        port = new JLabel("Portal");
        port.setFont(new Font("Segoe UI", Font.BOLD, 24));
        port.setForeground(TEXT_PRIMARY);

        //Creates separate label for the subtitle
        JLabel enrLeft = new JLabel("Student Enrollment System");
        enrLeft.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        enrLeft.setForeground(TEXT_PRIMARY);

        eduBox.add(edu);
        eduBox.add(port);

        leftMain = new JPanel(new BorderLayout());
        leftMain.setBorder(new EmptyBorder(60, 0, 0, 0));
        leftMain.setBackground(DARK_GREEN);

        //Uses a wrapper panel with BoxLayout but force left alignment
        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        contentWrapper.setBackground(DARK_GREEN);
        contentWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Uses a BoxLayout for joinBox instead of BorderLayout
        joinBox = new JPanel();
        joinBox.setLayout(new BoxLayout(joinBox, BoxLayout.Y_AXIS));
        joinBox.setBackground(DARK_GREEN);
        joinBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        joinLabel = new JLabel("<html>Join Our Academic Community</html>");
        joinLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        joinLabel.setForeground(TEXT_PRIMARY);
        joinLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        descLabel = new JLabel("<html>Register to access the student enrollment system and manage your academic journey.</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_PRIMARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Provides proper spacing with BoxLayout
        joinBox.add(joinLabel);
        joinBox.add(Box.createRigidArea(new Dimension(0, 10))); // This actually works in BoxLayout!
        joinBox.add(descLabel);

        infoBox = new JPanel(new BorderLayout());
        infoBox.setBackground(DARK_GREEN);

        // Creates a panel for the info items with BoxLayout
        JPanel infoItemsPanel = new JPanel();
        infoItemsPanel.setLayout(new BoxLayout(infoItemsPanel, BoxLayout.Y_AXIS));
        infoItemsPanel.setBackground(DARK_GREEN);
        infoItemsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Adds info boxes 
        infoItemsPanel.add(boxer("check1", "Easy Course Enrollment", "Browse and select from available courses with ease"));
        infoItemsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoItemsPanel.add(boxer("lock", "Secure Authentication", "Your data is protected with industry standard security"));
        infoItemsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoItemsPanel.add(boxer("pro", "Track Your Progress", "Monitor your enrollment status and academic records"));

        infoBox.add(infoItemsPanel, BorderLayout.NORTH);

        // Adds everything to the content wrapper
        contentWrapper.add(joinBox);
        contentWrapper.add(Box.createRigidArea(new Dimension(0, 40)));
        contentWrapper.add(infoItemsPanel);

        // Adds the wrapper to leftMain
        leftMain.add(contentWrapper, BorderLayout.NORTH);

        footerLabel = new JLabel("© 2025 Enrollify (ADP Assignment). All rights reserved.");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);

        //this is the right panel with the main content
        right = new JPanel(new BorderLayout());
        right.setBackground(BG_SURFACE);
        right.setBorder(new EmptyBorder(20, 40, 20, 40));

        // This is the container for the header title
        cenHead = new JPanel(new BorderLayout());
        cenHead.setBackground(BG_SURFACE);
        cenHead.setPreferredSize(new Dimension(Integer.MAX_VALUE, 58));

        cenTitle = new JLabel("Create Account");
        cenTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        cenTitle.setForeground(TEXT_PRIMARY);

        // Creates a separate label for the right panel subtitle
        JLabel enrRight = new JLabel("Fill in your details to create a new account");
        enrRight.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        enrRight.setForeground(SEC_TXT);

        cenHead.add(cenTitle, BorderLayout.NORTH);
        cenHead.add(enrRight, BorderLayout.SOUTH);

        //Creates the content panel first
        cenCon = new JPanel();
        cenCon.setLayout(new BoxLayout(cenCon, BoxLayout.Y_AXIS));
        cenCon.setBackground(BG_SURFACE);
        cenCon.setBorder(new EmptyBorder(25, 0, 0, 0));
        
        
// FULL NAME FIELD
// -----------------------------
        JPanel fullNamePanel = new JPanel(new BorderLayout());
        fullNamePanel.setBackground(BG_SURFACE);
        fullNamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        fullNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFullName = new JLabel("Full Name");
        lblFullName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFullName.setForeground(TEXT_PRIMARY);

        txtFullName = new JTextField();
        txtFullName.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        txtFullName.setBackground(INPUT_FIELDS);
        txtFullName.setForeground(TEXT_PRIMARY);
        txtFullName.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(INPUT_BORD, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        txtFullName.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        fullNamePanel.add(lblFullName, BorderLayout.NORTH);
        fullNamePanel.add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
        fullNamePanel.add(txtFullName, BorderLayout.SOUTH);
        cenCon.add(fullNamePanel);
        cenCon.add(Box.createRigidArea(new Dimension(0, 12)));

// -----------------------------
// EMAIL FIELD
// -----------------------------
        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(BG_SURFACE);
        emailPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        emailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblEmail = new JLabel("Email Address");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setForeground(TEXT_PRIMARY);

        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        txtEmail.setBackground(INPUT_FIELDS);
        txtEmail.setForeground(TEXT_PRIMARY);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(INPUT_BORD, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        emailPanel.add(lblEmail, BorderLayout.NORTH);
        emailPanel.add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
        emailPanel.add(txtEmail, BorderLayout.SOUTH);
        cenCon.add(emailPanel);
        cenCon.add(Box.createRigidArea(new Dimension(0, 12)));

// -----------------------------
// STUDENT NUMBER FIELD
// -----------------------------
        JPanel studentNumPanel = new JPanel(new BorderLayout());
        studentNumPanel.setBackground(BG_SURFACE);
        studentNumPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        studentNumPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblStudentNum = new JLabel("Student Number or Admin Id");
        lblStudentNum.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblStudentNum.setForeground(TEXT_PRIMARY);

        txtStudentNumber = new JTextField();
        txtStudentNumber.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        txtStudentNumber.setBackground(INPUT_FIELDS);
        txtStudentNumber.setForeground(TEXT_PRIMARY);
        txtStudentNumber.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(INPUT_BORD, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        txtStudentNumber.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        studentNumPanel.add(lblStudentNum, BorderLayout.NORTH);
        studentNumPanel.add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
        studentNumPanel.add(txtStudentNumber, BorderLayout.SOUTH);
        cenCon.add(studentNumPanel);
        cenCon.add(Box.createRigidArea(new Dimension(0, 12)));

// -----------------------------
// PASSWORD FIELD
// -----------------------------
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(BG_SURFACE);
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setForeground(TEXT_PRIMARY);

        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        txtPassword.setBackground(INPUT_FIELDS);
        txtPassword.setForeground(TEXT_PRIMARY);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(INPUT_BORD, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        passwordPanel.add(lblPassword, BorderLayout.NORTH);
        passwordPanel.add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
        passwordPanel.add(txtPassword, BorderLayout.SOUTH);
        cenCon.add(passwordPanel);
        cenCon.add(Box.createRigidArea(new Dimension(0, 12)));

// -----------------------------
// CONFIRM PASSWORD FIELD
// -----------------------------
        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.setBackground(BG_SURFACE);
        confirmPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        confirmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblConfirm = new JLabel("Confirm Password");
        lblConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblConfirm.setForeground(TEXT_PRIMARY);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        txtConfirmPassword.setBackground(INPUT_FIELDS);
        txtConfirmPassword.setForeground(TEXT_PRIMARY);
        txtConfirmPassword.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(INPUT_BORD, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        confirmPanel.add(lblConfirm, BorderLayout.NORTH);
        confirmPanel.add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
        confirmPanel.add(txtConfirmPassword, BorderLayout.SOUTH);
        cenCon.add(confirmPanel);
        cenCon.add(Box.createRigidArea(new Dimension(0, 12)));

        // Creates a account type selection
        JPanel accountTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        accountTypePanel.setBackground(BG_SURFACE);
        accountTypePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        accountTypePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        accountTypePanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        accountTypeLabel.setForeground(SEC_TXT);

        student = new JRadioButton("Student");
        admin = new JRadioButton("Admin");
        group = new ButtonGroup();
        group.add(student);
        group.add(admin);

        //This is the styling of the radio buttons
        student.setBackground(BG_SURFACE);
        admin.setBackground(BG_SURFACE);
        student.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        admin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        student.setForeground(TEXT_PRIMARY);
        admin.setForeground(TEXT_PRIMARY);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        radioPanel.setBackground(BG_SURFACE);
        student.setFocusPainted(false);
        admin.setFocusPainted(false);

        student.setBorderPainted(false);
        admin.setBorderPainted(false);

        student.setContentAreaFilled(false);
        admin.setContentAreaFilled(false);

        radioPanel.add(student);
        radioPanel.add(admin);

        accountTypePanel.add(accountTypeLabel);
        accountTypePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        accountTypePanel.add(radioPanel);

        cenCon.add(accountTypePanel);

        // Adding a sign up button
        signUpButton = new JButton("Create Account");
        signUpButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signUpButton.setBackground(DARK_GREEN);
        signUpButton.setFocusPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setOpaque(true);
        focuseBtn(signUpButton);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(new EmptyBorder(12, 0, 12, 0));
        signUpButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        signUpButton.setMaximumSize(new Dimension(500, 50));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cenCon.add(signUpButton);

        // Adding a login link
        JPanel logs = new JPanel(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loginPanel.setBackground(BG_SURFACE);
        JLabel haveAccount = new JLabel("Already have an account?");
        haveAccount.setForeground(SEC_TXT);
        loginLink = new JButton("Sign in");
        loginLink.setMargin(new Insets(0, 0, 0, 0));
        loginLink.setBorder(BorderFactory.createEmptyBorder());
        loginLink.setBackground(BG_SURFACE);
        loginLink.setContentAreaFilled(false);
        loginLink.setOpaque(true);
        loginLink.setForeground(GREEN);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginPanel.add(haveAccount);
        loginPanel.add(loginLink);
        loginPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        cenCon.add(Box.createRigidArea(new Dimension(0, 5)));
        cenCon.add(loginPanel);

        //This configures the scroll pane
        scroll = new JScrollPane(cenCon);
        scroll.setBorder(BorderFactory.createEmptyBorder()); 
        scroll.getViewport().setBackground(BG_SURFACE);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        // This customizes the scroll bar
        JScrollBar verticalScrollBar = scroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16); 
        verticalScrollBar.setBackground(BG_SURFACE);

        //action listener buttons
        signUpButton.addActionListener(this);
        loginLink.addActionListener(this);

        //--------------------------------------------------------
        //adding components
        this.add(main);
        main.add(left, BorderLayout.WEST);
        main.add(right, BorderLayout.CENTER);

        left.add(headerPanel, BorderLayout.NORTH);
        headerPanel.add(eduBox, BorderLayout.NORTH);
        headerPanel.add(enrLeft, BorderLayout.SOUTH); 
        left.add(leftMain, BorderLayout.CENTER);
        left.add(footerLabel, BorderLayout.SOUTH);

        right.add(cenHead, BorderLayout.NORTH);
        right.add(scroll, BorderLayout.CENTER);

        //--------------------------------------------------
        //base frame settings
        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Student Enrollment System - Sign Up");
    }

    // This is the helper method to create form fields
    private JPanel createAccount(String labelText) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        JPanel Icon = new JPanel();
        Icon.setBackground(INPUT_FIELDS);
        Icon.setPreferredSize(new Dimension(40, 40));
        Icon.setLayout(new GridBagLayout());

        java.net.URL imgURLPass = Thread.currentThread().getContextClassLoader().getResource("25_36.png");
        if (imgURLPass != null) {
            ImageIcon passer = new ImageIcon(imgURLPass);
            JLabel passPic = new JLabel(passer);
            Icon.add(passPic);
        } else {
            System.out.println("Could not load logo.png");
        }

        fieldPanel.add(Icon, BorderLayout.WEST);

        return fieldPanel;
    }

    private JPanel boxer(String pic, String text, String subText) {
        JPanel info = new JPanel(new BorderLayout(4, 0));
        info.setBackground(DARK_GREEN);
        info.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        info.setAlignmentX(Component.LEFT_ALIGNMENT); 

        JPanel Icon = new JPanel();
        Icon.setBackground(DARK_GREEN);
        Icon.setPreferredSize(new Dimension(40, 40));
        Icon.setLayout(new GridBagLayout());

        java.net.URL imgURLEmail = Thread.currentThread().getContextClassLoader().getResource(pic + ".png");
        if (imgURLEmail != null) {
            ImageIcon picture = new ImageIcon(imgURLEmail);
            JLabel insider = new JLabel(picture);
            Icon.add(insider);
        } else {
            System.out.println("Could not load " + pic + ".png");
        }

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setBackground(DARK_GREEN);

        JLabel hed = new JLabel(text);
        hed.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hed.setForeground(TEXT_PRIMARY);

        JLabel sub = new JLabel(subText);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(TEXT_PRIMARY);

        textPanel.add(hed);
        textPanel.add(sub);

        info.add(Icon, BorderLayout.WEST);
        info.add(textPanel, BorderLayout.CENTER);

        return info;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String name = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            String num = txtStudentNumber.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();
            String con = new String(txtConfirmPassword.getPassword()).trim();
            String select = null;

            // --- Input Verification ---
            // checks if the input fields are populated with information and shoots an error message if they aren't
            if (name.isEmpty() || email.isEmpty() || num.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Sign up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Enter a valid email address.", "Sign up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!num.matches("\\d+")) { 
                JOptionPane.showMessageDialog(this, "Student Number must contain only digits!", "Sign up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!pass.equals(con)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Sign up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (student.isSelected()) {
                select = "student";
            } else if (admin.isSelected()) {
                if (num.startsWith("625")) {
                    select = "admin";
                } else {
                    JOptionPane.showMessageDialog(this, "Make sure that your admin id is correct!", "Sign up Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a role!", "Sign up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- Sends a request to server ---
            String request = String.join(",", "REGISTER", name, email, num, pass, select);

            try {
                String response = Client.sendRequest(request);

                if (response.startsWith("SUCCESS")) {
                    JOptionPane.showMessageDialog(this, "You have successfully signed up as a " + select, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                    goToNextPage(this, new loginForm());
                } else if (response.startsWith("FAIL")) {
                    JOptionPane.showMessageDialog(this, "Registration failed: " + response.substring(5), "Sign up Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Unexpected response from server: " + response, "Server Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Server is down. Can't register right now. Try again later.", "Server Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == loginLink) {
            goToNextPage(this, new loginForm());
        }
    }

}
