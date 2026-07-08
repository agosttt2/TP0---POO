package juego.modelo;

import juego.item.Tienda;

public class Mago extends Personaje {

    private static final int COSTO_MANA_BASICA  = 30;
    private static final int COSTO_MANA_SUPREMA = 90;
    private static final int REGENERACION       = 40;

    private int mana;

    public Mago() {
        super("Mago", 100, 35, 7, 12, 1.0);
        Tienda.getInventario().aplicarEquipoA(this);
        mana = 100;
    }

    @Override
    public int getMana() { return mana; }

    @Override
    public void regenerarMana() {
        mana = Math.min(100, mana + REGENERACION);
    }

    @Override
    public String[] getNombresHabilidades() {
        return new String[] { "Bola de fuego (30 Maná)", "Invocación (90 Maná)" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        return index == 0 ? "imagenes/mago_habilidad.png" : "imagenes/mago_habilidad_suprema.png";
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (index == 0) {
            if (mana < COSTO_MANA_BASICA) return -1;
            mana -= COSTO_MANA_BASICA;
            return (int)(calcularDanoFinal() * 1.6);
        } else {
            if (mana < COSTO_MANA_SUPREMA) return -1;
            mana -= COSTO_MANA_SUPREMA;
            return (int)(calcularDanoFinal() * 2.8);
        }
    }
}