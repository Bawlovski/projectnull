package swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import classes.Player;
import classes.Bot;
import classes.planets.AbyssPlanet;
import classes.planets.GlitchPlanet;
import classes.planets.LostPlanet;

public class BattleScreen extends JFrame {
    private static final Color BUTTON_COLOR = new Color(70, 70, 70, 200);
    private static final Color HOVER_COLOR = new Color(90, 90, 90, 220);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Color HEALTH_COLOR = new Color(0, 255, 0, 200);
    private static final Color MISSILE_COLOR = new Color(255, 165, 0, 200);
    private static final Color DAMAGE_COLOR = new Color(255, 0, 0, 200);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 36);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font STATUS_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font LOGS_FONT = new Font("Arial", Font.PLAIN, 14);

    private Player currentPlayer;
    private List<Player> allPlayers;
    private List<Bot> bots;
    private JPanel mainPanel;
    private JPanel statusPanel;
    private JPanel actionPanel;
    private JPanel targetPanel;
    private JPanel logsPanel;
    private JPanel missilePanel;
    private JPanel gameOverPanel;
    private JLabel statusLabel;
    private JTextArea logsArea;
    private Timer turnTimer;
    private int selectedMissiles = 0;
    private JSlider missileSlider;
    private JLabel missileCountLabel;
    private int currentTurn = 0; // 0 = player's turn, 1+ = bot's turn (index+1)
    private List<Bot> activeBots;

    public BattleScreen(List<Player> players) {
        this.allPlayers = new ArrayList<>(players);
        this.currentPlayer = players.get(0); // First player is human
        this.bots = new ArrayList<>();
        this.activeBots = new ArrayList<>();
        
        // Create bots for all players except the first one
        for (int i = 1; i < players.size(); i++) {
            bots.add(new Bot(players.get(i)));
        }
        
        setTitle("Battle Screen");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        
        // Initialize panels
        statusPanel = new JPanel(new GridBagLayout());
        actionPanel = new JPanel(new GridBagLayout());
        targetPanel = new JPanel(new GridBagLayout());
        logsPanel = new JPanel(new GridBagLayout());
        missilePanel = new JPanel(new GridBagLayout());
        gameOverPanel = new JPanel(new GridBagLayout());
        
        createStatusPanel();
        createActionPanel();
        createTargetPanel();
        createMissilePanel();
        createGameOverPanel();
        createLogsPanel();
        createMainPanel();
        
        // Initially hide target, missile and gameOver panels
        targetPanel.setVisible(false);
        missilePanel.setVisible(false);
        gameOverPanel.setVisible(false);
        
        // Create turn timer
        turnTimer = new Timer(2000, e -> processTurn());
        turnTimer.setRepeats(false);
        
        // Initialize the game
        updateStatus();
        updateLogs();
        updateMissilePanel();
    }

    private void createLogsPanel() {
        logsPanel.setOpaque(false);
        logsPanel.setBorder(new LineBorder(TEXT_COLOR, 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Create logs text area
        logsArea = new JTextArea();
        logsArea.setEditable(false);
        logsArea.setFont(LOGS_FONT);
        logsArea.setBackground(new Color(20, 20, 40, 200));
        logsArea.setForeground(TEXT_COLOR);
        logsArea.setLineWrap(true);
        logsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(logsArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        gbc.gridx = 0;
        gbc.gridy = 0;
        logsPanel.add(scrollPane, gbc);
    }

    private void processTurn() {
        // Check if game is over first
        if (checkGameOver()) {
            return;
        }
        
        // Process current turn
        if (currentTurn == 0) {
            // Player's turn - do nothing here, we wait for player input
            System.out.println("\n\n=== PLAYER TURN ===");
            actionPanel.setVisible(true);
            statusLabel.setText("Your turn! Choose your action!");
            
            // Recreate target panel to ensure it has updated alive/dead status
            targetPanel.removeAll();
            createTargetPanel();
        } else {
            // Bot's turn
            int botIndex = currentTurn - 1;
            if (botIndex < bots.size()) {
                Bot bot = bots.get(botIndex);
                if (bot.getBotPlayer().isAlive()) {
                    System.out.println("\n\n=== BOT " + (botIndex + 1) + " TURN ===");
                    statusLabel.setText(bot.getBotPlayer().getName() + "'s turn...");
                    bot.makeMove(allPlayers);
                    updateLogs();
                    updateMissilePanel();
                } else {
                    System.out.println("Bot " + (botIndex + 1) + " is dead, skipping turn");
                }
                // Move to next turn
                advanceTurn();
            } else {
                // If we've gone past the last bot, go back to player
                currentTurn = 0;
                processTurn();
            }
        }
    }

    private void advanceTurn() {
        // Move to next turn
        currentTurn++;
        
        // If we've gone through all bots, go back to player
        if (currentTurn > bots.size()) {
            System.out.println("All bots have taken their turn, back to player");
            currentTurn = 0;
        }
        
        // Skip dead players/bots
        if (currentTurn == 0) {
            if (!currentPlayer.isAlive()) {
                System.out.println("Player is dead, game over");
                showGameOver(false);
                return;
            }
        } else {
            int botIndex = currentTurn - 1;
            if (botIndex < bots.size() && !bots.get(botIndex).getBotPlayer().isAlive()) {
                System.out.println("Bot " + (botIndex + 1) + " is dead, skipping turn");
                advanceTurn(); // Skip this bot
                return;
            }
        }
        
        // Start the next turn after a delay
        if (currentTurn > 0) { // Only use timer for bot turns
            System.out.println("Starting Bot " + currentTurn + "'s turn in 2 seconds...");
            turnTimer.start();
        } else {
            System.out.println("Starting Player's turn");
            processTurn(); // Immediately process player turn
        }
    }

    private void endPlayerTurn() {
        System.out.println("Player turn ended, moving to bots' turns");
        actionPanel.setVisible(false);
        advanceTurn();
    }

    private void createMainPanel() {
        mainPanel = new JPanel(new GridBagLayout()) {
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
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        mainPanel.add(statusPanel, gbc);

        gbc.gridy = 1;
        gbc.gridheight = 2;
        mainPanel.add(actionPanel, gbc);

        gbc.gridy = 3;
        gbc.gridheight = 1;
        mainPanel.add(missilePanel, gbc);

        gbc.gridy = 4;
        gbc.gridheight = 1;
        mainPanel.add(targetPanel, gbc);
        
        gbc.gridy = 1;
        gbc.gridheight = 4;
        mainPanel.add(gameOverPanel, gbc);

        // Right side logs panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        mainPanel.add(logsPanel, gbc);
    }

    private void createStatusPanel() {
        statusPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Current player status
        JLabel playerLabel = new JLabel(currentPlayer.getName() + " - " + currentPlayer.getPlanet().getName());
        playerLabel.setFont(TITLE_FONT);
        playerLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        statusPanel.add(playerLabel, gbc);

        // Health bar
        JPanel healthBar = createStatusBar(currentPlayer.getPlanet().getHealth(), 
                                        currentPlayer.getPlanet().getMaxHealth(), 
                                        HEALTH_COLOR);
        gbc.gridy = 1;
        statusPanel.add(healthBar, gbc);

        // Missile count
        JPanel missileBar = createStatusBar(currentPlayer.getPlanet().getMissiles(), 
                                         currentPlayer.getPlanet().getMaxMissiles(), 
                                         MISSILE_COLOR);
        gbc.gridy = 2;
        statusPanel.add(missileBar, gbc);

        // Status message
        statusLabel = new JLabel("Choose your action!");
        statusLabel.setFont(STATUS_FONT);
        statusLabel.setForeground(TEXT_COLOR);
        gbc.gridy = 3;
        statusPanel.add(statusLabel, gbc);
    }

    private void createActionPanel() {
        actionPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton attackButton = createStyledButton("ATTACK");
        JButton defendButton = createStyledButton("DEFEND");
        JButton healButton = createStyledButton("HEAL");
        JButton regenerateButton = createStyledButton("REGENERATE MISSILES");

        gbc.gridx = 0;
        gbc.gridy = 0;
        actionPanel.add(attackButton, gbc);

        gbc.gridy = 1;
        actionPanel.add(defendButton, gbc);

        gbc.gridy = 2;
        actionPanel.add(healButton, gbc);

        gbc.gridy = 3;
        actionPanel.add(regenerateButton, gbc);

        attackButton.addActionListener(e -> {
            // Check if there are any alive targets
            boolean hasAliveTargets = allPlayers.stream()
                .anyMatch(p -> p != currentPlayer && p.isAlive());
            
            if (hasAliveTargets) {
                // Recreate the target panel to ensure it's updated
                targetPanel.removeAll();
                createTargetPanel();
                
                statusLabel.setText("Select number of missiles!");
                actionPanel.setVisible(false);
                missilePanel.setVisible(true);
            } else {
                statusLabel.setText("No alive targets available!");
            }
        });

        defendButton.addActionListener(e -> {
            statusLabel.setText("Defending...");
            currentPlayer.defend();
            updateLogs();
            endPlayerTurn();
        });

        healButton.addActionListener(e -> {
            statusLabel.setText("Healing...");
            currentPlayer.heal();
            updateLogs();
            endPlayerTurn();
        });

        regenerateButton.addActionListener(e -> {
            statusLabel.setText("Regenerating missiles...");
            currentPlayer.regenerateMissiles();
            updateLogs();
            endPlayerTurn();
        });
    }

    private void createTargetPanel() {
        targetPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel targetLabel = new JLabel("Select Target:");
        targetLabel.setFont(TITLE_FONT);
        targetLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        targetPanel.add(targetLabel, gbc);

        // Add target buttons for all players except the current player
        int buttonIndex = 1;
        boolean hasAliveTargets = false;
        for (Player target : allPlayers) {
            if (target != currentPlayer && target.isAlive()) {
                hasAliveTargets = true;
                JButton targetButton = createStyledButton(target.getName() + " - " + 
                                                        target.getPlanet().getName());
                final Player finalTarget = target;
                targetButton.addActionListener(e -> {
                    // Double-check the target is still alive before attacking
                    if (!finalTarget.isAlive()) {
                        statusLabel.setText("Target is already destroyed!");
                        return;
                    }
                    statusLabel.setText("Attacking " + finalTarget.getName() + " with " + selectedMissiles + " missiles...");
                    currentPlayer.attack(finalTarget, selectedMissiles);
                    targetPanel.setVisible(false);
                    updateLogs();
                    updateMissilePanel();
                    endPlayerTurn();
                });
                
                gbc.gridy = buttonIndex++;
                targetPanel.add(targetButton, gbc);
            }
        }

        if (!hasAliveTargets) {
            statusLabel.setText("No alive targets available!");
            targetPanel.setVisible(false);
            actionPanel.setVisible(true);
            return;
        }

        JButton backButton = createStyledButton("BACK");
        gbc.gridy = buttonIndex;
        targetPanel.add(backButton, gbc);

        backButton.addActionListener(e -> {
            targetPanel.setVisible(false);
            actionPanel.setVisible(true);
            statusLabel.setText("Choose your action!");
        });
        
        targetPanel.revalidate();
        targetPanel.repaint();
    }

    private void createMissilePanel() {
        missilePanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel missileLabel = new JLabel("Select number of missiles:");
        missileLabel.setFont(TITLE_FONT);
        missileLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        missilePanel.add(missileLabel, gbc);

        // Create slider for missile selection
        missileSlider = new JSlider(1, currentPlayer.getPlanet().getMissiles(), 1);
        missileSlider.setMajorTickSpacing(5);
        missileSlider.setMinorTickSpacing(1);
        missileSlider.setPaintTicks(true);
        missileSlider.setPaintLabels(true);
        missileSlider.setBackground(new Color(50, 50, 50, 200));
        missileSlider.setForeground(TEXT_COLOR);
        missileSlider.setFont(STATUS_FONT);
        gbc.gridy = 1;
        missilePanel.add(missileSlider, gbc);

        // Add missile count label
        missileCountLabel = new JLabel("1");
        missileCountLabel.setFont(STATUS_FONT);
        missileCountLabel.setForeground(TEXT_COLOR);
        gbc.gridy = 2;
        missilePanel.add(missileCountLabel, gbc);

        // Update count label when slider changes
        missileSlider.addChangeListener(e -> {
            selectedMissiles = missileSlider.getValue();
            missileCountLabel.setText(String.valueOf(selectedMissiles));
        });

        // Add confirm button
        JButton confirmButton = createStyledButton("CONFIRM");
        gbc.gridy = 3;
        missilePanel.add(confirmButton, gbc);

        confirmButton.addActionListener(e -> {
            if (selectedMissiles > 0 && selectedMissiles <= currentPlayer.getPlanet().getMissiles()) {
                missilePanel.setVisible(false);
                targetPanel.setVisible(true);
                statusLabel.setText("Select your target!");
            }
        });

        // Add back button
        JButton backButton = createStyledButton("BACK");
        gbc.gridy = 4;
        missilePanel.add(backButton, gbc);

        backButton.addActionListener(e -> {
            missilePanel.setVisible(false);
            actionPanel.setVisible(true);
            statusLabel.setText("Choose your action!");
        });
    }

    private void updateMissilePanel() {
        int maxMissiles = currentPlayer.getPlanet().getMissiles();
        if (maxMissiles > 0) {
            missileSlider.setMaximum(maxMissiles);
            missileSlider.setValue(Math.min(selectedMissiles, maxMissiles));
            selectedMissiles = missileSlider.getValue();
            missileCountLabel.setText(String.valueOf(selectedMissiles));
        } else {
            missileSlider.setMaximum(1);
            missileSlider.setValue(1);
            selectedMissiles = 1;
            missileCountLabel.setText("1");
        }
    }

    private JPanel createStatusBar(int current, int max, Color color) {
        JPanel bar = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(50, 50, 50, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(color);
                int width = (int)((current * getWidth()) / max);
                g.fillRect(0, 0, width, getHeight());
            }
        };
        bar.setPreferredSize(new Dimension(300, 20));
        bar.setBorder(new LineBorder(TEXT_COLOR, 1));
        return bar;
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

    private void updateStatus() {
        // Update status bars and labels
        statusPanel.revalidate();
        statusPanel.repaint();
    }

    private void loadCustomFont() {
        try {
            // Load custom font
        } catch (Exception e) {
            System.err.println("Error loading custom font: " + e.getMessage());
        }
    }

    private boolean checkGameOver() {
        // Check if the player is dead
        if (!currentPlayer.isAlive()) {
            showGameOver(false);
            return true;
        }
        
        // Check if all enemies are dead (player wins)
        boolean allEnemiesDead = true;
        for (Player player : allPlayers) {
            if (player != currentPlayer && player.isAlive()) {
                allEnemiesDead = false;
                break;
            }
        }
        
        if (allEnemiesDead) {
            showGameOver(true);
            return true;
        }
        
        return false;
    }

    private void updateLogs() {
        StringBuilder logs = new StringBuilder();
        logs.append("=== BATTLE STATUS ===\n\n");
        
        for (Player player : allPlayers) {
            logs.append(player.getName())
                .append(" (")
                .append(player.getPlanet().getName())
                .append(")");
            
            if (!player.isAlive()) {
                logs.append(" [DEAD]");
            }
            
            logs.append("\n");
            logs.append("Health: ")
                .append(player.getPlanet().getHealth())
                .append("/")
                .append(player.getPlanet().getMaxHealth())
                .append("\n");
            logs.append("Missiles: ")
                .append(player.getPlanet().getMissiles())
                .append("/")
                .append(player.getPlanet().getMaxMissiles())
                .append("\n");
            logs.append("-------------------\n\n");
        }
        
        logsArea.setText(logs.toString());
        logsArea.setCaretPosition(logsArea.getDocument().getLength());
    }

    private void createGameOverPanel() {
        gameOverPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Title label
        JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gameOverPanel.add(titleLabel, gbc);

        // Message label
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(TITLE_FONT);
        messageLabel.setForeground(TEXT_COLOR);
        gbc.gridy = 1;
        gameOverPanel.add(messageLabel, gbc);

        // Exit button
        JButton exitButton = createStyledButton("EXIT GAME");
        gbc.gridy = 2;
        gameOverPanel.add(exitButton, gbc);

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Store labels as properties for later access
        titleLabel.setName("titleLabel");
        messageLabel.setName("messageLabel");
    }

    private void showGameOver(boolean playerWon) {
        // Hide all other panels
        statusPanel.setVisible(false);
        actionPanel.setVisible(false);
        targetPanel.setVisible(false);
        missilePanel.setVisible(false);
        
        // Get the title and message labels
        JLabel titleLabel = (JLabel) findComponentByName(gameOverPanel, "titleLabel");
        JLabel messageLabel = (JLabel) findComponentByName(gameOverPanel, "messageLabel");
        
        if (playerWon) {
            titleLabel.setText("VICTORY!");
            titleLabel.setForeground(HEALTH_COLOR);
            messageLabel.setText("You have defeated all enemies!");
        } else {
            titleLabel.setText("DEFEAT!");
            titleLabel.setForeground(DAMAGE_COLOR);
            messageLabel.setText("Your planet has been destroyed!");
        }
        
        // Show game over panel
        gameOverPanel.setVisible(true);
    }
    
    // Helper method to find a component by name
    private Component findComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> {
            // Create test players
            Player player1 = new Player("Player 1", new AbyssPlanet());
            Player player2 = new Player("Player 2", new GlitchPlanet());
            Player player3 = new Player("Player 3", new LostPlanet());
            
            // Create battle screen
            BattleScreen battleScreen = new BattleScreen(Arrays.asList(player1, player2, player3));
            battleScreen.setVisible(true);
        });
    }
} 