// A GUI to test if user-inputted text is a palindrome

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Dimension;

public class PalindromeDetectorGUI {
    final int HEIGHT = 300;
    final int BORDERLENGTH = 100;

    JFrame window;
    JLabel label;
    JLabel border1;
    JLabel border2;
    JTextField field;
    JButton button;

    public PalindromeDetectorGUI() {
        window = new JFrame("Palindrome Detector");
        label = new JLabel("Text:");
        border1 = new JLabel("");
        border1.setPreferredSize(new Dimension(BORDERLENGTH, HEIGHT));
        border2 = new JLabel("");
        border2.setPreferredSize(new Dimension(BORDERLENGTH, HEIGHT));
        field = new JTextField(30);
        button = new JButton("Is it a palindrome?");

        // Initialize window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());

        window.add(border1);
        window.add(label);
        window.add(field);
        window.add(button);
        window.add(border2);

        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                button_action();
            }
        });
        
        window.pack();
        window.setVisible(true);
    }

    // Functions
    private void button_action() {
        if (isPalindrome(field.getText())) {
            JOptionPane.showMessageDialog(null, "That's a palindrome!");
        } else {
            JOptionPane.showMessageDialog(null, "That's not a palindrome.");
        }
    }
    private void fillField(String text) {
        field.setText(text);
    }
    public boolean isPalindrome(String text) {
        // Remove space + punctiation and lower case
        text = text.replaceAll("[^a-zA-Z0-9]", "");
        text = text.toLowerCase();

        // Palindrome detection logic
        char[] charArr = text.toCharArray();
        int length = charArr.length;
        for (int i = 0; i < length/2; i++) {
            if (charArr[i] != charArr[length-i-1]) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        new PalindromeDetectorGUI().fillField("Was it a cat I saw?");;
    }
}
