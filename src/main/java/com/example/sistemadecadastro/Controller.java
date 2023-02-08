package com.example.sistemadecadastro;

import DataBase.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
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
    private RadioButton homem, mulher, prefiroNaoDizer;

    @FXML
    public Button botaoCadastrar;

    private String textoMostradoEmCasoDeCadastroInvalido = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Limitacoes.limitarCompoDeNomeComApenasLetras(nome);
        Limitacoes.limitarTamanhoMaximo(nome, 50);
        Limitacoes.limitarTamanhoMaximo(email, 50);
        Limitacoes.limitarTamanhoMaximo(senha, 20);
        Limitacoes.limitarTamanhoMaximo(confirmarSenha, 20);
        homem.setToggleGroup(grupoDeRadioButtons);
        mulher.setToggleGroup(grupoDeRadioButtons);
        prefiroNaoDizer.setToggleGroup(grupoDeRadioButtons);
    }

    private void listarInformacoesInvalidas(String informacao){
        textoMostradoEmCasoDeCadastroInvalido = textoMostradoEmCasoDeCadastroInvalido.concat(informacao + "\n");
    }

    private boolean validarCadastracao(){
        boolean cadastroValido = true;

        if(nome.getText().equals("") || nome.getText().equals(" ") || nome.getText() == null){
            cadastroValido = false;
            listarInformacoesInvalidas("Digite um nome");
        }

        if(email.getText().equals("") || email.getText().equals(" ") || email.getText() == null || (!email.getText().contains("@") && email.getText().contains(".com"))){
            cadastroValido = false;
            listarInformacoesInvalidas("Adicione um email válido");
        }

        try{
            dataDeNascimento.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        catch (Exception e){
            cadastroValido = false; listarInformacoesInvalidas("Data inválida");
        }

        if(senha.getText().length() < 12 || senha.getText().length() > 20){
            cadastroValido = false;
            listarInformacoesInvalidas((senha.getText().equals("") || senha.getText() == null) ? "Digite uma senha" : "A senha deve ter pelo menos 12 caracteres\n");
        }
        else if(!confirmarSenha.getText().equals(senha.getText())){
            cadastroValido = false;
            listarInformacoesInvalidas("A confirmação de senha não é igual a senha");
        }

        if(!grupoDeRadioButtons.getSelectedToggle().isSelected()){
            cadastroValido = false;
            listarInformacoesInvalidas("Selecione uma opção de sexo");
        }

        if(cadastroValido && DataBase.usuarioJaCadastrado(nome.getText(), email.getText())){
            cadastroValido = false;
            listarInformacoesInvalidas("usuario ou email já cadastrado");
        }
        return cadastroValido;
    }

    public void cadastrar(){
        DataBase.getConnection();
        if(validarCadastracao()){
            DataBase.cadastrarNovoUsuario(nome.getText(), email.getText(), Date.valueOf(dataDeNascimento.getValue()), senha.getText(), grupoDeRadioButtons.getSelectedToggle().getUserData().toString());
        }
        else {
            Alert alertaDeErro = new Alert(Alert.AlertType.WARNING, textoMostradoEmCasoDeCadastroInvalido);
            alertaDeErro.setResizable(false);
            alertaDeErro.setTitle("Informaçãoes que devem ser alteradas para cadastro");
            alertaDeErro.setHeaderText("");
            alertaDeErro.show();
            textoMostradoEmCasoDeCadastroInvalido = "";
        }
        DataBase.closeConnection();
    }
}