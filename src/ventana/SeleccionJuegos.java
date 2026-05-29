package ventana;

import animales.Animal;
import juegos.Packman;
import juegos.Sudoku;
import juegos.TorrePastel;
import juegos.saltitos.Saltitos;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SeleccionJuegos extends JPanel {

    private Animal mascotaActual;
    /**
     * Construye el menú de juegos con botones configurados con imagen y descripción.
     * * @param cardLayout Administrador de capas.
     * @param contenedorPrincipal Contenedor de pantallas.
     * @param mascota Referencia de la mascota actual.
     */
    public SeleccionJuegos(CardLayout cardLayout, JPanel contenedorPrincipal, Animal mascota) {
        this.mascotaActual = mascota;

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240));


        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.addActionListener(e -> {
            cardLayout.show(contenedorPrincipal, "INTERFAZ_PRINCIPAL");
        });

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.setOpaque(false);
        panelNorte.add(btnVolver);
        this.add(panelNorte, BorderLayout.NORTH);


        JPanel panelJuegos = new JPanel(new GridLayout(2, 2, 20, 20)); // 2x2 para 4 juegos
        panelJuegos.setOpaque(false);
        panelJuegos.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));


        String[][] juegos = {
                {"Packman", "Packman", "Packan.jpg"},
                {"Saltitos", "Plataformas", "saltitol.jpg"},
                {"Sudoku", "sudoku", "Sudoku.jpg"},
                {"Torrecita", "Construye torres", "torrePastel.jpg"}
        };

        for (int i = 0; i < juegos.length; i++) {
            String nombre = juegos[i][0];
            String descripcion = juegos[i][1];
            String rutaImagen = juegos[i][2];

            JButton botonJuego = crearBotonConImagen(nombre, descripcion, rutaImagen);

            botonJuego.addActionListener(e -> {
                lanzarJuego(nombre, cardLayout, contenedorPrincipal);
            });

            panelJuegos.add(botonJuego);
        }

        this.add(panelJuegos, BorderLayout.CENTER);
    }

    /**
     *  Crea un botón con imagen, título y descripción
     */
    private JButton crearBotonConImagen(String titulo, String descripcion, String rutaImagen) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        JPanel panelInterno = new JPanel(new BorderLayout());
        panelInterno.setOpaque(false);
        panelInterno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel labelTitulo = new JLabel(titulo, SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(new Color(50, 50, 50));
        panelInterno.add(labelTitulo, BorderLayout.NORTH);


        URL urlImagen = getClass().getResource("imagenes/" + rutaImagen);
        if (urlImagen != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlImagen);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel labelImagen = new JLabel(new ImageIcon(imagenEscalada), SwingConstants.CENTER);
            labelImagen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelInterno.add(labelImagen, BorderLayout.CENTER);
        } else {

            JLabel labelPlaceholder = new JLabel("", SwingConstants.CENTER);
            labelPlaceholder.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
            labelPlaceholder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelInterno.add(labelPlaceholder, BorderLayout.CENTER);
            System.out.println(" No se encontró la imagen: " + rutaImagen);
        }


        JLabel labelDescripcion = new JLabel(descripcion, SwingConstants.CENTER);
        labelDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));
        labelDescripcion.setForeground(new Color(100, 100, 100));
        panelInterno.add(labelDescripcion, BorderLayout.SOUTH);

        boton.add(panelInterno);
        return boton;
    }

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