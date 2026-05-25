package animales;

public class Perro extends Animal {

    // Constructor para mascota nueva
    public Perro(String nombre, Especie especie, Genero genero) {
        super(nombre, especie, genero);
    }

    // Constructor para cargar del XML
    public Perro(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int limpieza, int energia, int nivel, int experiencia, int monedas) {
        super(nombre, especie, genero);
        this.hambre = hambre;
        this.felicidad = felicidad;
        this.limpieza = limpieza;
        this.energia = energia;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.monedas = monedas;
    }

    @Override
    public void jugar() {
        this.energia -= 15;
        this.felicidad += 20;
        ganarExperiencia(30); // Añadido para que también sume exp al jugar
        limitarValores();
    }
}