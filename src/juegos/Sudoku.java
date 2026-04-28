
package juegos;

import java.util.*;

public class Sudoku {
    private int [][] tablero;
    private int [][] solucion;
    private Random aleatorio = new Random();
    private Scanner sc = new Scanner(System.in);

    public Sudoku(){
        tablero = new int[9][9];

        generarTableroCompleto();
        solucion = copiar(tablero);

        int huecos = elegirDificultad();
        eliminarNumeros(huecos);
    }


    private int elegirDificultad() {
        System.out.println("Elige dificultad:");
        System.out.println("1. Facil");
        System.out.println("2. Medio");
        System.out.println("3. Dificil");

        int opcion = sc.nextInt();

        switch (opcion) {
            case 1: return 30;
            case 2: return 40;
            case 3: return 50;
            default:
                System.out.println("Opcion invalida");
                return 40;
        }
    }

    private int[][] copiar(int[][] t){
        int[][] copia = new int[9][9];
        for(int i=0;i<9;i++)
            copia[i] = t[i].clone();
        return copia;
    }

    private boolean generarTableroCompleto() {
        return resolver(0, 0);
    }

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

    private boolean esValido(int fila, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (tablero[fila][i] == num || tablero[i][col] == num)
                return false;
        }

        int inicioFila = (fila / 3) * 3;
        int inicioCol = (col / 3) * 3;

        for (int i = inicioFila; i < inicioFila + 3; i++) {
            for (int j = inicioCol; j < inicioCol + 3; j++) {
                if (tablero[i][j] == num)
                    return false;
            }
        }
        return true;
    }

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

    public void imprimir() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) System.out.println("-------------------------");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print((tablero[i][j] == 0 ? "." : tablero[i][j]) + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------------------");
    }

    public void jugar() {
        while (true) {
            imprimir();
            System.out.println("Introduce: fila columna numero (1-9) o 0 para salir");

            int fila = sc.nextInt();
            if (fila == 0) break;

            int col = sc.nextInt();
            int num = sc.nextInt();

            fila--; col--;

            if (solucion[fila][col] == num) {
                tablero[fila][col] = num;
            } else {
                System.out.println("❌ Incorrecto");
            }

            if (completo()) {
                imprimir();
                System.out.println("¡Ganaste!");
                break;
            }
        }
    }

    private boolean completo() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (tablero[i][j] == 0)
                    return false;
        return true;
    }
}