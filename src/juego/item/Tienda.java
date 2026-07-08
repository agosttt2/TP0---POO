package juego.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tienda {

    private static int oro = 30000;
    private static final Inventario inventario = new Inventario();
    private static final Set<Item> equipoComprado = new HashSet<>();

    private static final List<Item> catalogo = new ArrayList<>();
    static {
        catalogo.add(new Arma("Espada mejorada", 1000, "Ataque Caballero +10", "Caballero", 10));
        catalogo.add(new Armadura("Escudo reforzado", 1000, "Defensa Caballero +5", "Caballero", 5));
        catalogo.add(new Arma("Arco élfico", 1000, "Ataque Arquera +10", "Arquera", 10));
        catalogo.add(new Arma("Báculo arcano", 1000, "Ataque Mago +10", "Mago", 10));
        catalogo.add(new Consumible("Poción de vida", 500, "Restaura 50 HP en combate", 50));
    }

    private Tienda() {}

    public static List<Item> getCatalogo() {
        return catalogo;
    }

    public static int getOro() {
        return oro;
    }

    public static Inventario getInventario() {
        return inventario;
    }

    public static boolean yaComprado(Item item) {
        return equipoComprado.contains(item);
    }

    public static boolean comprar(Item item) {
        if (!item.esConsumible() && yaComprado(item)) {
            return false;
        }
        if (oro < item.getPrecio()) {
            return false;
        }

        oro -= item.getPrecio();

        if (item.esConsumible()) {
            inventario.agregarConsumible((Consumible) item);
        } else {
            equipoComprado.add(item);
            inventario.agregarEquipo(item.getPersonajeObjetivo(), item);
        }
        return true;
    }

    public static void restaurarEstado(int oroGuardado, Set<String> nombresEquipoComprado, List<String> nombresConsumibles) {
        oro = oroGuardado;
        equipoComprado.clear();
        inventario.limpiar();

        for (Item item : catalogo) {
            if (!item.esConsumible() && nombresEquipoComprado.contains(item.getNombre())) {
                equipoComprado.add(item);
                inventario.agregarEquipo(item.getPersonajeObjetivo(), item);
            }
        }
        for (String nombreConsumible : nombresConsumibles) {
            for (Item item : catalogo) {
                if (item.esConsumible() && item.getNombre().equals(nombreConsumible)) {
                    inventario.agregarConsumible((Consumible) item);
                }
            }
        }
    }
}
