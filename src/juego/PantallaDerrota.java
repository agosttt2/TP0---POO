package juego;

import javax.swing.*;

public class PantallaDerrota extends JFrame {

    public PantallaDerrota() {

        setTitle("Derrota");
        setSize(1200,700);
        setLocationRelativeTo(null);

        JLabel fondo = new JLabel(
                new ImageIcon("imagenes/derrota.png"));

        setContentPane(fondo);

        setVisible(true);
    }
}