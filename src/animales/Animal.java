package animales;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    protected int monedas;

    /**
     * Constructor para inicializar una nueva mascota con estadísticas iniciales por defecto.
     * * @param nombre El nombre que se le asignará al animal.
     * @param especie La especie biológica de la mascota.
     * @param genero El género del animal.
     */
    public Animal(String nombre, Especie especie, Genero genero) {
        this.nombre = nombre;
        this.especie = especie;
        this.genero = genero;
        this.hambre = 50;
        this.felicidad = 50;
        this.energia = 50;
        this.nivel = 1;
        this.experiencia = 0;
        this.monedas = 0;
    }

    /**
     * Constructor completo utilizado para restaurar el estado de una mascota desde un archivo de guardado.
     * * @param nombre El nombre del animal.
     * @param especie La especie del animal.
     * @param genero El género del animal.
     * @param hambre Nivel de hambre.
     * @param felicidad Nivel de felicidad.
     * @param energia Nivel de energía.
     * @param nivel Nivel actual de la mascota.
     * @param experiencia Puntos de experiencia acumulados.
     * @param monedas Cantidad de monedas que posee.
     */
    public Animal(String nombre, Especie especie, Genero genero,
                  int hambre, int felicidad, int energia,
                  int nivel, int experiencia, int monedas) {
        this.nombre = nombre;
        this.especie = especie;
        this.genero = genero;
        this.hambre = hambre;
        this.felicidad = felicidad;
        this.energia = energia;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.monedas = monedas;
    }

    /**
     * Guarda el estado actual de los atributos del animal en un archivo XML local.
     */
    public void guardarPartidaCompleta() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("partida");
            doc.appendChild(root);

            addElement(doc, root, "nombre", this.nombre);
            addElement(doc, root, "especie", this.especie.name());
            addElement(doc, root, "genero", this.genero.name());
            addElement(doc, root, "hambre", String.valueOf(this.hambre));
            addElement(doc, root, "felicidad", String.valueOf(this.felicidad));
            addElement(doc, root, "energia", String.valueOf(this.energia));
            addElement(doc, root, "nivel", String.valueOf(this.nivel));
            addElement(doc, root, "experiencia", String.valueOf(this.experiencia));
            addElement(doc, root, "monedas", String.valueOf(this.monedas));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("partida.xml"));
            transformer.transform(source, result);
            System.out.println("Partida guardada correctamente para: " + nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga y reconstruye la instancia del animal específico a partir del archivo XML guardado.
     * * @return El objeto Animal cargado, o null si el archivo no existe o falla la lectura.
     */
    public static Animal cargarPartidaXML() {
        File archivo = new File("partida.xml");
        if (!archivo.exists()) {
            return null;
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            String nombre = obtenerTextoEtiqueta(doc, "nombre");
            Especie especie = Especie.valueOf(obtenerTextoEtiqueta(doc, "especie"));
            Genero genero = Genero.valueOf(obtenerTextoEtiqueta(doc, "genero"));
            int hambre = Integer.parseInt(obtenerTextoEtiqueta(doc, "hambre"));
            int felicidad = Integer.parseInt(obtenerTextoEtiqueta(doc, "felicidad"));
            int energia = Integer.parseInt(obtenerTextoEtiqueta(doc, "energia"));
            int nivel = Integer.parseInt(obtenerTextoEtiqueta(doc, "nivel"));
            int experiencia = Integer.parseInt(obtenerTextoEtiqueta(doc, "experiencia"));
            int monedas = Integer.parseInt(obtenerTextoEtiqueta(doc, "monedas"));

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
                    animalCargado = new Animal(nombre, especie, genero, hambre, felicidad, energia, nivel, experiencia, monedas);
                    break;
            }
            System.out.println("Mascota " + especie + " recuperada del XML correctamente.");
            return animalCargado;
        } catch (Exception e) {
            System.out.println("Error al cargar partida: " + e.getMessage());
            return null;
        }
    }
    /**
     * Extrae el contenido de texto de una etiqueta específica dentro del documento XML.
     * * @param doc El documento XML sobre el que se realiza la búsqueda.
     * @param etiqueta El nombre del nodo o etiqueta XML a buscar.
     * @return El contenido de texto de la etiqueta, o una cadena vacía si no se encuentra.
     */
    private static String obtenerTextoEtiqueta(Document doc, String etiqueta) {
        NodeList lista = doc.getElementsByTagName(etiqueta);
        if (lista.getLength() > 0) return lista.item(0).getTextContent();
        return "";
    }
    /**
     * Crea un nuevo elemento XML con texto y lo añade al nodo raíz proporcionado.
     * * @param doc El documento XML de referencia.
     * @param root El elemento raíz donde se insertará el nuevo nodo.
     * @param tag El nombre de la etiqueta del nuevo elemento.
     * @param value El contenido de texto para el nuevo elemento.
     */
    private void addElement(Document doc, Element root, String tag, String value) {
        Element elem = doc.createElement(tag);
        elem.appendChild(doc.createTextNode(value));
        root.appendChild(elem);
    }

    // ========== COMPORTAMIENTO ==========
    /**
     * Incrementa los fondos del animal y actualiza de inmediato el archivo de guardado.
     * * @param cantidad Número de monedas a añadir.
     */
    public void ganarMonedas(int cantidad) {
        this.monedas += cantidad;
        System.out.println("Has ganado " + cantidad + " monedas. Total: " + this.monedas);
        guardarPartidaCompleta();
    }
    /**
     * Gestiona el proceso de compra de alimento restando el coste e iniciando la acción de comer si hay saldo suficiente.
     * * @param precio El coste monetario de la comida.
     * @return true si la transacción se realiza con éxito, false en caso contrario.
     */
    public boolean comprarComida(int precio) {
        if (this.monedas >= precio) {
            this.monedas -= precio;
            System.out.println("Compra realizada. Saldo: " + this.monedas);
            this.comer();
            guardarPartidaCompleta();
            return true;
        } else {
            System.out.println("No tienes suficientes monedas.");
            return false;
        }
    }
    /**
     * Reduce el nivel de hambre del animal, aumenta su felicidad y le otorga puntos de experiencia.
     */
    public void comer() {
        hambre = Math.max(0, hambre - 20);
        felicidad = Math.min(100, felicidad + 10);
        ganarExperiencia(15);
        limitarValores();
    }
    /**
     * Realiza un paseo que consume energía del animal pero aumenta su felicidad y su experiencia.
     */
    public void pasear() {
        energia = Math.max(0, energia - 10);
        felicidad = Math.min(100, felicidad + 10);
        ganarExperiencia(25);
        limitarValores();
    }
    /**
     * Ejecuta una sesión de juego disminuyendo la energía e incrementando la felicidad y experiencia del animal.
     */
    public void jugar() {
        energia = Math.max(0, energia - 15);
        felicidad = Math.min(100, felicidad + 15);
        ganarExperiencia(30);
        limitarValores();
    }
    /**
     * Incrementa ligeramente la felicidad y otorga una pequeña cantidad de experiencia al bañar a la mascota.
     */
    public void bañar() {
        felicidad = Math.min(100, felicidad + 5);
        ganarExperiencia(10);
        limitarValores();
    }
    /**
     * Asegura que las estadísticas de hambre, felicidad y energía se mantengan estrictamente dentro del rango de [0, 100].
     */
    protected void limitarValores() {
        hambre = Math.max(0, Math.min(100, hambre));
        felicidad = Math.max(0, Math.min(100, felicidad));
        energia = Math.max(0, Math.min(100, energia));
    }
    /**
     * Añade experiencia al animal y maneja la lógica de subida de nivel cuando se alcanzan o superan los 100 puntos.
     * * @param cantidad Cantidad de puntos de experiencia a sumar.
     */
    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad;
        if (this.experiencia >= 100) {
            this.nivel++;
            this.experiencia = 0;
            System.out.println("Felicidades, " + nombre + " ha subido al nivel " + nivel);
        }
    }
    /**
     * Simula el paso del tiempo aplicando un desgaste natural continuo sobre el hambre, la energía y la felicidad.
     */
    public void actualizarEstado() {
        hambre += 2;
        energia -= 2;
        felicidad -= 1;
        limitarValores();
    }

    // GETTERS Y SETTERS
    public String getNombre() { return nombre; }
    public Especie getEspecie() { return especie; }
    public Genero getGenero() { return genero; }
    public int getHambre() { return hambre; }
    public int getFelicidad() { return felicidad; }
    public int getEnergia() { return energia; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public int getMonedas() { return monedas; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecie(Especie especie) { this.especie = especie; }
    public void setGenero(Genero genero) { this.genero = genero; }
    public void setMonedas(int monedas) { this.monedas = monedas; guardarPartidaCompleta(); }

    @Override
    public String toString() {
        return "Animal{nombre=" + nombre + ", especie=" + especie + ", monedas=" + monedas + "}";
    }
}
