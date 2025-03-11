package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class GameMenu extends JFrame {

    private Font customFont;

    public GameMenu() {
        // Cargar la fuente personalizada
        loadCustomFont();

        // Configuración de la ventana
        setTitle("Project Null");
        setSize(1200, 800); // Ventana más grande
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel de fondo con imagen en escala de grises
        BackgroundPanel background = new BackgroundPanel("C:/Users/ZONAINFORMATICA FRAN/Downloads/background.jpg");
        setContentPane(background);
        background.setLayout(new GridBagLayout());

        // Configuración del diseño
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(20, 0, 20, 0); // Espaciado entre botones
        gbc.anchor = GridBagConstraints.CENTER;

        // Título estilizado en escala de grises con fuente Oi
        JLabel title = new JLabel("PROJECT NULL");
        title.setFont(customFont.deriveFont(Font.BOLD, 70));
        title.setForeground(new Color(200, 200, 200)); // Gris claro
        title.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 4));
        title.setOpaque(true);
        title.setBackground(new Color(30, 30, 30, 200)); // Semi-transparente
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(600, 120));
        background.add(title, gbc);

        // Botones del menú con diseño en escala de grises y fuente Oi
        String[] buttonTexts = {"JUGAR", "REGLAS", "CREDITOS", "RANKING"};
        for (String text : buttonTexts) {
            JButton button = createStyledButton(text);
            background.add(button, gbc);
        }

        // Botón de salida en la esquina inferior izquierda
        JButton exitButton = createStyledButton("SALIR");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.anchor = GridBagConstraints.WEST;
        background.add(exitButton, gbc);
    }

    // Método para cargar la fuente personalizada
    private void loadCustomFont() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("fonts/RubikDistressed-Regular.ttf");
            if (is == null) {
                throw new FileNotFoundException("Font file not found");
            }
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.BOLD, 26); // Fallback en caso de error
        }
    }

    // Método para crear botones estilizados
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(customFont.deriveFont(Font.BOLD, 30)); // Fuente más grande
        button.setForeground(new Color(240, 240, 240)); // Texto gris claro
        button.setBackground(new Color(70, 70, 70, 200)); // Fondo gris oscuro con transparencia
        button.setFocusPainted(false); // Eliminar el borde de enfoque
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 2), // Borde exterior
            BorderFactory.createEmptyBorder(15, 30, 15, 30) // Padding interno
        ));
        button.setPreferredSize(new Dimension(350, 100)); // Tamaño más grande
        button.setOpaque(true);

        // Efecto hover mejorado
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(150, 150, 150, 220)); // Gris más claro al hacer hover
                button.setForeground(Color.WHITE); // Texto blanco al hacer hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 70, 200)); // Volver al color original
                button.setForeground(new Color(240, 240, 240)); // Volver al color original del texto
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameMenu().setVisible(true));
    }
}