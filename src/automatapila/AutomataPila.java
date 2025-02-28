/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package automatapila;

import automatapila.model.Simbolo;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author
 */
public class AutomataPila {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LinkedList<Simbolo> simbolos = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cadena de tokens separados por espacio: ");
        String entrada = scanner.nextLine().trim();
        scanner.close();

        String[] tokens = entrada.split("\\s+");

        for (String token : tokens) {
            Simbolo s = new Simbolo();
            s.token = token;
            simbolos.add(s);
        }

        System.out.println("\nLa entrada es (tokens):");
        for (Simbolo e : simbolos) {
            System.out.print(e.token + " ");
        }
        System.out.println("\n");

        Automata a = new Automata();
        try {
            System.out.println("La cadena es aceptada: " + a.parsear(simbolos));
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Cadena no aceptada");
        }

        System.out.println(a.pila);
    }
}