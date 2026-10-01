package co.edu.uniquindio.poo.Escenario3;

/**
 * Representa a un usuario que solicita servicios de taxi.
 *
 * <p>Cada usuario tiene un nombre y un identificador. Un usuario se
 * asocia a una o varias {@link Solicitud} dentro del {@link SistemaTaxi}.</p>
 *
 * @author Universidad del Quindío
 * @version 1.1
 */
public class Usuario {

    /** Nombre del usuario. */
    private String nombre;

    /** Identificador del usuario. */
    private int id;

    /**
     * Crea un nuevo usuario.
     *
     * @param nombre nombre del usuario
     * @param id     identificador del usuario
     */
    public Usuario(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    // =====================================================================
    //                          GETTERS Y SETTERS
    // =====================================================================

    /**
     * Retorna el nombre del usuario.
     *
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del usuario.
     *
     * @param nombre nuevo nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el identificador del usuario.
     *
     * @return ID del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador del usuario.
     *
     * @param id nuevo ID del usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    // =====================================================================
    //                              MÉTODOS
    // =====================================================================

    /**
     * Retorna una representación en texto del usuario con su nombre e ID.
     *
     * @return información del usuario en formato legible
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + " (ID: " + id + ")";
    }
}