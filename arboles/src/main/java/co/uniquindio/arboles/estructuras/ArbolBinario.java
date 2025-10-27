package co.uniquindio.arboles.estructuras;

public class ArbolBinario<T extends Comparable<T>> {

    private NodoArbol<T> nodoRaiz;
    private int cantidadNodos;

    public ArbolBinario() {
        this.nodoRaiz = null;
        this.cantidadNodos = 0;
    }

    // =====================================================
    // == RECORRIDOS QUE DEVUELVEN STRINGS ==
    // =====================================================

    // Inorden: izquierda - raíz - derecha
    public String inOrden(NodoArbol<T> nodo) {
        if (nodo == null) return "";
        return inOrden(nodo.getIzquierdo())
                + nodo.getDato().toString() + " "
                + inOrden(nodo.getDerecho());
    }

    // Preorden: raíz - izquierda - derecha
    public String preOrden(NodoArbol<T> nodo) {
        if (nodo == null) return "";
        return nodo.getDato().toString() + " "
                + preOrden(nodo.getIzquierdo())
                + preOrden(nodo.getDerecho());
    }

    // Postorden: izquierda - derecha - raíz
    public String postOrden(NodoArbol<T> nodo) {
        if (nodo == null) return "";
        return postOrden(nodo.getIzquierdo())
                + postOrden(nodo.getDerecho())
                + nodo.getDato().toString() + " ";
    }

    // Amplitud: nivel por nivel izquierda a derecha
    public String imprimirAmplitud() {
        if (nodoRaiz == null) return "El árbol está vacío.";

        StringBuilder sb = new StringBuilder();
        Cola<NodoArbol<T>> cola = new Cola<>();
        cola.encolar(nodoRaiz);

        while (!cola.estaVacia()) {
            NodoArbol<T> actual = cola.desencolar();
            sb.append(actual.getDato()).append(" ");
            if (actual.getIzquierdo() != null) cola.encolar(actual.getIzquierdo());
            if (actual.getDerecho() != null) cola.encolar(actual.getDerecho());
        }
        return sb.toString().trim();
    }

    // =====================================================
    // == MÉTODOS DE INSERCIÓN, BÚSQUEDA Y ELIMINACIÓN ==
    // =====================================================

    public boolean estaVacio() {
        return nodoRaiz == null;
    }

    public void agregarDato(T dato) {
        nodoRaiz = insertarRecursivo(nodoRaiz, dato);
            }

    private NodoArbol<T> insertarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            cantidadNodos++;
            return new NodoArbol<>(dato);
        }

        int comparacion = dato.compareTo(nodo.getDato());
        if (comparacion < 0) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), dato));
        }else{
            return nodo;
        }
        return nodo;
    }

    //método para verificar si existe un dato
    public boolean buscarNodo(T dato) {
        return buscarNodo(nodoRaiz, dato);
    }

    private boolean buscarNodo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) return false;

        int comp = dato.compareTo(nodo.getDato());
        if (comp == 0) return true;
        if (comp < 0) return buscarNodo(nodo.getIzquierdo(), dato);
        return buscarNodo(nodo.getDerecho(), dato);
    }

    public boolean eliminarDato(T dato) {
        int antes = cantidadNodos;
        nodoRaiz = eliminarRecursivo(nodoRaiz, dato);
        return cantidadNodos < antes;
    }

    private NodoArbol<T> eliminarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) return null;

        int comp = dato.compareTo(nodo.getDato());
        if (comp < 0)
            nodo.setIzquierdo(eliminarRecursivo(nodo.getIzquierdo(), dato));
        else if (comp > 0)
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), dato));
        else {
            // Nodo encontrado
            if (nodo.esHoja()) {
                cantidadNodos--;
                return null;
            }
            if (nodo.getIzquierdo() == null) {
                cantidadNodos--;
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                cantidadNodos--;
                return nodo.getIzquierdo();
            }
            // Nodo con dos hijos
            T sucesor = encontrarMinimo(nodo.getDerecho());
            nodo.setDato(sucesor);
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), sucesor));
        }
        return nodo;
    }

    // =====================================================
    // == CONSULTAS Y PROPIEDADES ==
    // =====================================================

    public int obtenerPeso() {
        return cantidadNodos;
    }

    public int altura() {
        return alturaRecursiva(nodoRaiz);
    }

    private int alturaRecursiva(NodoArbol<T> nodo) {
        if (nodo == null) return 0;
        return Math.max(alturaRecursiva(nodo.getIzquierdo()), alturaRecursiva(nodo.getDerecho())) + 1;
    }

    public int obtenerNivel(T dato) {
        return obtenerNivelRecursivo(nodoRaiz, dato, 0);
    }

    private int obtenerNivelRecursivo(NodoArbol<T> nodo, T dato, int nivel) {
        if (nodo == null) return -1;
        if (nodo.getDato().equals(dato)) return nivel;

        int nivelIzq = obtenerNivelRecursivo(nodo.getIzquierdo(), dato, nivel + 1);
        if (nivelIzq != -1) return nivelIzq;
        return obtenerNivelRecursivo(nodo.getDerecho(), dato, nivel + 1);
    }

    public int contarHojas() {
        return contarHojasRecursivo(nodoRaiz);
    }

    private int contarHojasRecursivo(NodoArbol<T> nodo) {
        if (nodo == null) return 0;
        if (nodo.esHoja()) return 1;
        return contarHojasRecursivo(nodo.getIzquierdo()) + contarHojasRecursivo(nodo.getDerecho());
    }

    // =====================================================
    // == NODOS MÁXIMO Y MÍNIMO ==
    // =====================================================

    public T obtenerNodoMenor() {
        if (nodoRaiz == null) return null;
        return encontrarMinimo(nodoRaiz);
    }

    private T encontrarMinimo(NodoArbol<T> nodo) {
        while (nodo.getIzquierdo() != null) nodo = nodo.getIzquierdo();
        return nodo.getDato();
    }

    public T obtenerNodoMayor() {
        if (nodoRaiz == null) return null;
        return encontrarMaximo(nodoRaiz);
    }

    private T encontrarMaximo(NodoArbol<T> nodo) {
        while (nodo.getDerecho() != null) nodo = nodo.getDerecho();
        return nodo.getDato();
    }

    // =====================================================
    // == OTRAS FUNCIONALIDADES ==
    // =====================================================

    public void borrarArbol() {
        nodoRaiz = null;
        cantidadNodos = 0;
    }

    public NodoArbol<T> getNodoRaiz() {
        return nodoRaiz;
    }

}
