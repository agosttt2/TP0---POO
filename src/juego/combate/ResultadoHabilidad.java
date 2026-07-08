package juego.combate;

import juego.modelo.Personaje;

public class ResultadoHabilidad {
    private final boolean manaInsuficiente;
    private final boolean curacion;
    private final int valor;
    private final Personaje objetivo;

    public ResultadoHabilidad(boolean manaInsuficiente, boolean curacion, int valor, Personaje objetivo) {
        this.manaInsuficiente = manaInsuficiente;
        this.curacion = curacion;
        this.valor = valor;
        this.objetivo = objetivo;
    }

    public boolean manaInsuficiente() { return manaInsuficiente; }
    public boolean esCuracion() { return curacion; }
    public int getValor() { return valor; }
    public Personaje getObjetivo() { return objetivo; }
}
