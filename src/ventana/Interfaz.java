package ventana;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import animales.Animal;


public class Interfaz extends JPanel {
    private Image imagenFondo;
    private Animal mascotaActual;
    private JLabel labelMonedas;
    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;
    //atributos para que se vean las estadisticas
    private JLabel labelNivel;
    private JProgressBar barraHambre;
    private JProgressBar barraFelicidad;
    private JProgressBar barraEnergia;
    private JProgressBar barraExp;

    private Menu menuRaiz;

    public Interfaz(CardLayout cardLayout, JPanel contenedorPrincipal, Animal mascota, String rutaImagenMascota, Menu menuRaiz) {
        this.cardLayout = cardLayout;
        this.contenedorPrincipal = contenedorPrincipal;
        this.mascotaActual = mascota;
        this.menuRaiz = menuRaiz; // 🛠️ Lo guardamos
        this.setLayout(new BorderLayout());
        URL urlFondo = getClass().getResource("fondo_tienda.jpg");
        if (urlFondo != null) {
            imagenFondo = new ImageIcon(urlFondo).getImage();
        }

        // --- PANEL DE CABECERA (Norte): Botones + Contador de Monedas ---
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setOpaque(false);
        panelCabecera.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnIzquierda = new JButton("TIENDA");
        JButton btnDerecha = new JButton("JUEGOS");

        btnIzquierda.setBackground(new Color(255, 204, 213));
        btnIzquierda.setForeground(new Color(60, 60, 60));
        btnIzquierda.setFont(new Font("Arial", Font.BOLD, 22));
        btnIzquierda.setFocusPainted(false);
        btnIzquierda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        btnDerecha.setBackground(new Color(202, 228, 241));
        btnDerecha.setForeground(new Color(60, 60, 60));
        btnDerecha.setFont(new Font("Arial", Font.BOLD, 22));
        btnDerecha.setFocusPainted(false);
        btnDerecha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        btnIzquierda.addActionListener(e -> {
            TiendaComida pantallaTienda = new TiendaComida(cardLayout, contenedorPrincipal, mascotaActual);
            contenedorPrincipal.add(pantallaTienda, "PANTALLA_TIENDA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_TIENDA");
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();
        });

        btnDerecha.addActionListener(e -> {
            // ✅ PASAMOS LA MASCOTA CORRECTAMENTE
            SeleccionJuegos pantallaJuegos = new SeleccionJuegos(cardLayout, contenedorPrincipal, mascotaActual);
            contenedorPrincipal.add(pantallaJuegos, "PANTALLA_CUADRICULA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_CUADRICULA");
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();
        });

        panelBotones.add(btnIzquierda);
        panelBotones.add(btnDerecha);
        panelCabecera.add(panelBotones, BorderLayout.CENTER);

        int monedasMostradas = (mascotaActual != null) ? mascotaActual.getMonedas() : 0;

        labelMonedas = new JLabel(monedasMostradas + " 🪙", SwingConstants.CENTER);
        labelMonedas.setFont(new Font("Arial", Font.BOLD, 18));
        labelMonedas.setForeground(Color.WHITE);
        labelMonedas.setBackground(new Color(0, 0, 0, 120));
        labelMonedas.setOpaque(true);
        labelMonedas.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel panelMonedasFlotante = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelMonedasFlotante.setOpaque(false);
        panelMonedasFlotante.add(labelMonedas);
        panelCabecera.add(panelMonedasFlotante, BorderLayout.EAST);

        this.add(panelCabecera, BorderLayout.NORTH);

        // --- PANEL CENTRAL: Nombre y Avatar ---
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(180, 10, 5, 10);

        String nombreMostrado = (mascotaActual != null) ? mascotaActual.getNombre() : "Mascota";

        JLabel labelNombre = new JLabel(nombreMostrado, SwingConstants.CENTER);
        labelNombre.setFont(new Font("Arial", Font.BOLD, 18));
        labelNombre.setForeground(Color.WHITE);
        labelNombre.setBackground(new Color(0, 0, 0, 120));
        labelNombre.setOpaque(true);
        labelNombre.setBorder(BorderFactory.createEmptyBorder(4, 14, 4, 14));
        panelCentral.add(labelNombre, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 10);

        if (rutaImagenMascota != null && !rutaImagenMascota.isEmpty()) {
            URL urlMascota = getClass().getResource(rutaImagenMascota);
            if (urlMascota != null) {
                Image imgMascota = new ImageIcon(urlMascota).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                JLabel labelAvatar = new JLabel(new ImageIcon(imgMascota));
                labelAvatar.setOpaque(false);
                panelCentral.add(labelAvatar, gbc);
            }
        }

        this.add(panelCentral, BorderLayout.CENTER);
        //añadir panel en la parte de abajo
        this.add(crearPanelInferiorEstadisticas(), BorderLayout.SOUTH);
        //rellenamos barras
        actualizarEstadisticasVisuales();
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                // En cuanto el usuario vuelve de la Tienda o de los Juegos,
                // leemos el XML o el objeto actualizado y repintamos las barras.
                actualizarEstadisticasVisuales();
                System.out.println("🔄 ¡Pantalla principal detectada! Estadísticas refrescadas.");
            }
        });
    }

    // ✅ GETTER para que Menu pueda guardar la partida
    public Animal getMascota() {
        return this.mascotaActual;
    }

    // ✅ MÉTODO para actualizar las monedas desde cualquier juego
    public void actualizarMonedasVisuales() {
        if (labelMonedas != null && mascotaActual != null) {
            labelMonedas.setText(mascotaActual.getMonedas() + " 🪙");
            actualizarEstadisticasVisuales();
            System.out.println("💰 Monedas actualizadas en Interfaz: " + mascotaActual.getMonedas());
        }
    }

    // ✅ MÉTODO para que los juegos puedan agregar monedas y actualizar la vista
    public void ganarMonedasYActualizar(int cantidad) {
        if (mascotaActual != null) {
            mascotaActual.ganarMonedas(cantidad);
            actualizarMonedasVisuales();
            actualizarEstadisticasVisuales();
            // Guardar automáticamente después de ganar monedas
            mascotaActual.guardarPartidaCompleta();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
    //metodos para que se vean las estadisticas
    /**
     * Une un texto identificador junto a su respectiva barra de progreso en horizontal.
     */
    private JPanel crearFilaStat(String texto, JProgressBar barra) {
        JPanel fila = new JPanel(new BorderLayout(5, 0));
        fila.setOpaque(false);

        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(65, 16)); // Tamaño fijo para alinear las barras

        fila.add(lbl, BorderLayout.WEST);
        fila.add(barra, BorderLayout.CENTER);
        return fila;
    }
    /**
     * Método auxiliar para estilizar de forma uniforme los JProgressBar.
     */
    private JProgressBar configurarBarraProgreso(Color colorBarra) {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setPreferredSize(new Dimension(110, 16));
        barra.setForeground(colorBarra);
        barra.setBackground(new Color(60, 60, 60)); // Fondo interno gris oscuro
        barra.setStringPainted(true); // Permite ver el texto del porcentaje/valor dentro
        barra.setFont(new Font("Arial", Font.BOLD, 11));
        barra.setBorderPainted(false);
        return barra;
    }
    /**
     * Construye de manera limpia el contenedor inferior alineado a la derecha.
     */
    private JPanel crearPanelInferiorEstadisticas() {
        // Contenedor principal del Sur orientado a la derecha
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setOpaque(false);
        // Margen inferior y derecho para separar la caja de los bordes de la ventana
        panelSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 15));

        // Caja negra semitransparente para agrupar las estadísticas de fondo
        JPanel cajaStats = new JPanel();
        cajaStats.setLayout(new BoxLayout(cajaStats, BoxLayout.Y_AXIS));
        cajaStats.setBackground(new Color(0, 0, 0, 140)); // Fondo oscuro traslúcido
        cajaStats.setOpaque(true);
        cajaStats.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Inicializar los componentes visuales
        labelNivel = new JLabel("NIVEL: 1");
        labelNivel.setFont(new Font("Arial", Font.BOLD, 14));
        labelNivel.setForeground(Color.YELLOW); // Color dorado para destacar el nivel
        labelNivel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Inicializamos las barras de progreso (mínimo 0, máximo 100)
        barraHambre = configurarBarraProgreso(Color.ORANGE);
        barraFelicidad = configurarBarraProgreso(Color.PINK);
        barraEnergia = configurarBarraProgreso(Color.CYAN);
        barraExp = configurarBarraProgreso(Color.GREEN);

        // Añadimos todo a la caja vertical con pequeños espacios de separación
        cajaStats.add(labelNivel);
        cajaStats.add(Box.createVerticalStrut(5));
        cajaStats.add(crearFilaStat("Hambre:", barraHambre));
        cajaStats.add(Box.createVerticalStrut(5));
        cajaStats.add(crearFilaStat("Felicidad:", barraFelicidad));
        cajaStats.add(Box.createVerticalStrut(5));
        cajaStats.add(crearFilaStat("Energía:", barraEnergia));
        cajaStats.add(Box.createVerticalStrut(5));
        cajaStats.add(crearFilaStat("Exp:", barraExp));

        panelSur.add(cajaStats);
        return panelSur;
    }
    public void actualizarEstadisticasVisuales() {
        if (mascotaActual != null) {
            labelNivel.setText("NIVEL: " + mascotaActual.getNivel());

            barraHambre.setValue(mascotaActual.getHambre());
            barraHambre.setString(mascotaActual.getHambre() + "/100");

            barraFelicidad.setValue(mascotaActual.getFelicidad());
            barraFelicidad.setString(mascotaActual.getFelicidad() + "/100");

            barraEnergia.setValue(mascotaActual.getEnergia());
            barraEnergia.setString(mascotaActual.getEnergia() + "/100");

            barraExp.setValue(mascotaActual.getExperiencia());
            barraExp.setString(mascotaActual.getExperiencia() + "%");
        }
    }
}


