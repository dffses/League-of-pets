package sistema;

import java.io.Serializable;

public class Monedas implements Serializable {

    private static  final long serialVersionUID=1L;
    private int cantidad;

    public Monedas(){
        this.cantidad=0;
    }

    public Monedas(int cantidadInicial){
        this.cantidad=cantidadInicial;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Añade monedas al saldo actual.
     * @param cantidad Cantidad de monedas a sumar (debe ser positiva).
     */
    public void ganarMonedas(int cantidad) {
        if (cantidad > 0) {
            this.cantidad += cantidad;
            System.out.println("¡Has ganado " + cantidad + " monedas! Saldo actual: " + this.cantidad);
        }
    }

    /**
     * Intenta restar monedas para una compra.
     * @param cantidad Coste del artículo (debe ser positivo).
     * @return true si la operación fue exitosa, false si no hay saldo suficiente.
     */
    public boolean gastarMonedas(int cantidad) {
        if (cantidad > 0 && this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
            System.out.println("Compra realizada. Gastadas " + cantidad + " monedas. Saldo restante: " + this.cantidad);
            return true;
        } else {
            System.out.println("Error: Monedas insuficientes o cantidad inválida.");
            return false;
        }
    }
}
