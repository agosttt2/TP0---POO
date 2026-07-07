package juego;

import javax.swing.JOptionPane;

public class Caballero extends Personaje {
	
    private int mana;
    private int energia;

    public Caballero() {
        super(
                "Caballero",
                150,
                30 + Tienda.ataqueCaballero,
                10 + Tienda.defensaCaballero,
                8,
                1.0);
        
        mana = 100;
        energia = 20;
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
        return new String[] { "Embestida con Escudo (30 Maná)", "Corte Supremo (70 Maná)" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        if (index == 0) {
            return "imagenes/caballero_habilidad.png";
        } else {
            return "imagenes/caballero_habilidad_suprema.png";
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
            return (int)(calcularDanoFinal() * 1.5);  // Embestida con Escudo
        } else {
            if (mana < 70) {
                JOptionPane.showMessageDialog(null, "No tienes suficiente maná.");
                return 0;
            }
            mana -= 70;
            return (int)(calcularDanoFinal() * 2.5);  // Corte Supremo
        }
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}