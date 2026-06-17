package juego;

public class Mago extends Personaje {

    public Mago() {
        super("Mago", 100, 35, 4);
    }

    @Override
    public int usarHabilidad() {
        return 55;
    }
}