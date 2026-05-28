package ventana;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.io.File;
import animales.Animal;

public class Interfaz extends JPanel {
    private Image imagenFondo;
    private Animal mascotaActual;
    private JLabel labelMonedas;
    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;
    // atributos para las estadísticas
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
        this.menuRaiz = menuRaiz;
        this.setLayout(new BorderLayout());

        // Cargar imagen de fondo
        cargarImagenFondo();

        // --- PANEL DE CABECERA (Norte): Botones (TIENDA, JUEGOS, REINICIAR) + Monedas ---
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setOpaque(false);
        panelCabecera.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Ahora usamos GridLayout con 3 columnas para los tres botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        panelBotones.setOpaque(false);

        JButton btnTienda = new JButton("TIENDA");
        JButton btnJuegos = new JButton("JUEGOS");
        JButton btnReiniciar = new JButton("REINICIAR");   // <-- Botón reinicio sin emoji

        // Estilo común para los botones
        Color btnBgTienda = new Color(255, 204, 213);
        Color btnBgJuegos = new Color(202, 228, 241);
        Color btnBgReinicio = new Color(255, 100, 100); // Rojo para destacar
        Color btnFg = new Color(60, 60, 60);
        Font btnFont = new Font("Arial", Font.BOLD, 18);

        btnTienda.setBackground(btnBgTienda);
        btnTienda.setForeground(btnFg);
        btnTienda.setFont(btnFont);
        btnTienda.setFocusPainted(false);
        btnTienda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        btnJuegos.setBackground(btnBgJuegos);
        btnJuegos.setForeground(btnFg);
        btnJuegos.setFont(btnFont);
        btnJuegos.setFocusPainted(false);
        btnJuegos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        btnReiniciar.setBackground(btnBgReinicio);
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFont(btnFont);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        // Acción de TIENDA
        btnTienda.addActionListener(e -> {
            TiendaComida pantallaTienda = new TiendaComida(cardLayout, contenedorPrincipal, mascotaActual);
            contenedorPrincipal.add(pantallaTienda, "PANTALLA_TIENDA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_TIENDA");
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();
        });

        // Acción de JUEGOS
        btnJuegos.addActionListener(e -> {
            SeleccionJuegos pantallaJuegos = new SeleccionJuegos(cardLayout, contenedorPrincipal, mascotaActual);
            contenedorPrincipal.add(pantallaJuegos, "PANTALLA_CUADRICULA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_CUADRICULA");
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();
        });

        // Acción de REINICIAR (borra partida y vuelve a selección de mascota)
        btnReiniciar.addActionListener(e -> reiniciarJuegoCompleto());

        panelBotones.add(btnTienda);
        panelBotones.add(btnJuegos);
        panelBotones.add(btnReiniciar);
        panelCabecera.add(panelBotones, BorderLayout.CENTER);

        // Contador de monedas
        int monedasMostradas = (mascotaActual != null) ? mascotaActual.getMonedas() : 0;
        labelMonedas = new JLabel(String.valueOf(monedasMostradas), SwingConstants.CENTER);
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

        // Panel inferior (PASEAR + estadísticas)
        this.add(crearPanelInferior(), BorderLayout.SOUTH);

        // Rellenar estadísticas iniciales
        actualizarEstadisticasVisuales();

        // Detectar cuando se vuelve a esta pantalla para refrescar
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                actualizarEstadisticasVisuales();
                System.out.println("Pantalla principal refrescada.");
            }
        });
    }

    // ========== MÉTODOS DE CARGA DE FONDO, REINICIO, ESTADÍSTICAS, ETC. ==========

    private void cargarImagenFondo() {
        String[] rutas = {
                "fondo_tienda.jpg",
                "imagenes/fondo_tienda.jpg",
                "/fondo_tienda.jpg",
                "/imagenes/fondo_tienda.jpg",
                "/ventana/imagenes/fondo_tienda.jpg"
        };
        for (String ruta : rutas) {
            URL url = getClass().getResource(ruta);
            if (url != null) {
                imagenFondo = new ImageIcon(url).getImage();
                System.out.println("Fondo cargado desde: " + ruta);
                return;
            }
        }
        File archivo = new File("fondo_tienda.jpg");
        if (archivo.exists()) {
            imagenFondo = new ImageIcon(archivo.getAbsolutePath()).getImage();
            System.out.println("Fondo cargado desde archivo: " + archivo.getAbsolutePath());
        } else {
            System.out.println("No se encontró la imagen de fondo.");
        }
    }

    /**
     * Reinicia completamente el juego: borra partida.xml y vuelve a la pantalla de selección de mascota.
     */
    private void reiniciarJuegoCompleto() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres reiniciar todo el juego?\nSe perderán todos los progresos.",
                "Reiniciar partida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // 1. Borrar archivo de partida
                File archivoPartida = new File("partida.xml");
                if (archivoPartida.exists()) {
                    archivoPartida.delete();
                    System.out.println("Archivo partida.xml eliminado.");
                }

                // 2. Limpiar el contenedor principal (todas las pantallas)
                contenedorPrincipal.removeAll();

                // 3. Volver a crear la pantalla de selección de mascota
                SeleccionMascota pantallaMascota = new SeleccionMascota(cardLayout, contenedorPrincipal, menuRaiz);
                contenedorPrincipal.add(pantallaMascota, "PANTALLA_SELECCION_MASCOTA");

                // (Opcional) Si se quiere tener también el menú de inicio, se puede añadir, pero no es necesario
                // porque el flujo normal desde selección ya lleva a la interfaz.

                // 4. Mostrar la pantalla de selección
                cardLayout.show(contenedorPrincipal, "PANTALLA_SELECCION_MASCOTA");

                // 5. Refrescar
                contenedorPrincipal.revalidate();
                contenedorPrincipal.repaint();

                JOptionPane.showMessageDialog(this, "Juego reiniciado correctamente.", "Reinicio", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al reiniciar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Construye el panel inferior (PASEAR + estadísticas)
     */
    private JPanel crearPanelInferior() {
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        // Botón PASear (sin emojis)
        JButton btnPasear = new JButton("PASEAR");
        btnPasear.setFont(new Font("Arial", Font.BOLD, 16));
        btnPasear.setBackground(new Color(100, 180, 100));
        btnPasear.setForeground(Color.WHITE);
        btnPasear.setFocusPainted(false);
        btnPasear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPasear.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        btnPasear.addActionListener(e -> {
            if (mascotaActual != null) {
                mascotaActual.pasear();
                actualizarEstadisticasVisuales();
                actualizarMonedasVisuales();
                mascotaActual.guardarPartidaCompleta();
                JOptionPane.showMessageDialog(this,
                        mascotaActual.getNombre() + " ha dado un paseo.\n" +
                                "Energía: " + mascotaActual.getEnergia() + " | Felicidad: " + mascotaActual.getFelicidad(),
                        "Paseo completado",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No hay mascota activa.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel de estadísticas
        JPanel cajaStats = new JPanel();
        cajaStats.setLayout(new BoxLayout(cajaStats, BoxLayout.Y_AXIS));
        cajaStats.setBackground(new Color(0, 0, 0, 140));
        cajaStats.setOpaque(true);
        cajaStats.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        labelNivel = new JLabel("NIVEL: 1");
        labelNivel.setFont(new Font("Arial", Font.BOLD, 14));
        labelNivel.setForeground(Color.YELLOW);
        labelNivel.setAlignmentX(Component.CENTER_ALIGNMENT);

        barraHambre = configurarBarraProgreso(Color.ORANGE);
        barraFelicidad = configurarBarraProgreso(Color.PINK);
        barraEnergia = configurarBarraProgreso(Color.CYAN);
        barraExp = configurarBarraProgreso(Color.GREEN);

        cajaStats.add(labelNivel);
        cajaStats.add(Box.createVerticalStrut(5));
        cajaStats.add(crearFilaStat("Hambre:", barraHambre));
        cajaStats.add(Box.createVerticalStrut(5));
        cajaStats.add(crearFilaStat("Felicidad:", barraFelicidad));
        cajaStats.add(Box.createVerticalStrut(5));
        cajaStats.add(crearFilaStat("Energía:", barraEnergia));
        cajaStats.add(Box.createVerticalStrut(5));
        cajaStats.add(crearFilaStat("Exp:", barraExp));

        panelSur.add(btnPasear, BorderLayout.WEST);
        panelSur.add(cajaStats, BorderLayout.EAST);

        return panelSur;
    }

    private JPanel crearFilaStat(String texto, JProgressBar barra) {
        JPanel fila = new JPanel(new BorderLayout(5, 0));
        fila.setOpaque(false);
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(65, 16));
        fila.add(lbl, BorderLayout.WEST);
        fila.add(barra, BorderLayout.CENTER);
        return fila;
    }

    private JProgressBar configurarBarraProgreso(Color colorBarra) {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setPreferredSize(new Dimension(110, 16));
        barra.setForeground(colorBarra);
        barra.setBackground(new Color(60, 60, 60));
        barra.setStringPainted(true);
        barra.setFont(new Font("Arial", Font.BOLD, 11));
        barra.setBorderPainted(false);
        return barra;
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

    public Animal getMascota() {
        return this.mascotaActual;
    }

    public void actualizarMonedasVisuales() {
        if (labelMonedas != null && mascotaActual != null) {
            labelMonedas.setText(String.valueOf(mascotaActual.getMonedas()));
            actualizarEstadisticasVisuales();
        }
    }

    public void ganarMonedasYActualizar(int cantidad) {
        if (mascotaActual != null) {
            mascotaActual.ganarMonedas(cantidad);
            actualizarMonedasVisuales();
            actualizarEstadisticasVisuales();
            mascotaActual.guardarPartidaCompleta();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(50, 100, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}