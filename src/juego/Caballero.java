package juego;

public class Caballero extends Personaje {

    public Caballero() {
        super("Caballero", 150, 25, 10);
    }

    @Override
    public int usarHabilidad() {
        return 40;
    }
}