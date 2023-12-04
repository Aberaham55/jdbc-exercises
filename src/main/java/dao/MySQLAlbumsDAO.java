package dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;

import java.sql.*;

public class MySQLAlbumsDAO {
    private Connection connection = null;

    // we can do simple testing using the class' main method
    public static void main(String[] args) {
        MySQLAlbumsDAO albumDao = new MySQLAlbumsDAO();

        try {
            albumDao.createConnection();

            System.out.println("Using the connection...");
            int numAlbums = albumDao.getTotalAlbums();
            System.out.println("Total # of album records: " + numAlbums);
        } catch(MySQLAlbumsException e) {
            System.out.println(e.getMessage());
        } finally {
            albumDao.closeConnection();
        }

    }

    public void createConnection() throws MySQLAlbumsException {
        System.out.printf("Trying to connect... ");
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    Config.getUrl(),
                    Config.getUser(),
                    Config.getPassword()
            );
            //TODO: create the connection and assign it to the instance variable

            System.out.println("connection created.");
        } catch (SQLException e) {
            throw new MySQLAlbumsException("connection failed!!!");
        }
    }

    public int getTotalAlbums() throws MySQLAlbumsException {
        int count = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Albums");


            //TODO: fetch the total number of albums from the albums table and assign it to the local variable
            while (resultSet.next()){
                count++;
            }

        } catch (SQLException sqlx) {
            throw new MySQLAlbumsException("Error executing query: " + sqlx.getMessage() + "!!!");
        }
        return count;
    }

    public void closeConnection() {
        if(connection == null) {
            System.out.println("Connection aborted.");
            return;
        }
        try { connection.close();
            //TODO: close the connection

            System.out.println("Connection closed.");
        } catch (SQLException sqlx) {
            // ignore this
        }
    }

}


