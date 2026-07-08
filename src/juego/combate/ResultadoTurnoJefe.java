package juego.combate;

import juego.modelo.Personaje;

public class ResultadoTurnoJefe {
    private final boolean curo;
    private final int valor;
    private final Personaje objetivo;

    public ResultadoTurnoJefe(boolean curo, int valor, Personaje objetivo) {
        this.curo = curo;
        this.valor = valor;
        this.objetivo = objetivo;
    }

    public boolean curo() { return curo; }
    public int getValor() { return valor; }
    public Personaje getObjetivo() { return objetivo; }
}
