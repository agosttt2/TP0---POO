package juego.persistencia;

import juego.combate.Combate;
import juego.item.Item;
import juego.item.Tienda;
import juego.modelo.EstadoPersonaje;
import juego.modelo.Jefe;
import juego.modelo.Personaje;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Se encarga de guardar y cargar la partida. A diferencia de la versión
 * anterior, nunca toca "vida" ni "estado" como campos: todo pasa por
 * getters públicos del modelo y por restaurarEstado(...), que es el único
 * punto de entrada pensado para restaurar una partida.
 */
public class GuardadoPartida {

    private static final String ARCHIVO = "partida_guardada.txt";

    private GuardadoPartida() {}

    public static boolean existeGuardado() {
        return new File(ARCHIVO).exists();
    }

    public static void guardar(Combate combate) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {

            Jefe jefe = combate.getJefe();
            pw.println(jefe.getVida() + ";" + jefe.getEstado());

            pw.println(combate.getAliados().size());
            for (Personaje p : combate.getAliados()) {
                pw.println(p.getVida() + ";" + p.getEstado() + ";" + p.getNivel() + ";" + p.getExperiencia());
            }

            pw.println(Tienda.getOro());

            long equipoComprado = Tienda.getCatalogo().stream()
                    .filter(i -> !i.esConsumible() && Tienda.yaComprado(i))
                    .count();
            pw.println(equipoComprado);
            for (Item item : Tienda.getCatalogo()) {
                if (!item.esConsumible() && Tienda.yaComprado(item)) {
                    pw.println(item.getNombre());
                }
            }

            pw.println(Tienda.getInventario().getConsumibles().size());
            for (juego.item.Consumible consumible : Tienda.getInventario().getConsumibles()) {
                pw.println(consumible.getNombre());
            }

        } catch (IOException ex) {
            throw new RuntimeException("Error al guardar la partida", ex);
        }
    }

    public static DatosPartidaGuardada cargar() {
        DatosPartidaGuardada datos = new DatosPartidaGuardada();

        try (Scanner lector = new Scanner(new File(ARCHIVO))) {

            String[] lineaJefe = lector.nextLine().split(";");
            datos.vidaJefe = Integer.parseInt(lineaJefe[0]);
            datos.estadoJefe = EstadoPersonaje.valueOf(lineaJefe[1]);

            int cantidadPersonajes = Integer.parseInt(lector.nextLine());
            for (int i = 0; i < cantidadPersonajes; i++) {
                String[] partes = lector.nextLine().split(";");
                DatosPartidaGuardada.DatosPersonaje dp = new DatosPartidaGuardada.DatosPersonaje();
                dp.vida = Integer.parseInt(partes[0]);
                dp.estado = EstadoPersonaje.valueOf(partes[1]);
                dp.nivel = Integer.parseInt(partes[2]);
                dp.experiencia = Integer.parseInt(partes[3]);
                datos.personajes.add(dp);
            }

            datos.oro = Integer.parseInt(lector.nextLine());

            int cantidadEquipo = Integer.parseInt(lector.nextLine());
            for (int i = 0; i < cantidadEquipo; i++) {
                datos.equipoComprado.add(lector.nextLine());
            }

            int cantidadConsumibles = Integer.parseInt(lector.nextLine());
            for (int i = 0; i < cantidadConsumibles; i++) {
                datos.consumibles.add(lector.nextLine());
            }

        } catch (IOException ex) {
            throw new RuntimeException("Error al cargar la partida", ex);
        }

        return datos;
    }

    /** Aplica el estado guardado de la tienda ANTES de crear los personajes, para que hereden el equipo. */
    public static void restaurarTienda(DatosPartidaGuardada datos) {
        Tienda.restaurarEstado(datos.oro, datos.equipoComprado, datos.consumibles);
    }
}
