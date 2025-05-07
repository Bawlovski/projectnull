package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LanguageDialog extends JDialog {
    private JComboBox<String> languageSelector;
    private boolean confirmed = false;
    private LanguageManager languageManager;
    
    public LanguageDialog(JFrame parent) {
        super(parent, "Language Settings", true);
        this.languageManager = LanguageManager.getInstance();
        
        // Set up the dialog
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add instruction label
        JLabel instructionLabel = new JLabel(languageManager.getText("SELECT_LANGUAGE"));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(instructionLabel);
        
        // Add spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add language selector
        String[] languages = {"ENGLISH", "SPANISH"};
        languageSelector = new JComboBox<>(languages);
        languageSelector.setSelectedItem(languageManager.getCurrentLanguage());
        languageSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        mainPanel.add(languageSelector);
        
        // Add spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add save button
        JButton saveButton = new JButton(languageManager.getText("SAVE"));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLanguage = (String) languageSelector.getSelectedItem();
                languageManager.setLanguage(selectedLanguage);
                confirmed = true;
                dispose();
            }
        });
        
        // Add cancel button
        JButton cancelButton = new JButton(languageManager.getText("CANCEL"));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
        });
        
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        mainPanel.add(buttonsPanel);
        
        // Add panel to dialog
        add(mainPanel, BorderLayout.CENTER);
        
        // Set default button and focus
        getRootPane().setDefaultButton(saveButton);
        languageSelector.requestFocusInWindow();
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
} 