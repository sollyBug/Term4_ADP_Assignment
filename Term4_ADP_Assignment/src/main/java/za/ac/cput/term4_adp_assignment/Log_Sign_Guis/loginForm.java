package za.ac.cput.term4_adp_assignment.Log_Sign_Guis;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import za.ac.cput.term4_adp_assignment.Admin_Gui.adminMain;
import za.ac.cput.term4_adp_assignment.Network_Handle.Client;
import za.ac.cput.term4_adp_assignment.Student_Gui.dashboard;
import za.ac.cput.term4_adp_assignment.Student_Gui.registered;
import za.ac.cput.term4_adp_assignment.Worker.reusable;
import za.ac.cput.term4_adp_assignment.authManager.manageSession;

/**
 * Login form GUI for user authentication.
 * Provides a user interface for entering credentials and handling login operations.
 * 
 */
public class loginForm extends reusable implements ActionListener{

    // This is where we declare the GUI components
    private JPanel main, logoPanel, emailWrapper, emailHold, passWrapper, passHold, options, signP, liningP, signupP, emailIcon, passIcon;
    private JLabel wel, subtitle, lblEmail, lblPass, forgot, orLabel, signup1;
    private JTextField emailField;
    private JPasswordField passField;
    private JCheckBox rememberMe;
    private JButton signIn, signup2;
    private JSeparator line1, line2;

    /**
     * This constructor creates the login form GUI with all components and event handlers.
     * Initializes and arranges all visual elements for user authentication.
     */
    public loginForm() {
        // This is the main container panel setup
        main = new JPanel();
        main.setPreferredSize(new Dimension(450, 584));
        main.setBorder(new LineBorder(Color.BLACK, 1));
        main.setBackground(BG_SURFACE);
        main.setBorder(new EmptyBorder(40, 33, 40, 33));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        // This initializes the Logo panel 
        logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(48, 48));
        logoPanel.setMaximumSize(new Dimension(48, 48));
        logoPanel.setBackground(DARK_GREEN);
        logoPanel.setLayout(new GridBagLayout()); 

        // This loads and displays the application logo image
        java.net.URL imgURL = Thread.currentThread().getContextClassLoader().getResource("logo1.png");
        if (imgURL != null) {
            ImageIcon logoIcon = new ImageIcon(imgURL);
            JLabel logoPic = new JLabel(logoIcon);
            logoPanel.add(logoPic);
        } else {
            System.out.println("Could not load logo1.png");
        }

        // Initializes the header components
        wel = new JLabel("Welcome Back");
        wel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        wel.setForeground(HEAD_TXT);
        wel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        subtitle = new JLabel("Sign in to your account to continue");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(SEC_TXT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
  
        // This is the Email input section
        emailWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        emailWrapper.setOpaque(false);
        emailWrapper.setMaximumSize(new Dimension(380, 20));

        lblEmail = new JLabel("Email Address");
        lblEmail.setForeground(TEXT_PRIMARY);
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailWrapper.add(lblEmail);

        //This is the email input field with the icons
        emailHold = new JPanel(new BorderLayout());
        emailHold.setBackground(INPUT_FIELDS);
        emailHold.setMaximumSize(new Dimension(380, 40));

        emailIcon = new JPanel(); 
        emailIcon.setBackground(INPUT_FIELDS);
        emailIcon.setPreferredSize(new Dimension(40, 40));
        emailIcon.setLayout(new GridBagLayout());
        
        java.net.URL imgURLEmail = Thread.currentThread().getContextClassLoader().getResource("25_25.png");
        if (imgURLEmail != null) {
            ImageIcon emailer = new ImageIcon(imgURLEmail);
            JLabel emailPic = new JLabel(emailer);
            emailIcon.add(emailPic);
        } else {
            System.out.println("Could not load logo.png");
        }
        
        emailHold.add(emailIcon, BorderLayout.WEST);

        emailField = new JTextField();
        emailField.setBackground(INPUT_FIELDS);
        emailField.setForeground(Color.WHITE);
        emailField.setCaretColor(Color.WHITE);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0x4b5563), 1, true),
                new EmptyBorder(8, 8, 8, 8)
        ));
        emailHold.add(emailField, BorderLayout.CENTER);
        
        // This initializes the password input section
        passWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passWrapper.setOpaque(false);
        passWrapper.setMaximumSize(new Dimension(380, 20));

        lblPass = new JLabel("Password");
        lblPass.setForeground(TEXT_PRIMARY);
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        passWrapper.add(lblPass);
        
        // This is the Password section with an icon added to it
        passHold = new JPanel(new BorderLayout());
        passHold.setBackground(INPUT_FIELDS);
        passHold.setMaximumSize(new Dimension(380, 40));

        passIcon = new JPanel();
        passIcon.setBackground(INPUT_FIELDS);
        passIcon.setPreferredSize(new Dimension(40, 40));
        passIcon.setLayout(new GridBagLayout());

        java.net.URL imgURLPass = Thread.currentThread().getContextClassLoader().getResource("25_36.png");
        if (imgURLPass != null) {
            ImageIcon passer = new ImageIcon(imgURLPass);
            JLabel passPic = new JLabel(passer);
            passIcon.add(passPic);
        } else {
            System.out.println("Could not load logo.png");
        }

        passHold.add(passIcon, BorderLayout.WEST);

        passField = new JPasswordField();
        passField.setBackground(new Color(0x374151));
        passField.setForeground(TEXT_PRIMARY);
        passField.setCaretColor(TEXT_PRIMARY);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(INPUT_BORD, 1, true),
                new EmptyBorder(8, 8, 8, 8)
        ));
        passHold.add(passField, BorderLayout.CENTER);
        
        // This is the login options section  with the Remember me & Forgot password
        options = new JPanel(new BorderLayout());
        options.setBackground(BG_SURFACE);
        options.setMaximumSize(new Dimension(380, 20));

        rememberMe = new JCheckBox("Remember me");
        rememberMe.setForeground(Color.WHITE);
        rememberMe.setBackground(BG_SURFACE);
        rememberMe.setFocusPainted(false);
        rememberMe.setBorderPainted(false);
        rememberMe.setContentAreaFilled(false);

        forgot = new JLabel("Forgot password?");
        forgot.setForeground(DARK_GREEN);
        forgot.setCursor(new Cursor(Cursor.HAND_CURSOR));

        options.add(rememberMe, BorderLayout.WEST);
        options.add(forgot, BorderLayout.EAST);

        // This is the Sign in button section
        signP = new JPanel(new BorderLayout());
        signP.setBackground(BG_SURFACE);
        signP.setMaximumSize(new Dimension(380, 48));

        signIn = new JButton("Sign In");
        signIn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signIn.setBackground(DARK_GREEN);
        signIn.setForeground(TEXT_PRIMARY);
        signIn.setFocusPainted(false);
        signIn.setBorder(new EmptyBorder(13, 13, 13, 13));
        signIn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        signIn.setContentAreaFilled(false);
        signIn.setOpaque(true);
        signIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // This is the Button hover effect implementation
        signIn.getModel().addChangeListener(e -> {
            ButtonModel model = signIn.getModel();
            if (model.isPressed()) {
                signIn.setBackground(new Color(0x15803d)); // This provide the darker green color
            } else if (model.isRollover()) {
                signIn.setBackground(new Color(0x22c55e)); // this provides the lighter hover green
                signIn.repaint();
            } else {
                signIn.setBackground(new Color(0x16a34a)); // This is the default color 
            }
        });

        signP.add(signIn, BorderLayout.CENTER);

        // This is the Separator line with "or" label
        liningP = new JPanel();
        liningP.setBackground(BG_SURFACE);
        liningP.setLayout(new BoxLayout(liningP, BoxLayout.X_AXIS));
        liningP.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        line1 = new JSeparator();
        line2 = new JSeparator();
        orLabel = new JLabel("or");
        orLabel.setForeground(SEC_TXT);
        orLabel.setBorder(new EmptyBorder(0, 8, 0, 8));

        liningP.add(line1);
        liningP.add(orLabel);
        liningP.add(line2);

        // Provides the Sign up section for new users
        signupP = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupP.setBackground(BG_SURFACE);
        signupP.setMaximumSize(new Dimension(380, 20));

        signup1 = new JLabel("Don't have an account?");
        signup1.setForeground(SEC_TXT);

        signup2 = new JButton("Sign up");
        signup2.setMargin(new Insets(0, 0, 0, 0));
        signup2.setBorder(BorderFactory.createEmptyBorder());
        signup2.setBackground(BG_SURFACE);
        signup2.setContentAreaFilled(false);
        signup2.setOpaque(true);
        signup2.setForeground(GREEN);
        signup2.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signupP.add(signup1);
        signupP.add(signup2);

        // Event listener registration
        signIn.addActionListener(this);
        signup2.addActionListener(this);

        //This adds the components into the main panel
        this.setLayout(new GridBagLayout());
        this.add(main, new GridBagConstraints());

        main.add(logoPanel);
        main.add(Box.createVerticalStrut(16));
        main.add(wel);
        main.add(Box.createVerticalStrut(8));
        main.add(subtitle);
        main.add(Box.createVerticalStrut(16));
        main.add(emailWrapper);
        main.add(Box.createVerticalStrut(6));
        main.add(emailHold);
        main.add(Box.createVerticalStrut(14));
        main.add(passWrapper);
        main.add(Box.createVerticalStrut(6));
        main.add(passHold);
        main.add(Box.createVerticalStrut(20));
        main.add(options);
        main.add(Box.createVerticalStrut(30));
        main.add(signP);
        main.add(Box.createVerticalStrut(30));
        main.add(liningP);
        main.add(signupP);

        // Frame configuration
        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(BG_MAIN);
    }

    /**
     * Handles the action events from the login form components.
     * Processes the login attempts and navigation and directs the user to other forms based on their account type.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle login button action
        if (e.getSource() == signIn) {
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            // Checks if the input fields are populated with information
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter email and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Send authentication request to the server
                String response = Client.sendRequest("LOGIN," + email + "," + password);

                if (response.startsWith("SUCCESS")) {
                    // Handles the successful user response
                    String[] parts = response.split(",");
                    int userId = Integer.parseInt(parts[1]);
                    String name = parts[2];
                    String number = parts[3];
                    String role = parts[4];

                    // This initialize the user session
                    manageSession.startSession(userId, email, name, number, role);

                    JOptionPane.showMessageDialog(this, "Login successful! Welcome " + name, "Welcome", JOptionPane.INFORMATION_MESSAGE);
                    
                    // This navigate to appropriate dashboard based on user role
                    if ("admin".equalsIgnoreCase(role)) {
                        goToNextPage(this, new adminMain());
                    } else {
                        goToNextPage(this, new registered());
                    }

                } else if (response.startsWith("FAIL")) {
                    // Handles the failed login attempt
                    String errorMessage = response.substring(5);
                    JOptionPane.showMessageDialog(this, errorMessage, "Login Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // this handle the unexpected server response
                    JOptionPane.showMessageDialog(this, "Unexpected response: " + response, "Server Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                // Handles  the server communication errors
                JOptionPane.showMessageDialog(this, "Server is down. Can't login right now. Try again later.", "Server Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Handles sign up button click
        if (e.getSource() == signup2) {
            goToNextPage(this, new signForm());
        }
    }
}