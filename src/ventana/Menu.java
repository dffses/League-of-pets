package ventana;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    JFrame menu_principal;
    JPanel contenedor;
    CardLayout cards;
    /**
     * Configura el marco principal e inserta la pantalla inicial de bienvenida.
     */
    public Menu() {
        menu_principal = new JFrame("League of pets");
        menu_principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu_principal.setSize(750, 580); // Tamaño ideal adaptado para las tres mascotas

        cards = new CardLayout();
        contenedor = new JPanel(cards);

        contenedor.add(crearMenuInicio(), "MENU_INICIO");

        menu_principal.add(contenedor);
        menu_principal.setLocationRelativeTo(null);
        menu_principal.setVisible(true);
    }

    private JPanel crearMenuInicio() {
        JPanel panel = new JPanel(new GridBagLayout());
        JButton boton = new JButton("LEAGUE_OF_PETS");
        boton.setFont(new Font("Arial", Font.BOLD, 16));

        boton.addActionListener(e -> {
            // Pasamos 'this' como referencia del Menú raíz
            SeleccionMascota pantallaMascota = new SeleccionMascota(cards, contenedor, this);
            contenedor.add(pantallaMascota, "PANTALLA_SELECCION_MASCOTA");
            cards.show(contenedor, "PANTALLA_SELECCION_MASCOTA");

            contenedor.revalidate();
            contenedor.repaint();
        });

        panel.add(boton);
        return panel;
    }
}