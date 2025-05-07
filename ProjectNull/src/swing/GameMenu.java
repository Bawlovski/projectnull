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
import classes.Planet;
import classes.planets.AbyssPlanet;
import classes.planets.GlitchPlanet;
import classes.planets.LostPlanet;
import database.DatabaseManager;

public class GameMenu extends JFrame {
    private static final Color BUTTON_COLOR = new Color(70, 70, 70, 200);
    private static final Color HOVER_COLOR = new Color(90, 90, 90, 220);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);

    private LanguageManager languageManager;
    private JLabel titleLabel;
    private JButton playButton;
    private JButton loadGameButton;
    private JButton optionsButton;
    private JButton exitButton;

    public GameMenu() {
        languageManager = LanguageManager.getInstance();
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
        titleLabel = new JLabel(languageManager.getText("GAME_TITLE"));
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Play button
        playButton = createStyledButton(languageManager.getText("PLAY"));
        gbc.gridy = 1;
        mainPanel.add(playButton, gbc);

        // Load Game button
        loadGameButton = createStyledButton(languageManager.getText("LOAD_GAME"));
        gbc.gridy = 2;
        mainPanel.add(loadGameButton, gbc);

        // Options button
        optionsButton = createStyledButton(languageManager.getText("OPTIONS"));
        gbc.gridy = 3;
        mainPanel.add(optionsButton, gbc);

        // Exit button
        exitButton = createStyledButton(languageManager.getText("EXIT"));
        gbc.gridy = 4;
        mainPanel.add(exitButton, gbc);

        // Add action listeners
        playButton.addActionListener(e -> {
            this.dispose(); // Close current window
            new TeamMenu().setVisible(true); // Open TeamMenu
        });

        loadGameButton.addActionListener(e -> {
            DatabaseManager dbManager = new DatabaseManager();
            LoadGameDialog loadDialog = new LoadGameDialog(this, dbManager);
            loadDialog.setVisible(true);
            
            if (loadDialog.isConfirmed()) {
                int gameId = loadDialog.getSelectedGameId();
                DatabaseManager.GameSaveData saveData = dbManager.loadGame(gameId);
                
                if (saveData != null) {
                    // Recreate players from save data
                    List<Player> players = new ArrayList<>();
                    
                    for (DatabaseManager.PlayerSaveData playerData : saveData.getPlayers()) {
                        // Create appropriate planet based on type
                        Planet planet;
                        switch (playerData.getPlanetType()) {
                            case "Dark":
                                planet = new AbyssPlanet();
                                break;
                            case "Glitch":
                                planet = new GlitchPlanet();
                                break;
                            case "Lost":
                                planet = new LostPlanet();
                                break;
                            default:
                                planet = new AbyssPlanet();
                        }
                        
                        // Set planet state
                        planet.setHealth(playerData.getHealth());
                        planet.setMissiles(playerData.getMissiles());
                        
                        // Create player with restored state
                        Player player = new Player(playerData.getName(), planet);
                        players.add(player);
                    }
                    
                    // Start battle screen with loaded players
                    this.dispose(); // Close current window
                    BattleScreen battleScreen = new BattleScreen(players);
                    battleScreen.setCurrentTurn(saveData.getCurrentTurn());
                    battleScreen.setVisible(true);
                }
            }
        });

        optionsButton.addActionListener(e -> {
            LanguageDialog languageDialog = new LanguageDialog(this);
            languageDialog.setVisible(true);
            
            if (languageDialog.isConfirmed()) {
                // Update all text elements with new language
                updateTexts();
            }
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void updateTexts() {
        titleLabel.setText(languageManager.getText("GAME_TITLE"));
        playButton.setText(languageManager.getText("PLAY"));
        loadGameButton.setText(languageManager.getText("LOAD_GAME"));
        optionsButton.setText(languageManager.getText("OPTIONS"));
        exitButton.setText(languageManager.getText("EXIT"));
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