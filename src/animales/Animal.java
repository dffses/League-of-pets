package animales;

public abstract class Animal {
    protected String nombre;
    protected Especie especie;
    protected Genero genero;
    protected int hambre;
    protected int felicidad;
    protected int limpieza;
    protected int energia;
    //atributos que añadí nuevos
    protected int nivel;
    protected int experiencia;

    public Animal(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int limpieza, int energia, int nivel, int experiencia) {
        this.nombre = nombre;
        this.especie = especie;
        this.genero = genero;
        this.hambre = 50;
        this.felicidad = 50;
        this.limpieza = 50;
        this.energia = 50;
        this.nivel = 1;
        this.experiencia = 0;
    }

    /**
     * Incrementa la experiencia de la mascota. Si alcanza 100 puntos, sube de nivel.
     */
    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad;
        if (this.experiencia >= 100) {
            this.nivel++;
            this.experiencia = 0; // se resetea la experiencia para el siguiente nivel
            System.out.println("¡Genial! " + nombre + " ha subido al nivel " + nivel);
        }
    }

    public void comer() {
        hambre -= 10;
        felicidad += 5;
        ganarExperiencia(15);
        limitarValores();
    }

    public void bañar() {
        limpieza = 100;
        felicidad += 5;
        ganarExperiencia(10);
        limitarValores();
    }

    public void pasear() {
        energia -= 10;
        felicidad += 10;
        ganarExperiencia(25);
        limitarValores();
    }

    public void jugar() {
        energia -= 10;
        felicidad += 15;
        ganarExperiencia(30);
        limitarValores();
    }

    /**
     * Método interno para evitar que los atributos superen 100 o bajen de 0.
     */
    protected void limitarValores() {
        hambre = Math.max(0, Math.min(100, hambre));
        felicidad = Math.max(0, Math.min(100, felicidad));
        limpieza = Math.max(0, Math.min(100, limpieza));
        energia = Math.max(0, Math.min(100, energia));
    }

    /**
     * Simula el paso del tiempo reduciendo paulatinamente sus barras vitales.
     */
    public void actualizarEstado() {
        hambre += 2;
        energia -= 2;
        limpieza -= 1;
        limitarValores();
    }

    //getters y setters
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
