package ventana;

import javax.swing.*;
import java.awt.*;
// Importamos la clase Animal (asegúrate de que el paquete sea el correcto si está en otro lado)
import animales.Animal;

public class TiendaComida extends JPanel {

    // NUEVO ATRIBUTO: Guardamos la referencia de la mascota actual del juego
    private Animal mascotaActual;

    // Label para mostrar en la tienda cuántas monedas tiene el jugador en tiempo real
    private JLabel lblMonedas;

    // MODIFICADO: El constructor ahora también recibe el objeto Animal (tu mascota)
    public TiendaComida(CardLayout cardLayout, JPanel contenedorPrincipal, Animal mascota) {
        // Asignamos la mascota a nuestro atributo de clase
        this.mascotaActual = mascota;

        // Usamos BorderLayout para poner el menú de arriba y los productos en el centro
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255, 255, 240));

        // --- 1. PANEL SUPERIOR (Norte): Botón volver y Monedas actuales ---
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);

        // Botón volver
        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.addActionListener(e -> {
            cardLayout.show(contenedorPrincipal, "INTERFAZ_PRINCIPAL");
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBoton.setOpaque(false);
        panelBoton.add(btnVolver);
        panelNorte.add(panelBoton, BorderLayout.WEST);

        // NUEVO: Indicador visual de monedas en la esquina superior derecha
        lblMonedas = new JLabel("Monedas: " + mascotaActual.getMonedas() + " 🪙 ");
        lblMonedas.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panelMonedas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelMonedas.setOpaque(false);
        panelMonedas.add(lblMonedas);
        panelNorte.add(panelMonedas, BorderLayout.EAST);

        this.add(panelNorte, BorderLayout.NORTH);

        // --- 2. CUADRÍCULA DE PRODUCTOS (Centro) ---
        JPanel panelProductos = new JPanel(new GridLayout(3, 4, 15, 15));
        panelProductos.setOpaque(false);

        // MODIFICADO: Cambiado a nombres de fantasía de ítems y precios enteros (monedas)
        String[] productos = {"Manzanas", "Pescado", "Pan", "Leche"};
        Integer[] precios = {15, 35, 10, 20}; // Precios en monedas virtuales

        String nombre;
        for (int i = 0; i < productos.length; i++) {
            nombre = productos[i];
            int precio = precios[i]; // Ahora es int

            JButton botonProducto = crearBotonProducto(nombre, precio);
            // Cuando haces clic, llama a procesarCompra pasándole el precio
            String finalNombre = nombre;
            botonProducto.addActionListener(e -> procesarCompra(finalNombre, precio));
            panelProductos.add(botonProducto);
        }

        this.add(panelProductos, BorderLayout.CENTER);

    }

    // MODIFICADO: Recibe precio como 'int' y cambia el símbolo '€' por 'Monedas'
    // CORREGIDO: 'String nombre' en lugar de 'String_nombre'
    JButton crearBotonProducto(String nombre, int precio) {
        String texto = "<html><center><b>" + nombre + "</b><br>" + precio + " Monedas</center></html>";
        JButton btn = new JButton(texto);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        return btn;
    }

    // NUEVO MÉTODO: Conecta directamente con la lógica de tu clase Animal
    // CORREGIDO: 'String nombre' en lugar de 'String_nombre'
    private void procesarCompra(String nombre, int precio) {
        // Llamamos al método que creamos en tu clase Animal.
        // Este método ya comprueba si hay dinero, resta las monedas, alimenta al animal y guarda en el XML.
        boolean compraExitosa = mascotaActual.comprarComida(precio);

        if (compraExitosa) {
            // Si la mascota tenía dinero suficiente, actualizamos el texto de la pantalla de la tienda
            lblMonedas.setText("Monedas: " + mascotaActual.getMonedas() + " 🪙 ");
            JOptionPane.showMessageDialog(this, "¡Compraste " + nombre + "!\nTu mascota se ha alimentado.");
        } else {
            // Si el método devolvió false es porque no alcanzaba el dinero
            JOptionPane.showMessageDialog(this, "No tienes suficientes monedas para comprar " + nombre, "Error de saldo", JOptionPane.ERROR_MESSAGE);
        }
    }
}
