package juego;

import juego.item.Arma;
import juego.item.Armadura;
import juego.item.Consumible;
import juego.item.Item;
import juego.item.Tienda;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class PantallaTienda extends JFrame {

    private static final Color DORADO = new Color(212, 175, 55);
    private static final Color DORADO_OSCURO = new Color(150, 120, 35);
    private static final Color PANEL_FONDO = new Color(15, 15, 20, 215);
    private static final Color TEXTO_GRIS = new Color(200, 200, 200);
    private static final Color COLOR_ARMA = new Color(200, 80, 60);
    private static final Color COLOR_ARMADURA = new Color(80, 130, 190);
    private static final Color COLOR_CONSUMIBLE = new Color(90, 180, 100);
    private static final Color COLOR_COMPRADO = new Color(90, 90, 90);

    private JLabel lblOro;

    public PantallaTienda() {

        setTitle("Tienda");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;

        ImageIcon icono = new ImageIcon("imagenes/tienda.png");
        Image imagenEscalada = icono.getImage().getScaledInstance(sw, sh, Image.SCALE_SMOOTH);
        JLabel fondo = new JLabel(new ImageIcon(imagenEscalada));
        fondo.setLayout(null);

        JPanel velo = new JPanel();
        velo.setBounds(0, 0, sw, sh);
        velo.setBackground(new Color(0, 0, 0, 90));
        velo.setLayout(null);
        fondo.add(velo);

        JLabel titulo = new JLabel("TIENDA");
        titulo.setFont(new Font("Serif", Font.BOLD, 46));
        titulo.setForeground(DORADO);
        titulo.setBounds(50, 35, 400, 60);
        velo.add(titulo);

        JLabel subtitulo = new JLabel("Equipá a tu grupo antes de la batalla");
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitulo.setForeground(TEXTO_GRIS);
        subtitulo.setBounds(52, 90, 500, 25);
        velo.add(subtitulo);

        JPanel badgeOro = crearPanelRedondeado(new Color(0, 0, 0, 200), DORADO, 2, 16);
        badgeOro.setLayout(null);
        badgeOro.setBounds(sw - 300, 45, 240, 55);
        lblOro = new JLabel("ORO: " + Tienda.getOro(), SwingConstants.CENTER);
        lblOro.setFont(new Font("Arial", Font.BOLD, 22));
        lblOro.setForeground(DORADO);
        lblOro.setBounds(0, 0, 240, 55);
        badgeOro.add(lblOro);
        velo.add(badgeOro);

        List<Item> catalogo = Tienda.getCatalogo();

        int cardW = 230, cardH = 300, gap = 30;
        int totalW = catalogo.size() * cardW + (catalogo.size() - 1) * gap;
        int startX = (sw - totalW) / 2;
        int y = (sh - cardH) / 2 + 20;

        int x = startX;
        for (Item item : catalogo) {
            JPanel card = crearTarjetaItem(item);
            card.setBounds(x, y, cardW, cardH);
            velo.add(card);
            x += cardW + gap;
        }

        JButton volver = crearBotonEstilizado("VOLVER", 220, 42, false);
        volver.setBounds(sw - 260, sh - 90, 220, 42);
        volver.addActionListener(e -> dispose());
        velo.add(volver);

        setContentPane(fondo);
        setVisible(true);
    }

    private JPanel crearTarjetaItem(Item item) {
        JPanel card = crearPanelRedondeado(PANEL_FONDO, DORADO_OSCURO, 2, 18);
        card.setLayout(null);

        Color colorCategoria;
        String categoria;
        if (item instanceof Arma) {
            colorCategoria = COLOR_ARMA;
            categoria = "ARMA";
        } else if (item instanceof Armadura) {
            colorCategoria = COLOR_ARMADURA;
            categoria = "ARMADURA";
        } else {
            colorCategoria = COLOR_CONSUMIBLE;
            categoria = "CONSUMIBLE";
        }

        JPanel franja = new JPanel();
        franja.setBackground(colorCategoria);
        franja.setBounds(0, 0, 230, 6);
        card.add(franja);

        JLabel lblCategoria = new JLabel(categoria);
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 11));
        lblCategoria.setForeground(colorCategoria);
        lblCategoria.setBounds(16, 16, 200, 18);
        card.add(lblCategoria);

        JLabel lblNombre = new JLabel("<html>" + item.getNombre() + "</html>");
        lblNombre.setFont(new Font("Serif", Font.BOLD, 18));
        lblNombre.setForeground(DORADO);
        lblNombre.setBounds(16, 36, 200, 50);
        card.add(lblNombre);

        JLabel lblDescripcion = new JLabel("<html>" + item.getDescripcion() + "</html>");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDescripcion.setForeground(TEXTO_GRIS);
        lblDescripcion.setVerticalAlignment(SwingConstants.TOP);
        lblDescripcion.setBounds(16, 90, 200, 80);
        card.add(lblDescripcion);

        JLabel lblPara = new JLabel("Para: " + item.getPersonajeObjetivo());
        lblPara.setFont(new Font("Arial", Font.ITALIC, 12));
        lblPara.setForeground(new Color(160, 160, 160));
        lblPara.setBounds(16, 168, 200, 20);
        card.add(lblPara);

        JLabel lblPrecio = new JLabel(item.getPrecio() + " oro");
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 20));
        lblPrecio.setForeground(DORADO);
        lblPrecio.setBounds(16, 200, 200, 30);
        card.add(lblPrecio);

        boolean yaComprado = !item.esConsumible() && Tienda.yaComprado(item);

        JButton boton = crearBotonEstilizado(yaComprado ? "COMPRADO" : "COMPRAR", 198, 40, yaComprado);
        boton.setBounds(16, 244, 198, 40);
        boton.setEnabled(!yaComprado);

        boton.addActionListener(e -> {
            boolean exito = Tienda.comprar(item);
            if (exito) {
                actualizarOro();
                if (!item.esConsumible()) {
                    boton.setText("COMPRADO");
                    boton.setEnabled(false);
                    boton.setBackground(COLOR_COMPRADO);
                }
                JOptionPane.showMessageDialog(this, "Compraste: " + item.getNombre());
            } else if (!item.esConsumible() && Tienda.yaComprado(item)) {
                JOptionPane.showMessageDialog(this, "Ya tenés ese objeto.");
            } else {
                JOptionPane.showMessageDialog(this, "No te alcanza el oro para esta compra.");
            }
        });

        card.add(boton);
        return card;
    }

    private void actualizarOro() {
        lblOro.setText("ORO: " + Tienda.getOro());
    }

    private JButton crearBotonEstilizado(String texto, int w, int h, boolean deshabilitado) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(deshabilitado ? COLOR_COMPRADO : DORADO);
        boton.setForeground(deshabilitado ? new Color(220, 220, 220) : Color.BLACK);
        boton.setPreferredSize(new Dimension(w, h));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JPanel crearPanelRedondeado(Color fondo, Color borde, int grosorBorde, int radio) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(fondo);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radio, radio));
                g2.setColor(borde);
                g2.setStroke(new BasicStroke(grosorBorde));
                g2.draw(new RoundRectangle2D.Double(1, 1, getWidth() - 3, getHeight() - 3, radio, radio));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        return panel;
    }
}