package co.uniquindio.arboles.estructuras;

public class Cola<T> {

    public Nodo<T> nodoPrimero, nodoUltimo;
    public int tamanio;


    public void encolar(T dato) {

        Nodo<T> nodo = new Nodo<>(dato);

        if(estaVacia()) {
            nodoPrimero = nodoUltimo = nodo;
        }else {
            nodoUltimo.setSiguienteNodo(nodo);
            nodoUltimo = nodo;
        }

        tamanio++;
    }


    public T desencolar() {

        if(estaVacia()) {
            throw new RuntimeException("La Cola está vacía");
        }

        T dato = nodoPrimero.getValorNodo();
        nodoPrimero = nodoPrimero.getSiguienteNodo();

        if(nodoPrimero==null) {
            nodoUltimo = null;
        }

        tamanio--;
        return dato;
    }


    public boolean estaVacia() {
        return nodoPrimero == null;
    }


    public void borrarCola() {
        nodoPrimero = nodoUltimo = null;
        tamanio = 0;
    }

    public Nodo<T> getPrimero() {
        return nodoPrimero;
    }


    public Nodo<T> getUltimo() {
        return nodoUltimo;
    }


    public int getTamano() {
        return tamanio;
    }


    public boolean sonIdenticas(Cola<T> cola) {

        Cola<T> clon1 = clone();
        Cola<T> clon2 = cola.clone();

        if(clon1.getTamano() == clon2.getTamano()) {

            while( !clon1.estaVacia() ) {
                if( !clon1.desencolar().equals( clon2.desencolar() ) ) {
                    return false;
                }
            }

        }else {
            return false;
        }

        return  true;
    }

    public void imprimir() {
        Nodo<T> aux = nodoPrimero;
        while(aux!=null) {
            System.out.print(aux.getValorNodo()+"\t");
            aux = aux.getSiguienteNodo();
        }
        System.out.println();
    }

    @Override
    protected Cola<T> clone() {

        Cola<T> nueva = new Cola<>();
        Nodo<T> aux = nodoPrimero;

        while(aux!=null) {
            nueva.encolar( aux.getValorNodo() );
            aux = aux.getSiguienteNodo();
        }

        return nueva;
    }


}
