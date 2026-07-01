package juego;

public class Caballero extends Personaje {

	public Caballero() {
	    super(
	            "Caballero",
	            150,
	            25 + Tienda.ataqueCaballero,
	            10 + Tienda.defensaCaballero,
	            8,
	            1.0);
	}

    @Override
    public String[] getNombresHabilidades() {
        return new String[] { "Embestida con Escudo", "Corte Supremo" };
    }

    @Override
    public String getSpriteHabilidad(int index) {
        if (index == 0) {
            return "imagenes/caballero_habilidad.png";
        } else {
            return "imagenes/caballero_habilidad_suprema.png";
        }
    }

    @Override
    public int ejecutarHabilidad(int index) {
        if (index == 0) {
            return (int)(calcularDanoFinal() * 1.5);  // Embestida con Escudo
        } else {
            return (int)(calcularDanoFinal() * 2.5);  // Corte Supremo
        }
    }

    @Override
    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}