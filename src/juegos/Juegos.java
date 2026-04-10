package juegos;

public abstract class Juegos {
    protected int niveles;
    protected int mejorPuntuacion;

    public Juegos(int niveles, int mejorPuntuacion) {
        this.niveles = niveles;
        this.mejorPuntuacion = mejorPuntuacion;
    }
    public abstract void jugar();

}
