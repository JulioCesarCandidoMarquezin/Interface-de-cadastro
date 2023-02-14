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

    private Connection conexao = null;

    public boolean connectionIsNull(){
        return conexao == null;
    }

    private Properties loadProerties(){
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

    public Connection getConnection(){
        try {
            if(connectionIsNull()){
                Properties properties = loadProerties();
                String url = properties.getProperty("dburl");
                String usuario = properties.getProperty("usuario");
                String senha = properties.getProperty("senha");
                conexao = DriverManager.getConnection(url, usuario, senha);
            }
            else if (conexao.isClosed()) {
                conexao = null;
                getConnection();
            }
        }
        catch (SQLException e){
            throw new DataBaseException(e.getMessage());
        }
        return conexao;
    }

    public void closeConnection(){
        if (conexao != null){
            try {
                conexao.close();
            }
            catch (SQLException e){
                throw new DataBaseException(e.getMessage());
            }
        }
    }

    public void cadastrarUsuario( String nome, String email, Date dataNascimento, String senha){
        try (Connection conexao = getConnection()){
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
        catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public boolean usuarioJaCadastrado(String nome){
        try (Connection conexao = getConnection()){
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
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public boolean emailJaCadastrado(String email){
        try (Connection conexao = getConnection()){
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
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}