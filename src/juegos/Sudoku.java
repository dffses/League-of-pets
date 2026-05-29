package juegos;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
// Importamos la clase Animal (asegúrate de que esté en la ruta correcta)
import animales.Animal;
import ventana.Interfaz;

public class Sudoku extends JPanel {
    private int[][] tablero;
    private int[][] solucion;
    private java.util.Random aleatorio = new java.util.Random();

    private int fallos = 0;
    private final int MAX_FALLOS = 3;
    private JLabel lblFallos;
    private JTextField[][] casillas = new JTextField[9][9];

    // NUEVA VARIABLE: Guardamos la referencia de tu mascota
    private Animal mascotaActual;

    /**
     * Constructor principal que inicializa las matrices, genera el tablero lógico, oculta casillas y construye la interfaz visual dividida en regiones 3x3.
     * @param cl El CardLayout encargado del flujo de navegación entre pantallas.
     * @param cont El contenedor principal que aloja los distintos paneles.
     * @param mascota El objeto Animal de la sesión actual que recibirá recompensas o desgaste.
     */
    public Sudoku(CardLayout cl, JPanel cont, Animal mascota) {
        // Asignamos la mascota
        this.mascotaActual = mascota;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        // Inicializamos las matrices vacías y calculamos una combinación matemática válida
        tablero = new int[9][9];
        generarTableroCompleto();
        solucion = copiar(tablero);
        eliminarNumeros(40); // 40 huecos libres

        // Cabecera informativa y botón de huida rápida
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(240, 240, 240));
        JButton btnVolver = new JButton("⬅ Salir del Sudoku");
        //cambio
        btnVolver.addActionListener(e -> {

            mascotaActual.jugar();
            mascotaActual.guardarPartidaCompleta();

            cl.show(cont, "PANTALLA_CUADRICULA");
        });

        lblFallos = new JLabel("Fallos: 0 / " + MAX_FALLOS + "    ");
        lblFallos.setFont(new Font("Arial", Font.BOLD, 14));
        lblFallos.setForeground(Color.RED);

        panelNorte.add(btnVolver, BorderLayout.WEST);
        panelNorte.add(lblFallos, BorderLayout.EAST);
        this.add(panelNorte, BorderLayout.NORTH);

        // Tablero general de juego compuesto por un Grid primario de 3x3 bloques mayores
        JPanel panelPrincipalTablero = new JPanel(new GridLayout(3, 3));
        panelPrincipalTablero.setBackground(Color.BLACK);
        panelPrincipalTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Borde exterior grueso

        // Creamos los 9 bloques grandes de 3x3
        JPanel[][] bloquesGrandes = new JPanel[3][3];
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                bloquesGrandes[m][n] = new JPanel(new GridLayout(3, 3, 1, 1));
                bloquesGrandes[m][n].setBackground(Color.GRAY);
                // Ponemos un borde negro grueso alrededor de cada región de 3x3
                Border bordeRegion = BorderFactory.createLineBorder(Color.BLACK, 2);
                bloquesGrandes[m][n].setBorder(bordeRegion);
                panelPrincipalTablero.add(bloquesGrandes[m][n]);
            }
        }

        // Lleno las casillas situándolas en su bloque correspondiente
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                casillas[f][c] = new JTextField();
                casillas[f][c].setHorizontalAlignment(JTextField.CENTER);
                casillas[f][c].setFont(new Font("Arial", Font.BOLD, 20));
                casillas[f][c].setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

                if (tablero[f][c] != 0) {
                    // Si el número viene predefinido de fábrica, lo bloqueamos para lectura
                    casillas[f][c].setText(String.valueOf(tablero[f][c]));
                    casillas[f][c].setEditable(false);
                    casillas[f][c].setBackground(new Color(235, 235, 235));
                    casillas[f][c].setForeground(Color.BLACK);
                } else {
                    final int filaActual = f;
                    final int colActual = c;
                    // al pulsar ENTER se comprueba el valor
                    casillas[f][c].addActionListener(e -> verificarCasilla(filaActual, colActual, cl, cont));
                }

                // calculo de a qué bloque de 3x3 pertenece matemáticamente esta celda
                int bloqueFila = f / 3;
                int bloqueCol = c / 3;
                bloquesGrandes[bloqueFila][bloqueCol].add(casillas[f][c]);
            }
        }
        this.add(panelPrincipalTablero, BorderLayout.CENTER);
    }

    /**
     * Comprueba si el valor numérico ingresado por el usuario coincide con la solución del tablero, gestionando la victoria o el error.
     * @param f Índice de la fila de la casilla modificada.
     * @param c Índice de la columna de la casilla modificada.
     * @param cl El CardLayout para gestionar el cambio de pantalla.
     * @param cont El contenedor principal de los paneles de la aplicación.
     */
    private void verificarCasilla(int f, int c, CardLayout cl, JPanel cont) {
        String texto = casillas[f][c].getText().trim();
        if (texto.isEmpty()) return;

        try {
            int valorIntroducido = Integer.parseInt(texto);

            // Verificación con la matriz solución
            if (valorIntroducido == solucion[f][c]) {
                casillas[f][c].setBackground(new Color(144, 238, 144)); // Verde claro
                casillas[f][c].setForeground(new Color(0, 100, 0)); // Texto verde oscuro
                casillas[f][c].setEditable(false); // Ya no se puede modificar
                tablero[f][c] = valorIntroducido;

                // MODIFICADO: Bloque de control al completar victoriosamente el tablero
                if (completo()) {
                    int recompensaSudoku = 200; // Monedas por resolver el Sudoku entero
                    mascotaActual.ganarMonedas(recompensaSudoku);
                    //cambio2
                    mascotaActual.jugar();
                    mascotaActual.guardarPartidaCompleta();
                    actualizarInterfazPrincipal();

                    JOptionPane.showMessageDialog(this,
                            "¡Felicidades! Has completado el Sudoku.\n¡Has ganado " + recompensaSudoku + " monedas para tu mascota! ",
                            "¡Ganaste!", JOptionPane.INFORMATION_MESSAGE);

                    cl.show(cont, "PANTALLA_CUADRICULA");
                }
            } else {
                //error
                casillas[f][c].setBackground(new Color(255, 182, 193)); // Rojo claro / Rosa
                casillas[f][c].setForeground(Color.RED);
                registrarFallo(cl, cont);
            }
        } catch (NumberFormatException ex) {
            casillas[f][c].setBackground(new Color(255, 182, 193));
            registrarFallo(cl, cont);
        }
    }
    /**
     * Incrementa el contador de errores del jugador y fuerza el Game Over inmediato si se alcanza el número máximo permitido.
     * @param cl El CardLayout para redirigir la navegación tras perder.
     * @param cont El contenedor que alberga los paneles de la interfaz gráfica.
     */
    private void registrarFallo(CardLayout cl, JPanel cont) {
        fallos++;
        lblFallos.setText("Fallos: " + fallos + " / " + MAX_FALLOS + "    ");
        if (fallos >= MAX_FALLOS) {
            //cambio3
            mascotaActual.jugar();
            mascotaActual.guardarPartidaCompleta();
            JOptionPane.showMessageDialog(this, "Has cometido " + MAX_FALLOS + " fallos. ¡Has perdido!", "Game Over", JOptionPane.ERROR_MESSAGE);
            cl.show(cont, "PANTALLA_CUADRICULA");
        }
    }

    /**
     * Realiza una copia profunda de una matriz bidimensional de enteros.
     * @param t La matriz bidimensional de origen.
     * @return Una réplica idéntica e independiente de la matriz proporcionada.
     */
    private int[][] copiar(int[][] t) {
        int[][] copia = new int[9][9];
        for (int i = 0; i < 9; i++) copia[i] = t[i].clone();
        return copia;
    }
    /**
     * Dispara el algoritmo recursivo de backtracking para rellenar de forma válida todas las celdas del tablero.
     * @return true si se logra completar un tablero legal, false en caso contrario.
     */
    private boolean generarTableroCompleto() { return resolver(0, 0); }
    /**
     * Algoritmo de backtracking que busca y asigna recursivamente números aleatorios válidos celda por celda.
     * @param fila Fila por la que progresa la resolución.
     * @param col Columna por la que progresa la resolución.
     * @return true si la ruta de números actual conduce a una solución válida completa, false si entra en conflicto.
     */
    private boolean resolver(int fila, int col) {
        if (fila == 9) return true;
        int siguienteFila = (col == 8) ? fila + 1 : fila;
        int siguienteCol = (col + 1) % 9;
        int[] numeros = mezclarNumeros();
        for (int num : numeros) {
            if (esValido(fila, col, num)) {
                tablero[fila][col] = num;
                if (resolver(siguienteFila, siguienteCol)) return true;
                tablero[fila][col] = 0;
            }
        }
        return false;
    }
    /**
     * Evalúa si un número puede colocarse legalmente en una casilla respetando las reglas de fila, columna y sub-bloque 3x3.
     * @param fila Fila donde se intenta colocar el número.
     * @param col Columna donde se intenta colocar el número.
     * @param num El número que se desea verificar.
     * @return true si la posición es legal de acuerdo a las reglas del Sudoku, false si está repetido.
     */
    private boolean esValido(int fila, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (tablero[fila][i] == num || tablero[i][col] == num) return false;
        }
        int inicioFila = (fila / 3) * 3;
        int inicioCol = (col / 3) * 3;
        for (int i = inicioFila; i < inicioFila + 3; i++) {
            for (int j = inicioCol; j < inicioCol + 3; j++) {
                if (tablero[i][j] == num) return false;
            }
        }
        return true;
    }
    /**
     * Genera un array con los números del 1 al 9 ordenados de forma completamente aleatoria.
     * @return Un array de enteros mezclados aleatoriamente.
     */
    private int[] mezclarNumeros() {
        int[] nums = {1,2,3,4,5,6,7,8,9};
        for (int i = 0; i < nums.length; i++) {
            int j = aleatorio.nextInt(9);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        return nums;
    }
    /**
     * Vacía de forma aleatoria una cantidad determinada de celdas para crear los huecos del puzle jugable.
     * @param cantidad El número total de casillas que se fijarán a cero.
     */
    private void eliminarNumeros(int cantidad) {
        while (cantidad > 0) {
            int fila = aleatorio.nextInt(9);
            int col = aleatorio.nextInt(9);
            if (tablero[fila][col] != 0) {
                tablero[fila][col] = 0;
                cantidad--;
            }
        }
    }
    /**
     * Verifica si se han rellenado todas las casillas del tablero sin dejar ningún espacio en blanco.
     * @return true si no quedan casillas con valor cero, false en caso contrario.
     */
    private boolean completo() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tablero[i][j] == 0) return false;
            }
        }
        return true;
    }

    /**
     * Rastrea hacia arriba los contenedores padres hasta dar con el panel principal de Interfaz para refrescar su visualizador de monedas.
     */
    private void actualizarInterfazPrincipal() {
        Component parent = this.getParent();
        while (parent != null) {
            if (parent instanceof JPanel) {
                JPanel panel = (JPanel) parent;
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof Interfaz) {
                        ((Interfaz) comp).actualizarMonedasVisuales();
                        return;
                    }
                }
            }
            parent = parent.getParent();
        }
    }

}