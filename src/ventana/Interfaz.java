package ventana;

import javax.swing.*;
import java.awt.*;

public class Interfaz extends JPanel {

    public Interfaz(CardLayout cardLayout, JPanel contenedorPrincipal) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);

        // --- PANEL DE BOTONES (SUR) ---
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false);

        JButton btnIzquierda = new JButton("TIENDA");
        JButton btnDerecha = new JButton("JUEGOS");

        // --- Acción del botón TIENDA (Izquierda) ---
        // Dentro de la clase Interfaz, en el listener del btnIzquierda:
        btnIzquierda.addActionListener(e -> {
            // Ahora le pasamos cardLayout y contenedorPrincipal
            TiendaComida pantallaTienda = new TiendaComida(cardLayout, contenedorPrincipal);

            contenedorPrincipal.add(pantallaTienda, "PANTALLA_TIENDA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_TIENDA");
            contenedorPrincipal.revalidate();
            contenedorPrincipal.repaint();
        });

        // --- Acción del botón JUEGOS (Derecha) ---
        btnDerecha.addActionListener(e -> {
            SeleccionJuegos pantallaJuegos = new SeleccionJuegos(cardLayout, contenedorPrincipal);
            contenedorPrincipal.add(pantallaJuegos, "PANTALLA_CUADRICULA");
            cardLayout.show(contenedorPrincipal, "PANTALLA_CUADRICULA");
        });

        panelBotones.add(btnIzquierda, BorderLayout.WEST);
        panelBotones.add(btnDerecha, BorderLayout.EAST);
        this.add(panelBotones, BorderLayout.SOUTH);

        // --- CONTENIDO CENTRAL ---
        JLabel label = new JLabel("ESTÁS EN LA INTERFAZ", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        this.add(label, BorderLayout.CENTER);
    }
}
