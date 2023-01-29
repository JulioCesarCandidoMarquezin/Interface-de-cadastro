package DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBase {

    private static Connection connection = null;
    public static Connection getConnection(){
        if(connection == null){
            try {
                Properties properties = loadProerties();
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");
                String url = properties.getProperty("dburl");
                connection = DriverManager.getConnection(url, user, password);
            }
            catch (SQLException e){
                throw new DataBaseException(e.getMessage());
            }
        }
        return connection;
    }
    public static void closeConnection(){
        if (connection != null){
            try {
                connection.close();
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
}