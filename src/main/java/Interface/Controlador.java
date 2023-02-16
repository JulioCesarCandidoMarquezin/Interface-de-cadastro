package Interface;

import DataBase.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.Closeable;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ResourceBundle;

public class Controlador implements Initializable, Closeable {

    private final Limitacoes limitacoes = new Limitacoes();

    private final DataBase dataBase = new DataBase();

    private final Verificacoes verificacoes = new Verificacoes();

    @FXML
    private TextField nome = null;

    @FXML
    private TextField email = null;

    @FXML
    private PasswordField senha = null;

    @FXML
    private PasswordField confirmarSenha = null;

    @FXML
    private DatePicker dataDeNascimento = new DatePicker();

    @FXML
    protected Button botaoCadastrar;

    private Connection conexao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limitacoes.limitarTextFieldComApenasLetras(nome);
        limitacoes.limitarTamanhoMaximo(nome, 50);
        limitacoes.limitarTamanhoMaximo(email, 50);
        limitacoes.limitarTamanhoMaximo(dataDeNascimento, 10);
        limitacoes.limitarTamanhoMaximo(senha, 20);
        limitacoes.limitarTamanhoMaximo(confirmarSenha, 20);
        limitacoes.limitarDatePickerComApenasDatasValidas(dataDeNascimento);
        limitacoes.limitarDatePickerComDatasAnterioresHoje(dataDeNascimento);
        dataDeNascimento.setShowWeekNumbers(true);
    }

    @Override
    public void close(){
        dataBase.closeConnection();
    }

    public void mostrarAlerta(String titulo, String cabecalho, String mensagem){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensagem);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.show();
    }

    public void cadastrar() {
        if (verificacoes.cadastroValido(dataBase, nome, email, dataDeNascimento, senha, confirmarSenha)) {
            dataBase.cadastrarUsuario(nome.getText().trim(), email.getText(), Date.valueOf(dataDeNascimento.getValue()), senha.getText());
            mostrarAlerta("Cadastro realizado", "", "Usuário cadastrado com sucesso!");
            limparFormulario();
        } else {
            mostrarAlerta("Informações que devem ser alteradas para cadastro", "", verificacoes.getMensagemDeErro());
            verificacoes.setMensagemDeErro("");
        }
    }

    private void limparFormulario(){
        nome.clear();
        email.clear();
        senha.clear();
        confirmarSenha.clear();
        dataDeNascimento.setValue(null);
    }
}