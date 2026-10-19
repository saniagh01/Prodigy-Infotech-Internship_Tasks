
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {

    private final List<Contact> contacts;

    private static final String FILE_NAME =
            "contacts.dat";

    public ContactManager() {

        contacts = loadContacts();
    }

    // =====================================================
    // ADD CONTACT
    // =====================================================

    public boolean addContact(Contact contact) {

    for (Contact existing : contacts) {

        if (existing.getPhone()
                .equals(contact.getPhone())) {

            return false;
        }

        if (existing.getEmail()
                .equalsIgnoreCase(contact.getEmail())) {

            return false;
        }
    }

    contacts.add(contact);

    saveContacts();

    return true;
}

    // =====================================================
    // DELETE CONTACT
    // =====================================================

    public void deleteContact(int index) {

        if (index >= 0 && index < contacts.size()) {

            contacts.remove(index);

            saveContacts();
        }
    }

    // =====================================================
    // UPDATE CONTACT
    // =====================================================

    public void updateContact(
            int index,
            Contact contact
    ) {

        if (index >= 0 && index < contacts.size()) {

            contacts.set(index, contact);

            saveContacts();
        }
    }

    // =====================================================
    // GET CONTACTS
    // =====================================================

    public List<Contact> getContacts() {

        return contacts;
    }

    // =====================================================
    // SEARCH
    // =====================================================

    public List<Contact> searchContacts(
            String keyword
    ) {

        List<Contact> results =
                new ArrayList<>();

        String search =
                keyword.toLowerCase();

        for (Contact contact : contacts) {

            if (contact.getName()
                    .toLowerCase()
                    .contains(search)

                    || contact.getPhone()
                    .contains(search)

                    || contact.getEmail()
                    .toLowerCase()
                    .contains(search)) {

                results.add(contact);
            }
        }

        return results;
    }

    // =====================================================
    // SAVE CONTACTS
    // =====================================================

    private void saveContacts() {

        try (
                FileOutputStream fileOutput =
                        new FileOutputStream(FILE_NAME);

                ObjectOutputStream objectOutput =
                        new ObjectOutputStream(fileOutput)
        ) {

            objectOutput.writeObject(contacts);

        } catch (IOException e) {

            System.err.println(
                    "Error saving contacts: "
                            + e.getMessage()
            );
        }
    }

    // =====================================================
    // LOAD CONTACTS
    // =====================================================

    @SuppressWarnings("unchecked")
    private List<Contact> loadContacts() {

        File file =
                new File(FILE_NAME);

        if (!file.exists()) {

            return new ArrayList<>();
        }

        try (
                FileInputStream fileInput =
                        new FileInputStream(file);

                ObjectInputStream objectInput =
                        new ObjectInputStream(fileInput)
        ) {

            return (List<Contact>)
                    objectInput.readObject();

        } catch (
                IOException |
                ClassNotFoundException e
        ) {

            System.err.println(
                    "Error loading contacts: "
                            + e.getMessage()
            );

            return new ArrayList<>();
        }
    }
}