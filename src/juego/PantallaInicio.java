package juego;

import javax.swing.*;
import java.awt.*;

public class PantallaInicio extends JFrame {

    public PantallaInicio() {

        setTitle("El Legado de la Sangre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Modo Pantalla Completa
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = screenSize.width;
        int screenH = screenSize.height;

        JLabel fondo = new JLabel(escalarImagen("imagenes/botonComenzar.png", screenW, screenH));
        fondo.setLayout(null);
        fondo.setBounds(0, 0, screenW, screenH);

        JButton comenzar = new JButton();
        // El botón invisible ahora ocupa toda la pantalla dinámicamente
        comenzar.setBounds(0, 0, screenW, screenH);
        comenzar.setOpaque(false);
        comenzar.setContentAreaFilled(false);
        comenzar.setBorderPainted(false);
        comenzar.setFocusPainted(false);

        comenzar.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });

        fondo.add(comenzar);
        setContentPane(fondo);
        setVisible(true);
    }

    private ImageIcon escalarImagen(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}