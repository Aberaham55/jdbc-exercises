package contacts_manager.dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAO {
    protected Connection connection = null;

    public  void createConnection (){
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    Config.getUrl(),
                    Config.getUser(),
                    Config.getPassword()
            );
        } catch (SQLException sqlx){
            System.out.println("error connecting" + sqlx.getMessage());
        }
    }
    public void closeConnection () {
        System.out.println("closing db connection");
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException sqlx){
                System.out.println(sqlx.getMessage());
            }
        }
    }
}
