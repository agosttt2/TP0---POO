package juego;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.*;

public class combateOrquestador {

	    private Personaje[] personajesObjeto = {
	            new Mago(),
	            new Caballero(),
	            new Arquera(),
	            new Curandera()
	    };

	    private int personajeActual = 0;
	    private int turnosJefe = 0;
	    private static Jefe jefe = new Jefe();

	    public void CombateOrquestador(String personajeInicial, boolean cargarPartida) {
	        if (cargarPartida) {
	            cargarPartida();
	        } else {
	            if (personajeInicial.equals("Mago"))      personajeActual = 0;
	            if (personajeInicial.equals("Caballero")) personajeActual = 1;
	            if (personajeInicial.equals("Arquera"))   personajeActual = 2;
	            if (personajeInicial.equals("Curandera")) personajeActual = 3;
	        }
	    }

	    
	    public Personaje[] getPersonajes() {
	        return personajesObjeto;
	    }

	    public int getPersonajeActual() {
	        return personajeActual;
	    }

	    public static Jefe getJefe() {
	        return jefe;
	    }

	   

	    public int atacar() {
	        Personaje p = personajesObjeto[personajeActual];
	        int dmg = p.calcularDanoFinal();
	        jefe.recibirDanio(dmg);
	        p.estado = "VIVO";
	        return dmg;
	    }

	    public void defender() {
	        Personaje p = personajesObjeto[personajeActual];
	        p.estado = "DEFENDIENDO";
	    }

	    public String[] getHabilidadesDisponibles() {
	        return personajesObjeto[personajeActual].getNombresHabilidades();
	    }

	   
	    public ResultadoHabilidad usarHabilidad(int idx) {
	        Personaje p = personajesObjeto[personajeActual];
	        String elegida = p.getNombresHabilidades()[idx];

	        int valor = p.ejecutarHabilidad(idx);
	        if (valor == 0) {
	            return null; 
	        }

	        String sprite = p.getSpriteHabilidad(idx);
	        boolean esCuracion = p.habilidadEsCuracion(idx);

	        int indiceObjetivoCurado = -1;
	        if (esCuracion) {
	            indiceObjetivoCurado = curarAliadoAleatorio(valor);
	        } else {
	            jefe.recibirDanio(valor);
	        }

	        p.estado = "VIVO";

	        return new ResultadoHabilidad(elegida, valor, sprite, esCuracion, indiceObjetivoCurado);
	    }


	     
	    public ResultadoTurno finalizarTurno() {

	        if (!jefe.estaVivo()) {
	            for (Personaje p : personajesObjeto) {
	                if (p.estaVivo()) {
	                    p.ganarExperiencia(100);
	                }
	            }
	            return ResultadoTurno.victoria();
	        }

	        ResultadoTurnoJefe turnoJefeInfo = turnoJefe();

	        if (verificarDerrota()) {
	            return ResultadoTurno.derrota(turnoJefeInfo);
	        }

	        regenerarManaDescanso(personajeActual);
	        siguientePersonaje();

	        return ResultadoTurno.continua(turnoJefeInfo);
	    }

	 

	    private ResultadoTurnoJefe turnoJefe() {
	        turnosJefe++;
	        int turnosParaCurar = 3 + (int) (Math.random() * 3);

	        if (turnosJefe >= turnosParaCurar) {
	            int heal = 50;
	            jefe.curar(heal);
	            turnosJefe = 0;
	            return ResultadoTurnoJefe.curacion(heal);
	        }

	        int vivosCount = 0;
	        for (Personaje p : personajesObjeto) {
	            if (p.estaVivo()) vivosCount++;
	        }

	        if (vivosCount == 0) {
	            return ResultadoTurnoJefe.sinObjetivo();
	        }

	        int objetivo = (int) (Math.random() * vivosCount);
	        int contador = 0;

	        for (int i = 0; i < personajesObjeto.length; i++) {
	            if (personajesObjeto[i].estaVivo()) {
	                if (contador == objetivo) {
	                    Personaje obj = personajesObjeto[i];

	                    int danioBase = jefe.calcularDanoFinal();
	                    int danio = Math.max(1, danioBase - obj.getDefensa());

	                    if (obj.estado.equals("DEFENDIENDO")) {
	                        danio = Math.max(1, danio / 2);
	                    }

	                    obj.recibirDanio(danio);
	                    obj.estado = "VIVO";

	                    return ResultadoTurnoJefe.ataque(i, obj.getNombre(), danio);
	                }
	                contador++;
	            }
	        }

	        return ResultadoTurnoJefe.sinObjetivo();
	    }

	    private boolean verificarDerrota() {
	        for (Personaje p : personajesObjeto) {
	            if (p.estaVivo()) return false;
	        }
	        return true;
	    }

	    private int curarAliadoAleatorio(int cantidad) {
	        int vivosCount = 0;
	        for (Personaje p : personajesObjeto) {
	            if (p.estaVivo()) vivosCount++;
	        }
	        if (vivosCount == 0) return -1;

	        int objetivo = (int) (Math.random() * vivosCount);
	        int contador = 0;

	        for (int i = 0; i < personajesObjeto.length; i++) {
	            if (personajesObjeto[i].estaVivo()) {
	                if (contador == objetivo) {
	                    personajesObjeto[i].curar(cantidad);
	                    return i;
	                }
	                contador++;
	            }
	        }
	        return -1;
	    }

	    private void regenerarManaDescanso(int excluido) {
	        for (int i = 0; i < personajesObjeto.length; i++) {
	            if (i != excluido && personajesObjeto[i].estaVivo()) {
	                Personaje p = personajesObjeto[i];
	                if (p instanceof Mago) ((Mago) p).regenerarMana();
	                else if (p instanceof Caballero) ((Caballero) p).regenerarMana();
	                else if (p instanceof Arquera) ((Arquera) p).regenerarMana();
	                else if (p instanceof Curandera) ((Curandera) p).regenerarMana();
	            }
	        }
	    }

	    private void siguientePersonaje() {
	        do {
	            personajeActual = (personajeActual + 1) % personajesObjeto.length;
	        } while (!personajesObjeto[personajeActual].estaVivo());
	    }

	   

	    public void guardarPartida() {
	        try (PrintWriter pw = new PrintWriter(new FileWriter("partida_guardada.txt"))) {
	            pw.println(personajeActual);
	            pw.println(turnosJefe);
	            pw.println(jefe.getVida());

	            for (Personaje p : personajesObjeto) {
	                pw.println(p.getNombre() + ";" + p.getVida() + ";" + p.estado);
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            throw new RuntimeException("Error al guardar la partida", ex);
	        }
	    }

	    private void cargarPartida() {
	        try {
	            Scanner lector = new Scanner(new File("partida_guardada.txt"));

	            personajeActual = Integer.parseInt(lector.nextLine());
	            turnosJefe = Integer.parseInt(lector.nextLine());

	            int vidaJefeGuardada = Integer.parseInt(lector.nextLine());
	            jefe.vida = vidaJefeGuardada;

	            for (int i = 0; i < personajesObjeto.length; i++) {
	                String linea = lector.nextLine();
	                String[] partes = linea.split(";");

	                personajesObjeto[i].vida = Integer.parseInt(partes[1]);
	                personajesObjeto[i].estado = partes[2];
	            }

	            lector.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            throw new RuntimeException("Error al cargar la partida", ex);
	        }
	    }

	   
	    public static class ResultadoHabilidad {
	        public final String nombreHabilidad;
	        public final int valor;
	        public final String sprite;
	        public final boolean esCuracion;
	        public final int indiceObjetivoCurado;

	        public ResultadoHabilidad(String nombreHabilidad, int valor, String sprite,
	                                   boolean esCuracion, int indiceObjetivoCurado) {
	            this.nombreHabilidad = nombreHabilidad;
	            this.valor = valor;
	            this.sprite = sprite;
	            this.esCuracion = esCuracion;
	            this.indiceObjetivoCurado = indiceObjetivoCurado;
	        }
	    }

	    public static class ResultadoTurnoJefe {
	        public enum Tipo { CURACION, ATAQUE, SIN_OBJETIVO }

	        public final Tipo tipo;
	        public final int heal;
	        public final int indiceObjetivo;
	        public final String nombreObjetivo;
	        public final int danio;

	        private ResultadoTurnoJefe(Tipo tipo, int heal, int indiceObjetivo, String nombreObjetivo, int danio) {
	            this.tipo = tipo;
	            this.heal = heal;
	            this.indiceObjetivo = indiceObjetivo;
	            this.nombreObjetivo = nombreObjetivo;
	            this.danio = danio;
	        }

	        public static ResultadoTurnoJefe curacion(int heal) {
	            return new ResultadoTurnoJefe(Tipo.CURACION, heal, -1, null, 0);
	        }

	        public static ResultadoTurnoJefe ataque(int indice, String nombre, int danio) {
	            return new ResultadoTurnoJefe(Tipo.ATAQUE, 0, indice, nombre, danio);
	        }

	        public static ResultadoTurnoJefe sinObjetivo() {
	            return new ResultadoTurnoJefe(Tipo.SIN_OBJETIVO, 0, -1, null, 0);
	        }
	    }

	    public static class ResultadoTurno {
	        public enum Estado { VICTORIA, DERROTA, CONTINUA }

	        public final Estado estado;
	        public final ResultadoTurnoJefe turnoJefe;

	        private ResultadoTurno(Estado estado, ResultadoTurnoJefe turnoJefe) {
	            this.estado = estado;
	            this.turnoJefe = turnoJefe;
	        }

	        public static ResultadoTurno victoria() {
	            return new ResultadoTurno(Estado.VICTORIA, null);
	        }

	        public static ResultadoTurno derrota(ResultadoTurnoJefe turnoJefe) {
	            return new ResultadoTurno(Estado.DERROTA, turnoJefe);
	        }

	        public static ResultadoTurno continua(ResultadoTurnoJefe turnoJefe) {
	            return new ResultadoTurno(Estado.CONTINUA, turnoJefe);
	        }
	    }
	}

