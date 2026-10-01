package co.edu.uniquindio.poo.Escenario3;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Clase principal del Escenario 3: Sistema de gestión de solicitudes de taxi.
 *
 * <p>El programa tiene dos partes:</p>
 * <ol>
 *     <li><b>Prueba de rendimiento:</b> inserta una cantidad masiva de solicitudes
 *     en el sistema y mide el tiempo de ejecución y la memoria utilizada.</li>
 *     <li><b>Menú interactivo:</b> permite al usuario agregar, atender, cancelar
 *     y consultar solicitudes desde la consola.</li>
 * </ol>
 *
 * @author Universidad del Quindío
 * @version 1.1
 */
public class Main {

    /** Cantidad de solicitudes que se insertan durante la prueba de rendimiento. */
    private static final int CANTIDAD_PRUEBA = 100_000;

    /** Opción del menú que finaliza el programa. */
    private static final int OPCION_SALIR = 5;

    /**
     * Punto de entrada del programa.
     * Ejecuta primero la prueba de rendimiento y luego muestra el menú interactivo.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {

        SistemaTaxi sistema = new SistemaTaxi();

        ejecutarPruebaRendimiento(sistema);

        Scanner teclado = new Scanner(System.in);
        ejecutarMenu(sistema, teclado);
        teclado.close();
    }

    // =====================================================================
    //                       PRUEBA DE RENDIMIENTO
    // =====================================================================

    /**
     * Inserta {@value #CANTIDAD_PRUEBA} solicitudes en el sistema y mide:
     * <ul>
     *     <li>El tiempo total de inserción (en nanosegundos y milisegundos).</li>
     *     <li>La memoria aproximada consumida por las solicitudes creadas.</li>
     * </ul>
     *
     * <p><b>Nota:</b> la medición de memoria es aproximada, ya que depende del
     * recolector de basura (Garbage Collector) de la JVM.</p>
     *
     * @param sistema sistema de taxis en el que se insertan las solicitudes
     */
    private static void ejecutarPruebaRendimiento(SistemaTaxi sistema) {

        Runtime runtime = Runtime.getRuntime();

        // Se sugiere a la JVM liberar memoria antes de medir, para una lectura más limpia
        runtime.gc();
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long inicio = System.nanoTime();

        for (int i = 1; i <= CANTIDAD_PRUEBA; i++) {
            Usuario usuario = new Usuario("Usuario" + i, i);
            Solicitud solicitud = new Solicitud(i, LocalDate.of(2026, 9, 30), usuario);
            sistema.agregarSolicitud(solicitud);
        }

        long fin = System.nanoTime();

        long memoriaDespues = runtime.totalMemory() - runtime.freeMemory();

        long tiempoNs = fin - inicio;
        long memoriaUsada = memoriaDespues - memoriaAntes;

        System.out.println("===== PRUEBA DE RENDIMIENTO =====");
        System.out.println("Solicitudes insertadas: " + CANTIDAD_PRUEBA);
        System.out.println("Tiempo: " + tiempoNs + " ns (" + (tiempoNs / 1_000_000.0) + " ms)");
        System.out.println("Memoria utilizada: " + memoriaUsada + " bytes ("
                + (memoriaUsada / (1024.0 * 1024.0)) + " MB)");
    }

    // =====================================================================
    //                          MENÚ INTERACTIVO
    // =====================================================================

    /**
     * Muestra el menú principal de forma repetida hasta que el usuario
     * seleccione la opción de salir.
     *
     * @param sistema sistema de taxis sobre el que se realizan las operaciones
     * @param teclado lector de la entrada estándar
     */
    private static void ejecutarMenu(SistemaTaxi sistema, Scanner teclado) {

        int opcion = 0;

        while (opcion != OPCION_SALIR) {

            mostrarMenu();
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1 -> agregarSolicitud(sistema, teclado);
                case 2 -> atenderSolicitud(sistema);
                case 3 -> cancelarSolicitud(sistema, teclado);
                case 4 -> mostrarPendientes(sistema);
                case 5 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Imprime en consola las opciones disponibles del menú.
     */
    private static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE TAXIS =====");
        System.out.println("1. Agregar solicitud");
        System.out.println("2. Atender solicitud");
        System.out.println("3. Cancelar solicitud");
        System.out.println("4. Mostrar solicitudes pendientes");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Opción 1: solicita los datos al usuario y registra una nueva solicitud.
     *
     * <p>El ID de la solicitud debe ser único; si ya existe, se vuelve a pedir
     * hasta que se ingrese uno disponible. La fecha se asigna automáticamente
     * con la fecha actual.</p>
     *
     * @param sistema sistema donde se agrega la solicitud
     * @param teclado lector de la entrada estándar
     */
    private static void agregarSolicitud(SistemaTaxi sistema, Scanner teclado) {

        int idSolicitud;

        // Se repite hasta obtener un ID que no esté registrado
        while (true) {
            System.out.print("Ingrese el ID de la solicitud: ");
            idSolicitud = teclado.nextInt();

            if (!sistema.existeSolicitud(idSolicitud)) {
                break;
            }
            System.out.println("Ese ID ya existe. Ingrese otro.");
        }

        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = teclado.next();

        System.out.print("Ingrese el ID del usuario: ");
        int idUsuario = teclado.nextInt();

        Usuario usuario = new Usuario(nombre, idUsuario);
        Solicitud solicitud = new Solicitud(idSolicitud, LocalDate.now(), usuario);

        sistema.agregarSolicitud(solicitud);
        System.out.println("Solicitud agregada correctamente.");
    }

    /**
     * Opción 2: atiende la siguiente solicitud pendiente del sistema
     * y muestra su información.
     *
     * @param sistema sistema del que se atiende la solicitud
     */
    private static void atenderSolicitud(SistemaTaxi sistema) {

        Solicitud atendida = sistema.atenderSolicitud();

        if (atendida != null) {
            System.out.println("Solicitud atendida:");
            System.out.println(atendida);
        } else {
            System.out.println("No hay solicitudes pendientes.");
        }
    }

    /**
     * Opción 3: cancela una solicitud a partir de su ID.
     *
     * @param sistema sistema del que se cancela la solicitud
     * @param teclado lector de la entrada estándar
     */
    private static void cancelarSolicitud(SistemaTaxi sistema, Scanner teclado) {

        System.out.print("Ingrese el ID de la solicitud a cancelar: ");
        int idCancelar = teclado.nextInt();

        if (sistema.cancelarSolicitud(idCancelar)) {
            System.out.println("Solicitud cancelada correctamente.");
        } else {
            System.out.println("No se encontró una solicitud con ese ID.");
        }
    }

    /**
     * Opción 4: muestra en consola todas las solicitudes pendientes.
     *
     * @param sistema sistema del que se consultan las solicitudes
     */
    private static void mostrarPendientes(SistemaTaxi sistema) {
        System.out.println("\nSolicitudes pendientes:");
        sistema.consultarSolicitudesPendientes();
    }
}