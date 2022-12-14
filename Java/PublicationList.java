// CS 2100 Lab Assignment:
    // Develop an application that maintains a LinkedList of publications
    // (books or journals). The class Book has a book number, title, and
    // author. The class Journal has a journal number, title and editorial.
    // Your program displays one publication at a time (printing these
    // attributes on the screen). You should have commands for displaying
    // the next publication (e.g entering "1"), displaying the previous
    // publication (entering "2"), adding a new publication ("3"), removing
    // the displayed publication ("4") or exit ("5"). When adding a new
    // publication, the user enters the data. After removing a publication,
    // the next publication is displayed

import java.util.LinkedList;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PublicationList {
    // Define LinkedList of publications, its iterator, and flag for whether program is running
    private LinkedList<Publication> publications = new LinkedList<Publication>();
    private ListIterator<Publication> iter = publications.listIterator();
    private boolean isRunning = true;

    // Helper methods:
    private Publication getPrev() {
        // Returns previous Publication without going backwards
        iter.previous();
        return iter.next();
    }
    private void displayPrev() {
        // Display previous Publication without going backwards, handling empty list
        if (publications.size() == 0) {
            System.out.println("No publications to display. Add a publication with '3'");
            return;
        }
        getPrev().display();
    }
    private String userInput() {
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

    // Methods from commands (1,2,3,4,5):
    private void next() { // "1"
        if (!iter.hasNext()) {
            // If we're at the end of the list, go to the beginning
            while (iter.hasPrevious()) {
                iter.previous();
            }
        }
        iter.next();
    }
    private void previous() { // "2"
        iter.previous();
        if (!iter.hasPrevious()) {
            // If we are at the beginnning of the list, go to the end:
            while (iter.hasNext()) {iter.next();}
        }
    }
    private int retrieveNumber(char pt) {
        // Helper for addNew() ("3")
        String pubType = pt == 'b' ? "Book" : "Journal";
        System.out.print(pubType + " number?: ");
        String numStr = userInput();
        int number = 0;
        try {
            number = Integer.parseInt(numStr);
            if (number < 1) {
                throw new NumberFormatException("Input less than 1");
            }
        } catch (NumberFormatException e) {
            System.out.println("Failed to create " + pubType.toLowerCase() + "; " + pubType + " number must be a positive integer");
        }
        return number;
    }
    private void addNew() { // "3"
        System.out.print("\nBook or Journal? (b/j): ");
        String pubType = userInput();
        char pt = Character.toLowerCase(pubType.charAt(0));
        if ((pt != 'b') && (pt !='j')) {
            System.out.println("Failed to create publication; Input must be 'b' or 'j'.");
            return;
        }
        int number = retrieveNumber(pt);
        if (number == 0) {return;} // This means bad user input
        if (pt == 'b') {
            System.out.print("Book title?: ");
            String title = userInput();
            System.out.print("Book author?: ");
            String author = userInput();
            iter.add(new Book(number, title, author));
        } else {
            System.out.print("Journal title?: ");
            String title = userInput();
            System.out.print("Journal editorial?: ");
            String editorial = userInput();
            iter.add(new Journal(number, title, editorial));
        }
    }
    private void removeCurrent() { // "4"
        iter.remove();
        if (iter.hasNext()) {
            iter.next();
        }
    }
    private void exit() { // "5"
        System.out.println("Exiting...");
        isRunning = false;
    }

    // Methods used by PublicationList constructor:
    private void initiate() {
        System.out.println("Welcome to my publication storage software.");        
        System.out.println("Use the following commands:");
        System.out.println("1\tDisplay the next publication");
        System.out.println("2\tDisplay the previous publication");
        System.out.println("3\tAdd a new publication");
        System.out.println("4\tRemove the current publication");
        System.out.println("5\tExit");
    }
    private void displayStatus() {
        int size = publications.size();
        if (size == 0) {
            System.out.println("\nCurrent publication: 0/0");
            return;
        }
        System.out.println("\nCurrent publication: " + (iter.previousIndex()+1) + "/" + size);
    }
    private void handleInput() {
        System.out.print("\nCommand (1,2,3,4,5): ");
        String cmdStr = userInput();
        int command = 0;
        // Catch if input is not int between 1 and 5
        try {
            command = Integer.parseInt(cmdStr);
            if (command < 1 || command > 5) {
                throw new NumberFormatException("Integer must be from 1 to 5");
            }
            } catch (NumberFormatException e) {
                System.out.println("Please input an integer from 1 to 5.");
                return;
            }
        // Handle if publication list is empty
        if ((publications.size() == 0) && !((command == 3) || (command == 5))) {
            System.out.println("No publications to display or delete. Add a publication with '3'.");
            return;
        }

        if (command == 1) {
            next();
        }
        if (command == 2) {
            previous();
        }
        if (command == 3) {
            addNew();
        }
        if (command == 4) {
            removeCurrent();
        }
        if (command == 5) {
            exit();
            return;
        }
        // Unless we have exited, always display the status and previous element:
        displayStatus();
        displayPrev();
    }
    public PublicationList() {
        initiate();
        while (isRunning) {
            handleInput();
        }
    }
    public static void main(String[] args) {
        new PublicationList();
    }
}

// Class definitions:
abstract class Publication {
    protected int number;
    protected String title;
    protected void display() {
        System.out.println("\tNumber:\t" + number);
        System.out.println("\tTitle:\t" + title);
    }
    public Publication(int number, String title) {
        this.number = number;
        this.title = title;
    }
}
class Book extends Publication {
    private String author;
    public Book(int number, String title, String author) {
        super(number, title);
        this.author = author;
    }
    public void display() {
        System.out.println("[Book] " + title + " by " + author);
        super.display();
        System.out.println("\tAuthor:\t" + author);
    }
}
class Journal extends Publication {
    private String editorial;
    public Journal(int number, String title, String editorial) {
        super(number, title);
        this.editorial = editorial;
    }
    public void display() {
        System.out.println("[Journal] " + title);
        System.out.println("\tNumber:\t\t" + number);
        System.out.println("\tTitle:\t\t" + title);
        System.out.println("\tEditorial:\t" + editorial);
    }
}