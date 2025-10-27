package co.uniquindio.arboles;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import co.uniquindio.arboles.estructuras.ArbolBinario;
import co.uniquindio.arboles.estructuras.NodoArbol;

public class ArbolController {

    // === Referencias al FXML ===
    @FXML private TextField campoDatos;
    @FXML private TextField campoAgregarNodo;
    @FXML private TextField campoEliminarNodo;
    @FXML private ComboBox<String> comboRecorrido;
    @FXML private ComboBox<String> comboObtener;
    @FXML private TextField campoExtra;
    @FXML private Pane panelDibujo;
    @FXML private TextArea areaResultado;
    @FXML private Button btnAgregarArbol;
    @FXML private Button btnAgregarNodo;
    @FXML private Button btnEliminarNodo;
    @FXML private Button btnBorrarArbol;

    @FXML
    private void initialize() {
        // Quita el foco inicial del primer TextField
        campoDatos.setFocusTraversable(false);
    }


    // === Árbol binario principal ===
    private final ArbolBinario<Integer> arbol = new ArbolBinario<>();

    // =====================================================
    // === MÉTODOS CONECTADOS A LOS BOTONES DEL INTERFAZ ===
    // =====================================================

    /** Crea el árbol inicial a partir de valores separados por comas */
    @FXML
    private void agregarDatos() {
        try {
            String texto = campoDatos.getText().trim();
            if (texto.isEmpty()) {
                areaResultado.setText("Ingrese uno o más valores separados por comas.");
                return;
            }

            String[] partes = texto.split(",");
            for (String parte : partes) {
                int valor = Integer.parseInt(parte.trim());
                arbol.agregarDato(valor);
            }

            campoDatos.clear();
            dibujarArbol();
            areaResultado.setText("Árbol inicial creado correctamente.");

            // Desactivar botón de crear árbol inicial
            campoDatos.setDisable(true);
            btnAgregarArbol.setDisable(true);

            // Activar controles para agregar nodos individuales
            btnAgregarNodo.setDisable(false);
            campoAgregarNodo.setDisable(false);

        } catch (NumberFormatException e) {
            areaResultado.setText("Error: asegúrese de ingresar solo números enteros.");
        }
    }

    /** Agrega un nuevo nodo individual al árbol existente */
    @FXML
    private void agregarNodo() {
        try {
            String texto = campoAgregarNodo.getText().trim();
            if (texto.isEmpty()) {
                areaResultado.setText("Ingrese un valor para agregar al árbol.");
                return;
            }

            int valor = Integer.parseInt(texto);
            arbol.agregarDato(valor);
            campoAgregarNodo.clear();
            dibujarArbol();
            areaResultado.setText("Nodo agregado correctamente: " + valor);

        } catch (NumberFormatException e) {
            areaResultado.setText("Error: ingrese un número válido.");
        }
    }

    /** Muestra el recorrido seleccionado */
    @FXML
    private void mostrarRecorrido() {
        if (arbol.getNodoRaiz() == null) {
            areaResultado.setText("El árbol está vacío.");
            return;
        }

        String tipo = comboRecorrido.getValue();
        if (tipo == null) {
            areaResultado.setText("Seleccione un tipo de recorrido.");
            return;
        }

        switch (tipo) {
            case "Inorden" -> areaResultado.setText("Inorden: " + arbol.inOrden(arbol.getNodoRaiz()));
            case "Preorden" -> areaResultado.setText("Preorden: " + arbol.preOrden(arbol.getNodoRaiz()));
            case "Postorden" -> areaResultado.setText("Postorden: " + arbol.postOrden(arbol.getNodoRaiz()));
            case "Amplitud" -> areaResultado.setText("Amplitud: " + arbol.imprimirAmplitud());
            default -> areaResultado.setText("Tipo de recorrido no reconocido.");
        }
    }

    /** Calcula información del árbol (peso, altura, etc.) */
    @FXML
    private void obtenerInformacion() {
        if (arbol.getNodoRaiz() == null) {
            areaResultado.setText("El árbol está vacío.");
            return;
        }

        String opcion = comboObtener.getValue();
        if (opcion == null) {
            areaResultado.setText("Seleccione una operación en el menú 'Obtener'.");
            return;
        }

        switch (opcion) {
            case "Peso" -> areaResultado.setText("Peso del árbol: " + arbol.obtenerPeso());
            case "Altura" -> areaResultado.setText("Altura del árbol: " + arbol.altura());
            case "Nivel" -> {
                try {
                    int valor = Integer.parseInt(campoExtra.getText().trim());
                    int nivel = arbol.obtenerNivel(valor);
                    if (nivel == -1)
                        areaResultado.setText("El valor " + valor + " no existe en el árbol.");
                    else
                        areaResultado.setText("Nivel del nodo " + valor + ": " + nivel);
                } catch (Exception e) {
                    areaResultado.setText("Ingrese un valor válido en el campo de texto.");
                }
            }
            case "Cantidad de hojas" -> areaResultado.setText("Cantidad de hojas: " + arbol.contarHojas());
            case "Menor" -> areaResultado.setText("Nodo menor: " + arbol.obtenerNodoMenor());
            case "Mayor" -> areaResultado.setText("Nodo mayor: " + arbol.obtenerNodoMayor());
            default -> areaResultado.setText("Opción no reconocida.");
        }
    }

    /** Habilita o deshabilita el campo 'campoExtra' según la selección */
    @FXML
    private void manejarSeleccionObtener() {
        String opcion = comboObtener.getValue();
        //campoExtra.setDisable(opcion == null || !opcion.equals("Nivel"));
        if (opcion == null || !opcion.equals("Nivel")) {
            campoExtra.clear();
            campoExtra.setDisable(true);
        }else{
            campoExtra.setDisable(false);
        }

    }

    /** Elimina un nodo específico */
    @FXML
    private void eliminarNodo() {
        try {
            String texto = campoEliminarNodo.getText().trim();
            if (texto.isEmpty()) {
                areaResultado.setText("Ingrese el valor del nodo que desea eliminar.");
                return;
            }

            int valor = Integer.parseInt(texto);
            boolean eliminado = arbol.eliminarDato(valor);

            if (eliminado) {
                dibujarArbol();
                areaResultado.setText("Nodo " + valor + " eliminado correctamente.");

                // Si el árbol queda vacío, reactivar creación inicial
                if (arbol.getNodoRaiz() == null) {
                    campoDatos.setDisable(false);
                    btnAgregarArbol.setDisable(false);
                    btnAgregarNodo.setDisable(true);
                    campoAgregarNodo.setDisable(true);
                }
            } else {
                areaResultado.setText("El valor " + valor + " no existe en el árbol.");
            }

            campoEliminarNodo.clear();

        } catch (NumberFormatException e) {
            areaResultado.setText("Error: ingrese un número válido para eliminar.");
        }
    }

    /** Borra completamente el árbol y restablece la interfaz */
    @FXML
    private void borrarArbol() {
        arbol.borrarArbol();
        panelDibujo.getChildren().clear();
        areaResultado.setText("Árbol borrado correctamente.");

        // Restablecer interfaz
        btnAgregarArbol.setDisable(false);
        campoDatos.setDisable(false);
        btnAgregarNodo.setDisable(true);
        campoAgregarNodo.setDisable(true);
    }

    // =====================================================
    // === MÉTODOS DE DIBUJO DEL ÁRBOL EN EL PANEL CENTRAL ==
    // =====================================================

    /** Dibuja el árbol completo */
    private void dibujarArbol() {
        panelDibujo.getChildren().clear();
        if (arbol.getNodoRaiz() != null) {
            double ancho = panelDibujo.getWidth();
            if (ancho <= 0) ancho = 1000; // fallback
            dibujarNodo(arbol.getNodoRaiz(), ancho / 2, 50, 150);
        }
    }



    /** Dibuja cada nodo y sus ramas */
    private void dibujarNodo(NodoArbol<Integer> nodo, double x, double y, double offsetX) {
        if (nodo == null) return;

        double distanciaVertical = 70;

        // Rama izquierda
        if (nodo.getIzquierdo() != null) {
            Line l = new Line(x, y, x - offsetX, y + distanciaVertical);
            panelDibujo.getChildren().add(l);
            dibujarNodo(nodo.getIzquierdo(), x - offsetX, y + distanciaVertical, offsetX / 1.5);
        }

        // Rama derecha
        if (nodo.getDerecho() != null) {
            Line l = new Line(x, y, x + offsetX, y + distanciaVertical);
            panelDibujo.getChildren().add(l);
            dibujarNodo(nodo.getDerecho(), x + offsetX, y + distanciaVertical, offsetX / 1.5);
        }

        // Nodo
        Circle c = new Circle(x, y, 20, Color.LIGHTBLUE);
        c.setStroke(Color.BLACK);
        Text t = new Text(x - 6, y + 5, nodo.getDato().toString());
        panelDibujo.getChildren().addAll(c, t);
    }
}
