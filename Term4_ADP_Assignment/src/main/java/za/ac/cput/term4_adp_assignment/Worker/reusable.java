package za.ac.cput.term4_adp_assignment.Worker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public abstract class reusable extends JFrame {

    //setting up color scheme
    public static Color BG_MAIN = new Color(0x0A0A0A);
    public static Color BG_SURFACE = new Color(0x1a1a1a);
    //main pages
    public static Color BORDER_COLOR = new Color(42, 42, 42);

    public static Color HEAD_TXT = new Color(0xe5e7eb);
    public static Color SEC_TXT = new Color(0x9ca3af);
    public static Color INPUT_FIELDS = new Color(0x374151);
    public static Color INPUT_BORD = new Color(0x4b5563);

    //BUTTON COLORS
    public static Color PRESS_GREEN = new Color(0x15803d);
    public static Color HOVER_GREEN = new Color(0x22c55e);

    public static Color TEXT_PRIMARY = Color.WHITE;
    public static Color TEXT_SECONDARY = new Color(0x9ca3af);
    public static Color TEXT_MUTED = new Color(156, 163, 175);
    public static Color GREEN = new Color(0x22c55e);
    public static Color ACCENT_BLUE_LIGHT = new Color(65, 95, 197);

    public static Color DARK_GREEN = new Color(13, 138, 79);

    //basically go to next scene
    public static void goToNextPage(JFrame current, JFrame nextPage) {
        nextPage.setVisible(true); // show the next page
        current.dispose();         // close current page
    }

    public static void focuseBtn(JButton press) {
        press.getModel().addChangeListener(e -> {
            ButtonModel model = press.getModel();

            if (model.isPressed()) {
                press.setBackground(new Color(0x15803d)); // darker green
            } else if (model.isRollover()) {
                press.setBackground(GREEN); // lighter hover green
                press.repaint();
            } else {
                press.setBackground(DARK_GREEN); // default
            }
        });
    }

    public static JPanel createBlock(String title, JComponent content) {
        JPanel block = new JPanel(new BorderLayout());
        block.setBackground(BG_SURFACE);
        block.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(DARK_GREEN);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        block.add(titleLabel, BorderLayout.NORTH);
        block.add(content, BorderLayout.CENTER);

        return block;
    }

    public static void focuseBtnBlue(JButton press) {
        press.getModel().addChangeListener(e -> {
            ButtonModel model = press.getModel();

            if (model.isPressed()) {
                press.setBackground(new Color(37, 99, 235)); // darker blue
            } else if (model.isRollover()) {
                press.setBackground(new Color(54, 101, 255)); // lighter hover blue
                press.repaint();
            } else {
                press.setBackground(ACCENT_BLUE_LIGHT); // default (your ACCENT_BLUE_LIGHT)
            }
        });
    }

    public static void focuseBtnRed(JButton press) {
        press.getModel().addChangeListener(e -> {
            ButtonModel model = press.getModel();

            if (model.isPressed()) {
                press.setBackground(new Color(180, 40, 40)); // darker red when pressed
            } else if (model.isRollover()) {
                press.setBackground(new Color(255, 100, 100)); // lighter red on hover
                press.repaint();
            } else {
                press.setBackground(new Color(255, 80, 80)); // default red
            }
        });
    }

    public static void focuseBtnMenu(JButton press) {
        press.getModel().addChangeListener(e -> {
            ButtonModel model = press.getModel();

            if (model.isPressed()) {
                press.setBackground(new Color(0x15803d)); // darker green
            } else if (model.isRollover()) {
                press.setBackground(GREEN); // lighter hover green
                press.repaint();
            } else {
                press.setBackground(BG_SURFACE); // default
            }
        });
    }
}
