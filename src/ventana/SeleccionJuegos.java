package ventana;

import animales.Animal;
import juegos.Packman;


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
            // Te lleva de vuelta a la interfaz donde está tu mascota y los botones principales
            cardLayout.show(contenedorPrincipal, "INTERFAZ_PRINCIPAL");
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
        Animal a=null;
        switch (nombre) {
            case "Packman":
                // Instanciamos tu Pac-man
                juegoSeleccionado = new Packman(cl,cont,a);

                // TRUCO IMPORTANTE PARA PACKMAN: Como necesita usar las flechas del teclado,
                // le añadimos un botón rápido de "Volver" arriba para no quedarnos atrapados.
                juegoSeleccionado.setLayout(new BorderLayout());
                JButton btnVolverPackman = new JButton("⬅ Salir del Juego");
                btnVolverPackman.addActionListener(e -> cl.show(cont, "PANTALLA_CUADRICULA"));
                JPanel panelPackmanNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panelPackmanNorte.setOpaque(false);
                panelPackmanNorte.add(btnVolverPackman);
                juegoSeleccionado.add(panelPackmanNorte, BorderLayout.NORTH);
                break;

            case "Sudoku":
                // Descomentamos y pasamos los navegadores de pantallas
                juegoSeleccionado = new juegos.Sudoku(cl, cont,a);
                break;

            case "Torrecita":
                // Descomentamos y pasamos los navegadores de pantallas
                juegoSeleccionado = new juegos.TorrePastel(cl, cont,a);
                break;

            case "Saltitos":
                juegoSeleccionado = new juegos.saltitos.Saltitos(cl,cont , a);
                break;

            default:
                juegoSeleccionado = new JPanel();
                juegoSeleccionado.add(new JLabel("Juego de " + nombre + " en desarrollo"));
        }

        // Añadimos el juego dinámicamente al contenedor y cambiamos de pantalla
        cont.add(juegoSeleccionado, "PANTALLA_JUEGO");
        cl.show(cont, "PANTALLA_JUEGO");

        // Esto es vital para que Pac-Man detecte el teclado inmediatamente al abrirse
        juegoSeleccionado.setFocusable(true);
        juegoSeleccionado.requestFocusInWindow();
    }
}
