// Below, I created an algorithm for randomly re-ordering an array.

const exampleArray = ['string 1', 'string 2', 'string 3', 'string 4', 'string 5', 6, 7, 8, 9, 10];
console.log('Orginal array: ' + exampleArray);

const scramble = array => {
    for (let i = 0; i < array.length; i++) {
        // This loops a number of times equal to the length of the array.
        let randomIndex = Math.floor(Math.random()*(array.length-i));
        // This chooses a random number ranging from 0 to the length of the array - i - 1. On an array with 5 elements, it would choose a random number from 0 to 4, then from 0 to 3, then from 0 to 2, and so on.
        let removed = array.splice((randomIndex),1);
        array.push(removed[0])
        // This removes a random element chosen from "randomIndex" and pushes it to the end of the array. For an array with 5 elements, that means a random element becomes the fifth element (index = 4), and since randomIndex can only pick a number from 0 to 3 on the next iteration, that element could not be chosen again. This principle applies to all iterations until each element has been randomly picked and pushed to the end exactly one time.
        }
        return array;
    }

console.log('Scrambled array: ' + scramble(exampleArray));
// The console should show all elements from the original array randomly ordered.

// The algorithm below runs 'scramble' 10000 times and sees how many times each input appears in each index
const oneToTen = [1,2,3,4,5,6,7,8,9,10];
const testScramble = testArray => {
    // The array 'results' will contain an array where the nth element of testArray is represented in the nth position of results, then results[n][i] will contain the number of times testArray[n] appears in the ith position
    let results = [];
    // This 'for' line creates empty arrays for results
    for (let i = 0; i < testArray.length; i++) {
        results.push([]);
        for (let j = 0; j < testArray.length; j++) {
            results[i].push([]);
        }
    }
    let testScrambled = testArray.slice();
    for (let i = 0; i < 1000; i++) {
        scramble(testScrambled);
        for (let j = 0; j < testScrambled.length; j++) {
            for (let k = 0; k < testScrambled.length; k++) {
                if (testArray[k] === testScrambled[j]) {
                    if (typeof results[k][j] === "number") {
                        results[k][j] = results[k][j] + 1
                    } else {
                        results[k][j] = 1;
                    }
                }
            }
        }
    }
    return results;
}

console.log(testScramble(oneToTen));
// If the 'scramble' function truly randomizes an array, this should log an array with numbers close in proximity
