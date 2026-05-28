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

    // Constructor para mascota nueva
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

    // Constructor completo para cargar desde XML (sin limpieza)
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

    // ========== PERSISTENCIA ==========

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

    private static String obtenerTextoEtiqueta(Document doc, String etiqueta) {
        NodeList lista = doc.getElementsByTagName(etiqueta);
        if (lista.getLength() > 0) return lista.item(0).getTextContent();
        return "";
    }

    private void addElement(Document doc, Element root, String tag, String value) {
        Element elem = doc.createElement(tag);
        elem.appendChild(doc.createTextNode(value));
        root.appendChild(elem);
    }

    // ========== COMPORTAMIENTO ==========

    public void ganarMonedas(int cantidad) {
        this.monedas += cantidad;
        System.out.println("Has ganado " + cantidad + " monedas. Total: " + this.monedas);
        guardarPartidaCompleta();
    }

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

    public void comer() {
        hambre = Math.max(0, hambre - 20);
        felicidad = Math.min(100, felicidad + 10);
        ganarExperiencia(15);
        limitarValores();
    }

    public void pasear() {
        energia = Math.max(0, energia - 10);
        felicidad = Math.min(100, felicidad + 10);
        ganarExperiencia(25);
        limitarValores();
    }

    public void jugar() {
        energia = Math.max(0, energia - 15);
        felicidad = Math.min(100, felicidad + 15);
        ganarExperiencia(30);
        limitarValores();
    }

    public void bañar() {
        felicidad = Math.min(100, felicidad + 5);
        ganarExperiencia(10);
        limitarValores();
    }

    protected void limitarValores() {
        hambre = Math.max(0, Math.min(100, hambre));
        felicidad = Math.max(0, Math.min(100, felicidad));
        energia = Math.max(0, Math.min(100, energia));
    }

    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad;
        if (this.experiencia >= 100) {
            this.nivel++;
            this.experiencia = 0;
            System.out.println("Felicidades, " + nombre + " ha subido al nivel " + nivel);
        }
    }

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
