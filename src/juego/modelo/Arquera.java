package juego.modelo;

import juego.item.Tienda;

public class Arquera extends Personaje {

    private static final int COSTO_MANA_TRIPLE  = 35;
    private static final int COSTO_MANA_SUPREMO = 60;
    private static final int REGENERACION       = 25;

    private int mana;

    public Arquera() {
        super("Arquera", 110, 25, 5, 15, 1.0);
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
        return new String[] { "Disparo Triple (35 Maná)", "Disparo Supremo (60 Maná)" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        return index == 0 ? "imagenes/arquera_habilidad.png" : "imagenes/arquera_habilidad_suprema.png";
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (index == 0) {
            if (mana < COSTO_MANA_TRIPLE) return -1;
            mana -= COSTO_MANA_TRIPLE;
            return (int)(calcularDanoFinal() * 1.3);
        } else {
            if (mana < COSTO_MANA_SUPREMO) return -1;
            mana -= COSTO_MANA_SUPREMO;
            return (int)(calcularDanoFinal() * 2.2);
        }
    }
}