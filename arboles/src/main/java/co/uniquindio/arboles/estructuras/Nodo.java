package co.uniquindio.arboles.estructuras;


public class Nodo<T> {

    private Nodo<T> siguienteNodo;
    private T valorNodo;

    public Nodo(T valorNodo) {
        this.valorNodo = valorNodo;
    }


    public Nodo(T dato, Nodo<T> siguiente) {
        super();
        this.valorNodo = dato;
        this.siguienteNodo = siguiente;
    }


    //Metodos get y set de la clase Nodo

    public Nodo<T> getSiguienteNodo() {
        return siguienteNodo;
    }


    public void setSiguienteNodo(Nodo<T> siguienteNodo) {
        this.siguienteNodo = siguienteNodo;
    }


    public T getValorNodo() {
        return valorNodo;
    }


    public void setValorNodo(T valorNodo) {
        this.valorNodo = valorNodo;
    }
}