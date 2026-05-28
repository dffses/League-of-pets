package animales;

public class Cocodrilo extends Animal {

    // Constructor para mascota nueva
    public Cocodrilo(String nombre, Especie especie, Genero genero) {
        super(nombre, especie, genero);
    }

    // Constructor para cargar del XML
    public Cocodrilo(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int energia, int nivel, int experiencia, int monedas) {
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
        this.energia+=20;
        this.hambre +=20;
        this.felicidad+=5;
        super.comer();
    }

    @Override
    public void pasear() {
        this.energia-=20;
        this.hambre -=10;
        this.felicidad+=15;
        super.pasear();
    }

    @Override
    public void jugar() {
        this.energia-=25;
        this.hambre -=10;
        this.felicidad+=15;
        super.jugar();
    }
}
