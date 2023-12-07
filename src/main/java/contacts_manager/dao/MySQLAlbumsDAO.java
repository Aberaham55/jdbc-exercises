package contacts_manager.dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import contacts_manager.models.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        System.out.print("Trying to connect... ");
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

    public List<Album> fetchAlbums() throws MySQLAlbumsException {
        List<Album> albums = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Albums");
            while (resultSet.next()){
            albums.add(new Album(
                    resultSet.getLong("id"),
                    resultSet.getString("artist"),
                    resultSet.getString("name"),
                    resultSet.getInt("release_date"),
                    resultSet.getDouble("sales"),
                    resultSet.getString("genre")
            ));

        }
        } catch (SQLException sqlx) {
            System.out.println(sqlx.getMessage());
        }

        return albums;
    }

    public Album fetchAlbumById(long id) {
        Album album = null;
        try {
            PreparedStatement st = connection.prepareStatement("select * from Albums " +
                    " where id = ? ");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();

            album = new Album();
            album.setId(rs.getLong("id"));
            album.setArtist(rs.getString("artist"));
            album.setName(rs.getString("name"));
            album.setReleaseDate(rs.getInt("release_date"));
            album.setSales(rs.getDouble("sales"));
            album.setGenre(rs.getString("genre"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // TODO: write your code here

        return album;
    }

    // Note that insertAlbum should return the id that MySQL creates for the new inserted album record
    public long insertAlbum(Album album) throws MySQLAlbumsException {
        long id = 0;
        PreparedStatement st = null;
        try {
          st = connection.prepareStatement("INSERT into Albums" +
                    "(artist, name, release_date, sales, genre) " +
                    "values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, album.getArtist());
            st.setString(2, album.getName());
            st.setLong(3, album.getReleaseDate());
            st.setDouble(4, album.getSales());
            st.setString(5, album.getGenre());

            int numInserted = st.executeUpdate();

            ResultSet keys = st.getGeneratedKeys();
            keys.next();

          id = keys.getLong(1);
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAlbum(Album album) throws MySQLAlbumsException {
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE Albums" +
                    " set artist = ? " +
                    " , name = ? " +
                    ", release_date = ?" +
                    ", sales = ?" +
                    ", genre = ?" +
                    " where id = ? ");
            st.setString(1, album.getArtist());
            st.setString(2, album.getName());
            st.setLong(3, album.getReleaseDate());
            st.setDouble(4, album.getSales());
            st.setString(5, album.getGenre());
            st.setLong(6, album.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






    public void deleteAlbumById(long id) throws MySQLAlbumsException {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("DELETE FROM Albums where ID = ?");
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

}


