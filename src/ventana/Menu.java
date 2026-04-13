package ventana;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Menu() {
        this.setSize(600, 800);
        this.setVisible(true);
        setTitle("League of Pets :)");
        setLocationRelativeTo(null);// ventana en el centro
        setResizable(false);
        iniciar();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void iniciar() {
        JPanel panel = new JPanel(); //crear panel
        this.getContentPane().add(panel);//Agregar panel a la ventana

        JLabel etiqueta = new JLabel();
        etiqueta.setText("League of Pets :)");
        etiqueta.setForeground(Color.pink);
        etiqueta.setBackground(Color.black);
        panel.add(etiqueta);
    }
}
