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

// The algorithm below runs 'scramble' testNum times and sees how many times each input appears in each index
const testScramble = (testArray,testNum) => {
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
    for (let i = 0; i < testNum; i++) {
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

const stdevOfScramble = (testArray, testNum) => {
    let results = testScramble(testArray,testNum);
    // combines results into one array
    let resultsAll = [];
    for (let i = 0; i < results.length; i++) {
        for (let j = 0; j < results[i].length; j++) {
            resultsAll.push(results[i][j]);
        }
    }
    let mean = 0;
    for (let i = 0; i < resultsAll.length; i++) {
        mean = mean + resultsAll[i];
    }
    mean = mean/resultsAll.length;
    let stDev = 0;
    for (let i = 0; i < resultsAll.length; i++) {
        let varianceI = resultsAll[i] - mean;
        varianceI = varianceI^2;
        stDev = stDev + varianceI;
    }
    stDev = stDev/resultsAll.length;
    stDev = stDev^0.5
    return stDev;
}

const oneToTen = [1,2,3,4,5,6,7,8,9,10];
console.log(testScramble(oneToTen,10000));
// If the 'scramble' function truly randomizes an array, this should log an array with numbers close in proximity

console.log(stdevOfScramble(oneToTen,10000));
// this gives the standard deviation of the above array, which should be small