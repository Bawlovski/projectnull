package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveGameDialog extends JDialog {
    private JTextField saveNameField;
    private boolean confirmed = false;
    
    public SaveGameDialog(JFrame parent) {
        super(parent, "Save Game", true);
        
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
        JLabel instructionLabel = new JLabel("Enter a name for your saved game:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(instructionLabel);
        
        // Add spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add save name field
        saveNameField = new JTextField();
        saveNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        mainPanel.add(saveNameField);
        
        // Add spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saveNameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(SaveGameDialog.this, 
                                                 "Please enter a name for your saved game.", 
                                                 "Input Required", 
                                                 JOptionPane.WARNING_MESSAGE);
                } else {
                    confirmed = true;
                    dispose();
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
        
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        mainPanel.add(buttonsPanel);
        
        // Add panel to dialog
        add(mainPanel, BorderLayout.CENTER);
        
        // Set default button and focus
        getRootPane().setDefaultButton(saveButton);
        saveNameField.requestFocusInWindow();
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public String getSaveName() {
        return saveNameField.getText().trim();
    }
} 