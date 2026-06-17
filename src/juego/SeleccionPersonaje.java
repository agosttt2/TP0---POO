package juego;

import javax.swing.*;
import java.awt.*;

public class SeleccionPersonaje extends JFrame {

    private String[] personajes = {
            "Caballero",
            "Arquera",
            "Mago"
    };

    private String[] imagenes = {
            "imagenes/caballero_idle.png",
            "imagenes/arquera_idle.png",
            "imagenes/mago_idle.png"
    };

    private int indice = 0;

    private JLabel imagenPersonaje;
    private JLabel nombrePersonaje;

    public SeleccionPersonaje() {

        setTitle("Seleccion de Personaje");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel fondo = new JLabel(
                new ImageIcon("imagenes/fondoCombate.png"));

        fondo.setLayout(null);

        JLabel titulo = new JLabel("ELIGE TU PERSONAJE");
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(350, 30, 600, 50);

        JButton izquierda = new JButton("<");
        izquierda.setBounds(300, 280, 80, 80);

        JButton derecha = new JButton(">");
        derecha.setBounds(820, 280, 80, 80);

        imagenPersonaje = new JLabel();
        imagenPersonaje.setBounds(450, 150, 300, 300);

        nombrePersonaje = new JLabel("", SwingConstants.CENTER);
        nombrePersonaje.setFont(new Font("Arial", Font.BOLD, 30));
        nombrePersonaje.setForeground(Color.WHITE);
        nombrePersonaje.setBounds(400, 430, 400, 40);

        JButton tienda = new JButton("TIENDA");
        tienda.setBounds(400, 520, 180, 50);

        JButton seleccionar = new JButton("SELECCIONAR");
        seleccionar.setBounds(620, 520, 220, 50);

        actualizarPersonaje();

        izquierda.addActionListener(e -> {
            indice--;
            if (indice < 0)
                indice = personajes.length - 1;
            actualizarPersonaje();
        });

        derecha.addActionListener(e -> {
            indice++;
            if (indice >= personajes.length)
                indice = 0;
            actualizarPersonaje();
        });

        seleccionar.addActionListener(e -> {

            new PantallaCombate(personajes[indice]);

            dispose();
        });
        
        tienda.addActionListener(e -> {

            new PantallaTienda();

        });

        fondo.add(titulo);
        fondo.add(izquierda);
        fondo.add(derecha);
        fondo.add(imagenPersonaje);
        fondo.add(nombrePersonaje);
        fondo.add(tienda);
        fondo.add(seleccionar);

        setContentPane(fondo);

        setVisible(true);
    }

    private void actualizarPersonaje() {

        ImageIcon icono = new ImageIcon(imagenes[indice]);

        Image imagenEscalada =
                icono.getImage().getScaledInstance(
                        250,
                        250,
                        Image.SCALE_SMOOTH);

        imagenPersonaje.setIcon(
                new ImageIcon(imagenEscalada));

        nombrePersonaje.setText(personajes[indice]);
    }
}