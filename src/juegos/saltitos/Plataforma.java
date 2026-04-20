package juegos.saltitos;

import java.awt.*;

public class Plataforma {
    int x;
    int y;
    int ancho = 60;
    int alto = 10;

    public  Plataforma(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(x, y, ancho, alto);
    }
}
