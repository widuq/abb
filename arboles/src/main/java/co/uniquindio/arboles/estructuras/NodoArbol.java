package co.uniquindio.arboles.estructuras;

public class NodoArbol<T extends Comparable<T>> {
    private T dato;
    private NodoArbol<T> getIzquierdo,derecho;

    public NodoArbol(T dato){//NodoArbol<T> izquierdo, NodoArbol<T> derecho) {
        this.dato = dato;
        this.getIzquierdo = null;
        this.derecho = null;
    }

    public NodoArbol<T> getIzquierdo() {
        return getIzquierdo;
    }

    public void setIzquierdo(NodoArbol<T> izquierdo) {
        this.getIzquierdo = izquierdo;
    }

    public NodoArbol<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol<T> derecho) {
        this.derecho = derecho;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public boolean esHoja() {
        return getIzquierdo == null && derecho == null;
    }

    }

