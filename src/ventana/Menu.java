package ventana;

import juegos.Packman;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    JFrame menu_principal;
    JPanel contenedor; // El "padre" de todos los paneles
    CardLayout cards;

    public Menu() {
        menu_principal = new JFrame("League of pets");
        menu_principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu_principal.setSize(600, 500);

        cards = new CardLayout();
        contenedor = new JPanel(cards);

        // Añadimos el menú inicial
        contenedor.add(crearMenuInicio(), "MENU_INICIO");

        menu_principal.add(contenedor);
        menu_principal.setLocationRelativeTo(null);
        menu_principal.setVisible(true);
    }

    private JPanel crearMenuInicio() {
        JPanel panel = new JPanel(new GridBagLayout());
        JButton boton = new JButton("ABRIR INTERFAZ");

        boton.addActionListener(e -> {
            // AQUÍ ESTÁ EL TRUCO:
            // 1. Creamos la interfaz
            Interfaz miInterfaz = new Interfaz(cards, contenedor);

            contenedor.add(miInterfaz, "INTERFAZ_PRINCIPAL");
            cards.show(contenedor, "INTERFAZ_PRINCIPAL");

            // Refresco de seguridad
            contenedor.revalidate();
            contenedor.repaint();
        });

        panel.add(boton);
        return panel;
    }
}
