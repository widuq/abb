package co.uniquindio.arboles.estructuras;

public class NodoArbol<T extends Comparable<T>> {
    private T dato;
    private NodoArbol<T> izquierdo,derecho;

    public NodoArbol(T dato){//NodoArbol<T> izquierdo, NodoArbol<T> derecho) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    public NodoArbol<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol<T> izquierdo) {
        this.izquierdo = izquierdo;
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
        return izquierdo == null && derecho == null;
    }

    }

