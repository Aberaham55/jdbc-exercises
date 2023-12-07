package contacts_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public interface ContactsDAO {
    List<Contact> fetchContacts();
    long insertContact(Contact contact);
    void deleteByName(String name);
    List<Contact> searchContacts(String searchTerm);
    void open();
    void close();
}
