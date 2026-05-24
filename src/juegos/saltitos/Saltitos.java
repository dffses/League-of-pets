package juegos.saltitos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
// Importamos la clase Animal (cambia la ruta del paquete si es necesario)
import animales.Animal;

public class Saltitos extends JPanel implements ActionListener, KeyListener {

    private int x = 200;
    private int y = 300;
    private int velocidadY = 0;
    private int gravedad = 1;
    private int velocidadX = 0;
    private ArrayList<Plataforma> plataformas;
    private Random aleatorio = new Random();
    private Timer temporizador;

    // NUEVOS ATRIBUTOS: Para el control de monedas y tiempo
    private Animal mascotaActual;
    private long tiempoInicio;

    // MODIFICADO: El constructor ahora recibe a tu mascota
    public Saltitos(Animal mascota) {
        this.mascotaActual = mascota;

        // Guardamos el momento exacto en el que empieza la partida (en milisegundos)
        this.tiempoInicio = System.currentTimeMillis();

        temporizador = new Timer(20, this);
        temporizador.start();

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        plataformas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            plataformas.add(new Plataforma(aleatorio.nextInt(330), i * 70));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.GREEN);
        g.fillRect(x, y, 40, 40);

        g.setColor(Color.darkGray);
        g.fillRect(0, 540, 400, 20);

        for (Plataforma p : plataformas) {
            p.dibujar(g);
        }
    }

    public void actionPerformed(ActionEvent e) {
        velocidadY += gravedad;
        y += velocidadY;
        x += velocidadX;

        if (velocidadY > 0) {
            for (Plataforma p : plataformas) {
                if (x + 40 > p.x && x < p.x + p.ancho &&
                        y + 40 > p.y && y + 40 < p.y + p.alto + velocidadY) {
                    velocidadY = -20;
                }
            }
        }

        if (y < 250) {
            int diferencia = 250 - y;
            y = 250;

            for (Plataforma p : plataformas) {
                p.y += diferencia;

                if (p.y > 600) {
                    p.y = 0;
                    p.x = aleatorio.nextInt(330);
                }
            }
        }

        if (x < 0) x = 0;
        if (x > 360) x = 360;

        // MODIFICADO: Aquí es cuando el jugador pierde (Game Over)
        if (y > 650) {
            temporizador.stop();

            // 1. Calculamos cuánto tiempo ha pasado en segundos
            long tiempoFin = System.currentTimeMillis();
            long tiempoJugadoMilisegundos = tiempoFin - tiempoInicio;
            int segundosJugados = (int) (tiempoJugadoMilisegundos / 1000);

            // 2. Calculamos las monedas: 50 monedas por cada 60 segundos (1 minuto)
            int bloquesDeUnMinuto = segundosJugados / 60;
            int monedasGanadas = bloquesDeUnMinuto * 50;

            // 3. Entregamos la recompensa si ha sobrevivido lo suficiente
            if (monedasGanadas > 0) {
                mascotaActual.ganarMonedas(monedasGanadas);
                JOptionPane.showMessageDialog(this,
                        "¡Game Over!\nHas aguantado " + segundosJugados + " segundos.\nGanaste: " + monedasGanadas + " monedas 🪙");
            } else {
                JOptionPane.showMessageDialog(this,
                        "¡Game Over!\nHas aguantado " + segundosJugados + " segundos.\nNecesitas aguantar al menos 60 segundos para ganar monedas.",
                        "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
            }

            // Valores de reinicio originales del juego
            y = 300;
            velocidadY = 0;

            // Opcional: Si quieres que al reiniciar la misma pantalla vuelva a contar el tiempo desde cero:
            // this.tiempoInicio = System.currentTimeMillis();
            // temporizador.start();
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT) {
            velocidadX = -7;
        }
        if (tecla == KeyEvent.VK_RIGHT) {
            velocidadX = 7;
        }
    }

    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();

        if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_RIGHT) {
            velocidadX = 0;
        }
    }

    public void keyTyped(KeyEvent e) {}
}

