package juego;

public class Curandera extends Personaje {

    public Curandera() {
        super("Curandera", 120, 15, 6, 10, 1.0);
    }

    @Override
    public String[] getNombresHabilidades() {
        return new String[] { "Curación" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        return "imagenes/curandera_habilidad.png";
    }

    @Override
    public int ejecutarHabilidad(int index) {
        return 30;  // Cantidad de curación
    }

    // Sobreescribe para indicar que esta habilidad cura en lugar de dañar
    @Override
    public boolean habilidadEsCuracion(int index) {
        return true;
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}