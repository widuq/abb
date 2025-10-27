module co.uniquindio.arboles {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.uniquindio.arboles to javafx.fxml;
    exports co.uniquindio.arboles;
}