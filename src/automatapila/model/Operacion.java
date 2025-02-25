package automatapila.model;

public class Operacion implements Expresion {

    Expresion izq;
    Expresion der;
    OperacionType type;
    final String tipo = "Operacion";

    public Operacion() {
    }

    public Operacion(Expresion izq, Expresion der, OperacionType type) {
        this.izq = izq;
        this.der = der;
        this.type = type;
    }

    public Expresion getIzq() {
        return izq;
    }

    public void setIzq(Expresion izq) {
        this.izq = izq;
    }

    public Expresion getDer() {
        return der;
    }

    public void setDer(Expresion der) {
        this.der = der;
    }

    public OperacionType getType() {
        return type;
    }

    public void setType(OperacionType type) {
        this.type = type;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Operacion{" +
                "type=" + type +
                ", der=" + der +
                ", izq=" + izq +
                '}';
    }

    @Override
    public boolean ejecutar() {
        System.out.println("Operacion ejecutada " + type.name());
        boolean izquierda = izq == null ? null : this.izq.ejecutar();
        boolean derecha = der == null ? null : this.der.ejecutar();
        switch (this.type) {
            case OR -> {
                return izquierda || derecha;
            }
            case AND -> {
                return izquierda && derecha;
            }
            case NOT -> {
                return !derecha;
            }
            default -> {
                throw new RuntimeException("Tipo de Operacion no valido");
            }
        }
    }
}
