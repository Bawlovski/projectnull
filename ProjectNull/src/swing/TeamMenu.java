package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class TeamMenu extends JFrame {

    private Font customFont;

    public TeamMenu() {
        // Configuración de la ventana
        setTitle("Crear Equipos");
        setSize(800, 600); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        setUndecorated(true);

        
        // Cargar la fuente personalizada
        loadCustomFont();

        // Panel principal con imagen de fondo
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                try {
                    // Cargar la imagen de fondo
                    BufferedImage backgroundImage = ImageIO.read(new File("C:/Users/ZONAINFORMATICA FRAN/Downloads/background4.jpg"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        setContentPane(panel);

        // Configuración del diseño
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre elementos
        gbc.anchor = GridBagConstraints.CENTER;

        // Título "CREAR EQUIPOS"
        JLabel title = new JLabel("CREAR EQUIPOS");
        title.setFont(customFont.deriveFont(100f)); // Usar la fuente personalizada
        title.setForeground(Color.WHITE); // Color del texto en blanco
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        // Tipos de planetas disponibles
        String[] planetTypes = {"Abyss", "Glitch", "Lost"};

        // Menús desplegables y campos de texto para nombres de jugadores
        gbc.gridwidth = 1; // Restablecer a una columna
        gbc.gridx = 0; // Columna izquierda

        // Planeta 1
        JLabel planeta1Label = new JLabel("Planeta 1:");
        planeta1Label.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta1Label.setForeground(Color.WHITE); // Color del texto en blanco
        gbc.gridy = 1;
        panel.add(planeta1Label, gbc);

        JComboBox<String> planeta1ComboBox = new JComboBox<>(planetTypes);
        planeta1ComboBox.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta1ComboBox.setForeground(Color.WHITE); // Color del texto en blanco
        planeta1ComboBox.setBackground(new Color(70, 70, 70, 200)); // Fondo del combo box
        gbc.gridy = 2;
        panel.add(planeta1ComboBox, gbc);

        JTextField planeta1TextField = new JTextField(15); // Campo para el nombre del jugador
        planeta1TextField.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta1TextField.setForeground(Color.WHITE); // Color del texto en blanco
        planeta1TextField.setBackground(new Color(70, 70, 70, 200)); // Fondo del campo de texto
        gbc.gridy = 3;
        panel.add(planeta1TextField, gbc);

        // Planeta 2
        JLabel planeta2Label = new JLabel("Planeta 2:");
        planeta2Label.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta2Label.setForeground(Color.WHITE); // Color del texto en blanco
        gbc.gridy = 4;
        panel.add(planeta2Label, gbc);

        JComboBox<String> planeta2ComboBox = new JComboBox<>(planetTypes);
        planeta2ComboBox.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta2ComboBox.setForeground(Color.WHITE); // Color del texto en blanco
        planeta2ComboBox.setBackground(new Color(70, 70, 70, 200)); // Fondo del combo box
        gbc.gridy = 5;
        panel.add(planeta2ComboBox, gbc);

        JTextField planeta2TextField = new JTextField(15); // Campo para el nombre del jugador
        planeta2TextField.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta2TextField.setForeground(Color.WHITE); // Color del texto en blanco
        planeta2TextField.setBackground(new Color(70, 70, 70, 200)); // Fondo del campo de texto
        gbc.gridy = 6;
        panel.add(planeta2TextField, gbc);

        // Menús desplegables y campos de texto a la derecha
        gbc.gridx = 1; // Columna derecha

        // Planeta 3
        JLabel planeta3Label = new JLabel("Planeta 3:");
        planeta3Label.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta3Label.setForeground(Color.WHITE); // Color del texto en blanco
        gbc.gridy = 1;
        panel.add(planeta3Label, gbc);

        JComboBox<String> planeta3ComboBox = new JComboBox<>(planetTypes);
        planeta3ComboBox.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta3ComboBox.setForeground(Color.WHITE); // Color del texto en blanco
        planeta3ComboBox.setBackground(new Color(70, 70, 70, 200)); // Fondo del combo box
        gbc.gridy = 2;
        panel.add(planeta3ComboBox, gbc);

        JTextField planeta3TextField = new JTextField(15); // Campo para el nombre del jugador
        planeta3TextField.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta3TextField.setForeground(Color.WHITE); // Color del texto en blanco
        planeta3TextField.setBackground(new Color(70, 70, 70, 200)); // Fondo del campo de texto
        gbc.gridy = 3;
        panel.add(planeta3TextField, gbc);

        // Planeta 4
        JLabel planeta4Label = new JLabel("Planeta 4:");
        planeta4Label.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta4Label.setForeground(Color.WHITE); // Color del texto en blanco
        gbc.gridy = 4;
        panel.add(planeta4Label, gbc);

        JComboBox<String> planeta4ComboBox = new JComboBox<>(planetTypes);
        planeta4ComboBox.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta4ComboBox.setForeground(Color.WHITE); // Color del texto en blanco
        planeta4ComboBox.setBackground(new Color(70, 70, 70, 200)); // Fondo del combo box
        gbc.gridy = 5;
        panel.add(planeta4ComboBox, gbc);

        JTextField planeta4TextField = new JTextField(15); // Campo para el nombre del jugador
        planeta4TextField.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        planeta4TextField.setForeground(Color.WHITE); // Color del texto en blanco
        planeta4TextField.setBackground(new Color(70, 70, 70, 200)); // Fondo del campo de texto
        gbc.gridy = 6;
        panel.add(planeta4TextField, gbc);

        // Botón "Empezar Partida"
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.gridx = 0;
        gbc.gridy = 7;
        JButton startGameButton = new JButton("EMPEZAR PARTIDA");
        startGameButton.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        startGameButton.setForeground(Color.WHITE); // Color del texto en blanco
        startGameButton.setBackground(new Color(70, 70, 70, 200)); // Fondo del botón
        startGameButton.setOpaque(true);
        startGameButton.setBorderPainted(false); // Eliminar el borde del botón
        panel.add(startGameButton, gbc);

        // Botón "SALIR"
        gbc.gridy = 8;
        JButton exitButton = new JButton("SALIR");
        exitButton.setFont(customFont.deriveFont(18f)); // Usar la fuente personalizada
        exitButton.setForeground(Color.WHITE); // Color del texto en blanco
        exitButton.setBackground(new Color(70, 70, 70, 200)); // Fondo del botón
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false); // Eliminar el borde del botón
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton, gbc);
    }

    // Método para cargar la fuente personalizada
    private void loadCustomFont() {
        try {
            // Cargar la fuente desde un archivo .ttf
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/BlackDahlia.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            // Fuente de respaldo en caso de error
            customFont = new Font("SansSerif", Font.BOLD, 18);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TeamMenu().setVisible(true));
    }
}