package swing;

import java.awt.Color;
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
    private JLabel statusLabel;
    private JTextArea logsArea;
    private Timer botTimer;
    private int selectedMissiles = 0;
    private JSlider missileSlider;
    private JLabel missileCountLabel;
    private int currentBotIndex = 0;
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
        
        createStatusPanel();
        createActionPanel();
        createTargetPanel();
        createMissilePanel();
        createLogsPanel();
        createMainPanel();
        
        // Initially hide target and missile panels
        targetPanel.setVisible(false);
        missilePanel.setVisible(false);
        
        // Create bot timer
        botTimer = new Timer(2000, e -> {
            if (currentBotIndex < activeBots.size()) {
                Bot currentBot = activeBots.get(currentBotIndex);
                if (currentBot.getBotPlayer().isAlive()) {
                    currentBot.makeMove(allPlayers);
                    updateLogs();
                }
                currentBotIndex++;
            } else {
                // All bots have moved, reset index and stop timer
                currentBotIndex = 0;
                botTimer.stop();
                
                // After all bots have moved, enable player actions
                actionPanel.setVisible(true);
                statusLabel.setText("Your turn! Choose your action!");
                updateLogs();
                updateMissilePanel();
            }
        });
        botTimer.setRepeats(true);
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
            startBotTurn();
        });

        healButton.addActionListener(e -> {
            statusLabel.setText("Healing...");
            currentPlayer.heal();
            updateLogs();
            startBotTurn();
        });

        regenerateButton.addActionListener(e -> {
            statusLabel.setText("Regenerating missiles...");
            currentPlayer.regenerateMissiles();
            updateLogs();
            startBotTurn();
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
                    statusLabel.setText("Attacking " + finalTarget.getName() + " with " + selectedMissiles + " missiles...");
                    currentPlayer.attack(finalTarget, selectedMissiles);
                    targetPanel.setVisible(false);
                    actionPanel.setVisible(true);
                    updateLogs();
                    updateMissilePanel();
                    startBotTurn();
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

    private void startBotTurn() {
        actionPanel.setVisible(false);
        statusLabel.setText("Bots are making their moves...");
        
        // Update active bots list to include only alive bots
        activeBots.clear();
        for (Bot bot : bots) {
            if (bot.getBotPlayer().isAlive()) {
                activeBots.add(bot);
            }
        }
        
        currentBotIndex = 0; // Reset bot index
        botTimer.start();
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