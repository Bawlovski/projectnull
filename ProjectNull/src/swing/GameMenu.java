package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameMenu extends JFrame {
    private static final Color BUTTON_COLOR = new Color(70, 70, 70, 200);
    private static final Color HOVER_COLOR = new Color(90, 90, 90, 220);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);

    public GameMenu() {
        setTitle("Game Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);

        createMainPanel();
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                // Draw gradient background
                g.setColor(new Color(20, 20, 40));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(new Color(40, 40, 60));
                for (int i = 0; i < getHeight(); i += 20) {
                    g.drawLine(0, i, getWidth(), i);
                }
            }
        };
        setContentPane(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("PROJECT NULL");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Play button
        JButton playButton = createStyledButton("JUGAR");
        gbc.gridy = 1;
        mainPanel.add(playButton, gbc);

        // Options button
        JButton optionsButton = createStyledButton("OPCIONES");
        gbc.gridy = 2;
        mainPanel.add(optionsButton, gbc);

        // Exit button
        JButton exitButton = createStyledButton("SALIR");
        gbc.gridy = 3;
        mainPanel.add(exitButton, gbc);

        // Add action listeners
        playButton.addActionListener(e -> {
            this.dispose(); // Close current window
            new TeamMenu().setVisible(true); // Open TeamMenu
        });

        optionsButton.addActionListener(e -> {
            // TODO: Implement options menu
            JOptionPane.showMessageDialog(this, "Options menu coming soon!");
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(HOVER_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g.setColor(HOVER_COLOR);
                } else {
                    g.setColor(BUTTON_COLOR);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 60));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
                button.repaint();
            }
        });
        
        return button;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GameMenu().setVisible(true);
        });
    }
}