package juegos.saltitos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Saltitos extends JPanel implements ActionListener, KeyListener {

    private int x = 200;
    private int y = 300;
    private int velocidadY = 0;
    private int gravedad = 1;
    private int velocidadX = 0;
    private ArrayList<Plataforma> plataformas;
    private Random aleatorio = new Random();
    private Timer temporizador;
    public Saltitos() {
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


        if (y > 650) {
            temporizador.stop();
            y = 300;
            velocidadY = 0;
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

    public void keyTyped(KeyEvent e) {
    }

//
//    JFrame ventana = new JFrame("Mi Juego Doodle");
//    Saltitos juego = new Saltitos();
//        ventana.add(juego);
//        ventana.setSize(400, 600);
//        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        ventana.setResizable(false);
//        ventana.setLocationRelativeTo(null);
//        ventana.setVisible(true);
//
}