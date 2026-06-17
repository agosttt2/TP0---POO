package juego;

import javax.swing.*;

public class PantallaInicio extends JFrame {

    public PantallaInicio() {

        setTitle("El Legado de la Sangre");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel fondo = new JLabel(
                new ImageIcon("imagenes/botonComenzar.png"));

        fondo.setLayout(null);

        JButton comenzar = new JButton();

        // BOTÓN INVISIBLE QUE OCUPA TODA LA PANTALLA
        comenzar.setBounds(0, 0, 1200, 700);

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
}