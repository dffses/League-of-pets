package ventana;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import animales.Animal;

public class Interfaz extends JPanel {
    private Image imagenFondo;
    private Animal mascotaActual;
    private JLabel labelMonedas;

    public Interfaz(CardLayout cardLayout, JPanel contenedorPrincipal, Animal mascota, String rutaImagenMascota) {
        // SEGURIDAD AUTOMÁTICA: Si nos pasan un objeto null por error, evitamos que la app explote
        this.mascotaActual = mascota;
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
            SeleccionJuegos pantallaJuegos = new SeleccionJuegos(cardLayout, contenedorPrincipal);
            contenedorPrincipal.add(pantallaJuegos, "PANTALLA_CUADRICULA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_CUADRICULA");
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();
        });

        panelBotones.add(btnIzquierda);
        panelBotones.add(btnDerecha);
        panelCabecera.add(panelBotones, BorderLayout.CENTER);

        // CONTROL DE SEGURIDAD: Comprobamos si la mascota es null antes de leer sus monedas
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

        // CONTROL DE SEGURIDAD: Evitamos el fallo también al recuperar el nombre
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

    public void actualizarMonedasVisuales() {
        if (labelMonedas != null && mascotaActual != null) {
            labelMonedas.setText(mascotaActual.getMonedas() + " 🪙");
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