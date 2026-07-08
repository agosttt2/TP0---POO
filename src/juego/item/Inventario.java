package juego.item;

import juego.modelo.Personaje;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventario {

    private final Map<String, List<Item>> equipoPorPersonaje = new HashMap<>();
    private final List<Consumible> consumibles = new ArrayList<>();

    public void agregarEquipo(String nombrePersonaje, Item item) {
        equipoPorPersonaje
                .computeIfAbsent(nombrePersonaje, k -> new ArrayList<>())
                .add(item);
    }

    public List<Item> getEquipoDe(String nombrePersonaje) {
        return equipoPorPersonaje.getOrDefault(nombrePersonaje, new ArrayList<>());
    }

    public void aplicarEquipoA(Personaje personaje) {
        for (Item item : getEquipoDe(personaje.getNombre())) {
            item.aplicar(personaje);
        }
    }

    public void limpiar() {
        equipoPorPersonaje.clear();
        consumibles.clear();
    }

    public void agregarConsumible(Consumible consumible) {
        consumibles.add(consumible);
    }

    public List<Consumible> getConsumibles() {
        return consumibles;
    }

    public boolean tieneConsumibles() {
        return !consumibles.isEmpty();
    }

    public void usarConsumible(Consumible consumible, Personaje objetivo) {
        consumible.aplicar(objetivo);
        consumibles.remove(consumible);
    }
}
