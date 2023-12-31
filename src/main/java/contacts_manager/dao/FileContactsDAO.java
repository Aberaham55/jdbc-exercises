package contacts_manager.dao;

import contacts_manager.models.Contact;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileContactsDAO implements ContactsDAO {
    private Path dataFile;
    public FileContactsDAO() {
        this.dataFile = Paths.get("data", "contacts.txt");
    }
    @Override
    public List<Contact> fetchContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            List<String>  lines = Files.readAllLines(dataFile);
            for (String line : lines) {
                String[] info = line.split("\\|");
                Contact contact  = new Contact( info[0], info[1]);
                contacts.add(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;

    }

    @Override
    public long insertContact(Contact contact) {
        String contactLine = String.format("%s|%s", contact.getFullName(), contact.getPhoneNumber());
        try {
            Files.write(
                    dataFile,
                    Arrays.asList(contactLine), // list with one item
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteByName(String name) {
        try {
            List<String> lines = Files.readAllLines(dataFile);
            List<String> writeLines = new ArrayList<>();

            for (String line: lines) {
                if(!line.toUpperCase().startsWith(name.toUpperCase())){
                    writeLines.add(line);
                }
            }

            Files.write(dataFile, writeLines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Contact> searchContacts(String searchTerm) {
        try {
            List<String> lines = Files.readAllLines(dataFile);
            for (String line: lines) {
                if(line.toLowerCase().startsWith(searchTerm.toLowerCase())){
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      return null;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
}
