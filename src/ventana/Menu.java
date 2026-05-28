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
     * Configura el marco principal e inserta la pantalla inicial de bienvenida.
     */
    public Menu() {
        // Configuramos los componentes directamente sobre el JFrame (this)
        setTitle("League of pets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 580); // Tamaño ideal adaptado para las tres mascotas

        cards = new CardLayout();
        contenedor = new JPanel(cards);

        contenedor.add(crearMenuInicio(), "MENU_INICIO");

        this.add(contenedor);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel crearMenuInicio() {
        // Usamos BorderLayout para que la imagen ocupe todo el panel automáticamente
        JPanel panel = new JPanel(new BorderLayout());

        java.net.URL urlPortada = getClass().getResource("league_of_pets.jpg");
        ImageIcon iconoPortada = new ImageIcon(urlPortada);


        Image imagenEscalada = iconoPortada.getImage().getScaledInstance(750, 580, Image.SCALE_SMOOTH);
        JLabel etiquetaPortada = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujamos la imagen para que empiece en (0,0) y se estire
                g.drawImage(iconoPortada.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // 2. Hacemos que la imagen sea interactiva escuchando el clic del ratón
        etiquetaPortada.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Al hacer clic en cualquier parte de la imagen, inicia el juego
                inicializarFlujoJuego();
            }
        });

        // Añadimos la etiqueta al centro del panel
        panel.add(etiquetaPortada, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Comprueba si hay una partida guardada en el archivo XML.
     * Si existe, va directo al juego. Si no, abre la selección de mascota.
     */
    private void inicializarFlujoJuego() {
        // Intentamos cargar la partida guardada polimórfica
        Animal mascotaActiva = Animal.cargarPartidaXML();

        if (mascotaActiva != null) {
            // ¡Hay partida guardada! Identificamos su foto por su especie
            String rutaImagen = "bran_perro.png"; // Ruta por defecto
            if (mascotaActiva.getEspecie() == Especie.GATO) {
                rutaImagen = "shasha_gato.png";
            } else if (mascotaActiva.getEspecie() == Especie.COCODRILO) {
                rutaImagen = "cocodrilo.png";
            }

            // Instanciamos la interfaz pasándole la mascota recuperada del XML
            Interfaz miInterfaz = new Interfaz(cards, contenedor, mascotaActiva, rutaImagen,this);
            contenedor.add(miInterfaz, "INTERFAZ_PRINCIPAL");

            // Saltamos directamente a la acción sin pasar por la selección de mascota
            cards.show(contenedor, "INTERFAZ_PRINCIPAL");
            System.out.println("¡Partida cargada de forma automática con éxito!");
        } else {
            // No hay archivo XML guardado: cargamos el panel de selección de mascota desde cero
            SeleccionMascota pantallaMascota = new SeleccionMascota(cards, contenedor, this);
            contenedor.add(pantallaMascota, "PANTALLA_SELECCION_MASCOTA");

            // Mostramos la pantalla de selección para comprar una mascota nueva
            cards.show(contenedor, "PANTALLA_SELECCION_MASCOTA");
            System.out.println("No se encontró partida guardada. Redirigiendo al selector de mascotas.");
        }

        // Refrescamos el contenedor para evitar fallos visuales
        contenedor.revalidate();
        contenedor.repaint();
    }

    // Método main para ejecutar el proyecto cómodamente desde aquí
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Menu();
        });
    }

    public JPanel getMenuInicioPanel() {
        return crearMenuInicio();
    }
}