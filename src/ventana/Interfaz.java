package ventana;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;

public class Interfaz {
    JPanel contenedor;
    CardLayout cartas = new CardLayout();

    public Interfaz() {
        contenedor=new JPanel();
        contenedor.setLayout(cartas);

        contenedor=new JPanel();
        contenedor.setBackground(Color.DARK_GRAY);
        contenedor.setLayout(new GridLayout());

        JButton botonTienda=new JButton("Tienda");
        botonTienda.setBounds(10, 10, 200, 50);

        JButton botonJuegos=new JButton("INICIAR LEAGUE");
        botonJuegos.setBounds(100, 10, 200, 50);
    }
}
