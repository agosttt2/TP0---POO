package juego.combate;

public class ResultadoAtaque {

    public final String nombreAtacante;
    public final int    danio;
    public final boolean jefeDerrotado;

    public ResultadoAtaque(String nombreAtacante, int danio, boolean jefeDerrotado) {
        this.nombreAtacante = nombreAtacante;
        this.danio          = danio;
        this.jefeDerrotado  = jefeDerrotado;
    }
}