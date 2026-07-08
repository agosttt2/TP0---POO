package juego.item;

import juego.modelo.Personaje;

public class Armadura extends Item {

    private final int bonusDefensa;

    public Armadura(String nombre, int precio, String descripcion, String personajeObjetivo, int bonusDefensa) {
        super(nombre, precio, descripcion, personajeObjetivo);
        this.bonusDefensa = bonusDefensa;
    }

    public int getBonusDefensa() { return bonusDefensa; }

    @Override
    public void aplicar(Personaje personaje) {
        personaje.aumentarDefensa(bonusDefensa);
    }

    @Override
    public boolean esConsumible() { return false; }
}
