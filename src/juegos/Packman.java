package juegos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Packman extends JPanel implements ActionListener, KeyListener {
    // --- Configuración Visual ---
    private final int TAMANO_CELDA = 40;
    private final int FILAS = 10;
    private final int COLUMNAS = 15;

    private int filaJugador = 1, colJugador = 1;
    private int puntos = 0;
    private int tiempoRestante = 30; // Segundos para jugar
    private boolean juegoTerminado = false;

    // Mapa del laberinto: 1 = Pared, 0 = Fruta, 2 = Vacío
    private int[][] laberinto = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
            {1,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,0,1,1,1,0,1,1,1,0,1,1,1},
            {1,0,0,0,0,0,1,0,1,0,0,0,0,0,1},
            {1,0,1,1,1,0,1,0,1,0,1,1,1,0,1},
            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public Packman() {
        // Configuramos el tamaño de la ventana
        setPreferredSize(new Dimension(COLUMNAS * TAMANO_CELDA, FILAS * TAMANO_CELDA + 50));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Temporizador: se ejecuta cada 1000ms (1 segundo)
        Timer relojJuego = new Timer(1000, e -> {
            if (tiempoRestante > 0) {
                tiempoRestante--;
            } else {
                juegoTerminado = true;
            }
            repaint(); // Redibuja la pantalla para actualizar el tiempo
        });
        relojJuego.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujamos el Laberinto
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                int x = c * TAMANO_CELDA;
                int y = f * TAMANO_CELDA;

                if (laberinto[f][c] == 1) {
                    g.setColor(Color.BLUE); // Paredes azules
                    g.fillRect(x, y, TAMANO_CELDA, TAMANO_CELDA);
                } else if (laberinto[f][c] == 0) {
                    g.setColor(Color.RED); // Frutas rojas
                    g.fillOval(x + 15, y + 15, 10, 10);
                }
            }
        }

        // Dibujamos al Jugador (Pac-Man)
        g.setColor(Color.YELLOW);
        g.fillArc(colJugador * TAMANO_CELDA + 5, filaJugador * TAMANO_CELDA + 5, 30, 30, 30, 300);

        // Dibujamos la Interfaz (Puntos y Tiempo)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Puntos: " + puntos, 20, FILAS * TAMANO_CELDA + 30);
        g.drawString("Tiempo: " + tiempoRestante + "s", 200, FILAS * TAMANO_CELDA + 30);

        // Mensaje de fin de juego
        if (juegoTerminado) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("¡FIN DEL JUEGO!", 120, (FILAS * TAMANO_CELDA) / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado) return; // Si terminó el tiempo, no se mueve

        int nuevaFila = filaJugador;
        int nuevaCol = colJugador;

        // Detectamos la tecla presionada
        if (e.getKeyCode() == KeyEvent.VK_UP)    nuevaFila--;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)  nuevaFila++;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  nuevaCol--;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) nuevaCol++;

        // Verificamos si no hay pared (1) en la nueva posición
        if (laberinto[nuevaFila][nuevaCol] != 1) {
            filaJugador = nuevaFila;
            colJugador = nuevaCol;

            // Si hay fruta (0), la "comemos" y sumamos puntos
            if (laberinto[filaJugador][colJugador] == 0) {
                laberinto[filaJugador][colJugador] = 2; // Marcamos como vacío
                puntos += 10;
            }
        }
        repaint(); // Actualiza la posición visual
    }

    // Métodos obligatorios que no necesitamos para este juego simple
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void actionPerformed(ActionEvent e) {}

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Pac-Man Frutas: Carrera contra el Tiempo");
        Packman juego = new Packman();
        ventana.add(juego);
        ventana.pack();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}