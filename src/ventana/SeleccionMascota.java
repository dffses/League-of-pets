package ventana;

import animales.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SeleccionMascota extends JPanel {
    private Image imagenFondo;
    private Menu menuRaiz;
    /**
     * Construye la interfaz de selección con GridBagLayout y los botones de los animales.
     * * @param cardLayout Administrador de capas.
     * @param contenedorPrincipal Contenedor de las pantallas.
     * @param menuRaiz Instancia del marco principal.
     */
    public SeleccionMascota(CardLayout cardLayout, JPanel contenedorPrincipal, Menu menuRaiz) {
        this.setLayout(new GridBagLayout());
        this.menuRaiz = menuRaiz;
        URL urlFondo = getClass().getResource("fondo_primero.jpg");
        if (urlFondo != null) imagenFondo = new ImageIcon(urlFondo).getImage();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        JLabel titulo = new JLabel("Selecciona a tu Compañero!", SwingConstants.CENTER);
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

        JButton btnPerro = crearBotonTransparente("Bran", "bran_perro.png");
        JButton btnGato = crearBotonTransparente("Shasha", "shasha_gato.png");
        JButton btnCocodrilo = crearBotonTransparente("Steve", "cocodrilo.png");

        btnPerro.addActionListener(e -> avanzarAInterfaz("Bran", Especie.PERRO, "imagenes.bran_perro.png", cardLayout, contenedorPrincipal));
        btnGato.addActionListener(e -> avanzarAInterfaz("Shasha", Especie.GATO, "imagenes.shasha_gato.png", cardLayout, contenedorPrincipal));
        btnCocodrilo.addActionListener(e -> avanzarAInterfaz("Steve", Especie.COCODRILO, "imagenes.cocodrilo.png", cardLayout, contenedorPrincipal));

        panelMascotas.add(btnPerro);
        panelMascotas.add(btnGato);
        panelMascotas.add(btnCocodrilo);
        this.add(panelMascotas, gbc);
    }
    /**
     * Crea un botón estilizado y transparente con la foto y nombre del animal.
     * * @param nombre Nombre por defecto.
     * @param rutaImagen Archivo de imagen.
     * @return JButton configurado.
     */
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
     * Instancia la subclase de Animal correspondiente, guarda en XML y cambia a la interfaz de juego.
     */
    private void avanzarAInterfaz(String nombre, Especie especie, String rutaImagen, CardLayout cl, JPanel contenedor) {
        Animal mascotaNueva = null;
        switch (especie) {
            case PERRO: mascotaNueva = new Perro(nombre, especie, Genero.Macho); break;
            case GATO: mascotaNueva = new Gato(nombre, especie, Genero.Hembra); break;
            case COCODRILO: mascotaNueva = new Cocodrilo(nombre, especie, Genero.Macho); break;
        }
        if (mascotaNueva != null) {
            mascotaNueva.guardarPartidaCompleta();  // Guarda inmediatamente
            Interfaz miInterfaz = new Interfaz(cl, contenedor, mascotaNueva, rutaImagen, menuRaiz);
            contenedor.add(miInterfaz, "INTERFAZ_PRINCIPAL");
            cl.show(contenedor, "INTERFAZ_PRINCIPAL");
            contenedor.revalidate();
            contenedor.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}