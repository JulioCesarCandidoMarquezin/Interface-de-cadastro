package Interface;

import DataBase.DataBase;
import Funcionalidades.Limitacoes;
import Funcionalidades.Verificacoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.Closeable;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controlador implements Initializable, Closeable {

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

    private Connection conexao = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Limitacoes.limitarTextFieldComApenasLetras(nome);
        Limitacoes.limitarTamanhoMaximo(nome, 50);
        Limitacoes.limitarTamanhoMaximo(email, 50);
        Limitacoes.limitarTamanhoMaximo(dataDeNascimento, 10);
        Limitacoes.limitarTamanhoMaximo(senha, 20);
        Limitacoes.limitarTamanhoMaximo(confirmarSenha, 20);
        Limitacoes.limitarDatePickerComApenasDatasValidas(dataDeNascimento);
        Limitacoes.limitarDatePickerComApenasNumerosBarras(dataDeNascimento);
        Limitacoes.limitarDatePickerComDatasAnterioresHoje(dataDeNascimento);
        Limitacoes.adicionarBarrasAutomaticamente(dataDeNascimento);
        dataDeNascimento.setShowWeekNumbers(true);
        conexao = DataBase.getConnection();
    }

    @Override
    public void close(){
        DataBase.closeConnection(conexao);
    }

    public static void mostrarAlerta(Alert.AlertType tipo, String titulo, String cabecalho, String mensagem){
        Alert alerta = new Alert(tipo, mensagem);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.show();
    }

    public void cadastrar() {
        try {
            if (verificacoes.cadastroValido(conexao, nome, email, dataDeNascimento, senha, confirmarSenha)) {
                if(conexao.isClosed()){
                    conexao.beginRequest();
                }
                DataBase.cadastrarUsuario(conexao, nome.getText().trim(), email.getText().trim(), Date.valueOf(dataDeNascimento.getValue()), senha.getText());
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cadastro realizado", "", "Usuário cadastrado com sucesso!");
                limparFormulario();
            }
            else {
                mostrarAlerta(Alert.AlertType.WARNING, "Informações que devem ser alteradas para cadastro", "", verificacoes.getMensagemDeErro());
                verificacoes.setMensagemDeErro("");
            }
        }
        catch (SQLException e){
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "", "Erro ao acessar o banco de dados");
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