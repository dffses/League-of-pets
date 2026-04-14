package juegos.saltitos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Saltitos extends JPanel implements ActionListener, KeyListener {

    private int x = 200;
    private int y = 300;
    private int velocidadY = 0;
    private int gravedad = 1;
    private int velocidadX = 0;

    public Saltitos() {
        Timer temporizador = new Timer(20, this);
        temporizador.start();

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.GREEN);
        g.fillRect(x, y, 40, 40);

        g.setColor(Color.darkGray);
        g.fillRect(0, 540, 400, 20);
    }

    public void actionPerformed(ActionEvent e) {
        velocidadY += gravedad;
        y += velocidadY;

        x += velocidadX;

        if (y > 500) {
            y = 500;
            velocidadY = -20;
        }
        if (x < 0) {
            x = 0;
        }
        if (x > 360) {
            x = 360;
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT) {
            x = -7;
        }
        if (tecla == KeyEvent.VK_RIGHT) {
            x = 7;
        }
    }

    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();

        if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_RIGHT) {
            x = 0;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

}