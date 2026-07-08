package juego.modelo;

import juego.item.Tienda;

public class Caballero extends Personaje {

    private static final int COSTO_MANA_BASICA  = 30;
    private static final int COSTO_MANA_SUPREMA = 70;
    private static final int REGENERACION       = 30;

    private int mana;

    public Caballero() {
        super("Caballero", 150, 30, 10, 8, 1.0);
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
        return new String[] { "Embestida con Escudo (30 Maná)", "Corte Supremo (70 Maná)" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        return index == 0 ? "imagenes/caballero_habilidad.png" : "imagenes/caballero_habilidad_suprema.png";
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (index == 0) {
            if (mana < COSTO_MANA_BASICA) return -1;
            mana -= COSTO_MANA_BASICA;
            return (int)(calcularDanoFinal() * 1.5);
        } else {
            if (mana < COSTO_MANA_SUPREMA) return -1;
            mana -= COSTO_MANA_SUPREMA;
            return (int)(calcularDanoFinal() * 2.5);
        }
    }
}