/*
This script receives 3 numbers as user input and informs the user whether or not these numbers
are consecutvely increasing or decreasing.

For example:
    [2,3,5] is increasing
    [5,3,2] is decreasing
    [2,3,3] is neither increasing nor decreasing

Supports upscaling comparisons to an arbitrary amount of numbers
*/

import java.util.Scanner;
import java.util.Arrays;

class growthDetector{
    // Create the Scanner instance
    static Scanner in = new Scanner(System.in);

    // Define printStatus mathod for below conciseness
    static void printStatus(String status, Double[] nums){
        String numsStr = Arrays.toString(nums);
        System.out.println("The numbers " + numsStr + " are " + status);
    }

    // Define constructor method with parameter 'interations' informing how many numbers to compare
    static void growthDetector(int iterations){
        // Return if iterations is less than 2
        if (iterations < 2){
            System.out.println("Error: Iterations < 2. Growth detector must compare at least 2 numbers");
            return;
        }

        String status = "N/A";
        Double[] nums = new Double[iterations];

        for (int i = 0; i < iterations; i++){
            // Get next number from user
            System.out.print("\nNumber " + (i+1) + ": ");
            String numStr = in.next();
            Double num = Double.parseDouble(numStr);
            nums[i] = num;
            if (i < 1){ // Don't test growth if this is the first iteration
                continue;
            }

            /* Growth detection logic:
            If current status is N/A or matches the status of last two numbers, maintain status of last two numbers.
            Else, status is neither.

            Examples:
                [1,2] is increasing. If the user puts "3", [2,3] is also increasing, therefore overall status is increasing.
                [2,1] is decreasing. If the user puts "3", [1,3] is increasing, therefore overall status is neither.
                [2] is "N/A". If the user puts "3", status is increasing. If the user puts "1", status is decreasing.
            */

            Double[] toTest = Arrays.copyOf(nums, i+1);
            if ((toTest[i] > toTest[i-1]) && (status.equals("N/A") || status.equals("increasing"))){
                status = "increasing";
            } else if ((toTest[i] < toTest[i-i]) && (status.equals("N/A") || status.equals("decreasing"))){
                status = "decreasing";
            } else {
                status = "neither increasing nor decreasing";
            }
            printStatus(status, toTest);
        }
    }

    public static void main(String args[]){
        growthDetector(3);
    }
}