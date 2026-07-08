package juego.persistencia;

import juego.combate.OrquestadorCombate;
import juego.item.Tienda;
import juego.item.Item;
import juego.modelo.Personaje;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GuardadoPartida {

    private static final String ARCHIVO = "partida_guardada.txt";

    private GuardadoPartida() {}

    public static boolean existeGuardado() {
        return new File(ARCHIVO).exists();
    }

    public static void guardar(OrquestadorCombate gestor) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO));

            pw.println(gestor.getJefe().getVida());
            pw.println(gestor.getIndiceActual());

            Personaje[] party = gestor.getParty();
            pw.println(party.length);
            for (int i = 0; i < party.length; i++) {
                pw.println(party[i].getVida() + ";" +
                           party[i].getNivel() + ";" +
                           party[i].getExperiencia());
            }

            pw.println(Tienda.getOro());

            int equipoCount = 0;
            for (Item item : Tienda.getCatalogo()) {
                if (!item.esConsumible() && Tienda.yaComprado(item)) equipoCount++;
            }
            pw.println(equipoCount);
            for (Item item : Tienda.getCatalogo()) {
                if (!item.esConsumible() && Tienda.yaComprado(item)) {
                    pw.println(item.getNombre());
                }
            }

            pw.println(Tienda.getInventario().getConsumibles().size());
            for (juego.item.Consumible c : Tienda.getInventario().getConsumibles()) {
                pw.println(c.getNombre());
            }

            pw.close();

        } catch (IOException ex) {
            throw new RuntimeException("Error al guardar la partida", ex);
        }
    }

    public static void cargar(OrquestadorCombate gestor) {
        try {
            Scanner lector = new Scanner(new File(ARCHIVO));

            int vidaJefe        = Integer.parseInt(lector.nextLine());
            int indiceActual    = Integer.parseInt(lector.nextLine());

            gestor.getJefe().restaurarEstado(vidaJefe, 1, 0);

            int cantidadPersonajes = Integer.parseInt(lector.nextLine());
            Personaje[] party = gestor.getParty();

            for (int i = 0; i < cantidadPersonajes; i++) {
                String[] partes = lector.nextLine().split(";");
                int vida        = Integer.parseInt(partes[0]);
                int nivel       = Integer.parseInt(partes[1]);
                int exp         = Integer.parseInt(partes[2]);
                party[i].restaurarEstado(vida, nivel, exp);
            }

            int oroGuardado = Integer.parseInt(lector.nextLine());
            Tienda.agregarOro(oroGuardado - Tienda.getOro());

            int cantEquipo = Integer.parseInt(lector.nextLine());
            java.util.Set<String> nombresEquipo = new java.util.HashSet<>();
            for (int i = 0; i < cantEquipo; i++) {
                nombresEquipo.add(lector.nextLine());
            }

            int cantConsumibles = Integer.parseInt(lector.nextLine());
            java.util.List<String> nombresConsumibles = new java.util.ArrayList<>();
            for (int i = 0; i < cantConsumibles; i++) {
                nombresConsumibles.add(lector.nextLine());
            }

            Tienda.restaurarEstado(oroGuardado, nombresEquipo, nombresConsumibles);

            lector.close();

        } catch (Exception ex) {
            throw new RuntimeException("Error al cargar la partida", ex);
        }
    }
}