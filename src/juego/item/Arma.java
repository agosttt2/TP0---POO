package juego.item;

import juego.modelo.Personaje;

public class Arma extends Item {

    private final int bonusAtaque;

    public Arma(String nombre, int precio, String descripcion, String personajeObjetivo, int bonusAtaque) {
        super(nombre, precio, descripcion, personajeObjetivo);
        this.bonusAtaque = bonusAtaque;
    }

    public int getBonusAtaque() { return bonusAtaque; }

    @Override
    public void aplicar(Personaje personaje) {
        personaje.aumentarAtaque(bonusAtaque);
    }

    @Override
    public boolean esConsumible() { return false; }
}