package animales;

public class Perro extends Animal {

    public Perro(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int limpieza, int energia, int nivel, int experiencia) {
        super(nombre, especie, genero, hambre, felicidad, limpieza, energia, nivel, experiencia);
    }
    /**
     *El perro es extremadamente juguetón, por lo que su acción de
     * jugar le cansa un poco más, pero eleva su felicidad notablemente.
     */
    @Override
    public void jugar() {
        energia -= 15;
        felicidad += 20;
        limitarValores();
    }
}
