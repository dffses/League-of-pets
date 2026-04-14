import juegos.saltitos.Saltitos;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Mi Juego Doodle");

        // Aquí llamamos al constructor de tu clase
        Saltitos juego = new Saltitos();

        ventana.add(juego);
        ventana.setSize(400, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}