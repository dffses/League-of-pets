package ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import animales.Animal;

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

        // ✅ CORRECCIÓN: Guardar partida automáticamente al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                guardarPartidaActual();
            }
        });
    }

    /**
     * ✅ NUEVO MÉTODO: Busca la mascota actual en el contenedor y guarda su estado
     */
    private void guardarPartidaActual() {
        // Recorremos todos los componentes del contenedor principal
        for (Component comp : contenedor.getComponents()) {
            // Buscamos la pantalla principal de la interfaz
            if (comp instanceof Interfaz) {
                Interfaz interfaz = (Interfaz) comp;
                Animal mascota = interfaz.getMascota();
                if (mascota != null) {
                    mascota.guardarPartidaCompleta();
                    System.out.println("✅ Partida guardada automáticamente al cerrar el juego para: " + mascota.getNombre());
                } else {
                    System.out.println("⚠️ No se encontró mascota activa al cerrar el juego.");
                }
                return;
            }
        }
        System.out.println("ℹ️ No se encontró una partida activa para guardar.");
    }

    private JPanel crearMenuInicio() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 80)); // Color de fondo elegante

        JButton boton = new JButton("LEAGUE OF PETS");
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(100, 150, 200));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        // Al pulsar el botón de inicio, se decide el flujo según el XML
        boton.addActionListener(e -> {
            inicializarFlujoJuego();
        });

        panel.add(boton);
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
            String rutaImagen;
            if (mascotaActiva.getEspecie() == animales.Especie.GATO) {
                rutaImagen = "shasha_gato.png";
            } else if (mascotaActiva.getEspecie() == animales.Especie.COCODRILO) {
                rutaImagen = "cocodrilo.png";
            } else {
                rutaImagen = "bran_perro.png"; // Perro por defecto
            }

            // Instanciamos la interfaz pasándole la mascota recuperada del XML
            Interfaz miInterfaz = new Interfaz(cards, contenedor, mascotaActiva, rutaImagen);
            contenedor.add(miInterfaz, "INTERFAZ_PRINCIPAL");

            // Saltamos directamente a la acción sin pasar por la selección de mascota
            cards.show(contenedor, "INTERFAZ_PRINCIPAL");
            System.out.println("✅ ¡Partida cargada de forma automática con éxito! Mascota: " + mascotaActiva.getNombre());
        } else {
            // No hay archivo XML guardado: cargamos el panel de selección de mascota desde cero
            SeleccionMascota pantallaMascota = new SeleccionMascota(cards, contenedor, this);
            contenedor.add(pantallaMascota, "PANTALLA_SELECCION_MASCOTA");

            // Mostramos la pantalla de selección para comprar una mascota nueva
            cards.show(contenedor, "PANTALLA_SELECCION_MASCOTA");
            System.out.println("ℹ️ No se encontró partida guardada. Redirigiendo al selector de mascotas.");
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
}