package animales;

public class Gato extends Animal {

    // Constructor para mascota nueva
    public Gato(String nombre, Especie especie, Genero genero) {
        super(nombre, especie, genero);
    }

    // Constructor para cargar del XML
    public Gato(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int energia, int nivel, int experiencia, int monedas) {
        super(nombre, especie, genero);
        this.hambre = hambre;
        this.felicidad = felicidad;

        this.energia = energia;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.monedas = monedas;
    }


    @Override
    public void comer() {
        this.energia -= 5;
        this.felicidad += 15;
        this.hambre +=20;
        super.comer();
    }

    @Override
    public void pasear() {
        this.felicidad += 15;
        this.energia -= 10;
        this.hambre -= 5;
        super.pasear();
    }

    @Override
    public void jugar() {
        this.energia -= 15;
        this.felicidad += 20;
        this.hambre-=7;
        super.jugar();
    }
}
