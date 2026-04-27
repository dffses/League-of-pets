package ventana;

import juegos.Packman;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    JFrame menu_principal;

    public Menu() {
        menu_principal = new JFrame("League of pets");
        menu_principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu_principal.setSize(600, 500);
        menu_principal.setLocationRelativeTo(null);

        mostrarMenu();
        menu_principal.setVisible(true);
    }

    public void mostrarMenu() {
        JPanel panelMenu=new JPanel();

        panelMenu=new JPanel();
        panelMenu.setBackground(Color.DARK_GRAY);
        panelMenu.setLayout(new GridLayout());

        JButton botonIniciar=new JButton("INICIAR LEAGUE");
        botonIniciar.setPreferredSize(new Dimension(200,50));


        botonIniciar.addActionListener(e -> {
            menu_principal.getContentPane().removeAll(); // Borramos el menú
            menu_principal.add(new Packman());       // Añadimos la clase del juego
            menu_principal.revalidate();                // Refrescamos la estructura
            menu_principal.repaint();                   // Redujamos todo
            menu_principal.pack();                      // Ajustamos el tamaño
            menu_principal.requestFocusInWindow();      // Importante para que el teclado funcione
        });

        panelMenu.add(botonIniciar);
        menu_principal.add(panelMenu);
    }

}
