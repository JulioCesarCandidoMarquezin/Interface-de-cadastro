package com.example.sistemadecadastro;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Limitacoes {

    public static void limitarCompoDeNomeComApenasLetras(TextField nome){
        nome.textProperty().addListener((obs, valorAntigo, novoValor) -> {
            if (novoValor != null && !novoValor.matches("[A-Za-zãõúáéíóâôê ]*")) {
                nome.setText(valorAntigo);
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
