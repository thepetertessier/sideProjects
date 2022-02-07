// My approach was to test if the first letter of a given string was equal to the last letter of that string, then test if the second letter was equal to the second to last letter, and so on, until the test returned false. If the test always returned true, then the string would be a palindrome. If it returned false at least once, then the string would not be a palindrome.
// For this code, I assumed the function would not be case sensitive but would be sensitive of spaces and punctuation.
function isPalindrome(string) {
    if (typeof string === "string") {
        let palindrome = true;
        // The code establishes the variable "palindrome" as "true" so the loop can initiate.
        for (let i = 0; i < string.length/2 && palindrome === true; i++) {
          // The 'i < string.length/2' means that it will loop a number of times equal to half the total length of the string, since the first half of the string should be compared to the second half no more than one time. The 'palindrome === true' means that the loop will stop running if 'palindrome' is ever returned as 'false', since the string cannot be a palindrome if any of the letters do not meet the condition. These both reduce computing time.
          let letter1 = string[i].toLowerCase();
          let letter2 = string[string.length-i-1].toLowerCase();
          // The "toLowerCase" method ensures that the code is not case sensitive.
          if (letter1 === letter2) {
            // This incrementally tests if each letter on the left mirrors each letter on the right, starting from the end and coming to the middle.
            palindrome = true;
          } else {
            palindrome = false;
          }
        }
        return palindrome;
        // If the test ever creates a 'false' value, the loop would break while 'palindrome' still equals 'false', meaning the string is not a palindrome. Otherwise, 'palindrome' would remain true throughout the whole string, meaning the string is a true palindrome.    
    } else {
        return 'Please input a string.';
        // This accounts for cases where the input is not a string, where the code is not intended to run.
    }
  }
  
// Some simple examples of working code:
console.log(isPalindrome('not a palindrome'));
// Expected output: false
console.log(isPalindrome('tacocat'));
// Expected output: true
console.log(isPalindrome('Bob'));
// Expected output: true

// The script only works with strings:
console.log(isPalindrome(1234321));
// Expected output: 'Please input a string.'

//The script does not omit spaces:
console.log(isPalindrome('A Santa at NASA'));
// Expected output: false


// Here is a function to test an array of strings:
const palindromeArrayTest = array => {
    let output = [];
    for (let i = 0; i < array.length; i++) {
        output.push(isPalindrome(array[i]));
        // This creates an array, "output", that contains the results of the isPalindrome function corresponding to their original index
    }   
    return output;
}

// Here is an array of only palindromes:
const palindromes = ["aha","aha","Anna","bib","bob","civic","dad","deed","did","dud","ere","eve","ewe","eye","gag","hah","Hannah","kayak","level","madam","mom","mum","noon","nun","Otto","peep","pep","pop","racecar","radar","refer","sees","solos","SOS","toot","tot","wow"];
console.log(palindromeArrayTest(palindromes));
// Expected output: [true, true, true,...]

// Here is an array of non-palindromes:
const nonPalindromes = ["about","all","also","and","as","at","be","because","but","by","can","come","could","day","do","even","find","first","for","from","get","give","go","have","he","her","here","him","his","how","I","if","in","into","it","its","just","know","like","look","make","man","many","me","more","my","new","no","not","now","of","on","one","only","or","other","our","out","people","say","see","she","so","some","take","tell","than","that","the","their","them","then","there","these","they","thing","think","this","those","time","to","two","up","use","very","want","way","we","well","what","when","which","who","will","with","would","year","you","your"]
console.log(palindromeArrayTest(nonPalindromes));
// Expected output: [false, false, false,...]