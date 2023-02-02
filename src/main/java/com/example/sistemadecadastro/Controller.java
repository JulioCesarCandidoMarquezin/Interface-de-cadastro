package com.example.sistemadecadastro;

import DataBase.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private RadioButton homem;

    @FXML
    private RadioButton mulher;

    @FXML
    private RadioButton prefiroNaoDizer;

    @FXML
    public Button botaoCadastrar;

    private String textoMostradoEmCasoDeCadastroInvalido = "";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBase.getConnection();
        Limitacoes.limitarCompoDoNomeComApenasLetras(nome);
        Limitacoes.limitarQuantidadeMinimaDaSenhaComUmCaracterDeCadaTipo(senha);
        homem.setToggleGroup(grupoDeRadioButtons);
        mulher.setToggleGroup(grupoDeRadioButtons);
        prefiroNaoDizer.setToggleGroup(grupoDeRadioButtons);
    }

    private boolean validarCadastracao(){
        boolean cadastroValido = true;
        if(nome.getText().equals("") || nome.getText().equals(" ") || nome.getText() == null){
            cadastroValido = false;
            textoMostradoEmCasoDeCadastroInvalido = textoMostradoEmCasoDeCadastroInvalido.concat("Digite um nome\n");
        }
        if(email.getText().equals("") || email.getText().equals(" ") || email.getText() == null || (!email.getText().contains("@gmail.com") && !email.getText().contains("@outlook.com"))){
            cadastroValido = false;
            textoMostradoEmCasoDeCadastroInvalido = textoMostradoEmCasoDeCadastroInvalido.concat("Adicione um email válido (@gmail.com ou @outlook.com)\n");
        }
        try{
            dataDeNascimento.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e){cadastroValido = false; textoMostradoEmCasoDeCadastroInvalido = textoMostradoEmCasoDeCadastroInvalido.concat("Data inválida\n");}
        if(senha.getText().length() < 12){
            cadastroValido = false;
            textoMostradoEmCasoDeCadastroInvalido = textoMostradoEmCasoDeCadastroInvalido.concat("Senha muito curta\n");
        }
        if(!confirmarSenha.getText().equals(senha.getText())){
            cadastroValido = false;
            textoMostradoEmCasoDeCadastroInvalido = textoMostradoEmCasoDeCadastroInvalido.concat("A confirmação de senha não bate com a senha\n");
        }
        if(!homem.isSelected() && !mulher.isSelected() && !prefiroNaoDizer.isSelected()){
            cadastroValido = false;
            textoMostradoEmCasoDeCadastroInvalido = textoMostradoEmCasoDeCadastroInvalido.concat("Selecione uma opção de sexo\n");
        }
        return cadastroValido;
    }

    public void cadastrar(){
        if(validarCadastracao()){
            DataBase.cadastrarNovoUsuario(nome.getText(), email.getText(), Date.valueOf(dataDeNascimento.getValue()), senha.getText(), grupoDeRadioButtons.getSelectedToggle().toString());
        }
        else {
            Alert alertaDeErro = new Alert(Alert.AlertType.WARNING, textoMostradoEmCasoDeCadastroInvalido);
            alertaDeErro.show();
            textoMostradoEmCasoDeCadastroInvalido = "";
        }
    }
}