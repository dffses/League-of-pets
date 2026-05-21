package ventana;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Interfaz extends JPanel {
    private Image imagenFondo;

    public Interfaz(CardLayout cardLayout, JPanel contenedorPrincipal, String nombreMascota, String rutaImagenMascota) {
        this.setLayout(new BorderLayout());


        URL urlFondo = getClass().getResource("fondo_tienda.jpg");
        if (urlFondo != null) {
            imagenFondo = new ImageIcon(urlFondo).getImage();
        }


        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

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
            TiendaComida pantallaTienda = new TiendaComida(cardLayout, contenedorPrincipal);
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
        this.add(panelBotones, BorderLayout.NORTH);



        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;


        gbc.gridy = 0;

        gbc.insets = new Insets(180, 10, 5, 10);

        JLabel labelNombre = new JLabel(nombreMascota, SwingConstants.CENTER);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}