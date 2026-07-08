package juego.combate;

import juego.modelo.Jefe;
import juego.modelo.Personaje;

import java.util.ArrayList;
import java.util.List;

public class Combate {

    private final List<Personaje> aliados;
    private final Jefe jefe;

    private final List<Personaje> ordenTurno = new ArrayList<>();
    private int indiceTurno = 0;

    private int contadorTurnosJefe = 0;
    private int turnosParaCurarJefe = calcularProximoUmbralCuracion();

    public Combate(List<Personaje> aliados, Jefe jefe) {
        this.aliados = aliados;
        this.jefe = jefe;
    }

    public List<Personaje> getAliados() { return aliados; }
    public Jefe getJefe() { return jefe; }

    
    public void iniciarRonda() {
        ordenTurno.clear();
        for (Personaje p : aliados) {
            if (p.estaVivo()) ordenTurno.add(p);
        }
        if (jefe.estaVivo()) ordenTurno.add(jefe);
        ordenTurno.sort((a, b) -> b.getVelocidad() - a.getVelocidad());
        indiceTurno = 0;
    }

    public boolean hayTurnoPendiente() {
        return indiceTurno < ordenTurno.size();
    }

    public Personaje turnoActual() {
        return ordenTurno.get(indiceTurno);
    }

    public void avanzarTurno() {
        indiceTurno++;
    }

    public boolean esTurnoDeJefe() {
        return hayTurnoPendiente() && turnoActual() instanceof Jefe;
    }

    public int atacar(Personaje atacante, Personaje objetivo) {
        int danio = atacante.calcularDanoFinal();
        objetivo.recibirAtaque(danio);
        return danio;
    }

    public ResultadoHabilidad usarHabilidad(Personaje personaje, int index) {
        int valor = personaje.ejecutarHabilidad(index);

        if (valor == -1) {
            return new ResultadoHabilidad(true, false, 0, null);
        }

        if (personaje.habilidadEsCuracion(index)) {
            Personaje objetivo = elegirAliadoVivoAlAzar();
            if (objetivo != null) {
                objetivo.curar(valor);
            }
            return new ResultadoHabilidad(false, true, valor, objetivo);
        } else {
            jefe.recibirAtaque(valor);
            return new ResultadoHabilidad(false, false, valor, jefe);
        }
    }

    private Personaje elegirAliadoVivoAlAzar() {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje p : aliados) {
            if (p.estaVivo()) vivos.add(p);
        }
        if (vivos.isEmpty()) return null;
        return vivos.get((int) (Math.random() * vivos.size()));
    }

    private int calcularProximoUmbralCuracion() {
        return 3 + (int) (Math.random() * 3);
    }

    public ResultadoTurnoJefe ejecutarTurnoJefe() {
        contadorTurnosJefe++;

        if (contadorTurnosJefe >= turnosParaCurarJefe) {
            int heal = 50;
            jefe.curar(heal);
            contadorTurnosJefe = 0;
            turnosParaCurarJefe = calcularProximoUmbralCuracion();
            return new ResultadoTurnoJefe(true, heal, null);
        }

        Personaje objetivo = elegirAliadoVivoAlAzar();
        if (objetivo == null) {
            return new ResultadoTurnoJefe(false, 0, null);
        }
        int danio = jefe.calcularDanoFinal();
        objetivo.recibirAtaque(danio);
        return new ResultadoTurnoJefe(false, danio, objetivo);
    }

    public boolean jefeDerrotado() {
        return !jefe.estaVivo();
    }

    public boolean todosLosAliadosDerrotados() {
        for (Personaje p : aliados) {
            if (p.estaVivo()) return false;
        }
        return true;
    }

   
    public List<Personaje> otorgarExperienciaPorVictoria(int xp) {
        List<Personaje> subieronNivel = new ArrayList<>();
        for (Personaje p : aliados) {
            if (p.estaVivo() && p.ganarExperiencia(xp)) {
                subieronNivel.add(p);
            }
        }
        return subieronNivel;
    }
}
