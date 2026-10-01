package co.edu.uniquindio.poo.Escenario4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class CatalogoProductos {

    private final Map<String, Producto> porCodigo = new HashMap<>();
    private final TreeMap<Double, List<Producto>> porPrecio = new TreeMap<>();
    private int totalProductos = 0;

    /** Inserta el producto en ambas estructuras */
    public void agregarProducto(Producto producto) {
        porCodigo.put(producto.getCodigo(), producto);
        porPrecio.computeIfAbsent(producto.getPrecio(), k -> new ArrayList<>()).add(producto);
        totalProductos++;
    }

    /** Búsqueda por código */
    public Producto buscarPorCodigo(String codigo) {
        return porCodigo.get(codigo);
    }

    /** Lista ordenada por precio*/
    public List<Producto> listarOrdenadoPorPrecio() {
        List<Producto> resultado = new ArrayList<>(totalProductos);
        for (List<Producto> mismosPrecio : porPrecio.values()) {
            resultado.addAll(mismosPrecio);
        }
        return resultado;
    }

    public int size() {
        return totalProductos;
    }
}