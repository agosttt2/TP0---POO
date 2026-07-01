package juego;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class PantallaCombate extends JFrame {

    private Personaje[] personajesObjeto = {
            new Mago(),
            new Caballero(),
            new Arquera(),
            new Curandera()
    };

    private int personajeActual = 0;
    private int turnosJefe = 0;
    private Jefe jefe = new Jefe();

    private JLabel jefeLabel;
    private JLabel lblVidaJefe;
    private JLabel[] personajeLabels = new JLabel[4];
    private JLabel[] panelNombre     = new JLabel[4];
    private JLabel[] panelVida       = new JLabel[4];

    private int anchoParty = 175;
    private int altoParty  = 175;
    private int tamanoJefe = 380;

    public PantallaCombate(String personajeInicial) {
        this(personajeInicial, false);
    }

    public PantallaCombate(String personajeInicial, boolean cargarPartida) {

        if (cargarPartida) {

            cargarPartida(); // Corrección menor sugerida por contexto: si es cargar, debería cargar en lugar de guardar
        } else {

            if (personajeInicial.equals("Mago"))      personajeActual = 0;
            if (personajeInicial.equals("Caballero")) personajeActual = 1;
            if (personajeInicial.equals("Arquera"))   personajeActual = 2;
            if (personajeInicial.equals("Curandera")) personajeActual = 3;
        }

        setTitle("EL LEGADO DE LA SANGRE - Combate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;

        JLabel fondo = new JLabel(
                escalarImagen("imagenes/fondoCombate.png", sw, sh));
        fondo.setLayout(null);

        int[] posX = {
            (int)(sw * 0.18),   
            (int)(sw * 0.11),   
            (int)(sw * 0.20),   
            (int)(sw * 0.04)    
        };
        int[] posY = {
            (int)(sh * 0.08),  
            (int)(sh * 0.28),   
            (int)(sh * 0.42),   
            (int)(sh * 0.55)    
        };

        for (int i = 0; i < personajesObjeto.length; i++) {
            personajeLabels[i] = new JLabel();
            personajeLabels[i].setBounds(posX[i], posY[i], anchoParty, altoParty);
            fondo.add(personajeLabels[i]);
        }

        jefeLabel = new JLabel();
        jefeLabel.setBounds((int)(sw * 0.62), (int)(sh * 0.08), tamanoJefe, tamanoJefe);
        fondo.add(jefeLabel);

        lblVidaJefe = new JLabel();
        lblVidaJefe.setForeground(Color.RED);
        lblVidaJefe.setFont(new Font("Arial", Font.BOLD, 22));
        lblVidaJefe.setBounds((int)(sw * 0.62), (int)(sh * 0.03), 300, 30);
        fondo.add(lblVidaJefe);

        JPanel barraInferior = crearBarraInferior(sw);
        barraInferior.setBounds(0, sh - 140, sw, 140);
        fondo.add(barraInferior);

        setContentPane(fondo);
        actualizarPantalla();
        setVisible(true);
    }

    private JPanel crearBarraInferior(int sw) {

        JPanel barra = new JPanel(null);
        barra.setBackground(new Color(0, 0, 0, 180));
        barra.setOpaque(true);
        barra.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55), 3));

        for (int i = 0; i < personajesObjeto.length; i++) {

            JPanel tarjeta = new JPanel(null);
            tarjeta.setOpaque(false);
            tarjeta.setBounds(20 + i * 250, 10, 240, 120);

            panelNombre[i] = new JLabel();
            panelNombre[i].setForeground(new Color(212, 175, 55));
            panelNombre[i].setFont(new Font("Arial", Font.BOLD, 13));
            panelNombre[i].setBounds(0, 0, 240, 20);

            panelVida[i] = new JLabel();
            panelVida[i].setForeground(Color.WHITE);
            panelVida[i].setFont(new Font("Arial", Font.PLAIN, 11));
            panelVida[i].setBounds(0, 22, 240, 95);
            panelVida[i].setVerticalAlignment(SwingConstants.TOP);

            tarjeta.add(panelNombre[i]);
            tarjeta.add(panelVida[i]);
            barra.add(tarjeta);
        }

        JButton atacar   = crearBoton("ATACAR",    sw - 350, 20, 150, 40);
        JButton defender = crearBoton("DEFENDER",  sw - 190, 20, 150, 40);
        JButton habil    = crearBoton("HABILIDAD", sw - 350, 70, 150, 40);
        JButton salir    = crearBoton("SALIR",     sw - 190, 70, 150, 40);
        JButton guardar  = crearBoton("GUARDAR",   sw - 510, 20, 150, 40);
        
        atacar.addActionListener(e -> {
            Personaje p = personajesObjeto[personajeActual];

            personajeLabels[personajeActual].setIcon(
                    escalarImagen("imagenes/" + p.getNombre().toLowerCase() + "_ataque.png",
                            anchoParty, altoParty));

            int dmg = p.calcularDanoFinal();
            jefe.recibirDanio(dmg);

            JOptionPane.showMessageDialog(
                    this, p.getNombre() + " hizo " + dmg + " de daño al Jefe!");

            p.estado = "VIVO";

            Timer t = new Timer(800, ev -> finalizarTurno());
            t.setRepeats(false);
            t.start();
        });

        defender.addActionListener(e -> {
            Personaje p = personajesObjeto[personajeActual];

            personajeLabels[personajeActual].setIcon(
                    escalarImagen("imagenes/" + p.getNombre().toLowerCase() + "_bloquear.png",
                            anchoParty, altoParty));

            p.estado = "DEFENDIENDO";

            JOptionPane.showMessageDialog(
                    this, p.getNombre() + " está defendiendo!");

            Timer t = new Timer(800, ev -> finalizarTurno());
            t.setRepeats(false);
            t.start();
        });

        habil.addActionListener(e -> {
            Personaje p = personajesObjeto[personajeActual];
            String[] habs = p.getNombresHabilidades();
           
            String elegida = (String) JOptionPane.showInputDialog(
                    this,
                    "Selecciona una habilidad:",
                    "Habilidades de " + p.getNombre(),
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    habs,
                    habs[0]);

            if (elegida != null) {
                int idx = 0;
                for (int i = 0; i < habs.length; i++) {
                    if (habs[i].equals(elegida)) {
                        idx = i;
                    }
                }

                personajeLabels[personajeActual].setIcon(
                        escalarImagen(p.getSpriteHabilidad(idx), anchoParty, altoParty));

                int valor = p.ejecutarHabilidad(idx);

                if (p.habilidadEsCuracion(idx)) {
                    curarAliadoAleatorio(valor);
                } else {
                    jefe.recibirDanio(valor);
                    JOptionPane.showMessageDialog(
                            this, p.getNombre() + " usó " + elegida +
                            " e hizo " + valor + " de daño!");
                }

                p.estado = "VIVO";

                Timer t = new Timer(1000, ev -> finalizarTurno());
                t.setRepeats(false);
                t.start();
            }
        });

        salir.addActionListener(e -> System.exit(0));
        
        guardar.addActionListener(e -> {
            guardarPartida();
            JOptionPane.showMessageDialog(this, "Partida guardada correctamente");
        });
        
        barra.add(atacar);
        barra.add(defender);
        barra.add(habil);
        barra.add(salir);
        barra.add(guardar); // ¡Corregido!
        return barra;
    }
   
    private void finalizarTurno() {

        if (!jefe.estaVivo()) {

            jefeLabel.setIcon(escalarImagen("imagenes/jefe_derrota.png", tamanoJefe, tamanoJefe));

            for (int i = 0; i < personajesObjeto.length; i++) {
                if (personajesObjeto[i].estaVivo()) {
                    personajesObjeto[i].ganarExperiencia(100);
                    personajeLabels[i].setIcon(escalarImagen(
                            "imagenes/" + personajesObjeto[i].getNombre().toLowerCase() + "_victoria.png",
                            anchoParty, altoParty));
                }
            }

            JOptionPane.showMessageDialog(this, "¡Victoria!");
            new PantallaVictoria();
            dispose();
            return;
        }

        turnoJefe();

        if (!verificarDerrota()) {
            siguientePersonaje();
            actualizarPantalla();
        }
    }

    private boolean verificarDerrota() {

        for (int i = 0; i < personajesObjeto.length; i++) {
            if (personajesObjeto[i].estaVivo()) {
                return false;
            }
        }

        JOptionPane.showMessageDialog(this, "¡DERROTA!");
        new PantallaDerrota();
        dispose();
        return true;
    }

    private void turnoJefe() {

        turnosJefe++;

        int turnosParaCurar = 3 + (int)(Math.random() * 3);

        if (turnosJefe >= turnosParaCurar) {

            jefeLabel.setIcon(escalarImagen("imagenes/jefe_curacion.png", tamanoJefe, tamanoJefe));

            int heal = 50;
            jefe.curar(heal);

            JOptionPane.showMessageDialog(
                    this, "¡El Jefe se regenera " + heal + " HP!");

            turnosJefe = 0;

        } else {

            jefeLabel.setIcon(escalarImagen("imagenes/jefe_ataque.png", tamanoJefe, tamanoJefe));

         
            int vivosCount = 0;
            for (int i = 0; i < personajesObjeto.length; i++) {
                if (personajesObjeto[i].estaVivo()) {
                    vivosCount++;
                }
            }

            if (vivosCount > 0) {

                int objetivo = (int)(Math.random() * vivosCount);
                int contador = 0;

                for (int i = 0; i < personajesObjeto.length; i++) {

                    if (personajesObjeto[i].estaVivo()) {

                        if (contador == objetivo) {

                            Personaje obj = personajesObjeto[i];

                            int danio;
                            if (obj.estado.equals("DEFENDIENDO")) {
                                danio = Math.max(5, jefe.calcularDanoFinal() - obj.getDefensa());
                            } else {
                                danio = jefe.calcularDanoFinal();
                            }

                            obj.recibirDanio(danio);

                            JOptionPane.showMessageDialog(
                                    this,
                                    "El Jefe ataca a " + obj.getNombre() +
                                    " por " + danio + " de daño.");

                            obj.estado = "VIVO";
                            break;
                        }

                        contador++;
                    }
                }
            }
        }
    }

    private void curarAliadoAleatorio(int cantidad) {

        int vivosCount = 0;
        for (int i = 0; i < personajesObjeto.length; i++) {
            if (personajesObjeto[i].estaVivo()) {
                vivosCount++;
            }
        }

        if (vivosCount == 0) {
            return;
        }

        int objetivo = (int)(Math.random() * vivosCount);
        int contador = 0;

        for (int i = 0; i < personajesObjeto.length; i++) {

            if (personajesObjeto[i].estaVivo()) {

                if (contador == objetivo) {
                    personajesObjeto[i].curar(cantidad);
                    JOptionPane.showMessageDialog(
                            this,
                            "La Curandera curó a " + personajesObjeto[i].getNombre() +
                            " por " + cantidad + " HP!");
                    break;
                }

                contador++;
            }
        }
    }

    private void siguientePersonaje() {

        do {
            personajeActual = (personajeActual + 1) % personajesObjeto.length;
        } while (!personajesObjeto[personajeActual].estaVivo());
    }

    private void actualizarPantalla() {

        for (int i = 0; i < personajesObjeto.length; i++) {

            Personaje p = personajesObjeto[i];
            String estadoStr = p.estaVivo() ? p.estado.toUpperCase() : "DERROTADO";
            String manaTexto = "";
            if (p instanceof Mago) {
                Mago mago = (Mago) p;
                manaTexto = "Mana: " + mago.getMana() + "<br>";
            }

            if (i == personajeActual) {
                panelNombre[i].setText("▶ " + p.getNombre());
            } else {
                panelNombre[i].setText(p.getNombre());
            }

            panelVida[i].setText(
                    "<html>" +
                    "HP: "     + p.getVida()        + "/" + p.getVidaMaxima()          + "<br>" +
                    "ATQ: "    + p.calcularDanoFinal() + "  DEF: " + p.getDefensa()    + "<br>" +
                    "VEL: "    + p.getVelocidad()    + "  Nv: " + p.getNivel()         + "<br>" +
                    manaTexto +
                    "Exp:"    + p.getExperiencia()  + "/" + p.getExperienciaNecesaria() + "<br>" +
                    "ESTADO: " + estadoStr           +
                    "</html>");

            if (p.estaVivo()) {
                personajeLabels[i].setIcon(escalarImagen(
                        "imagenes/" + p.getNombre().toLowerCase() + "_idle.png",
                        anchoParty, altoParty));
            } else {
                personajeLabels[i].setIcon(escalarImagen(
                        "imagenes/" + p.getNombre().toLowerCase() + "_derrota.png",
                        anchoParty, altoParty));
            }
        }

        if (jefe.estaVivo()) {
            jefeLabel.setIcon(escalarImagen("imagenes/jefe_idle.png", tamanoJefe, tamanoJefe));
        }

        lblVidaJefe.setText("HP Jefe: " + jefe.getVida() + "/" + jefe.getVidaMaxima());
    }

    private JButton crearBoton(String texto, int x, int y, int w, int h) {
        JButton b = new JButton(texto);
        b.setBounds(x, y, w, h);
        b.setBackground(new Color(212, 175, 55));
        return b;
    }
    
    private ImageIcon escalarImagen(String ruta, int w, int h) {
        return new ImageIcon(
                new ImageIcon(ruta).getImage()
                        .getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    private void guardarPartida() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("partida_guardada.txt"))) {

            pw.println(personajeActual);
            pw.println(turnosJefe);
            pw.println(jefe.getVida());

            for (Personaje p : personajesObjeto) {
                pw.println(p.getNombre() + ";" + p.getVida() + ";" + p.estado);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la partida");
        }
    }

    private void cargarPartida() {
        try {
            Scanner lector = new Scanner(new File("partida_guardada.txt"));

            personajeActual = Integer.parseInt(lector.nextLine());
            turnosJefe = Integer.parseInt(lector.nextLine());

            int vidaJefeGuardada = Integer.parseInt(lector.nextLine());
            jefe.vida = vidaJefeGuardada;

            for (int i = 0; i < personajesObjeto.length; i++) {
                String linea = lector.nextLine();
                String[] partes = linea.split(";");

                personajesObjeto[i].vida = Integer.parseInt(partes[1]);
                personajesObjeto[i].estado = partes[2];
            }

            lector.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar la partida");
        }
    }
} 