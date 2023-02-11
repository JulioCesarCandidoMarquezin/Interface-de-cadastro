package com.example.sistemadecadastro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class SistemaDeCadastro extends Application {

    @Override
    public void start(Stage stage)  {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(SistemaDeCadastro.class.getResource("SistemaDeCadastro.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 350, 375);
            stage.setTitle("Cadastro de novo usuário");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ioe){
            Alert erroAoCarregarFXML = new Alert(Alert.AlertType.ERROR, "Não foi possível carregar o FXML");
            erroAoCarregarFXML.setTitle("Erro");
            erroAoCarregarFXML.setHeaderText("");
            erroAoCarregarFXML.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}