package ventana;

import juegos.Packman;
import juegos.saltitos.Saltitos;

import javax.swing.*;
import java.awt.*;

public class SeleccionJuegos extends JPanel {

    public SeleccionJuegos(CardLayout cardLayout, JPanel contenedorPrincipal) {
        // Cambiamos el diseño principal a BorderLayout para organizar el botón arriba
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240));

        // --- 1. BOTÓN VOLVER (Norte) ---
        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.addActionListener(e -> {
            // Te lleva directo a la primera pantalla del contenedor
            cardLayout.first(contenedorPrincipal);
        });

        // Panel contenedor para que el botón de volver no se estire demasiado
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.setOpaque(false);
        panelNorte.add(btnVolver);
        this.add(panelNorte, BorderLayout.NORTH);

        // --- 2. REJILLA DE JUEGOS (Centro) ---
        JPanel panelJuegos = new JPanel(new GridLayout(3, 4, 15, 15));
        panelJuegos.setOpaque(false);
        panelJuegos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Lista de nombres de tus juegos
        String[] nombresJuegos = {
                "Packman", "Saltitos", "Sudoku", "Torrecita"
        };

        // Creamos los botones usando el bucle
        for (int i = 0; i < nombresJuegos.length; i++) {
            String nombreActual = nombresJuegos[i];
            JButton botonJuego = crearBotonCanal(nombreActual);

            // Acción al pulsar el cuadrado
            botonJuego.addActionListener(e -> {
                lanzarJuego(nombreActual, cardLayout, contenedorPrincipal);
            });

            panelJuegos.add(botonJuego);
        }

        this.add(panelJuegos, BorderLayout.CENTER);
    }

    // Método para darle estilo al botón (que parezca un canal de Wii)
    private JButton crearBotonCanal(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        return btn;
    }

    // Lógica para decidir qué juego abrir
    private void lanzarJuego(String nombre, CardLayout cl, JPanel cont) {
        JPanel juegoSeleccionado;

        switch (nombre) {
            case "Packman":
                juegoSeleccionado = new Packman();
                break;
            case "Saltitos":
                juegoSeleccionado = new Saltitos();
                break;
//            case "Sudoku":
//                juegoSeleccionado = new Sudoku();
//                break;
//            case "TorrePastel":
//                juegoSeleccionado = new TorrePastel();
//                break;
            default:
                juegoSeleccionado = new JPanel();
                juegoSeleccionado.add(new JLabel("Juego de " + nombre + " en desarrollo"));
        }

        cont.add(juegoSeleccionado, "PANTALLA_JUEGO");
        cl.show(cont, "PANTALLA_JUEGO");

        juegoSeleccionado.setFocusable(true);
        juegoSeleccionado.requestFocusInWindow();
    }
}
