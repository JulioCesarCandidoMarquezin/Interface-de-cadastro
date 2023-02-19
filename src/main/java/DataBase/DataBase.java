package DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataBase {

    private static Properties loadProerties(){
        try {
            FileInputStream fs = new FileInputStream("DataBase.properties");
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }
        catch (IOException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public static Connection getConnection(){
        try {
            Properties properties = loadProerties();
            String url = properties.getProperty("dburl");
            String usuario = properties.getProperty("usuario");
            String senha = properties.getProperty("senha");
            return DriverManager.getConnection(url, usuario, senha);
        }
        catch (SQLException e){
            throw new DataBaseException(e.getMessage());
        }
    }

    public static void closeConnection(Connection conexao){
        if (conexao != null){
            try {
                conexao.close();
            }
            catch (SQLException e){
                throw new DataBaseException(e.getMessage());
            }
        }
    }

    public static void cadastrarUsuario(Connection conexao, String nome, String email, Date dataNascimento, String senha) throws SQLException{
        PreparedStatement comandoSQL = conexao.prepareStatement(
                "insert into cadastro "
                        + "(nome, email, dataDeNascimento, senha) "
                        + "values "
                        + "(?,?,?,?)"
        );
        comandoSQL.setString(1, nome);
        comandoSQL.setString(2, email);
        comandoSQL.setDate(3, dataNascimento);
        comandoSQL.setString(4, senha);
        comandoSQL.executeUpdate();
    }

    public static boolean usuarioJaCadastrado(Connection conexao, String nome) throws SQLException{
        boolean usuarioCadastrado = false;
        Statement pesquisaUsuarios = conexao.createStatement();
        ResultSet resultadoDaPesquisa = pesquisaUsuarios.executeQuery("select nome from cadastro");
        while(resultadoDaPesquisa.next()){
            if(resultadoDaPesquisa.getString("nome").hashCode() == nome.hashCode()){
                if(resultadoDaPesquisa.getString("nome").equals(nome)){
                    pesquisaUsuarios.close();
                    return !usuarioCadastrado;
                }
            }
        }
        pesquisaUsuarios.close();
        return usuarioCadastrado;
    }

    public static boolean emailJaCadastrado(Connection conexao, String email) throws SQLException{
        boolean emailCadastrado = false;
        Statement pesquisaEmails = conexao.createStatement();
        ResultSet resultadoDaPesquisa = pesquisaEmails.executeQuery("select email from cadastro");
        while(resultadoDaPesquisa.next()){
            if(resultadoDaPesquisa.getString("email").hashCode() == email.hashCode()){
                if(resultadoDaPesquisa.getString("email").equals(email)){
                    pesquisaEmails.close();
                    return !emailCadastrado;
                }
            }
        }
        pesquisaEmails.close();
        return emailCadastrado;
    }
}