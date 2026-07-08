package juego.modelo;

public class Jefe extends Personaje {

    public Jefe() {
        super("Jefe Final", 500, 50, 8, 6, 1.0);
    }

    @Override
    public String[] getNombresHabilidades() {
        return new String[] { "Ataque Brutal" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        return "imagenes/jefe_ataque.png";
    }

    @Override
    public int ejecutarHabilidad(int index) {
        return (int) (calcularDanoFinal() * 2.0);
    }
}
