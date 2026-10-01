package co.edu.uniquindio.poo.Escenario1;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        pruebaFuncional();
        pruebaRendimiento();
    }

    private static void pruebaFuncional() {
        System.out.println("===== PARTE 1: PRUEBA FUNCIONAL =====\n");

        RegistroPacientes registro = new RegistroPacientes();

        // 1. Registrar pacientes al llegar
        System.out.println("1. Registrando pacientes...");
        registro.registrarPaciente("Ana", "1001", 4);
        registro.registrarPaciente("Luis", "1002", 1);
        registro.registrarPaciente("Marta", "1003", 3);
        registro.registrarPaciente("Pedro", "1004", 1);
        System.out.println("   Pacientes registrados: " + registro.cantidadPacientes());

        // 2. Evitar duplicados
        System.out.println("\n2. Intentando registrar a Ana otra vez (cédula 1001)...");
        boolean aceptado = registro.registrarPaciente("Ana", "1001", 4);
        System.out.println("   ¿Se aceptó? " + aceptado);

        // 3. Mantener el orden de llegada
        System.out.println("\n3. Pacientes en orden de llegada:");
        List<Paciente> lista = registro.listarPorLlegada();
        for (Paciente p : lista) {
            System.out.println("   " + p);
        }

        // 4. Buscar por cédula
        System.out.println("\n4. Buscando cédula 1003...");
        Paciente encontrado = registro.buscarPorCedulaPaciente("1003");
        System.out.println("   Encontrado: " + encontrado);
        System.out.println("   Buscando cédula 9999: " + registro.buscarPorCedulaPaciente("9999"));

        // 5. Atender por gravedad
        System.out.println("\n5. Orden de atención por gravedad:");
        while (registro.cantidadPacientes() > 0) {
            System.out.println("   Atendiendo a: " + registro.atenderPorGravedad());
        }

    }

    // PARTE 2: medir tiempo y memoria con distintos tamaños
    private static void pruebaRendimiento() {
        System.out.println("\n===== PARTE 2: PRUEBA DE RENDIMIENTO =====\n");
        System.out.println("Calentando la JVM, espere un momento...\n");

        MedidorRendimiento medidor = new MedidorRendimiento();
        medidor.calentarJVM();

        imprimirEncabezado();

        int[] tamanos = {100, 1_000, 10_000, 100_000, 1_000_000};
        for (int n : tamanos) {
            int repeticiones = (n >= 1_000_000) ? 3 : 5;
            imprimirFila("HashMap+Deque+PQ", n, medidor.medirConMediana(true, n, repeticiones));
        }
        // Comparación con la versión que usa solo ArrayList ,
        //  solo se pureba hasta 100.000 pacientes porque con 1.000.000
        // tarda demasiado tardando más de 3 minutos y no es necesario para
        // ver la diferencia de rendimiento.
        System.out.println("\nComparación con la versión que usa solo ArrayList:");
        int[] tamanosLista = {100, 1_000, 10_000, 100_000 };
        for (int n : tamanosLista) {
            int repeticiones = (n >= 100_000) ? 1 : 5;
            imprimirFila("Solo ArrayList", n, medidor.medirConMediana(false, n, repeticiones));
        }
    }

    private static void imprimirEncabezado() {
        System.out.printf("%-18s %10s %16s %15s %16s %14s%n",
                "Estructura", "n", "Registro (ms)", "Búsqueda (µs)", "Duplicado (µs)", "Memoria (MB)");
        System.out.println("-".repeat(94));
    }

    private static void imprimirFila(String nombre, int n, double[] r) {
        System.out.printf("%-18s %10d %16.3f %15.3f %16.3f %14.2f%n",
                nombre, n, r[0], r[1], r[2], r[3]);
    }
}