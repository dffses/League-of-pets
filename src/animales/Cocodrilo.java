package animales;

public class Cocodrilo extends Animal {

    public Cocodrilo(String nombre, Especie especie, Genero genero) {
        super(nombre, especie, genero);
    }
    /**
     * Al cocodrilo le gusta el agua más que a otros animales,
     * así que bañarse le suma más.
     */
    @Override
    public void bañar() {
        limpieza = 100;
        felicidad += 15;
        ganarExperiencia(15);
        limitarValores();
    }
}
