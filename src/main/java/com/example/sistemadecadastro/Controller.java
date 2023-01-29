package com.example.sistemadecadastro;

import DataBase.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Label textoPrincipal = new Label("Cadastro de novo cliente");
    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private PasswordField senha;
    @FXML
    private PasswordField confirmarSenha;
    @FXML
    private DatePicker dataDeNascimento;
    @FXML
    private ToggleGroup grupoDeRadioButtons = new ToggleGroup();
    @FXML
    private RadioButton homem;
    @FXML
    private RadioButton mulher;
    @FXML
    private Button cadastrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Conectado");
        DataBase.getConnection();
        Limitacoes.limitarCompoDoNomeComApenasLetras(nome);
        Limitacoes.limitarQuantidadeMinimaDaSenhaComUmCaracterDeCadaTipo(senha);
        senha.setFocusTraversable(true);
        homem.setToggleGroup(grupoDeRadioButtons);
        mulher.setToggleGroup(grupoDeRadioButtons);

    }
}