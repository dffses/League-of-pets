package ventana;

import animales.Animal;
import animales.Especie;
import animales.Genero;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SeleccionMascota extends JPanel {
    private Image imagenFondo;
    private Menu menuRaiz;

    public SeleccionMascota(CardLayout cardLayout, JPanel contenedorPrincipal, Menu menuRaiz) {
        // Usamos GridBagLayout en el panel principal para controlar las alturas perfectamente
        this.setLayout(new GridBagLayout());

        this.menuRaiz=menuRaiz;

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

        // Subimos el primer número a 200 para que el título baje bastante
        gbc.insets = new Insets(200, 0, 10, 0);
        this.add(titulo, gbc);

        // la mascota a altura de alfombra
        gbc.gridy = 1;
        gbc.weighty = 1.0;

        // Ajustamos este margen superior para que las mascotas no se pisen con el título modificado
        gbc.insets = new Insets(40, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        JPanel panelMascotas = new JPanel(new GridLayout(1, 3, 30, 0));
        panelMascotas.setOpaque(false); // Transparente para ver el fondo de la tienda

        // Creamos los botones gigantes con los nombres arriba y subrayados transparentes
        // Pasamos también la especie correspondiente para recuperarla en la acción
        JButton btnPerro = crearBotonTransparente("Bran", "bran_perro.png");
        JButton btnGato = crearBotonTransparente("Shasha", "shasha_gato.png");
        JButton btnCocodrilo = crearBotonTransparente("Steve", "cocodrilo.png");

        // Acciones al hacer clic asociando cada mascota a su Especie correspondiente
        btnPerro.addActionListener(e -> avanzarAInterfaz("Bran", Especie.PERRO, "imagenes.bran_perro.png", cardLayout, contenedorPrincipal));
        btnGato.addActionListener(e -> avanzarAInterfaz("Shasha", Especie.GATO, "imagenes.shasha_gato.png", cardLayout, contenedorPrincipal));
        btnCocodrilo.addActionListener(e -> avanzarAInterfaz("Steve", Especie.COCODRILO, "imagenes.cocodrilo.png", cardLayout, contenedorPrincipal));

        panelMascotas.add(btnPerro);
        panelMascotas.add(btnGato);
        panelMascotas.add(btnCocodrilo);

        this.add(panelMascotas, gbc);
    }

    // Método que crea el botón invisible, la foto gigante y el nombre arriba con subrayado pastel translúcido
    private JButton crearBotonTransparente(String nombre, String rutaImagen) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());

        // Volvemos el botón completamente transparente para eliminar el bloque blanco feo
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setOpaque(false);
        boton.setFocusPainted(false);

        // nombre de la mascota estilizado
        JLabel labelNombre = new JLabel(nombre, SwingConstants.CENTER);
        labelNombre.setFont(new Font("Arial", Font.BOLD, 18));
        labelNombre.setForeground(new Color(60, 60, 60));

        // Color Baby Blue con transparencia
        labelNombre.setBackground(new Color(202, 228, 241, 80));
        labelNombre.setOpaque(true);
        labelNombre.setBorder(BorderFactory.createEmptyBorder(2, 12, 2, 12));

        boton.add(labelNombre, BorderLayout.NORTH);

        // imagen mascota centro
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
     * MODIFICADO: Ahora el método recibe la Especie además del nombre y la ruta,
     * permitiendo instanciar el objeto Animal con todos sus requisitos del constructor.
     */
    private void avanzarAInterfaz(String nombre, Especie especie, String ruta, CardLayout cl, JPanel cont) {
        // Crear mascota nueva
        Animal mascotaNueva = new Animal(nombre, especie, Genero.Macho);

        //  CRUCIAL: Guardar la partida inmediatamente al crear la mascota
        mascotaNueva.guardarPartidaCompleta();

        // Enviar el objeto a la interfaz
        Interfaz miInterfaz = new Interfaz(cl, cont, mascotaNueva, ruta,menuRaiz);
        cont.add(miInterfaz, "INTERFAZ_PRINCIPAL");
        cl.show(cont, "INTERFAZ_PRINCIPAL");
        cont.revalidate();
        cont.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}