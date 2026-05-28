package animales;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import animales.Especie;
import animales.Genero;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Animal {
    protected String nombre;
    protected Especie especie;
    protected Genero genero;
    protected int hambre;
    protected int felicidad;
    protected int energia;
    protected int nivel;
    protected int experiencia;

    // NUEVO ATRIBUTO: Monedas de la mascota/personaje
    protected int monedas;

    // Constructor corregido para inicializar los valores base correctamente
    public Animal(String nombre, Especie especie, Genero genero) {
        this.nombre = nombre;
        this.especie = especie;
        this.genero = genero;
        this.hambre = 50;
        this.felicidad = 50;
        this.energia = 50;
        this.nivel = 1;
        this.experiencia = 0;

        // Intentamos cargar las monedas guardadas en el XML al crear el animal.
        // Si el archivo no existe, empezará con 0 monedas.
        this.monedas = cargarMonedasDesdeXML();
    }



    /**
     * MÉTODOS DE ECONOMÍA (MONEDAS Y TIENDA)
     */

    /**
     * Añade monedas al monedero (por ejemplo, al pasar 30s jugando).
     * Automáticamente guarda el nuevo saldo en el XML.
     */
    public void ganarMonedas(int cantidad) {
        this.monedas += cantidad;
        System.out.println("¡Has ganado " + cantidad + " monedas! Saldo actual: " + this.monedas);
        guardarPartidaCompleta();
        guardarMonedasEnXML(); // Guarda los cambios inmediatamente
    }

    /**
     * Resta monedas al comprar comida en la tienda.
     * Devuelve 'true' si la compra fue exitosa, o 'false' si no tiene dinero suficiente.
     */
    public boolean comprarComida(int precioComida) {
        if (this.monedas >= precioComida) {
            this.monedas -= precioComida;
            System.out.println("Compra realizada. Gastaste " + precioComida + " monedas. Saldo restante: " + this.monedas);

            // Efecto secundario de comer
            this.comer();

            // Guardamos el nuevo saldo en el XML
            guardarMonedasEnXML();
            return true;
        } else {
            System.out.println("¡No tienes suficientes monedas! Necesitas " + precioComida + " y tienes " + this.monedas);
            return false;
        }
    }

    /**
     * MÉTODOS DE PERSISTENCIA EN XML (DOM)
     */

    /**
     * Crea o sobrescribe un archivo 'partida.xml' guardando el valor de las monedas.
     */
    public void guardarMonedasEnXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Creamos la etiqueta raíz <partida>
            Element rootElement = doc.createElement("partida");
            doc.appendChild(rootElement);

            // Creamos la etiqueta <monedas> con el valor actual
            Element monedasElement = doc.createElement("monedas");
            monedasElement.appendChild(doc.createTextNode(String.valueOf(this.monedas)));
            rootElement.appendChild(monedasElement);

            // Estructura necesaria para transformar el documento en un archivo físico .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            // Nombre del archivo donde se guardará
            StreamResult result = new StreamResult(new File("partida.xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Error al guardar las monedas en el XML: " + e.getMessage());
        }
    }

    /**
     * Lee el archivo 'partida.xml' para recuperar las monedas guardadas.
     * Si el archivo no existe, devuelve 0.
     */
    private int cargarMonedasDesdeXML() {
        File archivo = new File("partida.xml");
        // Si el archivo no existe todavía, devolvemos 0 monedas por defecto
        if (!archivo.exists()) {
            return 0;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            // Buscamos la etiqueta <monedas>
            NodeList lista = doc.getElementsByTagName("monedas");
            if (lista.getLength() > 0) {
                String monedasTexto = lista.item(0).getTextContent();
                return Integer.parseInt(monedasTexto); // Convertimos el texto a número entero
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el XML (se usarán 0 monedas): " + e.getMessage());
        }
        return 0;
    }

    /**
     * MÉTODOS DE COMPORTAMIENTO ORIGINALES
     */
    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad;
        if (this.experiencia >= 100) {
            this.nivel++;
            this.experiencia = 0;
            System.out.println("¡Genial! " + nombre + " ha subido al nivel " + nivel);
        }
    }

    public void comer() {

        ganarExperiencia(15);
        limitarValores();
    }

    public void pasear() {
        ganarExperiencia(25);
        limitarValores();
    }

    public void jugar() {
        ganarExperiencia(30);
        limitarValores();
    }

    protected void limitarValores() {
        hambre = Math.max(0, Math.min(100, hambre));
        felicidad = Math.max(0, Math.min(100, felicidad));
        energia = Math.max(0, Math.min(100, energia));
    }

    public void actualizarEstado() {
        hambre += 2;
        energia -= 2;
        felicidad-=1;
        limitarValores();
    }

    // GETTERS Y SETTERS
    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
        guardarMonedasEnXML(); // Si cambias las monedas a mano, también se guarda
    }

    public String getNombre() { return nombre; }
    public Especie getEspecie() { return especie; }
    public Genero getGenero() { return genero; }
    public int getHambre() { return hambre; }
    public int getFelicidad() { return felicidad; }
    public int getEnergia() { return energia; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecie(Especie especie) { this.especie = especie; }
    public void setGenero(Genero genero) { this.genero = genero; }

    @Override
    public String toString() {
        return "Animal{" +
                "nombre='" + nombre + '\'' +
                ", especie=" + especie +
                ", genero=" + genero +
                ", monedas=" + monedas +
                ", hambre=" + hambre +
                ", felicidad=" + felicidad +
                ", energia=" + energia +
                ", nivel=" + nivel +
                '}';
    }

    private static String obtenerTextoEtiqueta(Document doc, String etiqueta) {
        NodeList lista = doc.getElementsByTagName(etiqueta);
        if (lista.getLength() > 0) {
            return lista.item(0).getTextContent();
        }
        return "";
    }


    /**
     * MÉTODO ESTÁTICO DE CARGA POLIMÓRFICA
     * Lee el archivo XML y reconstruye la subclase exacta del animal guardado.
     */
    public static Animal cargarPartidaXML() {
        File archivo = new File("partida.xml");
        if (!archivo.exists()) {
            return null; // No hay partida previa
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            // 1. Extraer los datos de configuración base
            String nombre = obtenerTextoEtiqueta(doc, "nombre");
            Especie especie = Especie.valueOf(obtenerTextoEtiqueta(doc, "especie"));
            Genero genero = Genero.valueOf(obtenerTextoEtiqueta(doc, "genero"));

            // 2. Extraer las estadísticas vitales
            int hambre = Integer.parseInt(obtenerTextoEtiqueta(doc, "hambre"));
            int felicidad = Integer.parseInt(obtenerTextoEtiqueta(doc, "felicidad"));
            int limpieza = Integer.parseInt(obtenerTextoEtiqueta(doc, "limpieza"));
            int energia = Integer.parseInt(obtenerTextoEtiqueta(doc, "energia"));
            int nivel = Integer.parseInt(obtenerTextoEtiqueta(doc, "nivel"));
            int experiencia = Integer.parseInt(obtenerTextoEtiqueta(doc, "experiencia"));
            int monedas = Integer.parseInt(obtenerTextoEtiqueta(doc, "monedas"));

            // 3. Crear la instancia de la subclase correspondiente según la Especie
            Animal animalCargado;
            switch (especie) {
                case PERRO:
                    animalCargado = new Perro(nombre, especie, genero, hambre, felicidad, energia, nivel, experiencia, monedas);
                    break;
                case GATO:
                    animalCargado = new Gato(nombre, especie, genero, hambre, felicidad, energia, nivel, experiencia, monedas);
                    break;
                case COCODRILO:
                    animalCargado = new Cocodrilo(nombre, especie, genero, hambre, felicidad, energia, nivel, experiencia, monedas);
                    break;
                default:
                    // Por si acaso tienes más especies en tu enum
                    animalCargado = new Animal(nombre, especie, genero);
                    break;
            }

            System.out.println("¡Mascota " + especie + " recuperada del XML con éxito!");
            return animalCargado;

        } catch (Exception e) {
            System.out.println("Error al cargar la partida (se ignorará el archivo): " + e.getMessage());
            return null;
        }
    }


    /**
     * Guarda TODOS los datos de la mascota en un XML completo.
     * Este método reemplaza a guardarMonedasEnXML() para guardar todo el estado.
     */
    public void guardarPartidaCompleta() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Etiqueta raíz
            Element rootElement = doc.createElement("partida");
            doc.appendChild(rootElement);

            // Guardar todos los atributos
            guardarEtiqueta(doc, rootElement, "nombre", this.nombre);
            guardarEtiqueta(doc, rootElement, "especie", this.especie.name());
            guardarEtiqueta(doc, rootElement, "genero", this.genero.name());
            guardarEtiqueta(doc, rootElement, "hambre", String.valueOf(this.hambre));
            guardarEtiqueta(doc, rootElement, "felicidad", String.valueOf(this.felicidad));
            guardarEtiqueta(doc, rootElement, "energia", String.valueOf(this.energia));
            guardarEtiqueta(doc, rootElement, "nivel", String.valueOf(this.nivel));
            guardarEtiqueta(doc, rootElement, "experiencia", String.valueOf(this.experiencia));
            guardarEtiqueta(doc, rootElement, "monedas", String.valueOf(this.monedas));

            // Guardar archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("partida.xml"));
            transformer.transform(source, result);

            System.out.println("Partida guardada correctamente para: " + nombre);

        } catch (Exception e) {
            System.out.println("Error al guardar la partida: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método auxiliar para crear etiquetas
    private void guardarEtiqueta(Document doc, Element root, String nombre, String valor) {
        Element etiqueta = doc.createElement(nombre);
        etiqueta.appendChild(doc.createTextNode(valor));
        root.appendChild(etiqueta);
    }
}
