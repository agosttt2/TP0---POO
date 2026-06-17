package juego;

public class Jefe extends Personaje {

    public Jefe() {
        super("Jefe Final", 300, 30, 8);
    }

    @Override
    public int usarHabilidad() {
        return 60;
    }
}