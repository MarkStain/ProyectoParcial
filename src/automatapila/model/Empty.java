package automatapila.model;

public class Empty implements Expresion {
    String tipo = "Empty";

    public Empty() {
    }


    @Override
    public String toString() {
        return "Empty{}";
    }

    @Override
    public boolean ejecutar() {
        System.out.println("Empty");
        return true;
    }
}
