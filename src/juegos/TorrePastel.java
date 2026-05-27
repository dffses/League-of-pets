package juegos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
// Importamos la clase Animal (asegúrate de que esté en el paquete correcto)
import animales.Animal;

public class TorrePastel extends JPanel {

    /**
     * Estructura interna de datos para fijar las propiedades físicas de un bloque.
     */
    private class PisoPastel {
        int x, ancho, y;
        Color color;

        public PisoPastel(int x, int ancho, int y, Color color) {
            this.x = x;
            this.ancho = ancho;
            this.y = y;
            this.color = color;
        }
    }

    private Timer timer;
    private Random random = new Random();
    private ArrayList<PisoPastel> historialPisos = new ArrayList<>();

    // Variables de control de posicionamiento para la rebanada en movimiento
    private int bloqueX = 0;
    private int bloqueY = 320;
    private int bloqueAncho = 200;
    private int bloqueAlto = 30;
    private int velocidad = 6;
    private Color colorActual;

    // Valores de centrado del bloque
    private int baseX = 275;
    private int baseAncho = 200;

    private int pisos = 0;
    private boolean juegoTerminado = false;

    // NUEVA VARIABLE: Instancia de tu mascota activa
    private Animal mascotaActual;

    private Color[] paletaColores = {
            new Color(255, 105, 180), new Color(255, 165, 0),
            new Color(255, 215, 0), new Color(50, 205, 50),
            new Color(30, 144, 255), new Color(186, 85, 211),
            new Color(244, 164, 96), new Color(0, 206, 209),
            new Color(255, 99, 71)
    };

    /**
     * Constructor del panel. Configura los listeners del ratón y arranca la animación basculante.
     * MODIFICADO: El constructor ahora también recibe a la mascota.
     */
    public TorrePastel(CardLayout cl, JPanel cont, Animal mascota) {
        this.mascotaActual = mascota;

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(224, 255, 255));
        this.setFocusable(true);

        colorActual = obtenerColorAleatorio();

        // Ajustamos la posición inicial de vuelo para que empiece sincronizada con la nueva base.
        bloqueX = baseX;

        // MODIFICADO: El botón de salir ahora también asegura dar las monedas acumuladas
        JButton btnSalir = new JButton("⬅ Salir");
        btnSalir.addActionListener(e -> {
            if (timer != null) timer.stop();

            // Si sale a mitad de partida con pisos colocados, le damos sus monedas
            if (pisos > 0 && !juegoTerminado) {
                int monedasGanadas = pisos * 5;
                mascotaActual.ganarMonedas(monedasGanadas);
                JOptionPane.showMessageDialog(this, "Dejaste la tarta a medias. ¡Tu mascota ganó " + monedasGanadas + " monedas! 🪙");
            }
            cl.show(cont, "PANTALLA_CUADRICULA");
        });
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.setOpaque(false);
        panelNorte.add(btnSalir);
        this.add(panelNorte, BorderLayout.NORTH);

        // proceso animado continuo
        timer = new Timer(20, e -> {
            bloqueX += velocidad;
            // Rebote elástico contra las paredes laterales de la ventana
            if (bloqueX + bloqueAncho > getWidth() || bloqueX < 0) {
                velocidad = -velocidad;
            }
            repaint();
        });
        timer.start();

        // Control de fijación de pisos mediante el click
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (juegoTerminado) return;

                int desajuste = bloqueX - baseX;

                // MODIFICADO: Bloque de control cuando la torre colapsa (Game Over)
                if (Math.abs(desajuste) >= baseAncho) {
                    juegoTerminado = true;
                    timer.stop();

                    // Calculamos: 5 monedas por cada piso colocado con éxito
                    int monedasGanadas = pisos * 5;

                    if (monedasGanadas > 0) {
                        mascotaActual.ganarMonedas(monedasGanadas);
                        JOptionPane.showMessageDialog(TorrePastel.this,
                                "¡La tarta se derrumbó!\nPisos totales: " + pisos + "\n¡Has ganado " + monedasGanadas + " monedas! 🪙",
                                "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(TorrePastel.this,
                                "¡La tarta se derrumbó sin colocar ningún piso!\nNo has ganado monedas esta vez.",
                                "Fin del juego", JOptionPane.WARNING_MESSAGE);
                    }

                    cl.show(cont, "PANTALLA_CUADRICULA");
                    return;
                }

                // Cálculo de recorte geométrico de la rebanada según el desvío lateral
                if (desajuste < 0) {
                    bloqueAncho += desajuste;
                    bloqueX = baseX;
                } else {
                    bloqueAncho -= desajuste;
                }

                // Guardado de la sección sólida en el historial de rebanadas estables
                historialPisos.add(new PisoPastel(bloqueX, bloqueAncho, bloqueY, colorActual));

                pisos++;
                baseX = bloqueX;
                baseAncho = bloqueAncho;

                bloqueY -= bloqueAlto; // Escalamos la altura del siguiente bloque flotante
                bloqueX = baseX;       // Lo hacemos reaparecer en la misma proyección vertical

                colorActual = obtenerColorAleatorio();

                // Efecto de scroll vertical de cámara si la torre supera la mitad de la pantalla
                if (bloqueY < 100) {
                    for (PisoPastel piso : historialPisos) {
                        piso.y += bloqueAlto;
                    }
                    bloqueY += bloqueAlto;
                }

                // Aceleración lineal del ritmo de desplazamiento para aumentar la dificultad progresivamente
                if (velocidad > 0) velocidad += 1; else velocidad -= 1;
            }
        });
    }

    private Color obtenerColorAleatorio() {
        return paletaColores[random.nextInt(paletaColores.length)];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Mesa de madera de soporte
        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, 350, getWidth(), 150);

        //centrado
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(275, 350, 200, 10);

        // juego bucle
        for (PisoPastel piso : historialPisos) {
            g.setColor(piso.color);
            g.fillRect(piso.x, piso.y, piso.ancho, bloqueAlto);

            g.setColor(new Color(0, 0, 0, 40));
            g.drawRect(piso.x, piso.y, piso.ancho, bloqueAlto);
        }

        // Bloque móvil activo en pleno vuelo horizontal
        if (!juegoTerminado) {
            g.setColor(colorActual);
            g.fillRect(bloqueX, bloqueY, bloqueAncho, bloqueAlto);

            g.setColor(Color.BLACK);
            g.drawRect(bloqueX, bloqueY, bloqueAncho, bloqueAlto);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Capas de tarta: " + pisos, 20, 70);
    }
}