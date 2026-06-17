package juego;

import javax.swing.*;

public class PantallaVictoria extends JFrame {

    public PantallaVictoria() {

        setTitle("Victoria");
        setSize(1200,700);
        setLocationRelativeTo(null);

        JLabel fondo = new JLabel(
                new ImageIcon("imagenes/victoria.png"));

        setContentPane(fondo);

        setVisible(true);
    }
}