/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatapila;

import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author Carlos
 */
public class Automata {

    Stack<String> pila = new Stack<>();
    boolean resultado = false;

    public boolean parsear(LinkedList<Simbolo> entrada) throws Exception {

        pila.push("$");
        pila.push("E");
        return reconocer(entrada);

    }

    private boolean reconocer(LinkedList<Simbolo> entrada) throws Exception {
        //entrada.stream().forEach(item-> System.out.println(item)
        System.out.println(pila.peek());
                
        switch (entrada.getFirst().token) {
            case "|" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "E'" -> {
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        pila.push("|");
                        return reconocer(entrada);
                    }
                    case "T" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T'" -> {
                        pila.pop();
                        return reconocer(entrada);
                    }
                    case "F" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "|" -> {
                        pila.pop();
                        entrada.removeFirst();
                        return reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "&" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "E'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T'" -> {
                        pila.pop();
                        pila.push("F");
                        pila.push("&");
                        return reconocer(entrada);
                    }
                    case "F" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "&" -> {
                        pila.pop();
                        entrada.removeFirst();
                        return reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "!" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        return reconocer(entrada);
                    }
                    case "E'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T" -> {
                        pila.pop();
                        pila.push("T'");
                        pila.push("F");
                        return reconocer(entrada);
                    }
                    case "T'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "F" -> {
                        pila.pop();
                        pila.push("F");
                        pila.push("!");
                        return reconocer(entrada);
                    }
                    case "!" -> {
                        pila.pop();
                        entrada.removeFirst();
                        return reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "(" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        return reconocer(entrada);
                    }
                    case "E'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T" -> {
                        pila.pop();
                        pila.push("T'");
                        pila.push("F");
                        return reconocer(entrada);
                    }
                    case "T'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "F" -> {
                        pila.pop();
                        pila.push(")");
                        pila.push("E");
                        pila.push("(");
                        return reconocer(entrada);
                    }
                    case "(" -> {
                        pila.pop();
                        entrada.removeFirst();
                        return reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case ")" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "E'" -> {
                        pila.pop();
                        return reconocer(entrada);
                    }
                    case "T" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T'" -> {
                        pila.pop();
                        return reconocer(entrada);
                    }
                    case "F" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case ")" -> {
                        pila.pop();
                        entrada.removeFirst();
                        return reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "false" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        return reconocer(entrada);
                    }
                    case "E'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T" -> {
                        pila.pop();
                        pila.push("T'");
                        pila.push("F");
                        return reconocer(entrada);
                    }
                    case "T'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "F" -> {
                        pila.pop();
                        pila.push("false");
                        return reconocer(entrada);
                    }
                    case "false" -> {
                        pila.pop();
                        entrada.removeFirst();
                        return reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "true" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        pila.pop();
                        pila.push("E'");
                        pila.push("T");
                        return reconocer(entrada);
                    }
                    case "E'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T" -> {
                        pila.pop();
                        pila.push("T'");
                        pila.push("F");
                        return reconocer(entrada);
                    }
                    case "T'" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "F" -> {
                        pila.pop();
                        pila.push("true");
                        return reconocer(entrada);
                    }
                    case "true" -> {
                        pila.pop();
                        entrada.removeFirst();
                        return reconocer(entrada);
                    }
                    default -> {
                        throw new Exception("cadena no aceptada");
                    }
                }
            }
            case "$" -> {
                switch (pila.peek()) {
                    case "E" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "E'" -> {
                        pila.pop();
                        return reconocer(entrada);
                    }
                    case "T" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "T'" -> {
                        pila.pop();
                        return reconocer(entrada);
                    }
                    case "F" -> {
                        throw new Exception("cadena no aceptada");
                    }
                    case "$" -> {
                        return true;
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

}
