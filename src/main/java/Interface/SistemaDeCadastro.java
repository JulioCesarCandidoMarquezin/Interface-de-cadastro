package Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SistemaDeCadastro extends Application {

    @Override
    public void start(Stage stage)  {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Interface/SistemaDeCadastro.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 350);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Interface/estilo.css")).toExternalForm());
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Interface/Icone.png"))));
            stage.setTitle("Cadastro de usuário");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ioe){
            throw new RuntimeException("Não foi pssível carregar o FXML");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}