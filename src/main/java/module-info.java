module Interface {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens Interface to javafx.fxml;
    exports Interface;
    exports Funcionalidades;
    opens Funcionalidades to javafx.fxml;
}