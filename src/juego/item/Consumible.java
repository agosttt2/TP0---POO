package juego.item;

import juego.modelo.Personaje;

public class Consumible extends Item {

    private final int curacion;

    public Consumible(String nombre, int precio, String descripcion, int curacion) {
        super(nombre, precio, descripcion, "Todos");
        this.curacion = curacion;
    }

    public int getCuracion() { return curacion; }

    @Override
    public void aplicar(Personaje personaje) {
        personaje.curar(curacion);
    }

    @Override
    public boolean esConsumible() { return true; }
}