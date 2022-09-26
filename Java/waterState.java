/*
waterState accepts a number and letter (f or c) from user. The number is temperature
and the letter is fahrenheit or celcius. The program then tells the user whether water
would be solid, liquid, or gaseous at this temperature
*/

import java.util.Scanner;
import java.lang.Character;

class waterState{
    public static void main(String args[]){
        // Get temperature number and measure from user
        Scanner in = new Scanner(System.in);
        System.out.print("Temperature? (number): ");
        String tempStr = in.next();
        Double temp = Double.parseDouble(tempStr);
        System.out.print("Fahrenheit or Celcius? (f/c): ");
        String measure = in.next();
        char m = Character.toLowerCase(measure.charAt(0));

        // Convert temperature to Celcius
        if (m == 'f'){
            temp -= 32;
            temp *= 5;
            temp /= 9;
        } else if (m != 'c'){
            System.out.println("Please input 'f' or 'c'");
            System.exit(0);
        }

        // Print whether water is solid, liquid, or gaseous
        String state = "liquid";
        if (temp <= 0){
            state = "solid";
        }
        if (temp >= 100){
            state = "gaseous";
        }
        System.out.println("Water is " + state);
        }
       }      