package juego;

import javax.swing.*;
import java.awt.Image;

public class PantallaTienda extends JFrame {

    public PantallaTienda() {

        setTitle("Tienda");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icono = new ImageIcon("imagenes/tienda.png");

        Image imagenEscalada =
                icono.getImage().getScaledInstance(
                        1000,
                        600,
                        Image.SCALE_SMOOTH);

        JLabel fondo = new JLabel(
                new ImageIcon(imagenEscalada));

        fondo.setLayout(null);

        JButton volver = new JButton("VOLVER");
        volver.setBounds(800, 540, 180, 40);

        volver.addActionListener(e -> {
            dispose();
        });

        fondo.add(volver);

        setContentPane(fondo);

        setVisible(true);
    }
}