// This script implements the MergeSort algorithm, an O(n*log(n)) sorting algorithm

import java.util.Arrays;

public class Mergesort {
    void sort(int[] a, boolean show_steps) {
        if (a.length <= 1) {return;}
        int[] first = new int[a.length/2];
        int[] second = new int[a.length - first.length];

        for (int i = 0; i < first.length; i++) {
            first[i] = a[i];
        }
        for (int i = second.length - 1; i >= 0; i--) {
            second[i] = a[first.length + i];
        }

        sort(first, show_steps);
        sort(second, show_steps);

        merge(first, second, a, show_steps);
    }
    void merge(int[] first, int[] second, int[] a, boolean show_steps) {
        if (show_steps) {
            System.out.println("Now merging: " + Arrays.toString(first) + " and " + Arrays.toString(second) + " into " + Arrays.toString(a));
        }

        // Merges two sorted arrays first and second into array a
        int iFirst = 0;
        int iSecond = 0;
        int j = 0;

        while ((iFirst < first.length) && (iSecond < second.length)) {
            if (first[iFirst] < second[iSecond]) {
                a[j] = first[iFirst];
                iFirst++;
            } else {
                a[j] = second[iSecond];
                iSecond++;
            }
            j++;
        }

        while (iFirst < first.length) {
            a[j] = first[iFirst];
            iFirst++;
            j++;
        }

        while (iSecond < second.length) {
            a[j] = second[iSecond];
            iSecond++;
            j++;
        }
        if (show_steps) {
            System.out.println("\tResult: " + Arrays.toString(a) + "\n");
        }
    }
    void demo(int[] a) {
        int[] a_new = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            a_new[i] = a[i];
        }

        sort(a_new, false);
        System.out.println("Orginal: " + Arrays.toString(a));
        System.out.println("  Sorted: " + Arrays.toString(a_new));
    }
    public static void main(String[] args) {
        Mergesort mergesort = new Mergesort();
        int[] a = {68, 33, 46, 20, 4, 49, 96, 48, 30, 7};
        mergesort.demo(a);
    }
}
