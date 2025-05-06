package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import database.DatabaseManager;
import database.DatabaseManager.GameSaveInfo;

public class LoadGameDialog extends JDialog {
    private JList<GameSaveInfo> savedGamesList;
    private DefaultListModel<GameSaveInfo> listModel;
    private DatabaseManager dbManager;
    private boolean confirmed = false;
    private int selectedGameId = -1;
    
    public LoadGameDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Load Game", true);
        this.dbManager = dbManager;
        
        // Set up the dialog
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add instruction label
        JLabel instructionLabel = new JLabel("Select a saved game to load:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(instructionLabel);
        
        // Add spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Create list model and list
        listModel = new DefaultListModel<>();
        savedGamesList = new JList<>(listModel);
        savedGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        savedGamesList.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Add list to a scroll pane
        JScrollPane scrollPane = new JScrollPane(savedGamesList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(scrollPane);
        
        // Add spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add load button
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSaveInfo selectedGame = savedGamesList.getSelectedValue();
                if (selectedGame == null) {
                    JOptionPane.showMessageDialog(LoadGameDialog.this, 
                                                 "Please select a game to load.", 
                                                 "Selection Required", 
                                                 JOptionPane.WARNING_MESSAGE);
                } else {
                    selectedGameId = selectedGame.getId();
                    confirmed = true;
                    dispose();
                }
            }
        });
        
        // Add delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSaveInfo selectedGame = savedGamesList.getSelectedValue();
                if (selectedGame == null) {
                    JOptionPane.showMessageDialog(LoadGameDialog.this, 
                                                 "Please select a game to delete.", 
                                                 "Selection Required", 
                                                 JOptionPane.WARNING_MESSAGE);
                } else {
                    int result = JOptionPane.showConfirmDialog(LoadGameDialog.this,
                                                             "Are you sure you want to delete the selected save?",
                                                             "Confirm Deletion",
                                                             JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        dbManager.deleteGame(selectedGame.getId());
                        loadSavedGames();
                    }
                }
            }
        });
        
        // Add cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
        });
        
        buttonsPanel.add(loadButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(cancelButton);
        mainPanel.add(buttonsPanel);
        
        // Add panel to dialog
        add(mainPanel, BorderLayout.CENTER);
        
        // Load saved games
        loadSavedGames();
        
        // Set default button
        getRootPane().setDefaultButton(loadButton);
    }
    
    private void loadSavedGames() {
        listModel.clear();
        List<GameSaveInfo> savedGames = dbManager.getSavedGamesList();
        
        if (savedGames.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                         "No saved games found.", 
                                         "No Saves", 
                                         JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (GameSaveInfo game : savedGames) {
                listModel.addElement(game);
            }
            savedGamesList.setSelectedIndex(0);
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public int getSelectedGameId() {
        return selectedGameId;
    }
} 