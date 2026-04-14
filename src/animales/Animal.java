package animales;

public abstract class Animal {
    protected String nombre;
    protected Especie especie;
    protected Genero genero;
    protected int hambre;
    protected int felicidad;
    protected int limpieza;
    protected int energia;

    public Animal(String nombre, Especie especie, Genero genero) {
        this.nombre = nombre;
        this.especie = especie;
        this.genero = genero;
        this.hambre = 50;
        this.felicidad = 50;
        this.limpieza = 50;
        this.energia = 50;
    }


    public void comer() {
        hambre -= 10;
        felicidad += 5;
        limitarValores();
    }

    public void banar() {
        limpieza = 100;
        felicidad += 5;
        limitarValores();
    }

    public void pasear() {
        energia -= 10;
        felicidad += 10;
        limitarValores();
    }

    public void jugar() {
        energia -= 10;
        felicidad += 15;
        limitarValores();
    }


    protected void limitarValores() {
        hambre = Math.max(0, Math.min(100, hambre));
        felicidad = Math.max(0, Math.min(100, felicidad));
        limpieza = Math.max(0, Math.min(100, limpieza));
        energia = Math.max(0, Math.min(100, energia));
    }


    public void actualizarEstado() {
        hambre += 2;
        energia -= 2;
        limpieza -= 1;
        limitarValores();
    }


    public String getNombre() {
        return nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getHambre() {
        return hambre;
    }

    public int getFelicidad() {
        return felicidad;
    }

    public int getLimpieza() {
        return limpieza;
    }

    public int getEnergia() {
        return energia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "nombre='" + nombre + '\'' +
                ", especie=" + especie +
                ", genero=" + genero +
                ", hambre=" + hambre +
                ", felicidad=" + felicidad +
                ", limpieza=" + limpieza +
                ", energia=" + energia +
                '}';
    }
}
