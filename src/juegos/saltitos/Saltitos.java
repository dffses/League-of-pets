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
import ventana.Interfaz;

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

    // MODIFICADO: Añadimos referencias para la navegación por pantallas
    private CardLayout cl;
    private JPanel cont;

    /**
     * Constructor principal que configura el panel de juego, inicializa el temporizador y genera el mapa de plataformas flotantes.
     * @param cl El CardLayout encargado del flujo de navegación entre pantallas.
     * @param cont El contenedor principal que aloja los distintos paneles.
     * @param mascota El objeto Animal de la sesión actual que recibirá recompensas o desgaste.
     */
    public Saltitos(CardLayout cl, JPanel cont, Animal mascota) {
        this.cl = cl;
        this.cont = cont;
        this.mascotaActual = mascota;

        // Fijamos BorderLayout en este panel principal para separar el botón del área de juego
        this.setLayout(new BorderLayout());

        // Guardamos el momento exacto en el que empieza la partida (en milisegundos)
        this.tiempoInicio = System.currentTimeMillis();

        temporizador = new Timer(20, this);
        temporizador.start();

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);


        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(240, 240, 240));

        JButton btnVolver = new JButton("⬅ Salir de Saltitos");
        btnVolver.addActionListener(e -> {
            temporizador.stop();

            if (mascotaActual != null) {
                mascotaActual.jugar();
                mascotaActual.guardarPartidaCompleta();
            }
            cl.show(cont, "PANTALLA_CUADRICULA");
        });

        panelNorte.add(btnVolver, BorderLayout.WEST);
        this.add(panelNorte, BorderLayout.NORTH);
        // -----------------------------------------------------------------------------

        plataformas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            plataformas.add(new Plataforma(aleatorio.nextInt(330), i * 70));
        }
    }
    /**
     * Se encarga de repintar los componentes visuales del minijuego en el panel gráfico.
     * @param g El contexto de dibujo Graphics del componente.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo del área de juego
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Personaje (Cubo verde)
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 40, 40);

        // Suelo firme inicial
        g.setColor(Color.darkGray);
        g.fillRect(0, 540, 400, 20);

        // Renderizado de las plataformas flotantes
        for (Plataforma p : plataformas) {
            p.dibujar(g);
        }
    }
    /**
     * Bucle lógico principal que actualiza las físicas del personaje, detecta colisiones, desplaza la pantalla y gestiona el Game Over.
     * @param e Evento de acción disparado de forma recurrente por el temporizador.
     */
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


            long tiempoFin = System.currentTimeMillis();
            long tiempoJugadoMilisegundos = tiempoFin - tiempoInicio;
            int segundosJugados = (int) (tiempoJugadoMilisegundos / 1000);


            int bloquesDeUnMinuto = segundosJugados / 60;
            int monedasGanadas = bloquesDeUnMinuto * 50;

            if (mascotaActual != null) {
                mascotaActual.jugar(); // Suma felicidad, gasta energía y da experiencia
                mascotaActual.guardarPartidaCompleta(); // Persiste los datos en tu XML
            }


            if (monedasGanadas > 0) {
                mascotaActual.ganarMonedas(monedasGanadas);
                actualizarInterfazPrincipal();
                JOptionPane.showMessageDialog(this,
                        "¡Game Over!\nHas aguantado " + segundosJugados + " segundos.\nGanaste: " + monedasGanadas + " monedas 🪙");
            } else {
                JOptionPane.showMessageDialog(this,
                        "¡Game Over!\nHas aguantado " + segundosJugados + " segundos.\nNecesitas aguantar al menos 60 segundos para ganar monedas.",
                        "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
            }


            cl.show(cont, "PANTALLA_CUADRICULA");
            return;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
    /**
     * Captura las pulsaciones de teclado para asignar velocidad horizontal en base a las flechas de dirección.
     * @param e Evento del teclado que contiene información de la tecla presionada.
     */
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT) {
            velocidadX = -7;
        }
        if (tecla == KeyEvent.VK_RIGHT) {
            velocidadX = 7;
        }
    }
    /**
     * Captura la liberación de teclas para detener el desplazamiento lateral del personaje cuando se sueltan las flechas.
     * @param e Evento del teclado que contiene información de la tecla liberada.
     */
    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();

        if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_RIGHT) {
            velocidadX = 0;
        }
    }
    /**
     *Buscar la ventana principal y refrescar el marcador visual de monedas.
     */
    private void actualizarInterfazPrincipal() {
        Component parent = this.getParent();
        while (parent != null) {
            if (parent instanceof JPanel) {
                JPanel panel = (JPanel) parent;
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof Interfaz) {
                        ((Interfaz) comp).actualizarMonedasVisuales();
                        return;
                    }
                }
            }
            parent = parent.getParent();
        }
    }
}