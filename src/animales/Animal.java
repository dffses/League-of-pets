package animales;

public abstract class Animal {
    protected String nombre;
    protected Especie especie;
    protected Genero genero;

    public Animal(String nombre, Especie especie, Genero genero) {
        this.nombre = nombre;
        this.especie = especie;
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "nombre='" + nombre + '\'' +
                ", especie=" + especie +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }


    public abstract void comer();

    public abstract void bañarse();

    public abstract void jugar();

    public abstract void pasear();

}
