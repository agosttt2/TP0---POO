package juego;

public class Curandera extends Personaje {

    public Curandera() {
        super("Curandera", 120, 15, 6);
    }

    @Override
    public int usarHabilidad() {
        vida += 30;

        if (vida > 120) {
            vida = 120;
        }

        return 0;
    }
}