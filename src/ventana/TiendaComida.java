package ventana;

import javax.swing.*;
import java.awt.*;

public class TiendaComida extends JPanel {

    private double totalCarrito = 0;

    // Ahora el constructor recibe el CardLayout y el Contenedor
    public TiendaComida(CardLayout cardLayout, JPanel contenedorPrincipal) {
        // Usamos BorderLayout para poner el botón de volver arriba y la tienda en el centro
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255, 255, 240));

        // --- 1. BOTÓN VOLVER (Norte) ---
        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.addActionListener(e -> {
            // Al usar .first() saltas directamente al menú principal sin importar el historial
            cardLayout.first(contenedorPrincipal);
        });

        // Panel pequeño para que el botón no ocupe todo el ancho
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.setOpaque(false);
        panelNorte.add(btnVolver);
        this.add(panelNorte, BorderLayout.NORTH);

        // --- 2. CUADRÍCULA DE PRODUCTOS (Centro) ---
        JPanel panelProductos = new JPanel(new GridLayout(3, 4, 15, 15));
        panelProductos.setOpaque(false);

        String[] productos = {"Manzanas", "Pescado", "Pan", "Leche"};
        Integer[] precios = {2, 6, 1, 2};

        for (int i = 0; i < productos.length; i++) {
            String nombre = productos[i];
            double precio = precios[i];
            JButton botonProducto = crearBotonProducto(nombre, precio);
            botonProducto.addActionListener(e -> comprarProducto(nombre, precio));
            panelProductos.add(botonProducto);
        }

        this.add(panelProductos, BorderLayout.CENTER);
    }

    private JButton crearBotonProducto(String nombre, double precio) {
        String texto = "<html><center><b>" + nombre + "</b><br>" + precio + "€</center></html>";
        JButton btn = new JButton(texto);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        return btn;
    }

    private void comprarProducto(String nombre, double precio) {
        totalCarrito += precio;
        JOptionPane.showMessageDialog(this, "Añadido: " + nombre + "\nTotal: " + String.format("%.2f", totalCarrito) + "€");
    }
}
