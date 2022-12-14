// Write a PhoneBook class that manages a phone
// directory based on a HashMap. You should be able to
// add, change, delete and show directory entries. The
// class must provide a search method, getByName(),
// that retrieve the directory information for a person,
// given the person's name.

// You must implement a PhoneBookTester class
// with a static main method to test and show the
// behavior of the previous class. You should offer a text
// based menu to show the user the different supported
// functionalities (e g. Enter 1 to add a contact, 2 to
// search, 3 change or delete a directory entry, 4 list all
// contacts.. 5 Close).

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhoneBookTester {
    // The UI: All front-end management (terminal input and output)
    PhoneBook phoneBook = new PhoneBook();
    boolean isRunning = true;

    private void nameNotFound(String name) {
        System.out.println("No name of '" + name + "' exists.");
    }
    
    private String strInput() {
        // Return input from command prompt
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String userText = input.readLine();
            if (userText.equals("")) {
                throw new IOException();
            }
            return userText;
        } catch (IOException e) {
            // This exception will be caught by the method using this helper
            return " ";
        }
    }
    private int intInput() {
        try {
            return Integer.parseInt(strInput());
        } catch (NumberFormatException e) {
            System.out.println("Please input an integer.");
            return 0;
        }
    }

    private boolean isValid(int phoneNumber) {
        // Must be non-negative with digits between 7 and 10
        if (phoneNumber < 0) {
            return false;
        }

        int numDigits = String.valueOf(phoneNumber).length();
        return (numDigits >= 7) && (numDigits <=10);
    }

    private void printInfo(String name, PersonInfo info) {
        System.out.println(name);
        System.out.println("\t First name: " + info.getFirstName());
        System.out.println("\t Last name: " + info.getLastName());
        System.out.println("\t Phone number: " + info.getPhoneNumber());
    }

    private void add() { // 1
        System.out.print("[Add] Name (key): ");
        String name = strInput();

        if (phoneBook.getDirectory().containsKey(name)) {
            System.out.println("The name '" + name + "' already exists. You can change its values with '3'.");
            return;
        }

        System.out.print("First name: ");
        String firstName = strInput();
        System.out.print("Last name: ");
        String lastName = strInput();
        System.out.print("Phone number: ");
        int phoneNumber = intInput();
        if (!isValid(phoneNumber)) {
            System.out.println("Invalid phone number: must be non-negative with 7-10 digits");
            return;
        }
        phoneBook.add(name, phoneNumber, firstName, lastName);
    }
    private void search() { // 2
        System.out.print("[Search] Name (key): ");
        String name = strInput();
        PersonInfo info = phoneBook.getByName(name);
        if (info == null) {
            nameNotFound(name);
            return;
        }
        printInfo(name, info);
    }
    private void change(String name, PersonInfo info) { // 3
        // Input fields one by one. Press "Enter" to keep old field
        String input;

        String oldFirstName = info.getFirstName();
        System.out.println("\nFirst name (Press Enter to keep the same)");
        System.out.println("\tOld: " + oldFirstName);
        System.out.print("\tNew: ");
        input = strInput();
        String firstName = (input.equals(" ")) ? oldFirstName : input;

        String oldLastName = info.getLastName();
        System.out.println("Last name (Press Enter to keep the same)");
        System.out.println("\tOld: " + oldLastName);
        System.out.print("\tNew: ");
        input = strInput();
        String lastName = (input.equals(" ")) ? oldLastName : input;

        int oldPhoneNumber = info.getPhoneNumber();
        System.out.println("Phone number (Press Enter to keep the same)");
        System.out.println("\tOld: " + oldPhoneNumber);
        System.out.print("\tNew: ");
        input = strInput();
        int phoneNumber = 0;
        if (input.equals(" ")) {
            phoneNumber = oldPhoneNumber;
        } else {
            try {
                phoneNumber = Integer.parseInt(input);
                if (!isValid(phoneNumber)) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Failed to modify '" + name + "'; please input a positive integer with 7-10 digits");
                return;
            }
    
        }

        phoneBook.change(name, phoneNumber, firstName, lastName);
    }
    private void delete(String name, PersonInfo info) { // 3
        phoneBook.delete(name);
        System.out.println("Deleted '" + name + "'");
    }
    private void listAll() { // 4
        System.out.println("");
        HashMap<String,PersonInfo> directory = phoneBook.getDirectory();
        for (String name : directory.keySet()) {
            PersonInfo info = directory.get(name);
            printInfo(name, info);
        }
    }
    private void exit() { // 5
        System.out.println("Exiting...");
        isRunning = false;
    }
    private void handleInput() {
        System.out.print("\nCommand (1,2,3,4,5): ");
        int command = intInput();
        // Catch if input is not between 1 and 5
        if (command < 1 || command > 5) {
            System.out.println("Integer must be from 1 to 5");
            return;
        }

        // Handle if directory is empty
        if ((phoneBook.getDirectory().size() == 0) && ((command == 3) || (command == 4))) {
            System.out.println("No entries to display, change, or delete. Add an entry with '1'.");
            return;
        }

        if (command == 1) {
            add();
        }
        if (command == 2) {
            search();
        }
        if (command == 3) {
            System.out.print("[Change/Delete] Name (key): ");
            String name = strInput();
            PersonInfo info = phoneBook.getByName(name);
            if (info == null) {
                nameNotFound(name);
                return;
            }
    
            System.out.print("Change or delete? (c/d): ");
            char c = Character.toLowerCase(strInput().charAt(0));
            if (c == 'c') {
                change(name, info);
            } else if (c == 'd') {
                delete(name, info);
            } else {
                System.out.println("Please input 'c' or 'd'");
                return;
            }
        }
        if (command == 4) {
            listAll();
        }
        if (command == 5) {
            exit();
            return;
        }
    }

    public PhoneBookTester() {
        // Initiate UI
        System.out.println("Welcome to my phone book software.");        
        System.out.println("Use the following commands:");
        System.out.println("1\tAdd a contact");
        System.out.println("2\tSearch for a contact");
        System.out.println("3\tChange or delete a contact");
        System.out.println("4\tList all contacts");
        System.out.println("5\tExit");

        while(isRunning) {
            handleInput();
        }
    }

    public static void main(String[] args) {
        new PhoneBookTester();
    }
}

class PhoneBook {
    // The back-end: all functionality directly manipulating directory

    // Create {name,info} pairs in HashMap 'directory'
    private HashMap<String,PersonInfo> directory = new HashMap<String,PersonInfo>();

    public PersonInfo add(String name, int phoneNumber, String firstName, String lastName) {
        // If returns null, successfully added. Else, returns already-existing element.
        return directory.putIfAbsent(name, new PersonInfo(phoneNumber, firstName, lastName));
    }
    public PersonInfo change(String name, int phoneNumber, String firstName, String lastName) {
        // If returns null, operation failed (key does not exist). Else, successfully replaced.
        return directory.replace(name, new PersonInfo(phoneNumber, firstName, lastName));
    }
    public PersonInfo delete(String name) {
        // If returns null, operation failed (key does not exist). Else, successfully deleted.
        return directory.remove(name);
    }
    public HashMap<String,PersonInfo> getDirectory() {
        return directory;
    }
    public PersonInfo getByName(String name) {
        return directory.get(name);
    }
}

class PersonInfo {
    private int phoneNumber;
    private String firstName;
    private String lastName;

    public PersonInfo(int phoneNumber, String firstName, String lastName) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
}
