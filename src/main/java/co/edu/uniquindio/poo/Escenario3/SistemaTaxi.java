package co.edu.uniquindio.poo.Escenario3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Gestiona las solicitudes de taxi utilizando una <b>cola (FIFO)</b>.
 *
 * <p>Las solicitudes se atienden en el mismo orden en que llegan:
 * la primera solicitud registrada es la primera en ser atendida.</p>
 *
 * <p>Internamente se usa un {@link LinkedList} como implementación de
 * {@link Queue}, lo que permite insertar al final y retirar del inicio
 * en tiempo constante.</p>
 *
 * <p><b>Complejidad de las operaciones:</b></p>
 * <ul>
 *     <li>{@link #agregarSolicitud(Solicitud)}: O(1)</li>
 *     <li>{@link #atenderSolicitud()}: O(1)</li>
 *     <li>{@link #cancelarSolicitud(int)}: O(n)</li>
 *     <li>{@link #existeSolicitud(int)}: O(n)</li>
 *     <li>{@link #consultarSolicitudesPendientes()}: O(n)</li>
 * </ul>
 *
 * @author Universidad del Quindío
 * @version 1.1
 */
public class SistemaTaxi {

    /** Cola con las solicitudes pendientes, en orden de llegada. */
    private final Queue<Solicitud> solicitudes;

    /**
     * Crea un sistema de taxis sin solicitudes pendientes.
     */
    public SistemaTaxi() {
        solicitudes = new LinkedList<>();
    }

    /**
     * Agrega una nueva solicitud al final de la cola.
     *
     * <p>Complejidad: O(1).</p>
     *
     * @param solicitud solicitud que se desea registrar
     */
    public void agregarSolicitud(Solicitud solicitud) {
        solicitudes.offer(solicitud);
    }

    /**
     * Atiende la solicitud más antigua, retirándola de la cola.
     *
     * <p>Complejidad: O(1).</p>
     *
     * @return la solicitud atendida, o {@code null} si no hay solicitudes pendientes
     */
    public Solicitud atenderSolicitud() {
        // poll() ya devuelve null cuando la cola está vacía
        return solicitudes.poll();
    }

    /**
     * Cancela (elimina) la solicitud cuyo ID coincida con el indicado,
     * sin importar su posición en la cola.
     *
     * <p>Se usa un {@link Iterator} para poder eliminar elementos de forma
     * segura mientras se recorre la colección.</p>
     *
     * <p>Complejidad: O(n), ya que puede ser necesario recorrer toda la cola.</p>
     *
     * @param id identificador de la solicitud a cancelar
     * @return {@code true} si la solicitud se encontró y se eliminó;
     *         {@code false} si no existe una solicitud con ese ID
     */
    public boolean cancelarSolicitud(int id) {
        Iterator<Solicitud> iterator = solicitudes.iterator();

        while (iterator.hasNext()) {
            Solicitud solicitud = iterator.next();

            if (solicitud.getSolicitudId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Imprime en consola todas las solicitudes pendientes, en orden de llegada.
     * Si no hay solicitudes, muestra un mensaje indicándolo.
     *
     * <p>Complejidad: O(n).</p>
     */
    public void consultarSolicitudesPendientes() {
        if (solicitudes.isEmpty()) {
            System.out.println("No hay solicitudes pendientes.");
            return;
        }

        for (Solicitud solicitud : solicitudes) {
            System.out.println(solicitud);
            System.out.println();
        }
    }

    /**
     * Verifica si existe una solicitud pendiente con el ID indicado.
     *
     * <p>Complejidad: O(n).</p>
     *
     * @param id identificador de la solicitud a buscar
     * @return {@code true} si existe una solicitud con ese ID; {@code false} en caso contrario
     */
    public boolean existeSolicitud(int id) {
        for (Solicitud solicitud : solicitudes) {
            if (solicitud.getSolicitudId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna la cantidad de solicitudes pendientes.
     *
     * <p>Complejidad: O(1).</p>
     *
     * @return número de solicitudes en la cola
     */
    public int cantidadPendientes() {
        return solicitudes.size();
    }
}