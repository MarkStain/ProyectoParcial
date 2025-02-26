/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package automatapila;

import automatapila.model.Simbolo;

import java.util.LinkedList;

/**
 * @author Carlos
 */
public class AutomataPila {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LinkedList<Simbolo> simbolos = new LinkedList<>();
        Simbolo s = new Simbolo();
        s.token = "false";
        s.lexema = "5";
        simbolos.add(s);
        s = new Simbolo();
        s.token = "|";
        s.lexema = "+";
        simbolos.add(s);
        s = new Simbolo();
        s.token = "true";
        s.lexema = "var";
        simbolos.add(s);
        s = new Simbolo();
        s.token = "$";
        s.lexema = "$";
        simbolos.add(s);

        System.out.println("La entrada es (lexemas):");
        for (Simbolo e : simbolos) {
            System.out.print(e.lexema);
        }

        System.out.println("");
        System.out.println("La entrada es (token):");
        for (Simbolo e : simbolos) {
            System.out.print(e.token);
        }
        System.out.println("");

        Automata a = new Automata();
        try {
            System.out.println("La cadena es aceptada: " + a.parsear(simbolos));
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("cadena no aceptada");
        }

        System.out.println(a.pila);

    }

}
