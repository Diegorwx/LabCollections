package co.edu.uniquindio.poo.Escenario1;

/**
 * Representa a un paciente que llega al hospital.
 *
 * Esta clase solo guarda los datos del paciente, no hace ninguna operación.
 * Todo lo de registrar, buscar y atender lo maneja RegistroPacientes.
 */
public class Paciente {

    // La cédula es lo que identifica a cada paciente. Es la clave que uso
    // en el HashMap para buscarlo y para saber si ya está registrado.
    String cedula;

    String nombre;

    // Nivel de gravedad del 1 al 5, donde 1 es el más grave.
    // Lo usa la PriorityQueue para decidir a quién se atiende primero.
    int gravedad;

    // Número de llegada: el primero que llega es el 1, el segundo el 2, y así.
    // Sirve para desempatar cuando dos pacientes tienen la misma gravedad,
    // porque en ese caso se atiende primero al que llegó antes.
    // Es long y no int por si el sistema llega a registrar muchísimos pacientes.
    long llegada;

    /**
     * Crea un paciente con sus datos.
     * El número de llegada no lo pongo yo a mano, lo asigna RegistroPacientes
     * con un contador cada vez que se registra a alguien nuevo.
     */
    public Paciente(String nombre, String cedula, int gravedad, long llegada) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.gravedad = gravedad;
        this.llegada = llegada;
    }

    // Getters para poder leer los datos desde las otras clases.
    // Los de gravedad y llegada los necesita el Comparator de la PriorityQueue.

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getGravedad() {
        return gravedad;
    }

    public long getLlegada() {
        return llegada;
    }

    /**
     * Muestra los datos del paciente en texto.
     * Sin esto, al imprimir un paciente saldría algo como Paciente@1b6d3586,
     * que no dice nada. Así en la consola se ve clarito quién es cada uno.
     */
    @java.lang.Override
    public java.lang.String toString() {
        return "Paciente{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", gravedad=" + gravedad +
                ", llegada=" + llegada +
                '}';
    }
}