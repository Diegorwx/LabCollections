package co.edu.uniquindio.poo.Escenario1;

import java.util.*;

/**
 * Clase usada para  para medir qué tan rápido es el sistema de registro
 * y cuánta memoria gasta. Comparo dos versiones: la que usa HashMap + ArrayDeque
 * + PriorityQueue, y otra que guarda todo en un ArrayList, para ver en números
 * reales la diferencia que ya habíamos calculado en la fase de complejidad.
 */
public class MedidorRendimiento {

    // se utiliza la  semilla 42 para que los números aleatorios salgan iguales cada vez
    // que se corra el programa. Así puedo repetir las pruebas y comparar.
    private final Random rnd = new Random(42);

    /**
     * Se generan n cédulas de 8 dígitos sin repetir.
     * Uso un Set porque no deja meter valores duplicados, entonces el while
     * sigue hasta completar n cédulas distintas. Es LinkedHashSet para que
     * conserve el orden en que se fueron generando.
     */
    private List<String> generarCedulas(int n) {
        Set<String> set = new LinkedHashSet<>();
        while (set.size() < n) {
            set.add(String.valueOf(10_000_000 + rnd.nextInt(90_000_000)));
        }
        return new ArrayList<>(set);
    }

    /**
     * Devuelve cuánta memoria está usando el programa en ese momento (en bytes).
     * Antes de medir llamo al recolector de basura varias veces para que no cuente
     * objetos que ya no se usan. Igual es una medida aproximada, porque Java
     * decide por su cuenta cuándo limpia la memoria.
     */
    private long memoriaUsada() {
        Runtime rt = Runtime.getRuntime();
        for (int i = 0; i < 3; i++) {
            System.gc();
        }
        return rt.totalMemory() - rt.freeMemory();
    }

    /**
     * Saca k cédulas al azar de la lista para usarlas en las pruebas de búsqueda.
     * Si buscara siempre las primeras, la lista las encontraría de una vez y
     * la comparación no sería justa, por eso las revuelvo.
     */
    private List<String> muestra(List<String> cedulas, int k) {
        List<String> copia = new ArrayList<>(cedulas);
        Collections.shuffle(copia, rnd);
        return copia.subList(0, Math.min(k, copia.size()));
    }

    /**
     * Prueba mi solución (HashMap + ArrayDeque + PriorityQueue) con n pacientes.
     *
     * Devuelve un arreglo con 4 resultados en este orden:
     * [0] tiempo total en registrar a todos (milisegundos)
     * [1] tiempo promedio de una búsqueda (microsegundos)
     * [2] tiempo promedio en detectar un duplicado (microsegundos)
     * [3] memoria que ocupó el registro (MB)
     */
    public double[] medirSolucionPropuesta(int n) {
        List<String> cedulas = generarCedulas(n);
        long memAntes = memoriaUsada();

        // Registro: tomo el tiempo antes y después de meter a todos los pacientes.
        // La gravedad la pongo al azar entre 1 y 5.
        RegistroPacientes registro = new RegistroPacientes();
        long inicio = System.nanoTime();
        for (String c : cedulas) {
            registro.registrarPaciente("Paciente " + c, c, 1 + rnd.nextInt(5));
        }
        double tRegistro = (System.nanoTime() - inicio) / 1e6;   // nanosegundos a milisegundos

        // La memoria del registro es la diferencia entre antes y después
        double memoria = (memoriaUsada() - memAntes) / (1024.0 * 1024.0);   // bytes a MB

        // Búsqueda: busco 1000 cédulas al azar y saco el promedio de una sola
        List<String> m = muestra(cedulas, 1000);
        inicio = System.nanoTime();
        for (String c : m) {
            registro.buscarPorCedulaPaciente(c);
        }
        double tBusqueda = (System.nanoTime() - inicio) / 1e3 / m.size();

        // Duplicados: intento registrar otra vez cédulas que ya existen.
        // Todas deberían ser rechazadas, lo que mido es cuánto tarda en darse cuenta.
        inicio = System.nanoTime();
        for (String c : m) {
            registro.registrarPaciente("Duplicado", c, 5);
        }
        double tDuplicado = (System.nanoTime() - inicio) / 1e3 / m.size();

        return new double[]{tRegistro, tBusqueda, tDuplicado, memoria};
    }

    /**
     * Hace exactamente las mismas pruebas pero con la versión de ArrayList,
     * para tener con qué comparar. Devuelve el arreglo en el mismo orden
     * que medirSolucionPropuesta.
     */
    public double[] medirSoloLista(int n) {
        List<String> cedulas = generarCedulas(n);
        long memAntes = memoriaUsada();

        RegistroSoloLista registro = new RegistroSoloLista();
        long inicio = System.nanoTime();
        for (String c : cedulas) {
            registro.registrarPaciente("Paciente " + c, c, 1 + rnd.nextInt(5));
        }
        double tRegistro = (System.nanoTime() - inicio) / 1e6;

        double memoria = (memoriaUsada() - memAntes) / (1024.0 * 1024.0);

        // Aquí uso solo 200 búsquedas en vez de 1000 porque en la lista cada
        // búsqueda recorre todos los elementos y con muchos datos se demora bastante
        List<String> m = muestra(cedulas, 200);
        inicio = System.nanoTime();
        for (String c : m) {
            registro.buscarPorCedulaPaciente(c);
        }
        double tBusqueda = (System.nanoTime() - inicio) / 1e3 / m.size();

        inicio = System.nanoTime();
        for (String c : m) {
            registro.registrarPaciente("Duplicado", c, 5);
        }
        double tDuplicado = (System.nanoTime() - inicio) / 1e3 / m.size();

        return new double[]{tRegistro, tBusqueda, tDuplicado, memoria};
    }

    /**
     * Repite la prueba varias veces y se queda con la mediana (el valor del medio).
     *
     * Lo hice así porque al correr el programa me daban números distintos cada vez,
     * a veces con picos raros. Eso pasa porque Java de repente activa el recolector
     * de basura en medio de la medición. La mediana ignora esos picos, a diferencia
     * del promedio, que sí se deja afectar por ellos.
     *
     * @param propuesta    true para medir mi solución, false para la de ArrayList
     * @param n            cantidad de pacientes
     * @param repeticiones cuántas veces se repite la prueba
     */
    public double[] medirConMediana(boolean propuesta, int n, int repeticiones) {
        double[][] resultados = new double[repeticiones][];
        for (int i = 0; i < repeticiones; i++) {
            resultados[i] = propuesta ? medirSolucionPropuesta(n) : medirSoloLista(n);
        }

        // Para cada uno de los 4 valores (registro, búsqueda, duplicado, memoria)
        // junto los resultados de todas las repeticiones, los ordeno y tomo el del medio
        double[] mediana = new double[4];
        for (int j = 0; j < 4; j++) {
            double[] columna = new double[repeticiones];
            for (int i = 0; i < repeticiones; i++) {
                columna[i] = resultados[i][j];
            }
            Arrays.sort(columna);
            // Con pocos pacientes la memoria a veces salía negativa por el mismo
            // ruido del recolector, así que si pasa la dejo en 0
            mediana[j] = Math.max(0, columna[repeticiones / 2]);
        }
        return mediana;
    }

    /**
     * Corre las pruebas unas cuantas veces antes de medir "en serio".
     *
     * Java no ejecuta el código a toda velocidad desde el principio: mientras el
     * programa corre, va detectando las partes que más se usan y las optimiza
     * (esto lo hace el compilador JIT). Si no calentaba primero, las mediciones
     * con 100 y 1000 pacientes salían más lentas de lo que realmente son.
     */
    public void calentarJVM() {
        for (int i = 0; i < 5; i++) {
            medirSolucionPropuesta(10_000);
            medirSoloLista(2_000);
        }
    }
}