package ventana;

import juegos.Packman;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class CuadriculaCanales extends JPanel {

    public CuadriculaCanales(CardLayout cardLayout, JPanel contenedorPrincipal) {
        // Configuramos el diseño de rejilla (3 filas, 4 columnas)
        this.setLayout(new GridLayout(3, 4, 15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(new Color(240, 240, 240));

        // Lista de nombres de tus juegos (ajusta estos nombres a tus clases)
        String[] nombresJuegos = {
                "Packman", "Saltitos", "Sudoku", "Torrecita",
                "Slot 5", "Slot 6", "Slot 7", "Slot 8",
                "Slot 9", "Slot 10", "Slot 11", "Slot 12"
        };

        // Creamos los 12 botones usando un bucle
        for (int i = 0; i < nombresJuegos.length; i++) {
            String nombreActual = nombresJuegos[i];
            JButton botonJuego = crearBotonCanal(nombreActual);

            // Acción al pulsar el cuadrado
            botonJuego.addActionListener(e -> {
                lanzarJuego(nombreActual, cardLayout, contenedorPrincipal);
            });

            this.add(botonJuego);
        }
    }

    // Método para darle estilo al botón (que parezca un canal de Wii)
    private JButton crearBotonCanal(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false); // Quita el recuadro feo al hacer clic
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        return btn;
    }

    // Lógica para decidir qué juego abrir
    private void lanzarJuego(String nombre, CardLayout cl, JPanel cont) {
        JPanel juegoSeleccionado;

        // Aquí es donde "llamas" a tus distintas clases
        switch (nombre) {
            case "Packman":
                juegoSeleccionado = new Packman();
                break;
            case "Snake":
                // juegoSeleccionado = new Snake();
                juegoSeleccionado = new JPanel(); // Temporal si no tienes la clase
                break;
            default:
                juegoSeleccionado = new JPanel();
                juegoSeleccionado.add(new JLabel("Juego de " + nombre + " en desarrollo"));
        }

        // Añadimos el juego al mazo y lo mostramos
        cont.add(juegoSeleccionado, "PANTALLA_JUEGO");
        cl.show(cont, "PANTALLA_JUEGO");

        // ¡No olvides pedir el foco para que funcionen las teclas!
        juegoSeleccionado.requestFocusInWindow();
    }
}
