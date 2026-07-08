package juego.combate;

public class ResultadoTurnoJefe {

    public final boolean curo;
    public final int     heal;
    public final int     indiceObjetivo;
    public final String  nombreObjetivo;
    public final int     danio;

    public ResultadoTurnoJefe(boolean curo, int heal,
                              int indiceObjetivo, String nombreObjetivo, int danio) {
        this.curo           = curo;
        this.heal           = heal;
        this.indiceObjetivo = indiceObjetivo;
        this.nombreObjetivo = nombreObjetivo;
        this.danio          = danio;
    }
}