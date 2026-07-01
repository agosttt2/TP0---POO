package juego;

import javax.swing.JOptionPane;

public class Mago extends Personaje {
	
	private int mana;

    public Mago() {
        super("Mago", 100, 35, 4, 12, 1.0);
        mana = 100;
    }
    public int getMana() {
    	return mana;
    }

    @Override
    public String[] getNombresHabilidades() {
        return new String[] { "Bola de fuego", "Invocación" };
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
        if (index == 0)
        {if (mana < 20) {
            JOptionPane.showMessageDialog(null, "No tienes suficiente maná.");
            return 0;
        }

        mana -= 20;
        
        System.out.println("Maná restante: " + mana);
        
            return (int)(calcularDanoFinal() * 1.6);  
        } else 
        {if (mana < 40) {
            JOptionPane.showMessageDialog(null, "No tienes suficiente maná.");
            return 0;
        }

        mana -= 40;
        
        System.out.println("Maná restante: " + mana);
        
            return (int)(calcularDanoFinal() * 2.8);  

        }
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
       
    }
}


