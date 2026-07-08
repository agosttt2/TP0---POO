package juego.combate;

public class ResultadoFinTurno {

    public enum Estado { VICTORIA, DERROTA, CONTINUA }

    public final Estado             estado;
    public final ResultadoTurnoJefe turnoJefe;
    public final int                oroGanado;
    public final boolean            subioNivel;

    public ResultadoFinTurno(Estado estado, ResultadoTurnoJefe turnoJefe,
                             int oroGanado, boolean subioNivel) {
        this.estado     = estado;
        this.turnoJefe  = turnoJefe;
        this.oroGanado  = oroGanado;
        this.subioNivel = subioNivel;
    }
}