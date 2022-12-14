// It is necessary to store information about animals to manage a veterinary clinic, it is
// assumed that the clinic will manage at most 100 animals. Develop an application that
// maintains an array of struct: “animal”.
// For each animal it is necessary to know:
// - Type of animal (dog, cat, etc.)
// - Name
// - Age
// - Owner's name
// Your C program will display one animal at a time (printing the fields on the screen).
// You should have commands -a text menu- for displaying the next animal (e.g entering
// “1”), displaying the previous animal (entering “2”), adding a new animal (“3”),
// removing the displayed animal (“4”) or exit (“5”). When adding a new animal, the user
// enters the data. After removing an animal, the next animal is displayed.

#include <stdio.h>
#include <string.h>
#define MAX_ANIMALS 100

// --------------------------------------------------------
// Constants ----------------------------------------------
// --------------------------------------------------------

const int false = 0;
const int true = 1;


// --------------------------------------------------------
// Variables ----------------------------------------------
// --------------------------------------------------------

int numOfAnimals = 0;
int currentIndex = -1; // Current index in animal list, starting at 0
int isRunning = true;


// --------------------------------------------------------
// Structs ------------------------------------------------
// --------------------------------------------------------

struct animal {
    char type[30]; // dog, cat, etc.
    char name[30];
    int age;
    char ownerName[50]; // longer to include last name
};

struct animal animals[MAX_ANIMALS];
struct animal emptyAnimal = {"","",0,""};


// --------------------------------------------------------
// Functions ----------------------------------------------
// --------------------------------------------------------

// First to run
void initiate();

// Print current status of clinic
void displayStatus();

// Prints information about given animal
void displayAnimal(struct animal);

// Get user input (1,2,3,4,5) and do something
void handleInput();

// Commands (1,2,3,4,5)
void next(); // 1
void previous(); // 2
void addNew(); // 3
void removeCurrent(); // 4
void exitSys(); // 5


int main() {    
    initiate();
    while (isRunning) {
        handleInput();
    }
    return 0;
};


// --------------------------------------------------------
// Animal functions ---------------------------------------
// --------------------------------------------------------

void displayAnimal(struct animal a) {
   printf("%s the %s\n", a.name, a.type);
   printf("\tName: %s\n", a.name);
   printf("\tType: %s\n", a.type);
   printf("\tAge: %d\n", a.age);
   printf("\tOwner name: %s\n", a.ownerName);
};


// --------------------------------------------------------
// Main functions -----------------------------------------
// --------------------------------------------------------

void initiate() {
    printf("\nWelcome to my veterinary clinic.\n");
    printf("Use the following commands:\n");
    printf("1\tDisplay the next animal\n");
    printf("2\tDisplay the previous animal\n");
    printf("3\tAdd a new animal\n");
    printf("4\tRemove the current animal\n");
    printf("5\tExit\n");
};

void displayStatus() {
    if (numOfAnimals == 0) {
        printf("\nCurrent animal: 0/0\n");
        return;
    };
    printf("\nCurrent animal: %d/%d\n", currentIndex+1, numOfAnimals);
};

void handleInput() {
    int command;

    // Get command (1,2,3,4,5)
    printf("\nCommand (1,2,3,4,5): ");
    scanf("%d", &command);

    // Catch if input is not between 1 and 5
    if (command < 1 || command > 5) {
        printf("Please input an integer from 1 to 5.\n");
        return;
    }
    // Handle if publication list is empty
    if ((numOfAnimals == 0) && !((command == 3) || (command == 5))) {
        printf("No animals to display or delete. Add an animal with '3'.\n");
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
        exitSys();
        return;
    }
    // Unless we have exited, always display the status
    displayStatus();
    if (numOfAnimals == 0) {
        printf("No animals to display. Add an animal with '3'\n");
    } else {
        // Display current animal if there is one
        displayAnimal(animals[currentIndex]);
    };
}

void next() {
    if (currentIndex >= numOfAnimals-1) {
        // e.g., if we have 5 animals and are on index 4, go back to first animal
        currentIndex = 0;
    } else {
        currentIndex++;
    }
};
void previous() {
    if (currentIndex == 0) {
        currentIndex = numOfAnimals-1;
    } else {
        currentIndex--;
    }
};
void addNew() {
    // Catch if we have reached the max of 100 animals
    if (numOfAnimals >= MAX_ANIMALS) {
        printf("Cannot add a new animal: maximum of 100 animals is allowed\n");
        return;
    }

    // Get animal fields from user
    struct animal a;
    
    printf("\nType? (i.e. dog, cat): ");
    scanf(" %[^\n]*c", a.type);

    printf("Name?: ");
    scanf(" %[^\n]*c", a.name);

    printf("Age?: ");
    scanf("%d", &a.age);

    printf("Owner name?: ");
    scanf(" %[^\n]*c", a.ownerName);

    // Shift animals to the right
    for (int i = numOfAnimals-1; i > currentIndex; i--) {
        animals[i+1] = animals[i];
    }

    currentIndex++;
    numOfAnimals++;
    animals[currentIndex] = a;
};
void removeCurrent() {
    numOfAnimals--;

    // Shift animals to the left
    for (int i = currentIndex; i < numOfAnimals-1; i++) {
        animals[i] = animals[i+1];
    }

    // If index is at the end, move back
    if (currentIndex == numOfAnimals) {
        currentIndex--;
    }
    
    // Delete last element
    animals[numOfAnimals] = emptyAnimal;
};
void exitSys() {
    printf("Exiting...\n");
    isRunning = false;
};

