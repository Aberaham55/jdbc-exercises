package contacts_manager;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Input input = new Input();
        ContactsDAO dao = new MySQLContactsDAO();
        while(true){
            System.out.println("1. View contacts.\n" +
                    "2. Add a new contact.\n" +
                    "3. Search a contact by name.\n" +
                    "4. Delete an existing contact by name.\n" +
                    "5. Exit.\n" +
                    "Enter an option (1, 2, 3, 4 or 5):");
            int option = input.getInt(1, 5);
            switch (option){
                case 1:
                   List<Contact> contacts = dao.fetchContacts();
                    System.out.println(contacts);
                    break;

                case 2:
                    String fn = input.getString("Give me the full name");
                    String phone = input.getString("Give me the phone number");
                    contacts_manager.Contact contact = new contacts_manager.Contact(fn, phone);
                    dao.insertContact(contact);
                    break;
                case 3:
                    String term = input.getString("Give me the name to search");
                    List<Contact> contacts1 = dao.searchContacts(term);
                    System.out.println(contacts1);
                    break;
                case 4:
                    String aNumber = input.getString("Give me the name to delete");
                    dao.deleteByName(aNumber);
                    break;
                case 5:
                    dao.close();
                    System.exit(0);
            }

        }


    }
}
