package juego;

public class Arquera extends Personaje {

    public Arquera() {
        super("Arquera", 110, 30, 5, 15, 1.0);
    }

    @Override
    public String[] getNombresHabilidades() {
        return new String[] { "Disparo Triple", "Disparo Supremo" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        if (index == 0) {
            return "imagenes/arquera_habilidad.png";
        } else {
            return "imagenes/arquera_habilidad_suprema.png";
        }
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (index == 0) {
            return (int)(calcularDanoFinal() * 1.3);  // Disparo Triple
        } else {
            return (int)(calcularDanoFinal() * 2.2);  // Disparo Supremo
        }
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}