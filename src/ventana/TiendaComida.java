package ventana;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import animales.Animal;

public class TiendaComida extends JPanel {

    private Animal mascotaActual;
    private JLabel lblMonedas;
    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;
    /**
     * Inicializa la tienda, el marcador de monedas y la rejilla con el catálogo de productos.
     * * @param cardLayout Administrador de capas.
     * @param contenedorPrincipal Contenedor de pantallas.
     * @param mascota Mascota actual de la sesión.
     */
    public TiendaComida(CardLayout cardLayout, JPanel contenedorPrincipal, Animal mascota) {
        this.cardLayout = cardLayout;
        this.contenedorPrincipal = contenedorPrincipal;
        this.mascotaActual = mascota;

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255, 255, 240));

        // --- PANEL SUPERIOR (Norte): Botón volver y Monedas ---
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(200, 200, 200));
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> {
            // Actualizar la interfaz principal antes de volver
            actualizarInterfazPrincipal();
            cardLayout.show(contenedorPrincipal, "INTERFAZ_PRINCIPAL");
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBoton.setOpaque(false);
        panelBoton.add(btnVolver);
        panelNorte.add(panelBoton, BorderLayout.WEST);

        lblMonedas = new JLabel(" " + mascotaActual.getMonedas() + " monedas");
        lblMonedas.setFont(new Font("Arial", Font.BOLD, 18));
        lblMonedas.setForeground(new Color(255, 140, 0));
        lblMonedas.setBackground(new Color(255, 255, 200));
        lblMonedas.setOpaque(true);
        lblMonedas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 140, 0), 2),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        JPanel panelMonedas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelMonedas.setOpaque(false);
        panelMonedas.add(lblMonedas);
        panelNorte.add(panelMonedas, BorderLayout.EAST);

        this.add(panelNorte, BorderLayout.NORTH);


        JPanel panelProductos = new JPanel(new GridLayout(2, 2, 25, 25));
        panelProductos.setOpaque(false);
        panelProductos.setBorder(BorderFactory.createEmptyBorder(40, 50, 50, 50));


        Object[][] productos = {
                {"Manzanas", 15, "Fruta fresca y crujiente", "Manzana.jpg"},
                {"Pescado", 35, "Pescado fresco del día", "Pescado.jpg"},
                {"Pan", 10, "Pan recién horneado", "Pan.jpg"},
                {"Leche", 20, "Leche pura y cremosa", "Leche.jpg"}
        };

        for (int i = 0; i < productos.length; i++) {
            String nombre = (String) productos[i][0];
            int precio = (int) productos[i][1];
            String descripcion = (String) productos[i][2];
            String rutaImagen = (String) productos[i][3];

            JButton botonProducto = crearBotonProductoPremium(nombre, precio, descripcion, rutaImagen);
            botonProducto.addActionListener(e -> procesarCompra(nombre, precio));
            panelProductos.add(botonProducto);
        }

        this.add(panelProductos, BorderLayout.CENTER);


        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setOpaque(false);
        JLabel lblInstruccion = new JLabel(" Haz clic en cualquier producto para comprarlo y alimentar a tu mascota");
        lblInstruccion.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInstruccion.setForeground(new Color(100, 100, 100));
        panelInferior.add(lblInstruccion);
        this.add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     *  Crea un botón de producto PREMIUM con imagen grande, nombre, precio y descripción
     */
    private JButton crearBotonProductoPremium(String nombre, int precio, String descripcion, String rutaImagen) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 150), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(255, 250, 240));
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 140, 0), 3),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 180, 150), 2),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
        });


        JPanel panelInterno = new JPanel(new BorderLayout(10, 10));
        panelInterno.setOpaque(false);


        JLabel labelNombre = new JLabel(nombre, SwingConstants.CENTER);
        labelNombre.setFont(new Font("Arial", Font.BOLD, 20));
        labelNombre.setForeground(new Color(80, 60, 40));
        labelNombre.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelInterno.add(labelNombre, BorderLayout.NORTH);


        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelImagen.setOpaque(false);

        URL urlImagen = getClass().getResource("/imagenes/" + rutaImagen);
        if (urlImagen != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlImagen);
            // Escalamos la imagen a 130x130 píxeles
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            JLabel labelImagen = new JLabel(new ImageIcon(imagenEscalada));
            labelImagen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelImagen.add(labelImagen);
        } else {

            JLabel labelPlaceholder = new JLabel("", SwingConstants.CENTER);
            labelPlaceholder.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            panelImagen.add(labelPlaceholder);
            System.out.println("️ No se encontró la imagen en: /imagenes/" + rutaImagen);
        }
        panelInterno.add(panelImagen, BorderLayout.CENTER);


        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 0, 5));
        panelInfo.setOpaque(false);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        JLabel labelPrecio = new JLabel(" " + precio + " monedas", SwingConstants.CENTER);
        labelPrecio.setFont(new Font("Arial", Font.BOLD, 16));
        labelPrecio.setForeground(new Color(255, 140, 0));

        JLabel labelDescripcion = new JLabel(descripcion, SwingConstants.CENTER);
        labelDescripcion.setFont(new Font("Arial", Font.PLAIN, 11));
        labelDescripcion.setForeground(new Color(120, 100, 80));

        panelInfo.add(labelPrecio);
        panelInfo.add(labelDescripcion);
        panelInterno.add(panelInfo, BorderLayout.SOUTH);

        boton.add(panelInterno);
        return boton;
    }

    /**
     * Procesa la compra de un producto
     */
    private void procesarCompra(String nombre, int precio) {

        boolean compraExitosa = mascotaActual.comprarComida(precio);

        if (compraExitosa) {

            lblMonedas.setText(" " + mascotaActual.getMonedas() + " monedas");


            JOptionPane.showMessageDialog(this,
                    " ¡Compra realizada!\n\nHas comprado " + nombre + " por " + precio + " monedas.\n\n" +
                            " Tu mascota ha comido y está más feliz.",
                    "Compra exitosa",
                    JOptionPane.INFORMATION_MESSAGE);


            actualizarInterfazPrincipal();
        } else {
            JOptionPane.showMessageDialog(this,
                    " No tienes suficientes monedas\n\n" +
                            "Necesitas: " + precio + " monedas\n" +
                            "Tienes: " + mascotaActual.getMonedas() + " monedas\n\n" +
                            "¡Juega para ganar más monedas!",
                    "Saldo insuficiente",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza el contador de monedas en la interfaz principal
     */
    private void actualizarInterfazPrincipal() {
        // Buscar la interfaz principal en el contenedor
        for (Component comp : contenedorPrincipal.getComponents()) {
            if (comp instanceof Interfaz) {
                ((Interfaz) comp).actualizarMonedasVisuales();
                break;
            }
        }
    }
}