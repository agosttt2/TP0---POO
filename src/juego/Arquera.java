package juego;

import javax.swing.JOptionPane;

public class Arquera extends Personaje {

    private int mana;
    private int energia;

    public Arquera() {
        super(
                "Arquera",
                110,
                25 + Tienda.ataqueArquera,
                5,
                15,
                1.0);
        
        mana = 100;
        energia = 15;
    }

    public int getMana() {
        return mana;
    }

    public void regenerarMana() {
        mana += energia;
        if (mana > 100) {
            mana = 100;
        }
    }

    @Override
    public String[] getNombresHabilidades() {
        return new String[] { "Disparo Triple (35 Maná)", "Disparo Supremo (60 Maná)" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        if (index == 0) {
            return "imagenes/arquera_habilidad.png";
        } else {
            return "imagenes/arquera_habilidad_suprema.png";
        }
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (index == 0) {
            if (mana < 35) {
                JOptionPane.showMessageDialog(null, "No tienes suficiente maná.");
                return 0;
            }
            mana -= 35;
            return (int)(calcularDanoFinal() * 1.3);  // Disparo Triple
        } else {
            if (mana < 60) {
                JOptionPane.showMessageDialog(null, "No tienes suficiente maná.");
                return 0;
            }
            mana -= 60;
            return (int)(calcularDanoFinal() * 2.2);  // Disparo Supremo
        }
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}