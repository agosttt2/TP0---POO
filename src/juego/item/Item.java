package juego.item;

import juego.modelo.Personaje;

public abstract class Item {

    private final String nombre;
    private final int precio;
    private final String descripcion;
    private final String personajeObjetivo;

    protected Item(String nombre, int precio, String descripcion, String personajeObjetivo) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.personajeObjetivo = personajeObjetivo;
    }

    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }

    public String getPersonajeObjetivo() { return personajeObjetivo; }

    public abstract void aplicar(Personaje personaje);

    public abstract boolean esConsumible();
}
