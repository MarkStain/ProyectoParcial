/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatapila;

import automatapila.model.*;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author
 */
public class Automata {


    Operacion actualOperacion = null;
    Operacion temporalOperacion = null;
    Stack<String> pila = new Stack<>();
    Stack<Operacion> prioridadStack = new Stack<>();

    public boolean parsear(LinkedList<Simbolo> entrada) throws Exception {
        actualOperacion = new Operacion();
        pila.push("$");
        pila.push("E");
        reconocer(entrada);
        System.out.println(actualOperacion);
        System.out.println(actualOperacion.ejecutar());
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
                        pila.push("T'");
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
                        addOperacion(OperacionType.NOT);
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
                        manejarPrioridad(false);
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
                        manejarPrioridad(true);
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

    private void manejarPrioridad(boolean cierre) {
        if (cierre) {
            Operacion anterior = prioridadStack.pop();
            if (anterior.getIzq() == null && anterior.getDer() == null) {
                actualOperacion = actualOperacion;
            } else if (anterior.getIzq() == null) {
                anterior.setIzq(actualOperacion);
                actualOperacion = anterior;
            } else if (anterior.getDer() == null) {
                anterior.setDer(actualOperacion);
                actualOperacion = anterior;
                if (!actualOperacion.getOperacionStack().isEmpty()) {
                    actualOperacion = actualOperacion.getOperacionStack().pop();
                }
            }
        } else {
            prioridadStack.push(actualOperacion);
            actualOperacion = new Operacion();
        }
    }

    private void asignar(Expresion valor) {
        System.out.println(valor);
        if (actualOperacion.getIzq() == null) {
            actualOperacion.setIzq(valor);
        } else if (actualOperacion.getDer() == null) {
            addSetDer(valor);
        }
    }

    private void addOperacion(OperacionType op) {
        System.out.println(op.name());
        Operacion newOperacion = new Operacion();
        if (op == OperacionType.NOT) {
            newOperacion.setIzq(new Empty());
            newOperacion.setType(op);
            if (actualOperacion.getType() != null) {
                addSetDer(newOperacion);
                newOperacion.getOperacionStack().push(actualOperacion);
            }
            actualOperacion = newOperacion;
        } else if (actualOperacion.getIzq() != null && actualOperacion.getDer() != null) {
            newOperacion.setIzq(actualOperacion);
            actualOperacion = newOperacion;
            actualOperacion.setType(op);
        } else {
            actualOperacion.setType(op);
        }
    }

    private void addSetDer(Expresion expresion) {
        actualOperacion.setDer(expresion);
        if (!actualOperacion.getOperacionStack().isEmpty()) {
            actualOperacion = actualOperacion.getOperacionStack().pop();
        }
    }
}


