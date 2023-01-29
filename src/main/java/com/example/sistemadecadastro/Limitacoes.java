package com.example.sistemadecadastro;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Limitacoes {
    public static void limitarCompoDoNomeComApenasLetras(TextField nome){
        try {
            //Limita a pessoa à colocar somente letras normais e letras com acento//
            nome.textProperty().addListener((obs, valorAntigo, novoValor) -> {
                if (novoValor != null && !novoValor.matches("[A-Za-zãõúáéíóâôê ]*")) {
                    nome.setText(valorAntigo);
                }
            });
        }
        catch (Exception e){}
    }
    static int sla = 1;
    public static void limitarQuantidadeMinimaDaSenhaComUmCaracterDeCadaTipo(PasswordField senha){
        try {
            senha.textProperty().addListener((obs, valorAntigo, novoValor) -> {
                String numeros = "[0-9]*";
                String letrasUpperCase = "[A-Z]";
                String letrasLowerCase = "[a-z]";
                String simbolos = "[.+*/,!@#$%¨&()_'¹²³£¢¬§|<>:?Ç^{} ;°~º\\[\\]ª`]";
                byte[] letras = novoValor.getBytes();

            });
        }
        catch (Exception e){}
    }
}
