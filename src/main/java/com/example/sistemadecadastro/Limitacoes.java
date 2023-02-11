package com.example.sistemadecadastro;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Limitacoes {

    public static void limitarTextFieldComApenasLetras(TextField textField){
        textField.textProperty().addListener((obs, valorAntigo, novoValor) -> {
            if (novoValor != null && !novoValor.matches("[A-Za-zãõúáéíóâôê ]*")) {
                textField.setText(valorAntigo);
            }
        });
    }

    public static void limitarTamanhoMaximo(TextField textField, int tamanhoMaximo){
        textField.textProperty().addListener((obs, valorAntigo, novoValor) ->{
            if(novoValor != null && novoValor.length() > tamanhoMaximo){
                textField.setText(valorAntigo);
            }
        });
    }

    public static void limitarTamanhoMaximo(PasswordField passwordField, int tamanhoMaximo){
        passwordField.textProperty().addListener((obs, valorAntigo, novoValor) ->{
            if(novoValor != null && novoValor.length() > tamanhoMaximo){
                passwordField.setText(valorAntigo);
            }
        });
    }
}
