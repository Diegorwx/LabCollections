package co.edu.uniquindio.poo.Escenario1;

import java.util.ArrayList;
import java.util.List;

/**
 * Versión "sencilla" del registro, donde todo se guarda en un solo ArrayList.
 *
 * Esta clase no es parte de la solución final. La hice solo para comparar:
 * es lo primero que a uno se le ocurre, y funciona bien con pocos pacientes,
 * pero quería comprobar con mediciones reales qué pasa cuando los datos crecen.
 *
 * El problema es que la lista no sabe dónde está cada paciente, así que para
 * buscar a alguien o revisar si ya existe tiene que ir uno por uno desde el
 * principio. Eso es O(n), mientras que en el HashMap es O(1).
 */
public class RegistroSoloLista {

    // Aquí van todos los pacientes en el orden en que se registran.
    // Lo bueno es que el orden de llegada se mantiene solo, sin hacer nada extra.
    private final List<Paciente> pacientes = new ArrayList<>();

    /**
     * Registra un paciente si su cédula no está repetida.
     *
     * Antes de agregarlo recorre toda la lista buscando la cédula. Ahí está
     * el problema: con 10 pacientes no se nota, pero con 100.000 cada registro
     * tiene que revisar hasta 100.000 pacientes. Y como eso se repite por cada
     * paciente nuevo, registrar n pacientes termina costando O(n²).
     * En mis pruebas, registrar 100.000 pacientes tardó unos 22 segundos,
     * contra unos 40 milisegundos con el HashMap.
     *
     * El número de llegada lo saco del tamaño de la lista más 1, para que
     * el primer paciente sea el 1 y no el 0.
     *
     * @return true si se registró, false si la cédula ya existía
     */
    public boolean registrarPaciente(String nombre, String cedula, int gravedad){
        for(Paciente paciente : pacientes){
            if(paciente.getCedula().equals(cedula)){
                return false;
            }
        }
        Paciente paciente = new Paciente(nombre, cedula, gravedad, pacientes.size() + 1);
        pacientes.add(paciente);   // agregar al final sí es rápido, O(1)
        return true;
    }

    /**
     * Busca un paciente por cédula recorriendo la lista de principio a fin.
     *
     * Si el paciente está al principio lo encuentra rápido, pero si está al
     * final o no existe, tiene que revisar todos. En promedio recorre la mitad
     * de la lista, por eso la complejidad es O(n).
     *
     * @return el paciente, o null si no se encontró
     */
    public Paciente buscarPorCedulaPaciente(String cedula){
        for(Paciente paciente : pacientes){
            if(paciente.getCedula().equals(cedula)){
                return paciente;
            }
        }
        return null;
    }
}
