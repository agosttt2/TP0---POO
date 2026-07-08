package juego.combate;

import juego.item.Consumible;
import juego.item.Tienda;
import java.util.List;
import juego.modelo.Arquera;
import juego.modelo.Caballero;
import juego.modelo.Curandera;
import juego.modelo.Jefe;
import juego.modelo.Mago;
import juego.modelo.Personaje;

public class OrquestadorCombate {

    private Personaje[] party;
    private int personajeActual;
    private int turnosJefe;
    private Jefe jefe;

    public OrquestadorCombate() {
        party = new Personaje[] {
            new Mago(),
            new Caballero(),
            new Arquera(),
            new Curandera()
        };
        personajeActual = 0;
        turnosJefe      = 0;
        jefe            = new Jefe();
    }

    public Personaje[] getParty()         { return party; }
    public int         getIndiceActual()  { return personajeActual; }
    public Jefe        getJefe()          { return jefe; }
    public Personaje   getActual()        { return party[personajeActual]; }

    public int contarVivos() {
        int count = 0;
        for (int i = 0; i < party.length; i++) {
            if (party[i].estaVivo()) count++;
        }
        return count;
    }

    public boolean todosDerotados() {
        return contarVivos() == 0;
    }

    public String getManaTexto(int i) {
        return "Maná: " + party[i].getMana();
    }

    public ResultadoAtaque atacar() {
        int danio = getActual().calcularDanoFinal();
        jefe.recibirAtaque(danio);
        getActual().estado = "VIVO";
        return new ResultadoAtaque(getActual().getNombre(), danio, !jefe.estaVivo());
    }

    public ResultadoFinTurno defender() {
        getActual().estado = "VIVO";
        return finalizarTurno();
    }

    public String[] getNombresConsumibles() {
        List<Consumible> consumibles = Tienda.getInventario().getConsumibles();
        String[] nombres = new String[consumibles.size()];
        for (int i = 0; i < consumibles.size(); i++) {
            nombres[i] = consumibles.get(i).getNombre();
        }
        return nombres;
    }

    public boolean tieneConsumibles() {
        return Tienda.getInventario().tieneConsumibles();
    }

    public ResultadoFinTurno usarObjeto(int idx) {
        List<Consumible> consumibles = Tienda.getInventario().getConsumibles();
        Consumible consumible = consumibles.get(idx);
        Tienda.getInventario().usarConsumible(consumible, getActual());
        return finalizarTurno();
    }

    public ResultadoHabilidad usarHabilidad(int idx) {
        Personaje p     = getActual();
        int valor       = p.ejecutarHabilidad(idx);
        String habNombre = p.getNombresHabilidades()[idx];
        String sprite   = p.getSpriteHabilidad(idx);

        if (valor == -1) {
            return new ResultadoHabilidad(true, false, habNombre, 0, sprite, -1);
        }

        if (p.habilidadEsCuracion(idx)) {
            int indiceCurado = curarAliadoAleatorio(valor);
            p.estado = "VIVO";
            return new ResultadoHabilidad(false, true, habNombre, valor, sprite, indiceCurado);
        } else {
            jefe.recibirAtaque(valor);
            p.estado = "VIVO";
            return new ResultadoHabilidad(false, false, habNombre, valor, sprite, -1);
        }
    }

    public ResultadoTurnoJefe ejecutarTurnoJefe() {
        turnosJefe++;
        int umbral = 3 + (int)(Math.random() * 3);

        if (turnosJefe >= umbral) {
            int heal = 50;
            jefe.curar(heal);
            turnosJefe = 0;
            return new ResultadoTurnoJefe(true, heal, -1, null, 0);
        }

        int indice = elegirVivoAleatorio();
        if (indice == -1) {
            return new ResultadoTurnoJefe(false, 0, -1, null, 0);
        }

        Personaje obj   = party[indice];
        int danioBase   = jefe.calcularDanoFinal();
        int danio = Math.max(1, danioBase - obj.getDefensa());

        obj.recibirAtaque(danio);
        obj.estado = "VIVO";
        return new ResultadoTurnoJefe(false, 0, indice, obj.getNombre(), danio);
    }

    public ResultadoFinTurno finalizarTurno() {
        if (!jefe.estaVivo()) {
            double mult     = contarVivos() * 0.5;
            int oroGanado   = (int)(250 * mult);
            Tienda.agregarOro(oroGanado);

            boolean subioNivel = false;
            for (int i = 0; i < party.length; i++) {
                if (party[i].estaVivo() && party[i].ganarExperiencia(100)) {
                    subioNivel = true;
                }
            }
            return new ResultadoFinTurno(ResultadoFinTurno.Estado.VICTORIA, null, oroGanado, subioNivel);
        }

        ResultadoTurnoJefe turnoJefe = ejecutarTurnoJefe();

        if (todosDerotados()) {
            return new ResultadoFinTurno(ResultadoFinTurno.Estado.DERROTA, turnoJefe, 0, false);
        }

        regenerarManaExcepto(personajeActual);
        siguientePersonaje();

        return new ResultadoFinTurno(ResultadoFinTurno.Estado.CONTINUA, turnoJefe, 0, false);
    }

    public void guardar() {
        juego.persistencia.GuardadoPartida.guardar(this);
    }

    public void cargar() {
        juego.persistencia.GuardadoPartida.cargar(this);
    }

    public static boolean existeGuardado() {
        return juego.persistencia.GuardadoPartida.existeGuardado();
    }

    private void siguientePersonaje() {
        do {
            personajeActual = (personajeActual + 1) % party.length;
        } while (!party[personajeActual].estaVivo());
    }

    private void regenerarManaExcepto(int excluido) {
        for (int i = 0; i < party.length; i++) {
            if (i != excluido && party[i].estaVivo()) {
                party[i].regenerarMana();
            }
        }
    }

    private int curarAliadoAleatorio(int cantidad) {
        int vivos = contarVivos();
        if (vivos == 0) return -1;

        int objetivo = (int)(Math.random() * vivos);
        int contador = 0;

        for (int i = 0; i < party.length; i++) {
            if (party[i].estaVivo()) {
                if (contador == objetivo) {
                    party[i].curar(cantidad);
                    return i;
                }
                contador++;
            }
        }
        return -1;
    }

    private int elegirVivoAleatorio() {
        int vivos = contarVivos();
        if (vivos == 0) return -1;

        int objetivo = (int)(Math.random() * vivos);
        int contador = 0;

        for (int i = 0; i < party.length; i++) {
            if (party[i].estaVivo()) {
                if (contador == objetivo) return i;
                contador++;
            }
        }
        return -1;
    }
}