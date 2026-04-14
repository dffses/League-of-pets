package animales;

public class Cocodrilo extends Animal {

    public Cocodrilo(String nombre, Especie especie, Genero genero) {
        super(nombre, especie, genero);
    }

    @Override
    public void bañar() {
        limpieza = 100;
        felicidad += 15;
        limitarValores();
    }
}
