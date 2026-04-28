package ventana;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class Interfaz extends JPanel {

    // Guardamos las referencias del menú principal para poder cambiar de pantalla
    public Interfaz(CardLayout cardLayout, JPanel contenedorPrincipal) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);

        // --- PANEL DE BOTONES (SUR) ---
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false);

        JButton btnIzquierda = new JButton("TIENDA");
        JButton btnDerecha = new JButton("JUEGOS");

        // Acción del botón JUEGOS (Derecha)
        btnDerecha.addActionListener(e -> {
            // 1. Creamos la pantalla de la cuadrícula
            CuadriculaCanales pantallaJuegos = new CuadriculaCanales(cardLayout, contenedorPrincipal);

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
