package juego.modelo;

import juego.item.Tienda;

public class Curandera extends Personaje {

    private static final int COSTO_MANA = 45;
    private static final int CURACION   = 30;
    private static final int REGENERACION = 20;

    private int mana;

    public Curandera() {
        super("Curandera", 120, 15, 4, 10, 1.0);
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
        return new String[] { "Curación (45 Maná)" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        return "imagenes/curandera_habilidad.png";
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (mana < COSTO_MANA) return -1;
        mana -= COSTO_MANA;
        return CURACION;
    }

    @Override
    public boolean habilidadEsCuracion(int index) {
        return true;
    }
}