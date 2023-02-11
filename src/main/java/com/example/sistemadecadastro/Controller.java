package com.example.sistemadecadastro;

import DataBase.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.Closeable;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable, Closeable {

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
    protected Button botaoCadastrar;

    private String mensagemDeErro = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Limitacoes.limitarTextFieldComApenasLetras(nome);
        Limitacoes.limitarTamanhoMaximo(nome, 50);
        Limitacoes.limitarTamanhoMaximo(email, 50);
        Limitacoes.limitarTamanhoMaximo(senha, 20);
        Limitacoes.limitarTamanhoMaximo(confirmarSenha, 20);
    }

    @Override
    public void close(){
        DataBase.closeConnection();
    }

    public void cadastrar() {
        if (cadastroValido()) {
            DataBase.cadastrarUsuario(nome.getText(), email.getText(), Date.valueOf(dataDeNascimento.getValue()), senha.getText());
            Alert cadastroRealizado = new Alert(Alert.AlertType.INFORMATION, "Usuário cadastrado com sucesso!");
            cadastroRealizado.setTitle("Cadastro realizado");
            cadastroRealizado.setHeaderText("");
            cadastroRealizado.show();
            limparFormulario();
        } else {
            Alert alertaDeErro = new Alert(Alert.AlertType.WARNING, mensagemDeErro);
            alertaDeErro.setResizable(false);
            alertaDeErro.setTitle("Informações que devem ser alteradas para cadastro");
            alertaDeErro.setHeaderText("");
            alertaDeErro.show();
            mensagemDeErro = "";
        }
    }

    private void informacaoDeErro(String informacao){
        mensagemDeErro = mensagemDeErro.concat(informacao + "\n");
    }

    private boolean cadastroValido(){
        boolean nomeValido = verificarNome();
        boolean emailValido = verificarEmail();
        boolean dataValida = verificarDataDeNascimento();
        boolean senhaValida = verificarSenha();
        return (nomeValido && emailValido && dataValida && senhaValida);
    }

    private boolean verificarNome() {
        String nomeInformado = nome.getText();
        if (nomeInformado == null || nomeInformado.trim().isEmpty()) {
            informacaoDeErro("Digite um nome");
            return false;
        }
        else if (DataBase.usuarioJaCadastrado(nome.getText())) {
            informacaoDeErro("Nome já cadastrado");
            return false;
        }
        return true;
    }

    private boolean verificarEmail() {
        String emailInformado = email.getText();
        if (emailInformado == null || emailInformado.trim().isEmpty() || !emailInformado.contains("@") || !emailInformado.contains(".com")) {
            informacaoDeErro("Digite um email válido");
            return false;
        }
        else if (DataBase.emailJaCadastrado(email.getText())) {
            informacaoDeErro("Email já cadastrado");
            return false;
        }
        return true;
    }

    private boolean verificarDataDeNascimento() {
        if(dataDeNascimento.getValue() == null){
            informacaoDeErro("Informe uma data");
            return false;
        }
        else if(dataDeNascimento.getValue().isAfter(LocalDate.now())){
            informacaoDeErro("A data informada é posterior à hoje");
            return false;
        }
        else {
            try {
                dataDeNascimento.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return true;
            } catch (Exception e) {
                informacaoDeErro("Data inválida");
                return false;
            }
        }
    }

    private boolean verificarSenha() {
        String senhaInformada = senha.getText();
        String confirmacaoDeSenha = confirmarSenha.getText();
        if (senhaInformada == null || senhaInformada.trim().isEmpty()) {
            informacaoDeErro("Digite uma senha");
            return false;
        }
        else if (senhaInformada.length() < 12 || senhaInformada.length() > 20) {
            informacaoDeErro("A senha deve ter entre 12 e 20 caracteres");
            return false;
        }
        else if (!confirmacaoDeSenha.equals(senhaInformada)) {
            informacaoDeErro("As senhas não correspondem");
            return false;
        }
        return true;
    }

    private void limparFormulario(){
        nome.clear();
        email.clear();
        senha.clear();
        confirmarSenha.clear();
        dataDeNascimento.setValue(null);
    }
}