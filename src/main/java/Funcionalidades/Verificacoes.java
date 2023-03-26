package Funcionalidades;

import DataBase.DataBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Verificacoes {

     private Connection connection;

     private String mensagemDeErro = "";

     public boolean cadastroValido(Connection connection, TextField nome, TextField email, DatePicker dataDeNascimento, PasswordField senha, PasswordField confirmarSenha) throws SQLException{
         this.connection = connection;
         boolean nomeValido = verificarNome(nome);
         boolean emailValido = verificarEmail(email);
         boolean dataValida = verificarDataDeNascimento(dataDeNascimento);
         boolean senhaValida = verificarSenha(senha, confirmarSenha);
         return (nomeValido && emailValido && dataValida && senhaValida);
     }

     public String getMensagemDeErro() {
        return mensagemDeErro;
    }

     public void setMensagemDeErro(String mensagemDeErro) {
        this.mensagemDeErro = mensagemDeErro;
    }

     public void adicionarInformacaoDeErro(String erro){
        mensagemDeErro = mensagemDeErro.concat(erro + "\n");
    }

     public boolean verificarNome(TextField nome) throws SQLException {
        String nomeInformado = nome.getText();
        if (nomeInformado == null || nomeInformado.trim().isEmpty()) {
            nome.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Digite um nome");
            return false;
        }
        else if (DataBase.usuarioJaCadastrado(connection, nome.getText())) {
            nome.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Nome já cadastrado");
            return false;
        }
        nome.setStyle("");
        return true;
    }

     public boolean verificarEmail(TextField email) throws SQLException{
        String emailInformado = email.getText();
        if (emailInformado == null || emailInformado.trim().isEmpty() || !email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            email.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Digite um email válido");
            return false;
        }
        else if (DataBase.emailJaCadastrado(connection, email.getText())) {
            email.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Email já cadastrado");
            return false;
        }
        email.setStyle("");
        return true;
    }

     public boolean verificarDataDeNascimento(DatePicker dataDeNascimento) {
        if(dataDeNascimento.getValue() == null){
            dataDeNascimento.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Informe uma data");
            return false;
        }
        else if(dataDeNascimento.getValue().isAfter(LocalDate.now())){
            dataDeNascimento.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("A data informada é posterior à hoje");
            return false;
        }
        else {
            try {
                dataDeNascimento.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                dataDeNascimento.setStyle("");
                return true;
            } catch (Exception e) {
                dataDeNascimento.setStyle("-fx-border-color: #ffc0cb;");
                adicionarInformacaoDeErro("Data inválida");
                return false;
            }
        }
    }

     public boolean verificarSenha(PasswordField senha, PasswordField confirmarSenha) {
        String senhaInformada = senha.getText();
        String confirmacaoDeSenha = confirmarSenha.getText();
        if (senhaInformada == null || senhaInformada.trim().isEmpty()) {
            senha.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Digite uma senha");
            return false;
        }
        else if (senhaInformada.length() < 12 || senhaInformada.length() > 20) {
            senha.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("A senha deve ter entre 12 e 20 caracteres");
            return false;
        }
        else if (!confirmacaoDeSenha.equals(senhaInformada)) {
            confirmarSenha.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("As senhas não correspondem");
            return false;
        }
        senha.setStyle("");
        confirmarSenha.setStyle("");
        return true;
    }
}