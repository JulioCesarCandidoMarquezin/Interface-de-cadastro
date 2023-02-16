package Interface;

import DataBase.DataBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Verificacoes {

     private String mensagemDeErro = "";

     public String getMensagemDeErro() {
        return mensagemDeErro;
    }

     public void setMensagemDeErro(String mensagemDeErro) {
        this.mensagemDeErro = mensagemDeErro;
    }

     void adicionarInformacaoDeErro(String erro){
        mensagemDeErro = mensagemDeErro.concat(erro + "\n");
    }

     boolean cadastroValido(DataBase dataBase, TextField nome, TextField email, DatePicker dataDeNascimento, PasswordField senha, PasswordField confirmarSenha){
         boolean nomeValido = verificarNome(dataBase, nome);
         boolean emailValido = verificarEmail(dataBase, email);
         boolean dataValida = verificarDataDeNascimento(dataDeNascimento);
         boolean senhaValida = verificarSenha(senha, confirmarSenha);
         return (nomeValido && emailValido && dataValida && senhaValida);
     }

     boolean verificarNome(DataBase dataBase, TextField nome) {
        String nomeInformado = nome.getText();
        if (nomeInformado == null || nomeInformado.trim().isEmpty()) {
            nome.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Digite um nome");
            return false;
        }
        else if (dataBase.usuarioJaCadastrado(nome.getText())) {
            nome.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Nome já cadastrado");
            return false;
        }
        nome.setStyle("");
        return true;
    }

     boolean verificarEmail(DataBase dataBase, TextField email) {
        String emailInformado = email.getText();
        if (emailInformado == null || emailInformado.trim().isEmpty() || !email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            email.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Digite um email válido");
            return false;
        }
        else if (dataBase.emailJaCadastrado(email.getText())) {
            email.setStyle("-fx-border-color: #ffc0cb;");
            adicionarInformacaoDeErro("Email já cadastrado");
            return false;
        }
        email.setStyle("");
        return true;
    }

     boolean verificarDataDeNascimento(DatePicker dataDeNascimento) {
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

     boolean verificarSenha(PasswordField senha, PasswordField confirmarSenha) {
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
