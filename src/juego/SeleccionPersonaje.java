package juego;

import javax.swing.*;
import java.awt.*;

public class SeleccionPersonaje extends JFrame {

    private String[] personajes = {"Caballero", "Arquera", "Mago"};
    private String[] imagenes = {
            "imagenes/caballero_idle.png",
            "imagenes/arquera_idle.png",
            "imagenes/mago_idle.png"
    };
    private int indice = 0;
    private JLabel imagenPersonaje;
    private JLabel nombrePersonaje;
    
    private double multiplicador = 3.5; 
    private int imgSize = (int)(250 * multiplicador); 

    public SeleccionPersonaje() {

        setTitle("Seleccion de Personaje");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = screenSize.width;
        int screenH = screenSize.height;

        JLabel fondo = new JLabel(escalarImagen("imagenes/fondoCombate.png", screenW, screenH));
        fondo.setLayout(null);
        fondo.setBounds(0, 0, screenW, screenH);

        int offsetX = 250; 
        int offsetY = -160;

        int textOffsetX = 0; 
        int textOffsetY = -150; 

        JLabel titulo = new JLabel("ELIGE TU PERSONAJE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, (int)(screenH * 0.05)));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, (int)(screenH * 0.02), screenW, (int)(screenH * 0.08));

        int imgX = (screenW - imgSize) / 2;
        int imgY = (screenH - imgSize) / 2 - 50; 

        imagenPersonaje = new JLabel("", SwingConstants.CENTER);
        imagenPersonaje.setBounds(imgX + offsetX, imgY + offsetY, imgSize, imgSize);

        int btnSize = (int)(screenH * 0.08);
        JButton izquierda = crearBotonEstilo("<");
        izquierda.setBounds(imgX - btnSize - 40, imgY + (imgSize / 2) - (btnSize / 2), btnSize, btnSize);

        JButton derecha = crearBotonEstilo(">");
        derecha.setBounds(imgX + imgSize + 40, imgY + (imgSize / 2) - (btnSize / 2), btnSize, btnSize);

        nombrePersonaje = new JLabel("", SwingConstants.CENTER);
        nombrePersonaje.setFont(new Font("Arial", Font.BOLD, (int)(screenH * 0.04)));
        nombrePersonaje.setForeground(new Color(212, 175, 55));
        nombrePersonaje.setBounds(imgX + textOffsetX, imgY + imgSize + 10 + textOffsetY, imgSize, (int)(screenH * 0.06));

        int btnW = (int)(screenW * 0.12);
        int btnH = (int)(screenH * 0.06);
        int gap = 30;
        int btnStartY = (int)(screenH * 0.85);
        int startX = (screenW - ((btnW * 2) + gap)) / 2;

        JButton tienda = crearBotonEstilo("TIENDA");
        tienda.setBounds(startX, btnStartY, btnW, btnH);
        JButton seleccionar = crearBotonEstilo("SELECCIONAR");
        seleccionar.setBounds(startX + btnW + gap, btnStartY, btnW, btnH);

        actualizarPersonaje();

        izquierda.addActionListener(e -> { indice = (indice - 1 + personajes.length) % personajes.length; actualizarPersonaje(); });
        derecha.addActionListener(e -> { indice = (indice + 1) % personajes.length; actualizarPersonaje(); });
        seleccionar.addActionListener(e -> { new PantallaCombate(personajes[indice]); dispose(); });
        tienda.addActionListener(e -> new PantallaTienda());

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

    private JButton crearBotonEstilo(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setForeground(new Color(40, 30, 10)); 
        boton.setBackground(new Color(212, 175, 55)); 
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(140, 110, 30), 2));
        return boton;
    }

    private void actualizarPersonaje() {
        imagenPersonaje.setIcon(escalarImagen(imagenes[indice], imgSize, imgSize));
        nombrePersonaje.setText(personajes[indice]);
    }

    private ImageIcon escalarImagen(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}