package animales;

public class Perro extends Animal {

    public Perro(String nombre, Especie especie, Genero genero) {
       super(nombre,especie, genero);
    }


    @Override
    public void jugar() {
        energia -= 15;
        felicidad += 20;
        limitarValores();
    }
}
