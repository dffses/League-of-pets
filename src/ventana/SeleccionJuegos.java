package ventana;

import animales.Animal;
import juegos.Packman;
import juegos.Sudoku;
import juegos.TorrePastel;
import juegos.saltitos.Saltitos;

import javax.swing.*;
import java.awt.*;

public class SeleccionJuegos extends JPanel {

    private Animal mascotaActual;  // Guardamos la mascota

    // MODIFICADO: Constructor ahora recibe la mascota
    public SeleccionJuegos(CardLayout cardLayout, JPanel contenedorPrincipal, Animal mascota) {
        this.mascotaActual = mascota;  //  Guardamos referencia

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240));

        // --- BOTÓN VOLVER (Norte) ---
        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.addActionListener(e -> {
            cardLayout.show(contenedorPrincipal, "INTERFAZ_PRINCIPAL");
        });

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.setOpaque(false);
        panelNorte.add(btnVolver);
        this.add(panelNorte, BorderLayout.NORTH);

        // --- REJILLA DE JUEGOS (Centro) ---
        JPanel panelJuegos = new JPanel(new GridLayout(3, 4, 15, 15));
        panelJuegos.setOpaque(false);
        panelJuegos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] nombresJuegos = {
                "Packman", "Saltitos", "Sudoku", "Torrecita"
        };

        for (int i = 0; i < nombresJuegos.length; i++) {
            String nombreActual = nombresJuegos[i];
            JButton botonJuego = crearBotonCanal(nombreActual);

            botonJuego.addActionListener(e -> {
                lanzarJuego(nombreActual, cardLayout, contenedorPrincipal);
            });

            panelJuegos.add(botonJuego);
        }

        this.add(panelJuegos, BorderLayout.CENTER);
    }

    private JButton crearBotonCanal(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        return btn;
    }

    // ✅ MODIFICADO: Ahora pasa la mascota real a los juegos
    private void lanzarJuego(String nombre, CardLayout cl, JPanel cont) {
        JPanel juegoSeleccionado;

        switch (nombre) {
            case "Packman":
                juegoSeleccionado = new Packman(cl, cont, mascotaActual);
                break;

            case "Sudoku":
                juegoSeleccionado = new Sudoku(cl, cont, mascotaActual);
                break;

            case "Torrecita":
                juegoSeleccionado = new TorrePastel(cl, cont, mascotaActual);
                break;

            case "Saltitos":
                juegoSeleccionado = new Saltitos(cl, cont, mascotaActual);
                break;

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
