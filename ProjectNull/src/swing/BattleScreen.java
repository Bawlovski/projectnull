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
    private Font customFont;
    private static final Color BUTTON_COLOR = new Color(70, 70, 70, 200);
    private static final Color HOVER_COLOR = new Color(90, 90, 90, 220);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Color HEALTH_COLOR = new Color(0, 255, 0, 200);
    private static final Color MISSILE_COLOR = new Color(255, 165, 0, 200);
    private static final Color DAMAGE_COLOR = new Color(255, 0, 0, 200);

    private Player currentPlayer;
    private List<Player> allPlayers;
    private List<Bot> bots;
    private JPanel mainPanel;
    private JPanel statusPanel;
    private JPanel actionPanel;
    private JPanel targetPanel;
    private JPanel logsPanel;
    private JLabel statusLabel;
    private JTextArea logsArea;
    private Timer botTimer;

    public BattleScreen(List<Player> players) {
        this.allPlayers = new ArrayList<>(players);
        this.currentPlayer = players.get(0); // First player is human
        this.bots = new ArrayList<>();
        
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
        
        loadCustomFont();
        
        // Initialize panels
        statusPanel = new JPanel(new GridBagLayout());
        actionPanel = new JPanel(new GridBagLayout());
        targetPanel = new JPanel(new GridBagLayout());
        logsPanel = new JPanel(new GridBagLayout());
        
        createStatusPanel();
        createActionPanel();
        createTargetPanel();
        createLogsPanel();
        createMainPanel();
        
        // Initially hide target panel
        targetPanel.setVisible(false);
        
        // Create bot timer
        botTimer = new Timer(2000, e -> {
            for (Bot bot : bots) {
                bot.makeMove(allPlayers);
                updateLogs();
            }
        });
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
        logsArea.setFont(customFont.deriveFont(14f));
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
                .append(")\n");
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
        mainPanel.add(targetPanel, gbc);

        // Right side logs panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        mainPanel.add(logsPanel, gbc);
    }

    private void createStatusPanel() {
        statusPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Current player status
        JLabel playerLabel = new JLabel(currentPlayer.getName() + " - " + currentPlayer.getPlanet().getName());
        playerLabel.setFont(customFont.deriveFont(24f));
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
        statusLabel.setFont(customFont.deriveFont(20f));
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
            statusLabel.setText("Select your target!");
            targetPanel.setVisible(true);
            actionPanel.setVisible(false);
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
        targetLabel.setFont(customFont.deriveFont(20f));
        targetLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        targetPanel.add(targetLabel, gbc);

        // Add target buttons for all players except the current player
        int buttonIndex = 1;
        for (Player target : allPlayers) {
            if (target != currentPlayer) {
                JButton targetButton = createStyledButton(target.getName() + " - " + 
                                                        target.getPlanet().getName());
                final Player finalTarget = target;
                targetButton.addActionListener(e -> {
                    statusLabel.setText("Attacking " + finalTarget.getName() + "...");
                    currentPlayer.attack(finalTarget);
                    targetPanel.setVisible(false);
                    updateLogs();
                    startBotTurn();
                });
                
                gbc.gridy = buttonIndex++;
                targetPanel.add(targetButton, gbc);
            }
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
        
        button.setFont(customFont.deriveFont(20f));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
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
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/BlackDahlia.ttf"));
        } catch (Exception e) {
            System.err.println("Error loading custom font: " + e.getMessage());
            customFont = new Font("Arial", Font.BOLD, 18);
        }
    }

    private void startBotTurn() {
        actionPanel.setVisible(false);
        statusLabel.setText("Bots are making their moves...");
        botTimer.setRepeats(false);
        botTimer.start();
        
        // After bot turns, enable player actions
        Timer enablePlayerTimer = new Timer(2000 * bots.size() + 500, e -> {
            actionPanel.setVisible(true);
            statusLabel.setText("Your turn! Choose your action!");
            updateLogs();
        });
        enablePlayerTimer.setRepeats(false);
        enablePlayerTimer.start();
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