package juego;

import javax.swing.*;
import java.awt.*;

public class PantallaInicio extends JFrame {

    public PantallaInicio() {

        setTitle("El Legado de la Sangre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
<<<<<<< HEAD
       
=======
>>>>>>> d7c497ce7ccda5f8c912299555ea3c3c07d75d65
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = screenSize.width;
        int screenH = screenSize.height;

        JLabel fondo = new JLabel(escalarImagen("imagenes/botonComenzar.png", screenW, screenH));
        fondo.setLayout(null);
        fondo.setBounds(0, 0, screenW, screenH);

        JButton comenzar = new JButton();
<<<<<<< HEAD
        
=======
>>>>>>> d7c497ce7ccda5f8c912299555ea3c3c07d75d65
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