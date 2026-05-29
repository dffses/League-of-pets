package animales;

public class Perro extends Animal {

    /**
     * Constructor para inicializar un nuevo perro con estadísticas base.
     * @param nombre El nombre del perro.
     * @param especie La especie (PERRO).
     * @param genero El género del animal.
     */
    public Perro(String nombre, Especie especie, Genero genero) {
        super(nombre, especie, genero);
    }

    /**
     * Constructor completo para restaurar el estado de un perro desde el archivo XML.
     * @param nombre El nombre del perro.
     * @param especie La especie del perro.
     * @param genero El género del perro.
     * @param hambre Nivel de hambre.
     * @param felicidad Nivel de felicidad.
     * @param energia Nivel de energía.
     * @param nivel Nivel actual.
     * @param experiencia Puntos de experiencia.
     * @param monedas Cantidad de monedas.
     */
    public Perro(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int energia, int nivel, int experiencia, int monedas) {
        super(nombre, especie, genero);
        this.hambre = hambre;
        this.felicidad = felicidad;

        this.energia = energia;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.monedas = monedas;
    }
    /**
     * Alimenta al perro, disminuyendo un poco su energía y aumentando su felicidad antes de la digestión general.
     */
    @Override
    public void comer() {
        this.energia -= 10;
        this.felicidad += 20;
        this.hambre+=20;
        super.comer();
    }
    /**
     * Saca a pasear al perro, otorgándole una gran bonificación de felicidad debido a su naturaleza canina.
     */
    @Override
    public void pasear() {
        this.energia -= 15;
        this.felicidad += 30; //le da más felicidad pasear porque es un perro
        this.hambre-=5;
        super.pasear();
    }
    /**
     * Hace jugar al perro, consumiendo energía e incrementando considerablemente su nivel de felicidad.
     */
    @Override
    public void jugar() {
        this.energia -= 15;
        this.felicidad += 20;
        this.hambre-=5;
        super.jugar();
    }
}