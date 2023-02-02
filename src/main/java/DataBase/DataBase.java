package DataBase;

import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DataBase {

    private static Connection conexao = null;

    public static Connection getConnection(){
        if(conexao == null){
            try {
                Properties properties = loadProerties();
                String url = properties.getProperty("dburl");
                String usuario = properties.getProperty("usuario");
                String senha = properties.getProperty("senha");
                conexao = DriverManager.getConnection(url, usuario, senha);
            }
            catch (SQLException e){
                throw new DataBaseException(e.getMessage());
            }
        }
        return conexao;
    }

    public static void closeConnection(){
        if (conexao != null){
            try {
                conexao.close();
            }
            catch (SQLException e){
                throw new DataBaseException(e.getMessage());
            }
        }
    }

    private static Properties loadProerties(){
        try{
            FileInputStream fs = new FileInputStream("DataBase.properties");
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }
        catch (IOException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public static void cadastrarNovoUsuario(String nome, String email, Date dataNascimento, String senha, String sexo){
        try {
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "insert into cadastros"
                    + "(id, nomeCompleto, email, dataNascimento, senha, sexo)"
                    + "values"
                    + "(?,?,?,?,?,?)"
            );
            comandoSQL.setInt(1,3);
            comandoSQL.setString(2, nome);
            comandoSQL.setString(3, email);
            comandoSQL.setDate(4, dataNascimento);
            comandoSQL.setString(5, senha);
            comandoSQL.setString(6, sexo);
            comandoSQL.executeUpdate();
        }
        catch (SQLException excecaoCadastro) {
            Alert cadastroFalhou = new Alert(Alert.AlertType.ERROR, "Não foi possível cadastrar o usuario!");
            cadastroFalhou.setTitle("Erro ao cadastrar usuário");
            cadastroFalhou.setHeaderText("");
            cadastroFalhou.show();
        }
    }

    public static void obterUsuariosCadastrados(){

    }
}