package ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import animales.Animal;
import animales.Especie;

public class Menu extends JFrame {
    private JPanel contenedor;
    private CardLayout cards;
    /**
     * Inicializa el marco principal, dimensiones y añade el panel de inicio.
     */
    public Menu() {
        setTitle("League of pets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 580);
        cards = new CardLayout();
        contenedor = new JPanel(cards);
        contenedor.add(crearMenuInicio(), "MENU_INICIO");
        this.add(contenedor);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Crea el panel de bienvenida con la imagen de portada interactiva.
     * * @return JPanel con la portada y su listener de clic.
     */
    private JPanel crearMenuInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        java.net.URL urlPortada = getClass().getResource("league_of_pets.jpg");
        ImageIcon iconoPortada = new ImageIcon(urlPortada);
        JLabel etiquetaPortada = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(iconoPortada.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        etiquetaPortada.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inicializarFlujoJuego();
            }
        });
        panel.add(etiquetaPortada, BorderLayout.CENTER);
        return panel;
    }
    /**
     * Evalúa si existe una partida guardada en XML para cargarla
     * o redirige al selector de mascotas.
     */
    private void inicializarFlujoJuego() {
        Animal mascotaActiva = Animal.cargarPartidaXML();

        if (mascotaActiva != null) {
            String rutaImagen = "bran_perro.png";
            if (mascotaActiva.getEspecie() == Especie.GATO) {
                rutaImagen = "shasha_gato.png";
            } else if (mascotaActiva.getEspecie() == Especie.COCODRILO) {
                rutaImagen = "cocodrilo.png";
            }
            Interfaz miInterfaz = new Interfaz(cards, contenedor, mascotaActiva, rutaImagen, this);
            contenedor.add(miInterfaz, "INTERFAZ_PRINCIPAL");
            cards.show(contenedor, "INTERFAZ_PRINCIPAL");
            System.out.println("Partida cargada automaticamente.");
        } else {
            SeleccionMascota pantallaMascota = new SeleccionMascota(cards, contenedor, this);
            contenedor.add(pantallaMascota, "PANTALLA_SELECCION_MASCOTA");
            cards.show(contenedor, "PANTALLA_SELECCION_MASCOTA");
            System.out.println("No hay partida guardada. Mostrando selector.");
        }
        contenedor.revalidate();
        contenedor.repaint();
    }
    /**
     * Genera y devuelve una nueva instancia del panel de inicio.
     * * @return JPanel del menú inicial.
     */
    public JPanel getMenuInicioPanel() {
        return crearMenuInicio();
    }
}