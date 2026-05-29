package juegos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// Importamos la clase Animal
import animales.Animal;
import ventana.Interfaz;

public class Packman extends JPanel implements KeyListener {
    private final int FILAS = 10;
    private final int COLUMNAS = 15;

    private int filaJugador = 1, colJugador = 1;
    private int puntos = 0;
    private int tiempoRestante = 30; // Segundos para jugar
    private boolean juegoTerminado = false;

    private Animal mascotaActual;
    private int frutasRestantes = 0;
    private Timer relojJuego;
    private boolean victoria = false;


    private CardLayout cl;
    private JPanel cont;


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
    /**
     * Constructor principal que inicializa el laberinto, configura los controles de teclado y arranca el temporizador de la partida.
     * @param cl El CardLayout encargado del flujo de navegación entre pantallas.
     * @param cont El contenedor principal que aloja los distintos paneles.
     * @param mascota El objeto Animal que sufrirá el desgaste por jugar y recibirá las monedas correspondientes.
     */
    public Packman(CardLayout cl, JPanel cont, Animal mascota) {
        this.cl = cl;
        this.cont = cont;
        this.mascotaActual = mascota;

        // Estructura BorderLayout para el botón superior
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);

        contarFrutasIniciales();

        this.setFocusable(true);
        this.addKeyListener(this);


        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(240, 240, 240));

        JButton btnVolver = new JButton("⬅ Salir del Juego");
        btnVolver.addActionListener(e -> {
            if (relojJuego != null) relojJuego.stop();
            //cambio1 guardae al abandonar voluntariamente
            if (mascotaActual != null) {
                mascotaActual.jugar();
                mascotaActual.guardarPartidaCompleta();
            }
            cl.show(cont, "PANTALLA_CUADRICULA");
        });

        panelNorte.add(btnVolver, BorderLayout.WEST);
        this.add(panelNorte, BorderLayout.NORTH);


        relojJuego = new Timer(1000, e -> {
            if (tiempoRestante > 0) {
                tiempoRestante--;
            } else {
                juegoTerminado = true;
                relojJuego.stop();

                if (mascotaActual != null) {
                    mascotaActual.jugar();
                    mascotaActual.guardarPartidaCompleta();
                }

                JOptionPane.showMessageDialog(this,
                        "¡Se acabó el tiempo! No has conseguido recolectar todas las frutas.",
                        "Fin de la partida", JOptionPane.WARNING_MESSAGE);

                cl.show(cont, "PANTALLA_CUADRICULA");
            }
            repaint();
        });
        relojJuego.start();
    }
    /**
     * Recorre la matriz del laberinto al iniciar para registrar cuántas frutas hay disponibles en el mapa.
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
    /**
     * Dibuja los elementos en pantalla, incluyendo las paredes del laberinto, las frutas coleccionables, el jugador y el marcador inferior.
     * @param g El contexto de dibujo Graphics del componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Escalado dinámico automático
        int altoBarraSuperior = 40;
        int tamanoCeldaX = getWidth() / COLUMNAS;
        int tamanoCeldaY = (getHeight() - altoBarraSuperior - 50) / FILAS;

        int tamanoCelda = Math.max(20, Math.min(tamanoCeldaX, tamanoCeldaY));

        int desplazarX = (getWidth() - (COLUMNAS * tamanoCelda)) / 2;
        int desplazarY = altoBarraSuperior + ((getHeight() - altoBarraSuperior - 50 - (FILAS * tamanoCelda)) / 2);

        // Dibujar laberinto
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                int x = desplazarX + (c * tamanoCelda);
                int y = desplazarY + (f * tamanoCelda);

                if (laberinto[f][c] == 1) {
                    g.setColor(new Color(25, 25, 166));
                    g.fillRect(x, y, tamanoCelda, tamanoCelda);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, tamanoCelda, tamanoCelda);
                } else if (laberinto[f][c] == 0) {
                    g.setColor(Color.RED);
                    int radioFruta = tamanoCelda / 4;
                    g.fillOval(x + (tamanoCelda/2) - (radioFruta/2), y + (tamanoCelda/2) - (radioFruta/2), radioFruta, radioFruta);
                }
            }
        }

        // Dibujar jugador
        g.setColor(Color.YELLOW);
        int px = desplazarX + (colJugador * tamanoCelda) + (tamanoCelda / 8);
        int py = desplazarY + (filaJugador * tamanoCelda) + (tamanoCelda / 8);
        int pTamano = (tamanoCelda * 3) / 4;
        g.fillArc(px, py, pTamano, pTamano, 30, 300);

        // Barra informativa inferior
        int panelInfoY = getHeight() - 25;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Puntos: " + puntos, desplazarX + 20, panelInfoY);
        g.drawString("Frutas: " + frutasRestantes, desplazarX + (COLUMNAS * tamanoCelda) / 3 + 10, panelInfoY);
        g.drawString("Tiempo: " + tiempoRestante + "s", desplazarX + (2 * (COLUMNAS * tamanoCelda)) / 3 + 10, panelInfoY);

        if (juegoTerminado) {
            g.setFont(new Font("Arial", Font.BOLD, tamanoCelda));
            if (victoria) {
                g.setColor(Color.GREEN);
                g.drawString("¡VICTORIA!", getWidth()/2 - 100, getHeight()/2);
            } else {
                g.setColor(Color.RED);
                g.drawString("¡FIN DEL JUEGO!", getWidth()/2 - 130, getHeight()/2);
            }
        }
    }
    /**
     * Procesa la entrada del teclado para mover al jugador por el laberinto, gestionando colisiones con muros, recolección de frutas y la victoria.
     * @param e Evento del teclado que contiene la tecla de dirección presionada.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado) return;

        int nuevaFila = filaJugador;
        int nuevaCol = colJugador;

        if (e.getKeyCode() == KeyEvent.VK_UP)    nuevaFila--;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)  nuevaFila++;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  nuevaCol--;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) nuevaCol++;

        if (laberinto[nuevaFila][nuevaCol] != 1) {
            filaJugador = nuevaFila;
            colJugador = nuevaCol;

            if (laberinto[filaJugador][colJugador] == 0) {
                laberinto[filaJugador][colJugador] = 2;
                puntos += 10;
                frutasRestantes--;

                if (frutasRestantes == 0) {
                    juegoTerminado = true;
                    victoria = true;
                    relojJuego.stop();

                    int recompensaMonedas = 100;


                    if (mascotaActual != null) {
                        mascotaActual.ganarMonedas(recompensaMonedas);

                        mascotaActual.jugar();
                        mascotaActual.guardarPartidaCompleta();
                        actualizarInterfazPrincipal();
                        JOptionPane.showMessageDialog(this,
                                "¡Felicidades! Has recolectado todas las frutas.\n¡Tu mascota ha ganado " + recompensaMonedas + " monedas! ",
                                "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "¡Felicidades! Has ganado la partida.\n(Nota: Las monedas no se sumaron porque no se detectó una mascota activa).",
                                "¡Victoria sin Mascota!", JOptionPane.WARNING_MESSAGE);
                    }

                    // Volvemos al menú pase lo que pase sin bloquearse
                    cl.show(cont, "PANTALLA_CUADRICULA");
                }
            }
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    /**
     * Busca el componente Interfaz en el contenedor principal para sincronizar y refrescar visualmente las monedas y estadísticas de la mascota.
     */
    private void actualizarInterfazPrincipal() {
        // Buscamos dinámicamente el panel Interfaz recorriendo el contenedor
        for (Component comp : cont.getComponents()) {
            if (comp instanceof Interfaz) {
                Interfaz interfaz = (Interfaz) comp;
                interfaz.actualizarMonedasVisuales();       // Actualiza el contador de monedas 🪙
                interfaz.actualizarEstadisticasVisuales(); // 🛠️ ¡NUEVO! Fuerza el refresco inmediato de Hambre, Felicidad, Energía y Exp
                break;
            }
        }
    }
}