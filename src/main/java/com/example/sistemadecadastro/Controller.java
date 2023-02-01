package com.example.sistemadecadastro;

import DataBase.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private TextField nome = null;

    @FXML
    private TextField email = null;

    @FXML
    private PasswordField senha = null;

    @FXML
    private PasswordField confirmarSenha = null;

    @FXML
    private DatePicker dataDeNascimento = null;

    @FXML
    private ToggleGroup grupoDeRadioButtons = new ToggleGroup();

    @FXML
    private RadioButton homem;

    @FXML
    private RadioButton mulher;

    @FXML
    private RadioButton prefiroNaoDizer;

    @FXML
    private Button BotaoCadastrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBase.getConnection();
        Limitacoes.limitarCompoDoNomeComApenasLetras(nome);
        Limitacoes.limitarQuantidadeMinimaDaSenhaComUmCaracterDeCadaTipo(senha);
        homem.setToggleGroup(grupoDeRadioButtons);
        mulher.setToggleGroup(grupoDeRadioButtons);
        prefiroNaoDizer.setToggleGroup(grupoDeRadioButtons);
    }

    private void validarCadastracao(){

    }

    public void cadastrar() throws RuntimeException{
        DataBase.cadastrarNovoUsuario(nome.getText(), email.getText(), Date.valueOf(dataDeNascimento.getValue()), senha.getText(), "homem");
    }
}