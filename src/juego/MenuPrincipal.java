package juego;

import javax.swing.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        setTitle("El Legado de la Sangre");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel fondo = new JLabel(
                new ImageIcon("imagenes/menuPrincipal.png"));

        fondo.setLayout(null);

        JButton nuevaPartida = new JButton();
        nuevaPartida.setBounds(500, 430, 280, 50);

        JButton cargarPartida = new JButton();
        cargarPartida.setBounds(500, 490, 280, 50);

        JButton salir = new JButton();
        salir.setBounds(500, 550, 280, 50);

        nuevaPartida.setOpaque(false);
        nuevaPartida.setContentAreaFilled(false);
        nuevaPartida.setBorderPainted(false);

        cargarPartida.setOpaque(false);
        cargarPartida.setContentAreaFilled(false);
        cargarPartida.setBorderPainted(false);

        salir.setOpaque(false);
        salir.setContentAreaFilled(false);
        salir.setBorderPainted(false);

       
        nuevaPartida.addActionListener(e -> {

            new SeleccionPersonaje();

            dispose();

        });

        cargarPartida.addActionListener(e -> {

            new PantallaCombate(null);

            dispose();

        });

        // SALIR
        salir.addActionListener(e -> {

            System.exit(0);

        });

        fondo.add(nuevaPartida);
        fondo.add(cargarPartida);
        fondo.add(salir);

        setContentPane(fondo);

        setVisible(true);
    }
}