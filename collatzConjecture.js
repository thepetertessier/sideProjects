// The Collatz conjecture is one of the simplest-to-understand unsolved problems in math. I created an algorithm to see if I could make any progress.

// These two functions (checking if a number is odd or even) are necessary for the Collatz conjecture's algorithm.
function isEven(n) {
  return n % 2 == 0;
}
function isOdd(n) {
  return n % 2 == 1;
}

// The following algorithm defines the process of the Collatz conjecture. 'seed' is the number put into the algorithm and 'step' contains the number of steps it takes before the number reaches one.
const collatz = seed => {
  let step = 0
  while (seed != 1) {
    if (isEven(seed)) {
        seed = seed/2;
      } else {
        if (isOdd(seed)) {
        seed = seed*3 + 1;
        }
      }
    step++;
  }
  return step
}

// The 'calcUntil' function calls on the 'collatz' function for all natural numbers from 1 to 'lastSeed'. It records the results of 'collatz' in an array 'steps', where the number of steps for a given number n is stored in steps[n].
const calcUntil = lastSeed => {
  const steps = [0];
  for (let i = 1; i <= lastSeed; i++) {
      let step = collatz(i);
      steps.push(step);
  }
  return steps;
}

// The following algorithm sorts the numbers from 1 to finalNum in order of how many steps it took them to reach 1 in the collatz algorithm, storing them in 'stepsArray'. A number that took an n number of steps to reach 1 can be found in stepsArray[n].
const stepsSorted = [];
// stepsArray sets up the sorting by first calculating the steps for each number until finalNum.
let finalNum = 10000;
const stepsArray = calcUntil(finalNum);
for (let i = 0; i < stepsArray.length; i++) {
  // arrayI is a bin for all the numbers that took i steps to reach 1
  let arrayI = [];
  // All the numbers j that took i steps to reach i are put into ArrayI
  for (let j = 0; j < stepsArray.length; j++) {
    if (stepsArray[j] === i) {
      arrayI.push(j);
    }
  }
  // Finally, arrayI is pushed into stepsSorted to produce an array of arrays, where stepsSorted[n] contains all the numbers that took n steps to reach 1.
  stepsSorted.push(arrayI);
}

//Now we can plot the size of stepsSorted[n] vs stepsSorted[n] to see if there is a pattern:
const stepsSortedSize = [];
for (let i = 0; i < stepsSorted.length; i++) {
  stepsSortedSize.push(stepsSorted[i].length);
  console.log(`${i}: ${stepsSortedSize[i]}`);
}


// The following are miscellaneous tests
const allEvens = [];
for (let i = 0; i < 500; i = i+2) {
  allEvens.push(i);
}
const evenTest = [];
for (let i = 0; i < allEvens.length; i++) {
  let alg = allEvens[i] * 3 + 1;
  evenTest.push(alg)
}
const evenSteps = []
for (let i = 0; i < evenTest.length; i++) {
  let evenStep = collatz(evenTest[i]);
  evenSteps.push(evenStep);
}
