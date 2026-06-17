package juego;

public class Arquera extends Personaje {

    public Arquera() {
        super("Arquera", 110, 30, 5);
    }

    @Override
    public int usarHabilidad() {
        return 45;
    }
}