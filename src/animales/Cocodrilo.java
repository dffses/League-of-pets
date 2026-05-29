package animales;

public class Cocodrilo extends Animal {

    /**
     * Constructor para inicializar un nuevo cocodrilo con estadísticas base.
     * @param nombre El nombre del cocodrilo.
     * @param especie La especie (COCODRILO).
     * @param genero El género del animal.
     */
    public Cocodrilo(String nombre, Especie especie, Genero genero) {
        super(nombre, especie, genero);
    }

    /**
     * Constructor completo para restaurar el estado de un cocodrilo desde el archivo XML.
     * @param nombre El nombre del cocodrilo.
     * @param especie La especie del cocodrilo.
     * @param genero El género del cocodrilo.
     * @param hambre Nivel de hambre.
     * @param felicidad Nivel de felicidad.
     * @param energia Nivel de energía.
     * @param nivel Nivel actual.
     * @param experiencia Puntos de experiencia.
     * @param monedas Cantidad de monedas.
     */
    public Cocodrilo(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int energia, int nivel, int experiencia, int monedas) {
        super(nombre, especie, genero);
        this.hambre = hambre;
        this.felicidad = felicidad;

        this.energia = energia;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.monedas = monedas;
    }
    /**
     * Alimenta al cocodrilo, modificando sus estados específicos antes de aplicar la lógica general de comer.
     */
    @Override
    public void comer() {
        this.energia+=20;
        this.hambre +=20;
        this.felicidad+=5;
        super.comer();
    }
    /**
     * Saca a pasear al cocodrilo, alterando su energía y felicidad de forma personalizada antes del paseo general.
     */
    @Override
    public void pasear() {
        this.energia-=20;
        this.hambre -=10;
        this.felicidad+=15;
        super.pasear();
    }
    /**
     * Hace jugar al cocodrilo, consumiendo más energía y aumentando su felicidad de acuerdo a su especie.
     */
    @Override
    public void jugar() {
        this.energia-=25;
        this.hambre -=10;
        this.felicidad+=15;
        super.jugar();
    }
}
