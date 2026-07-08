package juego;

import javax.swing.*;
import java.io.File;

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

        // NuevaPartida 
        nuevaPartida.addActionListener(e -> {

            new SeleccionPersonaje();

            dispose();

        });

        // CargarPartida
        cargarPartida.addActionListener(e -> {

            File archivoGuardado = new File("partida_guardada.txt");
            
            if (archivoGuardado.exists()) {
                
                new PantallaCombate("", true); 
                
                dispose(); 
                
            } else {

                JOptionPane.showMessageDialog(this, "No hay ninguna partida guardada todavía.");
            }

        });

        // Salir
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