package juego;

import javax.swing.*;
import java.awt.*;

public class PantallaCombate extends JFrame {

    private Personaje[] personajesObjeto = {
            new Caballero(),
            new Arquera(),
            new Mago()
    };
    private int personajeActual = 0;

    private int vidaJefe = 500;

    private JLabel lblVidaJugador;
    private JLabel lblVidaJefe;
    private JLabel lblTurno;

    private JLabel personajeLabel;
    private JLabel jefeLabel;
    private JLabel curanderaLabel;

    public PantallaCombate(String personajeInicial) {

        if (personajeInicial.equals("Caballero"))
            personajeActual = 0;

        if (personajeInicial.equals("Arquera"))
            personajeActual = 1;

        if (personajeInicial.equals("Mago"))
            personajeActual = 2;

        setTitle("Combate");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel fondo = new JLabel(
                new ImageIcon("imagenes/fondoCombate.png"));

        fondo.setLayout(null);

        personajeLabel = new JLabel();
        personajeLabel.setBounds(120, 220, 300, 300);

        ImageIcon iconoJefe =
                new ImageIcon("imagenes/jefe_idle.png");

        Image imagenJefe =
                iconoJefe.getImage().getScaledInstance(
                        300,
                        300,
                        Image.SCALE_SMOOTH);

        jefeLabel = new JLabel(
                new ImageIcon(imagenJefe));

        jefeLabel.setBounds(
                800,
                180,
                300,
                300);

        curanderaLabel = new JLabel(
                new ImageIcon("imagenes/curandera_idle.png"));
        curanderaLabel.setBounds(500, 180, 250, 250);

        lblTurno = new JLabel();
        lblTurno.setForeground(Color.WHITE);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 24));
        lblTurno.setBounds(470, 20, 300, 40);

        lblVidaJugador = new JLabel();
        lblVidaJugador.setForeground(Color.WHITE);
        lblVidaJugador.setFont(new Font("Arial", Font.BOLD, 22));
        lblVidaJugador.setBounds(100, 80, 350, 40);

        lblVidaJefe = new JLabel(
                "Jefe Vida: " + vidaJefe);
        lblVidaJefe.setForeground(Color.WHITE);
        lblVidaJefe.setFont(new Font("Arial", Font.BOLD, 22));
        lblVidaJefe.setBounds(800, 80, 300, 40);

        JButton atacar = new JButton("ATACAR");
        atacar.setBounds(380, 560, 150, 40);

        JButton defender = new JButton("DEFENDER");
        defender.setBounds(550, 560, 150, 40);

        JButton habilidad = new JButton("HABILIDAD");
        habilidad.setBounds(720, 560, 150, 40);
        
        JButton tienda = new JButton("TIENDA");
        tienda.setBounds(50, 560, 120, 40);
        tienda.addActionListener(e -> {

            new PantallaTienda();

        });

        atacar.addActionListener(e -> {

        	vidaJefe -= personajesObjeto[personajeActual].getAtaque();

        	actualizarPantalla();

        	if (vidaJefe <= 0) {

        	    new PantallaVictoria();
        	    dispose();
        	    return;
        	}

        	turnoJefe();

        	if (!personajesObjeto[personajeActual].estaVivo()) {

        	    JOptionPane.showMessageDialog(
        	            this,
        	            personajesObjeto[personajeActual].getNombre()
        	                    + " ha sido derrotado");
        	}

        	turnoCurandera();

        	siguientePersonaje();

        	actualizarPantalla();

            actualizarPantalla();
        });

        habilidad.addActionListener(e -> {

        	vidaJefe -= personajesObjeto[personajeActual].usarHabilidad();

        	actualizarPantalla();

        	if (vidaJefe <= 0) {

        	    new PantallaVictoria();
        	    dispose();
        	    return;
        	}

        	turnoJefe();

        	if (!personajesObjeto[personajeActual].estaVivo()) {

        	    JOptionPane.showMessageDialog(
        	            this,
        	            personajesObjeto[personajeActual].getNombre()
        	                    + " ha sido derrotado");
        	}

        	turnoCurandera();

        	siguientePersonaje();

        	actualizarPantalla();

            actualizarPantalla();
        });

        defender.addActionListener(e -> {

            turnoJefe();
            turnoCurandera();
            siguientePersonaje();

            actualizarPantalla();
        });

        fondo.add(personajeLabel);
        fondo.add(jefeLabel);
        fondo.add(curanderaLabel);

        fondo.add(lblTurno);
        fondo.add(lblVidaJugador);
        fondo.add(lblVidaJefe);

        fondo.add(atacar);
        fondo.add(defender);
        fondo.add(habilidad);
        fondo.add(tienda);

        setContentPane(fondo);

        actualizarPantalla();

        setVisible(true);
    }

    private void actualizarPantalla() {

    	lblTurno.setText(
    	        "Turno: " + personajesObjeto[personajeActual].getNombre());

    	lblVidaJugador.setText(
    	        personajesObjeto[personajeActual].getNombre()
    	                + " Vida: "
    	                + personajesObjeto[personajeActual].getVida());

        lblVidaJefe.setText(
                "Jefe Vida: " + vidaJefe);

        String sprite = "";

        if (personajeActual == 0) {

        	if (personajesObjeto[0].estaVivo())
                sprite = "imagenes/caballero_idle.png";
            else
                sprite = "imagenes/caballero_derrota.png";
        }

        if (personajeActual == 1) {

        	if (personajesObjeto[1].estaVivo())
                sprite = "imagenes/arquera_idle.png";
            else
                sprite = "imagenes/arquera_derrota.png";
        }

        if (personajeActual == 2) {

        	if (personajesObjeto[2].estaVivo())
                sprite = "imagenes/mago_idle.png";
            else
                sprite = "imagenes/mago_derrota.png";
        }

        ImageIcon icono = new ImageIcon(sprite);

        Image imagenEscalada =
                icono.getImage().getScaledInstance(
                        250,
                        250,
                        Image.SCALE_SMOOTH);

        personajeLabel.setIcon(
                new ImageIcon(imagenEscalada));

        verificarDerrota();
    }

    private void turnoJefe() {

        JOptionPane.showMessageDialog(
                this,
                "Turno del Jefe\nAtaque: -20 HP");

        personajesObjeto[personajeActual].recibirDanio(20);
        actualizarPantalla();
    }

    private void turnoCurandera() {

        curanderaLabel.setVisible(true);

        curanderaLabel.setIcon(
                new ImageIcon(
                        "imagenes/curandera_curacion.png"));

        JOptionPane.showMessageDialog(
                this,
                "Turno de la Curandera\nCuración: +25 HP");

        personajesObjeto[personajeActual].curar(25);


        Timer timer = new Timer(1000, e -> {

            curanderaLabel.setIcon(
                    new ImageIcon(
                            "imagenes/curandera_idle.png"));
        });

        timer.setRepeats(false);
        timer.start();
    }

    private void siguientePersonaje() {

        int intentos = 0;

        do {

            personajeActual++;

            if (personajeActual >= personajesObjeto.length)
                personajeActual = 0;

            intentos++;

        } while (!personajesObjeto[personajeActual].estaVivo()
                && intentos < 3);
    }

    private void verificarDerrota() {

    	if (!personajesObjeto[0].estaVivo() &&
    		    !personajesObjeto[1].estaVivo() &&
    		    !personajesObjeto[2].estaVivo()) {
        }
    }
}