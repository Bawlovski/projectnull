package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import classes.Player;
import classes.planets.AbyssPlanet;
import classes.planets.GlitchPlanet;
import classes.planets.LostPlanet;

public class TeamMenu extends JFrame {
    private static final Color BUTTON_COLOR = new Color(70, 70, 70, 200);
    private static final Color HOVER_COLOR = new Color(90, 90, 90, 220);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 36);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 16);

    private List<Player> players;
    private JComboBox<String>[] planetSelectors;
    private JTextField[] nameFields;

    public TeamMenu() {
        players = new ArrayList<>();
        setTitle("Team Menu");
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
        JLabel titleLabel = new JLabel("SELECT YOUR TEAM");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Create player selection panels
        planetSelectors = new JComboBox[4];
        nameFields = new JTextField[4];
        String[] planets = {"Abyss Planet", "Glitch Planet", "Lost Planet"};

        for (int i = 0; i < 4; i++) {
            JPanel playerPanel = createPlayerPanel(i, planets);
            gbc.gridy = i + 1;
            mainPanel.add(playerPanel, gbc);
        }

        // Start Battle button
        JButton startButton = createStyledButton("START BATTLE");
        gbc.gridy = 5;
        mainPanel.add(startButton, gbc);

        startButton.addActionListener(e -> {
            players.clear();
            for (int i = 0; i < 4; i++) {
                String name = nameFields[i].getText().trim();
                if (!name.isEmpty()) {
                    String planetName = (String) planetSelectors[i].getSelectedItem();
                    Player player = createPlayer(name, planetName);
                    if (player != null) {
                        players.add(player);
                    }
                }
            }

            if (players.size() >= 2) {
                this.dispose(); // Close TeamMenu
                new BattleScreen(players).setVisible(true); // Open BattleScreen
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select at least 2 players to start the battle!",
                    "Not Enough Players",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private JPanel createPlayerPanel(int index, String[] planets) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Player name field
        JTextField nameField = new JTextField("Player " + (index + 1));
        nameField.setFont(INPUT_FONT);
        nameField.setBackground(new Color(50, 50, 50, 200));
        nameField.setForeground(TEXT_COLOR);
        nameField.setCaretColor(TEXT_COLOR);
        nameField.setBorder(new LineBorder(TEXT_COLOR, 1));
        nameFields[index] = nameField;

        // Planet selector
        JComboBox<String> planetSelector = new JComboBox<>(planets);
        planetSelector.setFont(INPUT_FONT);
        planetSelector.setBackground(new Color(50, 50, 50, 200));
        planetSelector.setForeground(TEXT_COLOR);
        planetSelectors[index] = planetSelector;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 1;
        panel.add(planetSelector, gbc);

        return panel;
    }

    private Player createPlayer(String name, String planetName) {
        switch (planetName) {
            case "Abyss Planet":
                return new Player(name, new AbyssPlanet());
            case "Glitch Planet":
                return new Player(name, new GlitchPlanet());
            case "Lost Planet":
                return new Player(name, new LostPlanet());
            default:
                return null;
        }
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
            new TeamMenu().setVisible(true);
        });
    }
}