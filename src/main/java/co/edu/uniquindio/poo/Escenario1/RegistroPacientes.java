package co.edu.uniquindio.poo.Escenario1;

import java.util.*;

/**
 * Esta es la clase principal del sistema. Aquí se registran, buscan y atienden
 * los pacientes.
 *
 * Uso tres estructuras al mismo tiempo porque ninguna sola cumple con todo
 * lo que pide el hospital:
 * - HashMap: para buscar un paciente por cédula y saber si ya existe, en O(1).
 * - ArrayDeque: funciona como una fila normal, guarda el orden en que llegaron.
 * - PriorityQueue: ordena a los pacientes por gravedad para atender primero a los más graves.
 *
 * Las tres guardan el mismo objeto Paciente, no copias. Así que no se gasta
 * el triple de memoria, solo se guardan referencias al mismo paciente.
 */
public class RegistroPacientes{

    // La clave es la cédula y el valor es el paciente.
    // Es lo que hace que buscar y detectar duplicados sea tan rápido,
    // porque va directo al paciente sin tener que recorrer nada.
    private final Map<String,Paciente> porDocumento = new HashMap <>();

    // Fila de espera en orden de llegada. Los nuevos entran por el final
    // y se atienden por el frente (el primero que llega es el primero que sale).
    private final Deque<Paciente> porLlegada = new ArrayDeque<>();

    // Cola de prioridad para el triage. Siempre deja adelante al paciente
    // con menor número de gravedad (el 1 es el más grave).
    // Si dos tienen la misma gravedad, va primero el que llegó antes.
    // Al principio lo tenía desempatando por nombre, pero eso no tenía sentido:
    // atendería a "Ana" antes que a "Zoe" solo por el orden alfabético.
    private final PriorityQueue<Paciente> porGravedad = new PriorityQueue<>(
            Comparator.comparingInt(Paciente::getGravedad).
                    thenComparingLong(Paciente::getLlegada));

    // Lleva la cuenta de cuántos pacientes han llegado, para asignarle
    // a cada uno su número de llegada.
    private long contadorLlegada = 0;


    /**
     * Registra un paciente nuevo.
     *
     * Primero revisa que la cédula no esté registrada. Si ya existe, no hace nada
     * y devuelve false. Si es nuevo, le asigna su número de llegada y lo agrega
     * a las tres estructuras.
     *
     * Complejidad: O(1) en el HashMap y en la fila, y O(log n) en la cola
     * de prioridad, porque tiene que reacomodarse para dejar al más grave adelante.
     *
     * @return true si se registró, false si la cédula ya existía
     */
    public boolean registrarPaciente(String nombre, String cedula, int gravedad){
        if(porDocumento.containsKey(cedula)){
            return false;
        }
        contadorLlegada++;
        Paciente paciente = new Paciente(nombre, cedula, gravedad, contadorLlegada);
        porDocumento.put(cedula, paciente);
        porLlegada.addLast(paciente);
        porGravedad.add(paciente);
        return true;
    }

    /**
     * Busca un paciente por su cédula. Es O(1) gracias al HashMap.
     *
     * @return el paciente, o null si no hay nadie con esa cédula
     */
    public Paciente buscarPorCedulaPaciente(String cedula){
        return porDocumento.get(cedula);
    }

    /**
     * Dice si una cédula ya está registrada, sin devolver el paciente.
     * También es O(1).
     */
    public boolean existe (String cedula){
        return porDocumento.containsKey(cedula);
    }

    /**
     * Devuelve los pacientes en el orden en que llegaron.
     *
     * No hay que ordenar nada porque la fila ya se va llenando en orden.
     * Devuelvo una copia en un ArrayList para que desde afuera no se pueda
     * modificar la fila original por accidente. Recorrerla cuesta O(n).
     */
    public List<Paciente> listarPorLlegada(){
        return new ArrayList<>(porLlegada);
    }

    /**
     * Atiende al paciente que lleva más tiempo esperando (el primero de la fila).
     *
     * Sacarlo de la fila es O(1), pero también hay que quitarlo de las otras
     * dos estructuras para que no quede "fantasma" ahí. En el HashMap eso es O(1),
     * pero en la PriorityQueue es O(n) porque tiene que buscarlo primero.
     *
     * @return el paciente atendido, o null si no hay nadie esperando
     */
    public Paciente atenderPorLlegada(){
        Paciente paciente = porLlegada.poll();
        if(paciente != null){
            porDocumento.remove(paciente.getCedula());
            porGravedad.remove(paciente);
        }
        return paciente;
    }

    /**
     * Atiende al paciente más grave. Si hay empate, al que llegó primero.
     *
     * Sacarlo de la cola de prioridad es O(log n). Igual que en el método
     * anterior, hay que quitarlo de las otras estructuras, y en la fila eso
     * cuesta O(n) porque hay que buscarlo.
     *
     * Una forma de mejorar esto sería no borrarlo de la otra estructura,
     * sino marcarlo como "atendido" y saltarlo cuando aparezca, pero para
     * este trabajo lo dejé así porque es más fácil de entender.
     *
     * @return el paciente atendido, o null si no hay nadie esperando
     */
    public Paciente atenderPorGravedad(){
        Paciente paciente = porGravedad.poll();
        if(paciente != null){
            porDocumento.remove(paciente.getCedula());
            porLlegada.remove(paciente);
        }
        return paciente;
    }

    /**
     * Cuántos pacientes hay esperando en este momento.
     * Uso el tamaño del HashMap porque siempre tiene a todos los pacientes activos.
     */
    public int cantidadPacientes(){
        return porDocumento.size();
    }
}