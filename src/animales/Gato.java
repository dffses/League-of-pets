package animales;

public class Gato extends Animal {

    public Gato(String nombre, Especie especie,  Genero genero) {
        super(nombre, especie,  genero);
    }


    @Override
    public void pasear() {
        energia -= 15;
        felicidad += 20;
        limitarValores();
    }
}
