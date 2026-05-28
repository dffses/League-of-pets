package ventana;

import animales.Animal;
import animales.Especie;
import animales.Genero;
import animales.Perro; // 🛠️ Importamos las subclases específicas
// import animales.Gato;     <- Descomenta estos si tus clases se llaman así
// import animales.Cocodrilo;<- Descomenta estos si tus clases se llaman así

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SeleccionMascota extends JPanel {
    private Image imagenFondo;
    private Menu menuRaiz; // Guardamos la referencia del menú principal

    public SeleccionMascota(CardLayout cardLayout, JPanel contenedorPrincipal, Menu menuRaiz) {
        this.setLayout(new GridBagLayout());
        this.menuRaiz = menuRaiz;

        // Cargar el fondo de la Pet Shop
        URL urlFondo = getClass().getResource("fondo_primero.jpg");
        if (urlFondo != null) {
            imagenFondo = new ImageIcon(urlFondo).getImage();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        // ¡Selecciona a tu Compañero!
        JLabel titulo = new JLabel("¡Selecciona a tu Compañero!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.BLACK);

        gbc.insets = new Insets(200, 0, 10, 0);
        this.add(titulo, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(40, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        JPanel panelMascotas = new JPanel(new GridLayout(1, 3, 30, 0));
        panelMascotas.setOpaque(false);

        // Nombres y rutas de recursos
        JButton btnPerro = crearBotonTransparente("Bran", "bran_perro.png");
        JButton btnGato = crearBotonTransparente("Shasha", "shasha_gato.png");
        JButton btnCocodrilo = crearBotonTransparente("Steve", "cocodrilo.png");

        // 🛠️ CORREGIDO: Pasamos las rutas relativas correctas usando "/" si están en carpetas
        btnPerro.addActionListener(e -> avanzarAInterfaz("Bran", Especie.PERRO, "bran_perro.png", cardLayout, contenedorPrincipal));
        btnGato.addActionListener(e -> avanzarAInterfaz("Shasha", Especie.GATO, "shasha_gato.png", cardLayout, contenedorPrincipal));
        btnCocodrilo.addActionListener(e -> avanzarAInterfaz("Steve", Especie.COCODRILO, "cocodrilo.png", cardLayout, contenedorPrincipal));

        panelMascotas.add(btnPerro);
        panelMascotas.add(btnGato);
        panelMascotas.add(btnCocodrilo);

        this.add(panelMascotas, gbc);
    }

    private JButton crearBotonTransparente(String nombre, String rutaImagen) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());

        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setOpaque(false);
        boton.setFocusPainted(false);

        JLabel labelNombre = new JLabel(nombre, SwingConstants.CENTER);
        labelNombre.setFont(new Font("Arial", Font.BOLD, 18));
        labelNombre.setForeground(new Color(60, 60, 60));
        labelNombre.setBackground(new Color(202, 228, 241, 80));
        labelNombre.setOpaque(true);
        labelNombre.setBorder(BorderFactory.createEmptyBorder(2, 12, 2, 12));

        boton.add(labelNombre, BorderLayout.NORTH);

        URL urlImg = getClass().getResource(rutaImagen);
        if (urlImg != null) {
            Image imgEscalada = new ImageIcon(urlImg).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel labelImg = new JLabel(new ImageIcon(imgEscalada), SwingConstants.CENTER);
            labelImg.setOpaque(false);
            labelImg.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
            boton.add(labelImg, BorderLayout.CENTER);
        } else {
            boton.add(new JLabel("[Sin Foto]", SwingConstants.CENTER), BorderLayout.CENTER);
        }

        return boton;
    }

    /**
     * 🛠️ MODIFICADO: Aplicamos polimorfismo real creando la subclase según la especie elegida.
     */
    private void avanzarAInterfaz(String nombre, Especie especie, String ruta, CardLayout cl, JPanel cont) {
        Animal mascotaNueva = null;

        // Evaluamos la especie elegida para instanciar su clase hija correspondiente
        switch (especie) {
            case PERRO:
                mascotaNueva = new Perro(nombre, especie, Genero.Macho);
                break;
            case GATO:
                // Si tienes la clase Gato creada, cámbialo por: new Gato(nombre, especie, Genero.Macho);
                mascotaNueva = new Perro(nombre, especie, Genero.Macho);
                break;
            case COCODRILO:
                // Si tienes la clase Cocodrilo creada, cámbialo aquí también
                mascotaNueva = new Perro(nombre, especie, Genero.Macho);
                break;
        }

        if (mascotaNueva != null) {
            // Guardar la partida inmediatamente al crear la mascota
            mascotaNueva.guardarPartidaCompleta();

            // 🛠️ CORREGIDO: Pasamos el objeto 'mascotaNueva' y la referencia 'ruta' a la Interfaz principal
            Interfaz miInterfaz = new Interfaz(cl, cont, mascotaNueva, ruta,menuRaiz);
            cont.add(miInterfaz, "INTERFAZ_PRINCIPAL");
            cl.show(cont, "INTERFAZ_PRINCIPAL");
            cont.revalidate();
            cont.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}