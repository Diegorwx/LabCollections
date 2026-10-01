package co.edu.uniquindio.poo.Escenario4;

import java.util.Random;

public class Main {

    private static final Random random = new Random(42); // reproducible

    public static void main(String[] args) {
        int[] tamanos = {100, 1_000, 10_000, 100_000};

        System.out.println("Catálogo de E-commerce");
        System.out.println();

        for (int n : tamanos) {
            ejecutarPrueba(n);
        }

    }

    /** Crea un catálogo con n productos y mide tiempo/memoria de cada operación. */
    private static void ejecutarPrueba(int n) {
        CatalogoProductos catalogo = new CatalogoProductos();
        Runtime runtime = Runtime.getRuntime();

        runtime.gc();
        long memAntes = runtime.totalMemory() - runtime.freeMemory();
        long inicioInsercion = System.nanoTime();

        for (int i = 0; i < n; i++) {
            catalogo.agregarProducto(generarProducto(i));
        }

        long finInsercion = System.nanoTime();
        long memDespues = runtime.totalMemory() - runtime.freeMemory();

        int busquedas = Math.min(1000, n);
        long inicioBusqueda = System.nanoTime();
        for (int i = 0; i < busquedas; i++) {
            catalogo.buscarPorCodigo("PROD-" + random.nextInt(n));
        }
        long finBusqueda = System.nanoTime();

        long inicioOrden = System.nanoTime();
        catalogo.listarOrdenadoPorPrecio();
        long finOrden = System.nanoTime();

        imprimirResultados(n, inicioInsercion, finInsercion, inicioBusqueda, finBusqueda,
                busquedas, inicioOrden, finOrden, memAntes, memDespues);
    }

    private static Producto generarProducto(int indice) {
        String codigo = "PROD-" + indice;
        double precio = Math.round((1000 + random.nextDouble() * 9000) * 100.0) / 100.0;
        return new Producto(codigo, "Producto " + indice, precio);
    }

    private static void imprimirResultados(int n, long inicioInsercion, long finInsercion,
                                           long inicioBusqueda, long finBusqueda, int busquedas,
                                           long inicioOrden, long finOrden, long memAntes, long memDespues) {

        double msInsercion = (finInsercion - inicioInsercion) / 1_000_000.0;
        double msBusquedaProm = (finBusqueda - inicioBusqueda) / 1_000_000.0 / busquedas;
        double msOrden = (finOrden - inicioOrden) / 1_000_000.0;
        double memUsadaMB = (memDespues - memAntes) / (1024.0 * 1024.0);

        System.out.printf("N = %,d productos%n", n);
        System.out.printf("  Inserción total:          %10.3f ms  (%.6f ms/producto)%n",
                msInsercion, msInsercion / n);
        System.out.printf("  Búsqueda por código:      %10.6f ms/promedio%n", msBusquedaProm);
        System.out.printf("  Listar ordenado (precio): %10.3f ms%n", msOrden);
        System.out.printf("  Memoria aproximada:       %10.2f MB%n", memUsadaMB);
        System.out.println();
    }
}