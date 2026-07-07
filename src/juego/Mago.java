package juego;

import javax.swing.JOptionPane;

public class Mago extends Personaje {
	
    private int mana;
    private int energia;

    public Mago() {
        super(
                "Mago",
                100,
                35 + Tienda.ataqueMago,
                7,
                12,
                1.0);

        mana = 100;
        energia = 30;
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
        return new String[] { "Bola de fuego (30 Maná)", "Invocación (90 Maná)" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        if (index == 0) {
            return "imagenes/mago_habilidad.png";
        } else {
            return "imagenes/mago_habilidad_suprema.png";
        }
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (index == 0) {
            if (mana < 30) {
                JOptionPane.showMessageDialog(null, "No tienes suficiente maná.");
                return 0;
            }
            mana -= 30;
            return (int)(calcularDanoFinal() * 1.6);  
        } else {
            if (mana < 90) {
                JOptionPane.showMessageDialog(null, "No tienes suficiente maná.");
                return 0;
            }
            mana -= 90;
            return (int)(calcularDanoFinal() * 2.8);  
        }
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}