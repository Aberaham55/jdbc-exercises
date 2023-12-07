package contacts_manager.dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import contacts_manager.models.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLContactsDAO implements ContactsDAO {

    protected Connection connection = null;
    public MySQLContactsDAO() {
        open();
    }
    public  void open (){
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
    public void close () {
        System.out.println("closing db connection");
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException sqlx){
                System.out.println(sqlx.getMessage());
            }
        }
    }

    @Override
    public List<Contact> fetchContacts() {
            List<Contact> contacts = new ArrayList<>();

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts");
                while (resultSet.next()){
                    Contact contact = new Contact(resultSet.getString("name"),
                            resultSet.getString("phone"));
                    contact.setId(resultSet.getLong("ID"));
                    contacts.add(contact);
                }
            } catch (SQLException sqlx) {
                System.out.println(sqlx.getMessage());
            }

            return contacts;

    }

    @Override
    public long insertContact(Contact contact) throws MySQLAlbumsException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("insert into contacts " + "(name, phone) " + "values (?,?)", statement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getFullName());
            statement.setString(2, contact.getPhoneNumber());

            int numInserted = statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            return contact.getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteByName(String name) throws MySQLAlbumsException{
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("DELETE FROM contacts " + "WHERE name = ? ");
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }


    }

    @Override
    public List<Contact> searchContacts(String searchTerm) throws MySQLAlbumsException {
        PreparedStatement st = null;
        List<Contact> contacts = new ArrayList<>();
        try {
            st = connection.prepareStatement("SELECT * FROM contacts WHERE name LIKE ? ");
            st.setString(1, "%" + searchTerm + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                contacts.add(new Contact( rs.getString("name"),
                rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return contacts;
    }




}
