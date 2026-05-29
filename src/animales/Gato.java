package animales;

public class Gato extends Animal {

    /**
     * Constructor para inicializar un nuevo gato con estadísticas base.
     * @param nombre El nombre del gato.
     * @param especie La especie (GATO).
     * @param genero El género del animal.
     */
    public Gato(String nombre, Especie especie, Genero genero) {
        super(nombre, especie, genero);
    }

    /**
     * Constructor completo para restaurar el estado de un gato desde el archivo XML.
     * @param nombre El nombre del gato.
     * @param especie La especie del gato.
     * @param genero El género del gato.
     * @param hambre Nivel de hambre.
     * @param felicidad Nivel de felicidad.
     * @param energia Nivel de energía.
     * @param nivel Nivel actual.
     * @param experiencia Puntos de experiencia.
     * @param monedas Cantidad de monedas.
     */
    public Gato(String nombre, Especie especie, Genero genero, int hambre, int felicidad, int energia, int nivel, int experiencia, int monedas) {
        super(nombre, especie, genero);
        this.hambre = hambre;
        this.felicidad = felicidad;

        this.energia = energia;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.monedas = monedas;
    }

    /**
     * Alimenta al gato, mejorando su felicidad pero alterando ligeramente su energía antes de la rutina común.
     */
    @Override
    public void comer() {
        this.energia -= 5;
        this.felicidad += 15;
        this.hambre +=20;
        super.comer();
    }
    /**
     * Realiza un paseo con el gato, adaptando el desgaste físico y el beneficio de felicidad propios felinos.
     */
    @Override
    public void pasear() {
        this.felicidad += 15;
        this.energia -= 10;
        this.hambre -= 5;
        super.pasear();
    }
    /**
     * Ejecuta una sesión de juego para el gato, aumentando notablemente su felicidad y reduciendo su energía.
     */
    @Override
    public void jugar() {
        this.energia -= 15;
        this.felicidad += 20;
        this.hambre-=7;
        super.jugar();
    }
}
