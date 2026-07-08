package juego;

import juego.combate.Combate;
import juego.combate.ResultadoHabilidad;
import juego.combate.ResultadoTurnoJefe;
import juego.item.Consumible;
import juego.item.Tienda;
import juego.modelo.Arquera;
import juego.modelo.Caballero;
import juego.modelo.Curandera;
import juego.modelo.Jefe;
import juego.modelo.Mago;
import juego.modelo.Personaje;
import juego.persistencia.DatosPartidaGuardada;
import juego.persistencia.GuardadoPartida;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PantallaCombate extends JFrame {

    private final List<Personaje> personajes = new ArrayList<>();
    private final Jefe jefe = new Jefe();
    private final Combate combate;

    private JLabel jefeLabel;
    private JLabel lblVidaJefe;
    private JLabel[] personajeLabels;
    private JLabel[] panelNombre;
    private JLabel[] panelVida;

    private final int anchoParty = 320;
    private final int altoParty  = 320;
    private final int tamanoJefe = 530;

    private JButton btnAtacar, btnDefender, btnHabilidad, btnObjetos, btnSalir, btnGuardar;

    public PantallaCombate(String personajeInicial) {
        this(personajeInicial, false);
    }

    public PantallaCombate(String personajeInicial, boolean cargarPartida) {

        personajes.add(new Mago());
        personajes.add(new Caballero());
        personajes.add(new Arquera());
        personajes.add(new Curandera());

        personajeLabels = new JLabel[personajes.size()];
        panelNombre = new JLabel[personajes.size()];
        panelVida = new JLabel[personajes.size()];

        combate = new Combate(personajes, jefe);

        if (cargarPartida) {
            aplicarDatosGuardados();
        }

        combate.iniciarRonda();

        setTitle("EL LEGADO DE LA SANGRE - Combate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;

        JLabel fondo = new JLabel(escalarImagen("imagenes/fondoCombate.png", sw, sh));
        fondo.setLayout(null);

        int[] posX = {
                (int) (sw * 0.28),
                (int) (sw * 0.27),
                (int) (sw * 0.27),
                (int) (sw * 0.27)
        };
        int[] posY = {
                (int) (sh * 0.11),
                (int) (sh * 0.20),
                (int) (sh * 0.30),
                (int) (sh * 0.45)
        };

        for (int i = 0; i < personajes.size(); i++) {
            personajeLabels[i] = new JLabel();
            personajeLabels[i].setBounds(posX[i], posY[i], anchoParty, altoParty);
            fondo.add(personajeLabels[i]);
        }

        fondo.setComponentZOrder(personajeLabels[0], 3);
        fondo.setComponentZOrder(personajeLabels[1], 2);
        fondo.setComponentZOrder(personajeLabels[2], 1);
        fondo.setComponentZOrder(personajeLabels[3], 0);

        jefeLabel = new JLabel();
        jefeLabel.setBounds((int) (sw * 0.62), (int) (sh * 0.08), tamanoJefe, tamanoJefe);
        fondo.add(jefeLabel);

        lblVidaJefe = new JLabel();
        lblVidaJefe.setForeground(Color.RED);
        lblVidaJefe.setFont(new Font("Arial", Font.BOLD, 22));
        lblVidaJefe.setBounds((int) (sw * 0.62), (int) (sh * 0.03), 300, 30);
        fondo.add(lblVidaJefe);

        JPanel barraInferior = crearBarraInferior(sw);
        barraInferior.setBounds(0, sh - 140, sw, 140);
        fondo.add(barraInferior);

        setContentPane(fondo);
        actualizarPantalla();
        procesarTurnosAutomaticos();
        setVisible(true);
    }

    private void aplicarDatosGuardados() {
        DatosPartidaGuardada datos = GuardadoPartida.cargar();
        GuardadoPartida.restaurarTienda(datos);

        jefe.restaurarEstado(datos.vidaJefe, datos.estadoJefe, jefe.getNivel(), jefe.getExperiencia());

        for (int i = 0; i < personajes.size() && i < datos.personajes.size(); i++) {
            DatosPartidaGuardada.DatosPersonaje dp = datos.personajes.get(i);
            personajes.get(i).restaurarEstado(dp.vida, dp.estado, dp.nivel, dp.experiencia);
        }
    }

    private JPanel crearBarraInferior(int sw) {

        JPanel barra = new JPanel(null);
        barra.setBackground(new Color(0, 0, 0, 180));
        barra.setOpaque(true);
        barra.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55), 3));

        for (int i = 0; i < personajes.size(); i++) {
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

        btnAtacar   = crearBoton("ATACAR",    sw - 510, 20, 150, 40);
        btnDefender = crearBoton("DEFENDER",  sw - 350, 20, 150, 40);
        btnHabilidad= crearBoton("HABILIDAD", sw - 190, 20, 150, 40);
        btnObjetos  = crearBoton("OBJETOS",   sw - 510, 70, 150, 40);
        btnGuardar  = crearBoton("GUARDAR",   sw - 350, 70, 150, 40);
        btnSalir    = crearBoton("SALIR",     sw - 190, 70, 150, 40);

        btnAtacar.addActionListener(e -> onAtacar());
        btnDefender.addActionListener(e -> onDefender());
        btnHabilidad.addActionListener(e -> onHabilidad());
        btnObjetos.addActionListener(e -> onObjetos());
        btnSalir.addActionListener(e -> System.exit(0));
        btnGuardar.addActionListener(e -> {
            GuardadoPartida.guardar(combate);
            JOptionPane.showMessageDialog(this, "Partida guardada correctamente");
        });

        barra.add(btnAtacar);
        barra.add(btnDefender);
        barra.add(btnHabilidad);
        barra.add(btnObjetos);
        barra.add(btnGuardar);
        barra.add(btnSalir);
        return barra;
    }

    private Personaje personajeEnTurno() {
        return combate.turnoActual();
    }

    private void onAtacar() {
        Personaje p = personajeEnTurno();
        personajeLabels[personajes.indexOf(p)].setIcon(
                escalarImagen("imagenes/" + p.getNombre().toLowerCase() + "_ataque.png", anchoParty, altoParty));

        int danio = combate.atacar(p, jefe);
        JOptionPane.showMessageDialog(this, p.getNombre() + " hizo " + danio + " de dano al Jefe!");

        finalizarTurnoDelJugador();
    }

    private void onDefender() {
        Personaje p = personajeEnTurno();
        p.defender();
        personajeLabels[personajes.indexOf(p)].setIcon(
                escalarImagen("imagenes/" + p.getNombre().toLowerCase() + "_bloquear.png", anchoParty, altoParty));

        JOptionPane.showMessageDialog(this, p.getNombre() + " esta defendiendo!");
        finalizarTurnoDelJugador();
    }

    private void onHabilidad() {
        Personaje p = personajeEnTurno();
        String[] habs = p.getNombresHabilidades();

        String elegida = (String) JOptionPane.showInputDialog(
                this, "Selecciona una habilidad:", "Habilidades de " + p.getNombre(),
                JOptionPane.QUESTION_MESSAGE, null, habs, habs[0]);

        if (elegida == null) return;

        int idx = 0;
        for (int i = 0; i < habs.length; i++) {
            if (habs[i].equals(elegida)) idx = i;
        }

        ResultadoHabilidad resultado = combate.usarHabilidad(p, idx);

        if (resultado.manaInsuficiente()) {
            JOptionPane.showMessageDialog(this, "No tenes suficiente mana para esa habilidad.");
            return; // no se consume el turno
        }

        personajeLabels[personajes.indexOf(p)].setIcon(
                escalarImagen(p.getSpriteHabilidad(idx), anchoParty, altoParty));

        if (resultado.esCuracion()) {
            if (resultado.getObjetivo() != null) {
                JOptionPane.showMessageDialog(this,
                        p.getNombre() + " curo a " + resultado.getObjetivo().getNombre() +
                        " por " + resultado.getValor() + " HP!");
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    p.getNombre() + " uso " + elegida + " e hizo " + resultado.getValor() + " de dano!");
        }

        finalizarTurnoDelJugador();
    }

    private void onObjetos() {
        List<Consumible> consumibles = Tienda.getInventario().getConsumibles();
        if (consumibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tenes objetos para usar. Comprá pociones en la tienda!");
            return;
        }

        String[] nombres = consumibles.stream().map(Consumible::getNombre).distinct().toArray(String[]::new);
        String elegido = (String) JOptionPane.showInputDialog(
                this, "Elegi un objeto:", "Objetos", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
        if (elegido == null) return;

        Consumible consumible = consumibles.stream().filter(c -> c.getNombre().equals(elegido)).findFirst().orElse(null);
        if (consumible == null) return;

        Personaje objetivo = personajeEnTurno();
        Tienda.getInventario().usarConsumible(consumible, objetivo);
        JOptionPane.showMessageDialog(this, objetivo.getNombre() + " uso " + elegido + "!");

        finalizarTurnoDelJugador();
    }

    private void finalizarTurnoDelJugador() {
        Personaje personajeQueActuo = personajeEnTurno();
        combate.avanzarTurno();
        regenerarManaDescanso(personajeQueActuo);
        actualizarPantalla();

        if (verificarFinDeCombate()) return;

        procesarTurnosAutomaticos();
    }

    private void procesarTurnosAutomaticos() {
        while (true) {
            if (!combate.hayTurnoPendiente()) {
                combate.iniciarRonda();
                if (!combate.hayTurnoPendiente()) {
                    return;
                }
            }

            if (combate.esTurnoDeJefe()) {
                ejecutarTurnoJefeAutomatico();
                combate.avanzarTurno();
                actualizarPantalla();
                if (verificarFinDeCombate()) return;
                continue;
            }

            actualizarPantalla();
            return;
        }
    }

    private void regenerarManaDescanso(Personaje excluido) {
        for (Personaje personaje : personajes) {
            if (personaje != excluido && personaje.estaVivo()) {
                if (personaje instanceof Mago) ((Mago) personaje).regenerarMana();
                if (personaje instanceof Caballero) ((Caballero) personaje).regenerarMana();
                if (personaje instanceof Arquera) ((Arquera) personaje).regenerarMana();
                if (personaje instanceof Curandera) ((Curandera) personaje).regenerarMana();
            }
        }
    }

    private void ejecutarTurnoJefeAutomatico() {
        ResultadoTurnoJefe resultado = combate.ejecutarTurnoJefe();

        if (resultado.curo()) {
            jefeLabel.setIcon(escalarImagen("imagenes/jefe_curacion.png", tamanoJefe, tamanoJefe));
            JOptionPane.showMessageDialog(this, "El Jefe se regenera " + resultado.getValor() + " HP!");
        } else {
            jefeLabel.setIcon(escalarImagen("imagenes/jefe_ataque.png", tamanoJefe, tamanoJefe));
            if (resultado.getObjetivo() != null) {
                JOptionPane.showMessageDialog(this,
                        "El Jefe ataca a " + resultado.getObjetivo().getNombre() +
                        " por " + resultado.getValor() + " de dano.");
            }
        }
    }

    private boolean verificarFinDeCombate() {
        if (combate.jefeDerrotado()) {
            jefeLabel.setIcon(escalarImagen("imagenes/jefe_derrota.png", tamanoJefe, tamanoJefe));

            List<Personaje> subieronNivel = combate.otorgarExperienciaPorVictoria(100);
            if (!subieronNivel.isEmpty()) {
                StringBuilder sb = new StringBuilder("Subieron de nivel!\n");
                for (Personaje p : subieronNivel) {
                    sb.append(p.getNombre()).append(" ahora es nivel ").append(p.getNivel()).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            }

            JOptionPane.showMessageDialog(this, "VICTORIA!");
            new PantallaVictoria();
            dispose();
            return true;
        }

        if (combate.todosLosAliadosDerrotados()) {
            JOptionPane.showMessageDialog(this, "DERROTA!");
            new PantallaDerrota();
            dispose();
            return true;
        }

        return false;
    }

    private void actualizarPantalla() {
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            String estadoStr = p.estaVivo() ? p.getEstado().name() : "DERROTADO";
            String manaTexto = "";
            if (p instanceof Caballero) manaTexto = "Mana: " + ((Caballero) p).getMana() + "<br>";
            if (p instanceof Mago) manaTexto = "Mana: " + ((Mago) p).getMana() + "<br>";
            if (p instanceof Arquera) manaTexto = "Mana: " + ((Arquera) p).getMana() + "<br>";
            if (p instanceof Curandera) manaTexto = "Mana: " + ((Curandera) p).getMana() + "<br>";

            boolean esElTurno = combate.hayTurnoPendiente() && combate.turnoActual() == p;
            panelNombre[i].setText((esElTurno ? "> " : "") + p.getNombre());

            panelVida[i].setText(
                    "<html>" +
                    "HP: " + p.getVida() + "/" + p.getVidaMaxima() + "<br>" +
                    "ATQ: " + p.calcularDanoFinal() + "  DEF: " + p.getDefensa() + "<br>" +
                    "VEL: " + p.getVelocidad() + "  Nv: " + p.getNivel() + "<br>" +
                    manaTexto +
                    "Exp:" + p.getExperiencia() + "/" + p.getExperienciaNecesaria() + "<br>" +
                    "ESTADO: " + estadoStr +
                    "</html>");

            if (p.estaVivo()) {
                personajeLabels[i].setIcon(escalarImagen(
                        "imagenes/" + p.getNombre().toLowerCase() + "_idle.png", anchoParty, altoParty));
            } else {
                personajeLabels[i].setIcon(escalarImagen(
                        "imagenes/" + p.getNombre().toLowerCase() + "_derrota.png", anchoParty, altoParty));
            }
        }

        if (jefe.estaVivo()) {
            jefeLabel.setIcon(escalarImagen("imagenes/jefe_idle.png", tamanoJefe, tamanoJefe));
        }

        lblVidaJefe.setText("HP Jefe: " + jefe.getVida() + "/" + jefe.getVidaMaxima());

        boolean esTurnoDeJugador = combate.hayTurnoPendiente() && !combate.esTurnoDeJefe();
        btnAtacar.setEnabled(esTurnoDeJugador);
        btnDefender.setEnabled(esTurnoDeJugador);
        btnHabilidad.setEnabled(esTurnoDeJugador);
        btnObjetos.setEnabled(esTurnoDeJugador);
    }

    private JButton crearBoton(String texto, int x, int y, int w, int h) {
        JButton b = new JButton(texto);
        b.setBounds(x, y, w, h);
        b.setBackground(new Color(212, 175, 55));
        return b;
    }

    private ImageIcon escalarImagen(String ruta, int w, int h) {
        return new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }
}
