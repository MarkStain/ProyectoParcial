package automatapila.model;

public class Literal implements Expresion {
    String valor;
    String tipo = "Literal";

    public Literal() {
    }

    public Literal(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Literal{" +
                "valor='" + valor + '\'' +
                '}';
    }

    @Override
    public boolean ejecutar() {
        System.out.println("Literal ejecutado: " + valor);
        return this.valor.equals("true");
    }
}
