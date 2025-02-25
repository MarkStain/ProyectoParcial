/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatapila;

import automatapila.model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author Carlos
 */
public class Automata {

    List<String> ejecutor = new ArrayList<>();
    Operacion mainOperacion = null;
    Operacion actualOperacion = null;
    Operacion cursorOperacion = null;
    Stack<String> pila = new Stack<>();
    boolean izquierda = true;
    boolean resultado = false;
    Expresion izq;

    public boolean parsear(LinkedList<Simbolo> entrada) throws Exception {
        izquierda = true;
        pila.push("$");
        pila.push("E");
        reconocer(entrada);
        System.out.println(mainOperacion);
        System.out.println(mainOperacion.ejecutar());
        return true;
    }

    private void reconocer(LinkedList<Simbolo> entrada) throws Exception {
        switch (entrada.getFirst().token) {
            case "|" -> {
                switch (pila.peek()) {
                    case "E'" -> {
                        System.out.println("| -> Ep");
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        pila.push("|");
                        reconocer(entrada);
                    }
                    case "T'" -> {
                        System.out.println("| -> Tp");
                        pila.pop();
                        reconocer(entrada);
                    }
                    case "|" -> {
                        System.out.println("| -> |");
                        addOperacion(OperacionType.OR);
                        pila.pop();
                        entrada.removeFirst();
                        reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "&" -> {
                switch (pila.peek()) {
                    case "T'" -> {
                        System.out.println("& -> Tp");
                        pila.pop();
                        pila.push("F");
                        pila.push("&");
                        reconocer(entrada);
                    }
                    case "&" -> {
                        addOperacion(OperacionType.AND);
                        System.out.println("& -> &");
                        pila.pop();
                        entrada.removeFirst();
                        reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "!" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        System.out.println("! -> E");
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        reconocer(entrada);
                    }
                    case "T" -> {
                        System.out.println("! -> T");
                        pila.pop();
                        pila.push("T'");
                        pila.push("F");
                        reconocer(entrada);
                    }
                    case "F" -> {
                        System.out.println("! -> F");
                        pila.pop();
                        pila.push("F");
                        pila.push("!");
                        reconocer(entrada);
                    }
                    case "!" -> {
                        System.out.println("! -> !");
                        pila.pop();
                        entrada.removeFirst();
                        reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "(" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        System.out.println("( -> E");
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        reconocer(entrada);
                    }
                    case "T" -> {
                        System.out.println("( -> T");
                        pila.pop();
                        pila.push("T'");
                        pila.push("F");
                        reconocer(entrada);
                    }
                    case "F" -> {
                        System.out.println("( -> F");
                        pila.pop();
                        pila.push(")");
                        pila.push("E");
                        pila.push("(");
                        reconocer(entrada);
                    }
                    case "(" -> {
                        System.out.println("( -> (");
                        pila.pop();
                        entrada.removeFirst();
                        reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case ")" -> {
                switch (pila.peek()) {
                    case "E'", "T'" -> {
                        System.out.println(") -> Ep, Tp");
                        pila.pop();
                        reconocer(entrada);
                    }
                    case ")" -> {
                        System.out.println(") -> )");
                        pila.pop();
                        entrada.removeFirst();
                        reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "false" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        System.out.println("false -> E");
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        reconocer(entrada);
                    }
                    case "T" -> {
                        System.out.println("false -> T");
                        pila.pop();
                        pila.push("T'");
                        pila.push("F");
                        reconocer(entrada);
                    }
                    case "F" -> {
                        System.out.println("false -> F");
                        pila.pop();
                        pila.push("false");
                        reconocer(entrada);
                    }
                    case "false" -> {
                        System.out.println("false -> False");
                        asignar(new Literal(pila.peek()));
                        pila.pop();
                        entrada.removeFirst();
                        reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "true" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        System.out.println("true -> E");
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        reconocer(entrada);
                    }
                    case "T" -> {
                        System.out.println("true -> T");
                        pila.pop();
                        pila.push("T'");
                        pila.push("F");
                        reconocer(entrada);
                    }
                    case "F" -> {
                        System.out.println("true -> F");
                        pila.pop();
                        pila.push("true");
                        reconocer(entrada);
                    }
                    case "true" -> {
                        System.out.println("true -> True");
                        asignar(new Literal(pila.peek()));
                        pila.pop();
                        entrada.removeFirst();
                        reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "$" -> {
                switch (pila.peek()) {
                    case "E'", "T'" -> {
                        pila.pop();
                        reconocer(entrada);
                    }
                    case "$" -> {
                        System.out.println("Fin de cadena");
                        ;
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            default -> {
                throw new Exception("cadena no aceptada");
            }
        }
    }

    private void asignar(Expresion valor) {
        if (actualOperacion == null) {
            actualOperacion = new Operacion();
        }
        if (actualOperacion.getIzq() == null) {
            actualOperacion.setIzq(valor);
        } else if (actualOperacion.getDer() == null) {
            actualOperacion.setDer(valor);
        }
    }

    private void addOperacion(OperacionType op) {
        System.out.println("Add operacion");
        actualOperacion.setType(op);
        if (mainOperacion == null) {
            mainOperacion = cursorOperacion = actualOperacion;
        } else {
            if (cursorOperacion.getIzq() == null) {
                cursorOperacion.setIzq(actualOperacion);
            } else {
                cursorOperacion.setDer(actualOperacion);
                cursorOperacion = actualOperacion;
                actualOperacion = null;
            }
        }
    }

}
