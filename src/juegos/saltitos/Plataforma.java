package juegos.saltitos;

import java.awt.*;

public class Plataforma {
    int x;
    int y;
    int ancho = 60;
    int alto = 10;
    /**
     * Constructor para inicializar una plataforma en unas coordenadas específicas.
     * @param x Posición horizontal de la plataforma.
     * @param y Posición vertical de la plataforma.
     */
    public  Plataforma(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Dibuja la plataforma de color gris oscuro en el entorno gráfico del juego.
     * @param g Objeto de tipo Graphics utilizado para renderizar la figura.
     */
    public void dibujar(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(x, y, ancho, alto);
    }
}
