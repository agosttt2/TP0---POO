package juego;

import javax.swing.*;
import java.awt.*;

public class PantallaVictoria extends JFrame {

    public PantallaVictoria() {

        setTitle("Victoria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;

        JLabel fondo = new JLabel(
                new ImageIcon(
                        new ImageIcon("imagenes/victoria.png")
                                .getImage()
                                .getScaledInstance(sw, sh, Image.SCALE_SMOOTH)));
        fondo.setLayout(null);

        int btnW = 220;
        int btnH = 50;
        int btnY = (int)(sh * 0.80);
        int gap  = 40;
        int startX = (sw - btnW * 2 - gap) / 2;

        JButton btnMenu  = crearBoton("MENÚ PRINCIPAL", startX,           btnY, btnW, btnH);
        JButton btnJugar = crearBoton("VOLVER A JUGAR", startX + btnW + gap, btnY, btnW, btnH);

        btnMenu.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });

        btnJugar.addActionListener(e -> {
            new SeleccionPersonaje();
            dispose();
        });

        fondo.add(btnMenu);
        fondo.add(btnJugar);

        setContentPane(fondo);
        setVisible(true);
    }

    private JButton crearBoton(String texto, int x, int y, int w, int h) {
        JButton b = new JButton(texto);
        b.setBounds(x, y, w, h);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBackground(new Color(212, 175, 55));
        b.setForeground(new Color(30, 20, 5));
        b.setFocusPainted(false);
        return b;
    }
}