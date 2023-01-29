module com.example.sistemadecadastro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.sistemadecadastro to javafx.fxml;
    exports com.example.sistemadecadastro;
}