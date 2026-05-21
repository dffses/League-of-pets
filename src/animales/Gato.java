package animales;

public class Gato extends Animal {

    public Gato(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int limpieza, int energia, int nivel, int experiencia) {
        super(nombre, especie, genero, hambre, felicidad, limpieza, energia, nivel, experiencia);
    }
    /**
     *A los gatos les cuesta más salir a pasear,
     * pero la aventura les genera más impacto emocional.
     */
    @Override
    public void pasear() {
        energia -= 15;
        felicidad += 20;
        ganarExperiencia(35);
        limitarValores();
    }
}
