package co.edu.uniquindio.poo.Escenario4;

public class Producto {

    private final String codigo;
    private final String nombre;
    private final double precio;

    public Producto(String codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | $%.2f", codigo, nombre, precio);
    }
}
