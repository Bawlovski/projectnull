package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class GameMenu extends JFrame {

    private Font customFont;

    public GameMenu() {
        // Configuración de la ventana
        setTitle("Project Null");
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
                    BufferedImage backgroundImage = ImageIO.read(new File("C:/Users/ZONAINFORMATICA FRAN/Downloads/background.jpg"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        setContentPane(panel);

        // Configuración del diseño
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 10); // Espaciado entre elementos (izquierda: 20)
        gbc.anchor = GridBagConstraints.CENTER; // Alinear al centro
        gbc.fill = GridBagConstraints.HORIZONTAL; // No expandir los componentes

        // Título "PROJECT NULL"
        JLabel title = new JLabel("PROJECT NULL");
        title.setFont(customFont.deriveFont(100f)); // Usar la fuente personalizada
        title.setForeground(Color.white); // Color del texto
        title.setHorizontalAlignment(SwingConstants.LEFT); // Alinear texto a la izquierda
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        // Botones del menú
        String[] buttonTexts = {"JUGAR", "REGLAS", "CREDITOS", "RANKING"};
        for (String text : buttonTexts) {
            JButton button = new JButton(text);
            button.setFont(customFont.deriveFont(30f)); // Usar la fuente personalizada
            button.setForeground(Color.WHITE); // Color del texto
            button.setBackground(new Color(70, 70, 70, 200)); // Fondo del botón
            button.setOpaque(true);
            button.setBorderPainted(false); // Eliminar el borde del botón
            button.setPreferredSize(new Dimension(300, 60)); // Mismo tamaño para todos los botones
            gbc.gridy++;
            panel.add(button, gbc);
        }

        // Botón de salida
        JButton exitButton = new JButton("SALIR");
        exitButton.setFont(customFont.deriveFont(30f)); // Usar la fuente personalizada
        exitButton.setForeground(Color.WHITE); // Color del texto
        exitButton.setBackground(new Color(70, 70, 70, 200)); // Fondo del botón
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false); // Eliminar el borde del botón
        exitButton.setPreferredSize(new Dimension(300, 60)); // Mismo tamaño para el botón de salida
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy++;
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
        SwingUtilities.invokeLater(() -> new GameMenu().setVisible(true));
    }
}