package juego;

import javax.swing.JOptionPane;

public class Curandera extends Personaje {

    private int mana;
    private int energia;

    public Curandera() {
        super(
        		"Curandera", 
        		120, 
        		15, 
        		4, 
        		10, 
        		1.0);
        
        mana = 100;
        energia = 10;
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
        return new String[] { "Curación (45 Maná)" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        return "imagenes/curandera_habilidad.png";
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (mana < 45) {
            JOptionPane.showMessageDialog(null, "No tienes suficiente maná.");
            return 0;
        }
        mana -= 45;
        return 30;  // Cantidad de curación
    }

    // Sobreescribe para indicar que esta habilidad cura en lugar de dañar
    @Override
    public boolean habilidadEsCuracion(int index) {
        return true;
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}