package juego;

import javax.swing.*;
import java.awt.*;

public class PantallaTienda extends JFrame {

    private JLabel lblOro;

    public PantallaTienda() {

        setTitle("Tienda");
        setSize(1200,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icono = new ImageIcon("imagenes/tienda.png");

        Image imagenEscalada =
                icono.getImage().getScaledInstance(
                        1000,
                        600,
                        Image.SCALE_SMOOTH);

        JLabel fondo = new JLabel(new ImageIcon(imagenEscalada));
        fondo.setLayout(null);

        lblOro = new JLabel("ORO: " + Tienda.oro);
        lblOro.setFont(new Font("Arial", Font.BOLD, 24));
        lblOro.setForeground(Color.YELLOW);
        lblOro.setBounds(30,20,250,40);

        //espada
        JButton espada = new JButton();
        espada.setBounds(280, 190, 120, 220);
        hacerInvisible(espada);

        espada.addActionListener(e -> {

            if(Tienda.espadaComprada){

                JOptionPane.showMessageDialog(this,
                        "Ya compraste esta mejora.");
                return;
            }

            if(Tienda.oro >= 1000){

                Tienda.oro -= 1000;
                Tienda.ataqueCaballero += 10;
                Tienda.espadaComprada = true;

                actualizarOro();

                JOptionPane.showMessageDialog(this,
                        "¡Espada mejorada!\nAtaque Caballero +10");

            }else{

                JOptionPane.showMessageDialog(this,
                        "No tienes suficiente oro.");
            }

        });

        //escudo
        JButton escudo = new JButton();
        escudo.setBounds(450, 190, 120, 220);
        hacerInvisible(escudo);

        escudo.addActionListener(e -> {

            if(Tienda.escudoComprada){

                JOptionPane.showMessageDialog(this,
                        "Ya compraste esta mejora.");
                return;
            }

            if(Tienda.oro >= 1000){

                Tienda.oro -= 1000;
                Tienda.defensaCaballero += 5;
                Tienda.escudoComprada = true;

                actualizarOro();

                JOptionPane.showMessageDialog(this,
                        "¡Escudo mejorado!\nDefensa +5");

            }else{

                JOptionPane.showMessageDialog(this,
                        "No tienes suficiente oro.");
            }

        });

        //arco
        JButton arco = new JButton();
        arco.setBounds(650, 190, 120, 220);
        hacerInvisible(arco);

        arco.addActionListener(e -> {

            if(Tienda.arcoComprada){

                JOptionPane.showMessageDialog(this,
                        "Ya compraste esta mejora.");
                return;
            }

            if(Tienda.oro >= 1000){

                Tienda.oro -= 1000;
                Tienda.ataqueArquera += 10;
                Tienda.arcoComprada = true;

                actualizarOro();

                JOptionPane.showMessageDialog(this,
                        "¡Arco mejorado!\nAtaque +10");

            }else{

                JOptionPane.showMessageDialog(this,
                        "No tienes suficiente oro.");
            }

        });

        //baston
        JButton baculo = new JButton();
        baculo.setBounds(850, 190, 120, 220);
        hacerInvisible(baculo);

        baculo.addActionListener(e -> {

            if(Tienda.baculoComprada){

                JOptionPane.showMessageDialog(this,
                        "Ya compraste esta mejora.");
                return;
            }

            if(Tienda.oro >= 1000){

                Tienda.oro -= 1000;
                Tienda.ataqueMago += 10;
                Tienda.baculoComprada = true;

                actualizarOro();

                JOptionPane.showMessageDialog(this,
                        "¡Báculo mejorado!\nAtaque +10");

            }else{

                JOptionPane.showMessageDialog(this,
                        "No tienes suficiente oro.");
            }

        });

        JButton volver = new JButton("VOLVER");
        volver.setBounds(800,540,180,40);

        volver.addActionListener(e -> dispose());

        fondo.add(lblOro);

        fondo.add(espada);
        fondo.add(escudo);
        fondo.add(arco);
        fondo.add(baculo);

        fondo.add(volver);

        setContentPane(fondo);

        setVisible(true);
    }

    private void actualizarOro(){

        lblOro.setText("ORO: " + Tienda.oro);

    }

    private void hacerInvisible(JButton b){

        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);

    }

}