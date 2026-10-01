package co.edu.uniquindio.poo.Escenario3;

import java.time.LocalDate;

/**
 * Representa una solicitud de taxi realizada por un usuario.
 *
 * <p>Cada solicitud tiene un identificador único, la fecha en que se
 * realizó y el usuario que la hizo. Las solicitudes son gestionadas
 * por la clase {@link SistemaTaxi}.</p>
 *
 * @author Universidad del Quindío
 * @version 1.1
 */
public class Solicitud {

    /** Identificador único de la solicitud. */
    private int solicitudId;

    /** Fecha en la que se realizó la solicitud. */
    private LocalDate solicitudFecha;

    /** Usuario que realizó la solicitud. */
    private Usuario usuario;

    /**
     * Crea una nueva solicitud de taxi.
     *
     * @param solicitudId    identificador único de la solicitud
     * @param solicitudFecha fecha en la que se realiza la solicitud
     * @param usuario        usuario que realiza la solicitud
     */
    public Solicitud(int solicitudId, LocalDate solicitudFecha, Usuario usuario) {
        this.solicitudId = solicitudId;
        this.solicitudFecha = solicitudFecha;
        this.usuario = usuario;
    }

    // =====================================================================
    //                          GETTERS Y SETTERS
    // =====================================================================

    /**
     * Retorna el identificador de la solicitud.
     *
     * @return ID de la solicitud
     */
    public int getSolicitudId() {
        return solicitudId;
    }

    /**
     * Modifica el identificador de la solicitud.
     *
     * <p><b>Precaución:</b> si la solicitud ya está registrada en un
     * {@link SistemaTaxi}, cambiar su ID podría generar IDs repetidos,
     * ya que esta clase no valida la unicidad.</p>
     *
     * @param solicitudId nuevo ID de la solicitud
     */
    public void setSolicitudId(int solicitudId) {
        this.solicitudId = solicitudId;
    }

    /**
     * Retorna la fecha en la que se realizó la solicitud.
     *
     * @return fecha de la solicitud
     */
    public LocalDate getSolicitudFecha() {
        return solicitudFecha;
    }

    /**
     * Modifica la fecha de la solicitud.
     *
     * @param solicitudFecha nueva fecha de la solicitud
     */
    public void setSolicitudFecha(LocalDate solicitudFecha) {
        this.solicitudFecha = solicitudFecha;
    }

    /**
     * Retorna el usuario que realizó la solicitud.
     *
     * @return usuario asociado a la solicitud
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Modifica el usuario asociado a la solicitud.
     *
     * @param usuario nuevo usuario de la solicitud
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // =====================================================================
    //                              MÉTODOS
    // =====================================================================

    /**
     * Retorna una representación en texto de la solicitud, con su ID,
     * el nombre del usuario y la fecha.
     *
     * <p>Si la solicitud no tiene usuario asignado, se muestra
     * "Sin usuario" en lugar de lanzar una excepción.</p>
     *
     * @return información de la solicitud en formato legible
     */
    @Override
    public String toString() {
        String nombreUsuario = (usuario != null) ? usuario.getNombre() : "Sin usuario";

        return "Id: " + solicitudId
                + "\nUsuario: " + nombreUsuario
                + "\nFecha: " + solicitudFecha;
    }
}