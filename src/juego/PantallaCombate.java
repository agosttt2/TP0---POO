package juego;

import juego.combate.OrquestadorCombate;
import juego.combate.ResultadoAtaque;
import juego.combate.ResultadoFinTurno;
import juego.combate.ResultadoHabilidad;
import juego.combate.ResultadoTurnoJefe;
import juego.item.Tienda;
import juego.modelo.Personaje;

import javax.swing.*;
import java.awt.*;

public class PantallaCombate extends JFrame {

    private OrquestadorCombate gestor;

    private JLabel   jefeLabel;
    private JLabel   lblVidaJefe;
    private JLabel[] personajeLabels;
    private JLabel[] panelNombre;
    private JLabel[] panelVida;

    private JButton btnAtacar;
    private JButton btnDefender;
    private JButton btnHabilidad;
    private JButton btnObjetos;
    private JButton btnGuardar;

    private final int anchoParty = 320;
    private final int altoParty  = 320;
    private final int tamanoJefe = 530;

    public PantallaCombate(String personajeInicial) {
        gestor = new OrquestadorCombate();
        iniciarUI();
    }

    public PantallaCombate(String personajeInicial, boolean cargar) {
        gestor = new OrquestadorCombate();
        if (cargar) gestor.cargar();
        iniciarUI();
    }

    private void iniciarUI() {

        int cantidad = gestor.getParty().length;
        personajeLabels = new JLabel[cantidad];
        panelNombre     = new JLabel[cantidad];
        panelVida       = new JLabel[cantidad];

        setTitle("EL LEGADO DE LA SANGRE - Combate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;

        JLabel fondo = new JLabel(escalar("imagenes/fondoCombate.png", sw, sh));
        fondo.setLayout(null);

        int[] posX = { (int)(sw*0.28), (int)(sw*0.27), (int)(sw*0.27), (int)(sw*0.27) };
        int[] posY = { (int)(sh*0.11), (int)(sh*0.20), (int)(sh*0.30), (int)(sh*0.45) };

        for (int i = 0; i < cantidad; i++) {
            personajeLabels[i] = new JLabel();
            personajeLabels[i].setBounds(posX[i], posY[i], anchoParty, altoParty);
            fondo.add(personajeLabels[i]);
        }

        jefeLabel = new JLabel();
        jefeLabel.setBounds((int)(sw*0.62), (int)(sh*0.08), tamanoJefe, tamanoJefe);
        fondo.add(jefeLabel);

        lblVidaJefe = new JLabel();
        lblVidaJefe.setForeground(Color.RED);
        lblVidaJefe.setFont(new Font("Arial", Font.BOLD, 22));
        lblVidaJefe.setBounds((int)(sw*0.62), (int)(sh*0.03), 300, 30);
        fondo.add(lblVidaJefe);

        JPanel barra = crearBarra(sw);
        barra.setBounds(0, sh - 140, sw, 140);
        fondo.add(barra);

        setContentPane(fondo);
        actualizarPantalla();
        setVisible(true);
    }

    private JPanel crearBarra(int sw) {

        JPanel barra = new JPanel(null);
        barra.setBackground(new Color(0, 0, 0, 180));
        barra.setOpaque(true);
        barra.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55), 3));

        int cantidad = gestor.getParty().length;

        for (int i = 0; i < cantidad; i++) {
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

        btnAtacar    = boton("ATACAR",    sw - 510, 20, 150, 40);
        btnDefender  = boton("DEFENDER",  sw - 350, 20, 150, 40);
        btnHabilidad = boton("HABILIDAD", sw - 190, 20, 150, 40);
        btnObjetos   = boton("OBJETOS",   sw - 510, 70, 150, 40);
        btnGuardar   = boton("GUARDAR",   sw - 350, 70, 150, 40);
        JButton btnSalir = boton("SALIR", sw - 190, 70, 150, 40);

        btnAtacar.addActionListener(e -> onAtacar());
        btnDefender.addActionListener(e -> onDefender());
        btnHabilidad.addActionListener(e -> onHabilidad());
        btnObjetos.addActionListener(e -> onObjetos());
        btnSalir.addActionListener(e -> System.exit(0));
        btnGuardar.addActionListener(e -> {
            gestor.guardar();
            JOptionPane.showMessageDialog(this, "Partida guardada.");
        });

        barra.add(btnAtacar);
        barra.add(btnDefender);
        barra.add(btnHabilidad);
        barra.add(btnObjetos);
        barra.add(btnGuardar);
        barra.add(btnSalir);
        return barra;
    }

    private void onAtacar() {
        ResultadoAtaque r = gestor.atacar();
        personajeLabels[gestor.getIndiceActual()].setIcon(
                escalar("imagenes/" + gestor.getActual().getNombre().toLowerCase() + "_ataque.png", anchoParty, altoParty));
        JOptionPane.showMessageDialog(this, r.nombreAtacante + " hizo " + r.danio + " de daño al Jefe!");
        procesarFinTurno();
    }

    private void onDefender() {
        int idx = gestor.getIndiceActual();
        Personaje p = gestor.getActual();
        String nombre = p.getNombre();
        personajeLabels[idx].setIcon(
                escalar("imagenes/" + nombre.toLowerCase() + "_bloquear.png", anchoParty, altoParty));
        JOptionPane.showMessageDialog(this, nombre + " está defendiendo!");
        ResultadoFinTurno r = gestor.defender();
        procesarResultado(r);
    }

    private void onHabilidad() {
        Personaje p     = gestor.getActual();
        int idx         = gestor.getIndiceActual();
        String[] habs   = p.getNombresHabilidades();

        String elegida = (String) JOptionPane.showInputDialog(
                this, "Selecciona una habilidad:", "Habilidades de " + p.getNombre(),
                JOptionPane.QUESTION_MESSAGE, null, habs, habs[0]);

        if (elegida == null) return;

        int habIdx = 0;
        for (int i = 0; i < habs.length; i++) {
            if (habs[i].equals(elegida)) habIdx = i;
        }

        ResultadoHabilidad r = gestor.usarHabilidad(habIdx);

        if (r.manaInsuficiente) {
            JOptionPane.showMessageDialog(this, "No tenés suficiente maná.");
            return;
        }

        personajeLabels[idx].setIcon(escalar(r.sprite, anchoParty, altoParty));

        if (r.esCuracion) {
            String curado = r.indiceObjetivo >= 0
                    ? gestor.getParty()[r.indiceObjetivo].getNombre()
                    : "nadie";
            JOptionPane.showMessageDialog(this,
                    p.getNombre() + " curó a " + curado + " por " + r.valor + " HP!");
        } else {
            JOptionPane.showMessageDialog(this,
                    p.getNombre() + " usó " + r.nombreHabilidad + " e hizo " + r.valor + " de daño!");
        }

        procesarFinTurno();
    }

    private void onObjetos() {
        if (!gestor.tieneConsumibles()) {
            JOptionPane.showMessageDialog(this, "No tenés objetos para usar.");
            return;
        }

        String[] nombres = gestor.getNombresConsumibles();

        String elegido = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona un objeto:",
                "Objetos",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombres,
                nombres[0]);

        if (elegido == null) return;

        int idx = 0;
        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i].equals(elegido)) idx = i;
        }

        Personaje p = gestor.getActual();
        ResultadoFinTurno r = gestor.usarObjeto(idx);
        JOptionPane.showMessageDialog(this, p.getNombre() + " usó " + elegido + "!");
        procesarResultado(r);
    }

    private void procesarFinTurno() {
        ResultadoFinTurno r = gestor.finalizarTurno();
        procesarResultado(r);
    }

    private void procesarResultado(ResultadoFinTurno r) {

        if (r.estado == ResultadoFinTurno.Estado.VICTORIA) {
            jefeLabel.setIcon(escalar("imagenes/jefe_derrota.png", tamanoJefe, tamanoJefe));
            if (r.subioNivel) {
                String msg = "¡Personajes subieron de nivel!\n";
                for (Personaje p : gestor.getParty()) {
                    if (p.estaVivo()) msg += p.getNombre() + " -> Nivel " + p.getNivel() + "\n";
                }
                JOptionPane.showMessageDialog(this, msg);
            }
            JOptionPane.showMessageDialog(this,
                    "¡VICTORIA!\nOro ganado: " + r.oroGanado + "\nOro total: " + Tienda.getOro());
            new PantallaVictoria();
            dispose();
            return;
        }

        if (r.estado == ResultadoFinTurno.Estado.DERROTA) {
            mostrarTurnoJefe(r.turnoJefe);
            actualizarPantalla();
            JOptionPane.showMessageDialog(this, "¡DERROTA!");
            new PantallaDerrota();
            dispose();
            return;
        }

        mostrarTurnoJefe(r.turnoJefe);
        actualizarPantalla();
    }

    private void mostrarTurnoJefe(ResultadoTurnoJefe r) {
        if (r == null) return;
        if (r.curo) {
            jefeLabel.setIcon(escalar("imagenes/jefe_curacion.png", tamanoJefe, tamanoJefe));
            JOptionPane.showMessageDialog(this, "El Jefe se regenera " + r.heal + " HP!");
        } else if (r.indiceObjetivo >= 0) {
            jefeLabel.setIcon(escalar("imagenes/jefe_ataque.png", tamanoJefe, tamanoJefe));
            JOptionPane.showMessageDialog(this,
                    "El Jefe ataca a " + r.nombreObjetivo + " por " + r.danio + " de daño.");
        }
    }

    private void actualizarPantalla() {
        Personaje[] party   = gestor.getParty();
        int         actual  = gestor.getIndiceActual();

        for (int i = 0; i < party.length; i++) {
            Personaje p     = party[i];
            String estadoStr = p.estaVivo() ? "VIVO" : "DERROTADO";

            panelNombre[i].setText((i == actual ? "▶ " : "") + p.getNombre());

            panelVida[i].setText(
                    "<html>" +
                    "HP: "    + p.getVida()          + "/" + p.getVidaMaxima()    + "<br>" +
                    "ATQ: "   + p.calcularDanoFinal() + "  DEF: " + p.getDefensa() + "<br>" +
                    "VEL: "   + p.getVelocidad()      + "<br>" +
                    gestor.getManaTexto(i)             + "<br>" +
                    "Nivel: " + p.getNivel()           + "<br>" +
                    "ESTADO: " + estadoStr             +
                    "</html>");

            if (p.estaVivo()) {
                personajeLabels[i].setIcon(escalar(
                        "imagenes/" + p.getNombre().toLowerCase() + "_idle.png", anchoParty, altoParty));
            } else {
                personajeLabels[i].setIcon(escalar(
                        "imagenes/" + p.getNombre().toLowerCase() + "_derrota.png", anchoParty, altoParty));
            }
        }

        if (gestor.getJefe().estaVivo()) {
            jefeLabel.setIcon(escalar("imagenes/jefe_idle.png", tamanoJefe, tamanoJefe));
        }
        lblVidaJefe.setText("HP Jefe: " + gestor.getJefe().getVida() + "/" + gestor.getJefe().getVidaMaxima());
    }

    private JButton boton(String texto, int x, int y, int w, int h) {
        JButton b = new JButton(texto);
        b.setBounds(x, y, w, h);
        b.setBackground(new Color(212, 175, 55));
        return b;
    }

    private ImageIcon escalar(String ruta, int w, int h) {
        return new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }
}