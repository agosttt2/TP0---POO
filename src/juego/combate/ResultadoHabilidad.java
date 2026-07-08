package juego.combate;

public class ResultadoHabilidad {

    public final boolean manaInsuficiente;
    public final boolean esCuracion;
    public final String  nombreHabilidad;
    public final int     valor;
    public final String  sprite;
    public final int     indiceObjetivo;

    public ResultadoHabilidad(boolean manaInsuficiente, boolean esCuracion,
                              String nombreHabilidad, int valor,
                              String sprite, int indiceObjetivo) {
        this.manaInsuficiente = manaInsuficiente;
        this.esCuracion       = esCuracion;
        this.nombreHabilidad  = nombreHabilidad;
        this.valor            = valor;
        this.sprite           = sprite;
        this.indiceObjetivo   = indiceObjetivo;
    }
}