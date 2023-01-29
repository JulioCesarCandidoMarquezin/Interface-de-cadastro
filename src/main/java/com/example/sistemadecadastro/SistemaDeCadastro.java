package com.example.sistemadecadastro;

import DataBase.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SistemaDeCadastro extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaDeCadastro.class.getResource("SistemaDeCadastro.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 375);
        stage.setTitle("Cadastro");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Desconectado");
        DataBase.closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}