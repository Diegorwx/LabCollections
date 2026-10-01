package co.edu.uniquindio.poo.Escenario2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class CatalogoProductos {
    private int totalProductos = 0;

    private final LinkedList<Producto> ordenIngreso = new LinkedList<>();

    // Inserta el producto y actualiza los 4 índices
    public void agregarProducto(Producto producto) {
        porCodigo.put(producto.getCodigo(), producto);
        ordenIngreso.addFirst(producto);
        porPrecio.computeIfAbsent(producto.getPrecio(), k -> new ArrayList<>()).add(producto);
        porCategoria.computeIfAbsent(producto.getCategoria(), k -> new ArrayList<>()).add(producto);
        totalProductos++;
    }

    // Productos en orden de llegada
    public List<Producto> listarOrdenIngreso() {
        return Collections.unmodifiableList(ordenIngreso);
    }

    private final Map<String, Producto> porCodigo = new HashMap<>();

    /** Búsqueda por código */
    public Producto buscarPorCodigo(String codigo) {
        return porCodigo.get(codigo);
    }

    private final TreeMap<Double, List<Producto>> porPrecio = new TreeMap<>();

    /** Lista completa ordenada por precio */
    public List<Producto> listarOrdenadoPorPrecio() {
        List<Producto> resultado = new ArrayList<>(totalProductos);
        for (List<Producto> mismosPrecio : porPrecio.values()) {
            resultado.addAll(mismosPrecio);
        }
        return resultado;
    }

    private final Map<String, List<Producto>> porCategoria = new HashMap<>();

    /** Filtra productos de una categoría*/
    public List<Producto> filtrarPorCategoria(String categoria) {
        return porCategoria.getOrDefault(categoria, Collections.emptyList());
    }

    public int size() {
        return totalProductos;
    }
}
