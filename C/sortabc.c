// Make a function in C language that receives three arguments of integer type, a, b and
// c. The function should sort them from least to greatest. At the end of the function, a
// should have the smallest value and c the largest value. Use argument passed by
// reference.
// Write a C program that uses this function and prints the values of a, b and c before and
// after the function call.
// The program will request the data from the user.

#include <stdio.h>

void sort(int* a, int* b, int* c); // sorts a,b,c from least to greatest

int main() {
    printf("Insert integers a,b,c to sort from least to greatest.\n");

    int a,b,c;
    printf("a: ");
    scanf("%d", &a);
    printf("b: ");
    scanf("%d", &b);
    printf("c: ");
    scanf("%d", &c);

    int new_a = a;
    int new_b = b;
    int new_c = c;

    sort(&new_a, &new_b, &new_c);

    printf("\nBefore: a,b,c = (%d,%d,%d)\n", a,b,c);
    printf("After:  a,b,c = (%d,%d,%d)\n", new_a, new_b, new_c);

    return 0;
}

void sort(int* a, int* b, int* c) {
    int first, second, third;

    // Determines numerical order in 3 checks
    if (*a <= *b) {
        if (*a <= *c) {
            first = *a;
            if (*b <= *c) {
                second = *b;
                third = *c;
            } else {
                second = *c;
                third = *b;
            }
        } else {
            first = *c;
            if (*a <= *b) {
                second = *a;
                third = *b;
            } else {
                second = *b;
                third = *a;
            }
        }
    } else {
        if (*b <= *c) {
            first = *b;
            if (*a < *c) {
                second = *a;
                third = *c;
            } else {
                second = *c;
                third = *a;
            }
        } else {
            first = *c;
            if (*a < *b) {
                second = *a;
                third = *b;
            } else {
                second = *b;
                third = *a;
            }
        }
    }

    *a = first;
    *b = second;
    *c = third;
};