package juegos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// Importamos la clase Animal (cambia la ruta del paquete si es necesario)
import animales.Animal;

public class Packman extends JPanel implements ActionListener, KeyListener {
    // --- Configuración Visual ---
    private final int TAMANO_CELDA = 40;
    private final int FILAS = 10;
    private final int COLUMNAS = 15;

    private int filaJugador = 1, colJugador = 1;
    private int puntos = 0;
    private int tiempoRestante = 30; // Segundos para jugar
    private boolean juegoTerminado = false;

    // NUEVOS ATRIBUTOS: Para conectar con la mascota y controlar la victoria
    private Animal mascotaActual;
    private int frutasRestantes = 0;
    private Timer relojJuego; // Lo hacemos atributo de clase para poder detenerlo desde fuera del constructor
    private boolean victoria = false; // Bandera para saber si ganó o perdió por tiempo

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

    // MODIFICADO: El constructor ahora recibe a tu mascota
    public Packman(Animal mascota) {
        this.mascotaActual = mascota;

        // Contamos cuántas frutas hay inicialmente en el mapa
        contarFrutasIniciales();

        // Configuramos el tamaño de la ventana
        setPreferredSize(new Dimension(COLUMNAS * TAMANO_CELDA, FILAS * TAMANO_CELDA + 50));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Temporizador: se ejecuta cada 1000ms (1 segundo)
        relojJuego = new Timer(1000, e -> {
            if (tiempoRestante > 0) {
                tiempoRestante--;
            } else {
                juegoTerminado = true;
                relojJuego.stop(); // Detenemos el reloj si se acaba el tiempo
            }
            repaint(); // Redibuja la pantalla para actualizar el tiempo
        });
        relojJuego.start();
    }

    /**
     * Cuenta cuántas celdas tienen el valor '0' (fruta) al iniciar el juego.
     */
    private void contarFrutasIniciales() {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (laberinto[f][c] == 0) {
                    frutasRestantes++;
                }
            }
        }
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

        // Dibujamos la Interfaz (Puntos, Frutas y Tiempo)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Puntos: " + puntos, 20, FILAS * TAMANO_CELDA + 30);
        g.drawString("Frutas: " + frutasRestantes, 150, FILAS * TAMANO_CELDA + 30);
        g.drawString("Tiempo: " + tiempoRestante + "s", 300, FILAS * TAMANO_CELDA + 30);

        // MODIFICADO: Mensaje de fin de juego dinámico (Victoria o Derrota)
        if (juegoTerminado) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            if (victoria) {
                g.setColor(Color.GREEN);
                g.drawString("¡VICTORIA!", 180, (FILAS * TAMANO_CELDA) / 2);
            } else {
                g.setColor(Color.RED);
                g.drawString("¡FIN DEL JUEGO!", 120, (FILAS * TAMANO_CELDA) / 2);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado) return; // Si terminó el juego, no se mueve

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
                frutasRestantes--; // Restamos una fruta del contador

                // NUEVA LÓGICA: Comprobamos si ya no quedan frutas en el mapa
                if (frutasRestantes == 0) {
                    juegoTerminado = true;
                    victoria = true;
                    relojJuego.stop(); // Paramos el temporizador de inmediato

                    // Otorgamos la recompensa personalizada (ej: 100 monedas)
                    int recompensaMonedas = 100;
                    mascotaActual.ganarMonedas(recompensaMonedas);

                    JOptionPane.showMessageDialog(this,
                            "¡Felicidades! Has recolectado todas las frutas.\n¡Tu mascota ha ganado " + recompensaMonedas + " monedas! 🪙",
                            "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        repaint(); // Actualiza la posición visual
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void actionPerformed(ActionEvent e) {}

    // Eliminamos o ignoramos el main original para integrarlo mediante el constructor con mascota
}