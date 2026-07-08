package juego.persistencia;

import juego.modelo.EstadoPersonaje;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Contenedor simple con todo lo necesario para reconstruir una partida guardada. */
public class DatosPartidaGuardada {

    public static class DatosPersonaje {
        public int vida;
        public EstadoPersonaje estado;
        public int nivel;
        public int experiencia;
    }

    public int vidaJefe;
    public EstadoPersonaje estadoJefe;
    public final List<DatosPersonaje> personajes = new ArrayList<>();
    public int oro;
    public final Set<String> equipoComprado = new HashSet<>();
    public final List<String> consumibles = new ArrayList<>();
}
