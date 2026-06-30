package juego;

public class Mago extends Personaje {

    public Mago() {
        super("Mago", 100, 35, 4, 12, 1.0);
    }

    @Override
    public String[] getNombresHabilidades() {
        return new String[] { "Bola de fuego", "Invocación" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        if (index == 0) {
            return "imagenes/mago_habilidad.png";
        } else {
            return "imagenes/mago_habilidad_suprema.png";
        }
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (index == 0) {
            return (int)(calcularDanoFinal() * 1.6);  // Bola de fuego
        } else {
            return (int)(calcularDanoFinal() * 2.8);  // Invocación
        }
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}