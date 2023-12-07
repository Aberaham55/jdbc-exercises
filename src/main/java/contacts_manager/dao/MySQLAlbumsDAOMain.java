package contacts_manager.dao;

import contacts_manager.models.Album;

import java.util.List;

public class MySQLAlbumsDAOMain extends MySQLDAO{

    public static void main(String[] args) {
        MySQLAlbumsDAO albumsDAO= new MySQLAlbumsDAO();

        try {
            albumsDAO.createConnection();

            System.out.println("Using the connection...");
            int numAlbums = albumsDAO.getTotalAlbums();
            System.out.println("Total # of album records: " + numAlbums);

            // fetch all albums and print the size
            // this should equal the above Total # of album records:
            List<Album> albums = albumsDAO.fetchAlbums();
            System.out.println("Number of album records: " + albums.size());

            // fetch and print a single album (the album with id 1 in the db)
            Album album = albumsDAO.fetchAlbumById(1L);
            System.out.println("Album with id 1: " + album);

            // insert a new album
            Album newAlbum = new Album();
            newAlbum.setName("Wish You Were Hear");
            newAlbum.setArtist("Pink Floyd");
            newAlbum.setReleaseDate(1975);
            newAlbum.setGenre("Progressive Rock");
            newAlbum.setSales(9.2);
            long newId = albumsDAO.insertAlbum(newAlbum);
            System.out.println("Id for new album: " + newId);
            newAlbum.setId(newId);

            // fix a mistake in the new album
            newAlbum.setName("Wish You Were Here");
            albumsDAO.updateAlbum(newAlbum);

            // let's confirm that the update worked
            album = albumsDAO.fetchAlbumById(newId);
            System.out.println("Fixed album: " + album);

            // delete the album
            albumsDAO.deleteAlbumById(newId);
            System.out.println("Album with id " + newId + " deleted");

        } catch(MySQLAlbumsException e) {
            System.out.println(e.getMessage());
        } finally {
            albumsDAO.closeConnection();
        }

    }
}
