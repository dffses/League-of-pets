package ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import animales.Animal;

public class Interfaz extends JPanel {
    private Image imagenFondo;
    private Animal mascotaActual;
    private JLabel labelMonedas;
    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;
    private JFrame ventanaPrincipal; // Referencia a la ventana principal (Menu)

    public Interfaz(CardLayout cardLayout, JPanel contenedorPrincipal, Animal mascota, String rutaImagenMascota, JFrame ventanaPrincipal) {
        this.cardLayout = cardLayout;
        this.contenedorPrincipal = contenedorPrincipal;
        this.mascotaActual = mascota;
        this.ventanaPrincipal = ventanaPrincipal;
        this.setLayout(new BorderLayout());

        URL urlFondo = getClass().getResource("fondo_tienda.jpg");
        if (urlFondo != null) {
            imagenFondo = new ImageIcon(urlFondo).getImage();
        }

        // --- PANEL DE CABECERA (Norte): Botones + Contador de Monedas ---
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setOpaque(false);
        panelCabecera.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0)); //  3 botones ahora
        panelBotones.setOpaque(false);

        JButton btnTienda = new JButton("TIENDA");
        JButton btnJuegos = new JButton("JUEGOS");
        JButton btnReinicio = new JButton(" REINICIAR JUEGO"); //  Botón de reinicio

        // Estilo del botón Tienda
        btnTienda.setBackground(new Color(255, 204, 213));
        btnTienda.setForeground(new Color(60, 60, 60));
        btnTienda.setFont(new Font("Arial", Font.BOLD, 18));
        btnTienda.setFocusPainted(false);
        btnTienda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        // Estilo del botón Juegos
        btnJuegos.setBackground(new Color(202, 228, 241));
        btnJuegos.setForeground(new Color(60, 60, 60));
        btnJuegos.setFont(new Font("Arial", Font.BOLD, 18));
        btnJuegos.setFocusPainted(false);
        btnJuegos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        //  Estilo del botón Reinicio (color naranja/rojo para destacar)
        btnReinicio.setBackground(new Color(255, 100, 100));
        btnReinicio.setForeground(Color.WHITE);
        btnReinicio.setFont(new Font("Arial", Font.BOLD, 18));
        btnReinicio.setFocusPainted(false);
        btnReinicio.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        // Acción del botón Tienda
        btnTienda.addActionListener(e -> {
            TiendaComida pantallaTienda = new TiendaComida(cardLayout, contenedorPrincipal, mascotaActual);
            contenedorPrincipal.add(pantallaTienda, "PANTALLA_TIENDA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_TIENDA");
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();
        });

        // Acción del botón Juegos
        btnJuegos.addActionListener(e -> {
            SeleccionJuegos pantallaJuegos = new SeleccionJuegos(cardLayout, contenedorPrincipal, mascotaActual);
            contenedorPrincipal.add(pantallaJuegos, "PANTALLA_CUADRICULA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_CUADRICULA");
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();
        });

        //  Acción del botón Reinicio
        btnReinicio.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que quieres reiniciar TODO el juego?\n\n" +
                            "Se perderán todos los progresos:\n" +
                            "• Mascota actual\n" +
                            "• Monedas acumuladas\n" +
                            "• Nivel y experiencia\n\n" +
                            "Volverás a la pantalla de selección de mascota.",
                    " REINICIAR PARTIDA ",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                reiniciarJuegoCompleto();
            }
        });

        panelBotones.add(btnTienda);
        panelBotones.add(btnJuegos);
        panelBotones.add(btnReinicio); //  Añadir botón de reinicio
        panelCabecera.add(panelBotones, BorderLayout.CENTER);

        // Contador de monedas
        int monedasMostradas = (mascotaActual != null) ? mascotaActual.getMonedas() : 0;

        labelMonedas = new JLabel(monedasMostradas + " ", SwingConstants.CENTER);
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
    }

    /**
     *  MÉTODO DE REINICIO COMPLETO
     * Borra el archivo XML y vuelve a la pantalla de selección de mascota
     */
    private void reiniciarJuegoCompleto() {
        try {
            // 1. Borrar el archivo de partida guardada
            File archivoPartida = new File("partida.xml");
            if (archivoPartida.exists()) {
                boolean eliminado = archivoPartida.delete();
                if (eliminado) {
                    System.out.println(" Archivo partida.xml eliminado correctamente");
                } else {
                    System.out.println("No se pudo eliminar partida.xml");
                }
            }

            // 2. Limpiar el contenedor principal (eliminar todas las pantallas)
            contenedorPrincipal.removeAll();

            // 3. Crear una nueva pantalla de selección de mascota
            SeleccionMascota pantallaMascota = new SeleccionMascota(cardLayout, contenedorPrincipal, (Menu) ventanaPrincipal);
            contenedorPrincipal.add(pantallaMascota, "PANTALLA_SELECCION_MASCOTA");

            // 4. Volver a añadir el menú de inicio (por si acaso)
            // Buscamos si ya existe el menú de inicio en algún lado
            boolean tieneMenuInicio = false;
            for (Component comp : contenedorPrincipal.getComponents()) {
                if (comp.getName() != null && comp.getName().equals("MENU_INICIO")) {
                    tieneMenuInicio = true;
                    break;
                }
            }

            // Si no tiene el menú de inicio, lo añadimos (aunque normalmente ya está)
            if (!tieneMenuInicio) {
                // Necesitamos acceso al método crearMenuInicio de Menu
                // Por simplicidad, asumimos que Menu tiene un método para obtenerlo
                if (ventanaPrincipal instanceof Menu) {
                    JPanel menuInicio = ((Menu) ventanaPrincipal).getMenuInicioPanel();
                    if (menuInicio != null) {
                        menuInicio.setName("MENU_INICIO");
                        contenedorPrincipal.add(menuInicio, "MENU_INICIO");
                    }
                }
            }

            // 5. Mostrar la pantalla de selección de mascota
            cardLayout.show(contenedorPrincipal, "PANTALLA_SELECCION_MASCOTA");

            // 6. Refrescar el contenedor
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();

            // 7. Mensaje de confirmación
            JOptionPane.showMessageDialog(
                    this,
                    " Juego reiniciado correctamente.\n\nAhora puedes elegir una nueva mascota.",
                    "Reinicio completado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            System.out.println(" Error al reiniciar el juego: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error al reiniciar el juego: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // GETTER para que Menu pueda guardar la partida
    public Animal getMascota() {
        return this.mascotaActual;
    }

    // MÉTODO para actualizar las monedas desde cualquier juego
    public void actualizarMonedasVisuales() {
        if (labelMonedas != null && mascotaActual != null) {
            labelMonedas.setText(mascotaActual.getMonedas() + " ");
            System.out.println(" Monedas actualizadas en Interfaz: " + mascotaActual.getMonedas());
        }
    }

    // MÉTODO para que los juegos puedan agregar monedas y actualizar la vista
    public void ganarMonedasYActualizar(int cantidad) {
        if (mascotaActual != null) {
            mascotaActual.ganarMonedas(cantidad);
            actualizarMonedasVisuales();
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
}