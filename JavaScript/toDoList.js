// This to-do list maker sets out to create the following:
    // A compilation of To Do items with properties of title, description, date created, date due, and its status (New, Working on, Finished).
    // A way to add and delete items
    // A way to reorganize the list (i.e. bring one to top, send down 1, etc)
    // A way to edit information about each task
    // A way to view deleted items
 
// The way to use the code below is specified after the script and before the examples.
// Whenever the variable 'ind' is used, it refers to the index of an item on the 'items' array starting at 1, not 0. This is for the convenience of the user, so that when referring to an item, they can simply look at the number it is listed under in the display.
 
const toDoListFactory = (_listTitle,_owner) => {
  return {
      _listTitle,
      _owner,
      items: [],
      createItem(title) {
          let newItem = itemFactory(title);
          this.items.push(newItem);
          // uses the itemFactory specified below to create a new item and push it into the 'items' array
      },
      removedItems: [],
      // In case someone accidentally deleted an item, it can be retrieved from removedItems
      finishedItems: [],
      // By dedicating an array to finished items, one can toggle between showing finished tasks and not showing them when this.display is called.
      _showFinished: true,
      set showFinished(parameter) {
          if (typeof parameter === 'boolean') {
              this._showFinished = parameter
          } else {
              console.log('Could not change showFinished; please enter a boolean value');
          }
      },
      get showFinished() {
          return this._showFinished
      },
      deleteItem(ind, finished) {
          // This method deletes the 'ind'th item
          if (ind > 0 && ind <= this.items.length && finished === false && this.items[ind-1].status != 'Finished') {
              this.removedItems.push(this.items.splice(ind-1,1));
              // Not only is the item spliced, but pushed into a new array, 'removedItems,' in order to save the data.
              Object.assign(this.removedItems[this.removedItems.length-1][0], {
                  dateRemoved: Date()
                  // This records the date that the item got removed
              });
              this.removedItems[this.removedItems.length-1][0].status = 'Deleted'    
              // ensures that the item gains the 'Deleted' status
          } else {
              if (ind > 0 && ind <= this.finishedItems.length && finished === true && this.finishedItems[ind-1].status === 'Finished') {
                  // same as before but for finishedItems
                  this.removedItems.push(this.finishedItems.splice(ind-1,1));
                  this.finishedItems.length += -1;
                  Object.assign(this.removedItems[this.removedItems.length-1], {
                      dateRemoved: Date()
                  });
                  this.removedItems[this.removedItems.length-1][0].status = 'Deleted'    
              } else {
                  console.log('Error: cannot remove an item that doesn\'t exist. Make sure you are deleting it from the correct array.')
              }
          }
      },
      finishItem(ind) {
          // this function marks an item as 'Finished' and moves it to the finishedItems array
          if (ind > 0 && ind <= this.items.length) {
          this.items[ind-1].status = 'Finished';
          let removedItem = this.items.splice(ind-1,1);
          this.finishedItems.push(removedItem);
          // This moves the item into the finishedItems array
          } else {
              console.log('Error: cannot remove item that doesn\'t exist')
              // this error takes into account if the user put in an 'ind' value that doesn't correspond to any item
          }
      },
      setDescription(ind,descrip) {
          this.items[ind-1].description = descrip
          // this calls the setter function specified under itemFactory
      },
      setDueDate(ind,YYYYMMDD) {
          this.items[ind-1].dueDate = YYYYMMDD
          // this also calls the setter function specified under itemFactory
      },
      setTitle(ind,t) {
          this.items[ind-1].title = t
          // also calls the setter function specified under itemFactory
      },
      moveItem(ind,newInd) {
          ogItem = this.items.splice(ind-1,1);
          this.items.splice(newInd-1,0,ogItem[0])
          // The this.items.splice function takes the original item specified at ind-1 and puts it in at the new index, newInd-1. It puts in ogItem[0] and not ogItem because ogItem is a one-element array
      },

      get display() {
          // This method is responsible for displaying the To-Do list. If you want the order of how things are listed to change, it can be done relatively easy by changing the order of this script. It includes indenting for legibility.
          console.log(_listTitle);
          console.log(`Owner: ${_owner}`);
          console.log('    Items:')
          if (this.items.length < 1) {
              console.log('No items yet')
              // It will log 'No items yet' if no items exist because the below function would bug out otherwise
          } else {
              for (let i = 0; i < this.items.length; i++) {
                  // below are listed the formatted title, status, dateCreated, description, and dueDate of each item
                  console.log('        '+ `${i+1}. ` + this.items[i]._title);
                  console.log('           Status: ' + this.items[i].status);
                  console.log('           Date created: ' + this.items[i]._dateCreated);
                  if (this.items[i].description) {
                      // does not log it if it doesn't exist
                      console.log('           Description: ' + this.items[i].description);
                  }
                  if (this.items[i].dueDate) {
                      console.log('           Due date: ' + this.items[i].dueDate);
                  }
              }    
          }
          // finishedItems are shown next if specified by showFinished:
          if (this.showFinished && this.finishedItems.length) {
              console.log('    Finished Items:');
              for (let i = 0; i < this.finishedItems.length; i++) {
                  console.log('        '+ `${i+1}. ` + this.finishedItems[i][0].title);
                  console.log('           Status: ' + this.finishedItems[i][0].status);
                  console.log('           Date created: ' + this.finishedItems[i][0].dateCreated);
                  if (this.finishedItems[i][0].description) {
                      console.log('           Description: ' + this.finishedItems[i][0].description);
                  }
                  if (this.finishedItems[i][0].dueDate) {
                      console.log('           Due date: ' + this.finishedItems[i][0].dueDate);
                  }
              }    
          }
      },
      get displayDeleted() {
          // the user may display the deleted items with displayDeleted
          console.log('Deleted:');
          for (let i = 0; i < this.removedItems.length; i++) {
              console.log('    '+ `${i+1}. ` + this.removedItems[i][0].title);
              console.log('       Status: ' + 'Deleted');
              console.log('       Date created: ' + this.removedItems[i][0].dateCreated);
              if (this.removedItems[i][0].description) {
                  console.log('       Description: ' + this.removedItems[i][0].description);
              };
              if (this.removedItems[i][0].dueDate) {
                  console.log('       Due date: ' + this.removedItems[i][0].dueDate);
              };
              console.log('       Date removed: ' + this.removedItems[i][0].dateRemoved)
          }
      }
      // when displaying both finishedItems and removedItems, it is technically extracting the first element of each array in removedItems and finishedItems because both are technically an array of one-element arrays

  }
};

// the function responsible for making items:
const itemFactory = _title => {
  return {
      _title,
      get title() {
          return _title
      },
      set title(titl) {
          this._title = titl
      },
      _dateCreated: Date(),
      get dateCreated() {
          return this._dateCreated
      },
      _oneDayLater: Date.now() + 86400000,
      // adds 24 hours to current date to set up the 'Working On' status
      _description: undefined,
      get description() {
          return this._description
      },
      set description(descr) {
          this._description = descr
      },
      _dueDate: null,
      set dueDate(date) {
          // This setter will accept a date in the form of 'YYYY-MM-DD' and return a value in the form of 'Month, day, Year.' 'January 1, 2021', for example.
          const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
          let includesDashes = [];
          includesDashes.push(date[4]); // Checks dash at 4th index
          includesDashes.push(date[7]); // Checks dash at 7th index
          let dateTypeParse = [parseInt(date[0]), parseInt(date[1]), parseInt(date[2]), parseInt(date[3]), parseInt(date[5]), parseInt(date[6]), parseInt(date[8]), parseInt(date[9])];
          // If any of the YYYYMMDD is not an integer from 0 to 9, NaN will be a value in dateTypeParse
          let isDateNumbers = true;
          for (let i = 0; i < dateTypeParse.length; i++) {
              if (isDateNumbers === true && dateTypeParse[i] !== 'NaN') {
                  isDateNumbers = true
              } else {
                  isDateNumbers = false
              }
              // If any of the YYMMDD is not a number, isDateNumbers will be false
          };
          let dateProperties = [typeof date, date.length, includesDashes, isDateNumbers];
          const idealProperties = [ 'string', 10, [ '-', '-' ], true ];
          // dateIdeal will compare these above two arrays in the following, which should be the same for a correctly formatted date
          let dateIdeal = true;
          for (let i = 0; i < 4; i++) {
              if (dateProperties[i] === idealProperties[i] && dateIdeal === true) {
                  dateIdeal = true
              } else {
                  if (i === 2 && dateIdeal === true && dateProperties[i][0] === '-' && dateProperties[i][1] === '-') {
                      dateIdeal = true
                  } else {
                      dateIdeal = false
                  }
              }
          };
          if (dateIdeal === false) {
              console.log('Error: Please enter a date in "YYYY-MM-DD" format')
              // This ensures that YYYY-MM-DD is in the correct format
          } else {
              let yearArray = [];
              for (let i = 0; i < 4; i++) {
                  yearArray.push(date[i])
                  // Pushes YYYY into yearArray
              };
              let year = parseInt(yearArray.join(''));
              // Joins YYYY and converts into an integer
              let monthArray = [];
              for (let i = 0; i < 2; i++) {
                  monthArray.push(date[i+5])
                  // Pushes MM into monthArray
              };
              if (parseInt(monthArray.join('')) > 12) {
                  console.log('Error: month greater than 12')
              } else {
                  let month = months[parseInt(monthArray.join(''))-1];
                  // Chooses the month from the 'months' array according to MM
                  let dayArray = [];
                  for (let i = 0; i < 2; i++) {
                      dayArray.push(date[i+8])
                      // Pushes DD into dayArray
                  };
                  if (parseInt(dayArray.join('') > 31)) {
                      console.log('Error: date is greater than 31')
                  } else {
                      let day = parseInt(dayArray.join(''))
                      // Again, extracts DD as integer.    
                      this._dueDate = `${month} ${day}, ${year}`
                      // Puts it all together.
                  }
              }
          }    
      },
      get dueDate() {
          if (this._dueDate) {
              return this._dueDate
          } else {
              return null
          }
      },
      _status: 'New',
      set status(stat) {
          this._status = stat
      },
      get status() {
          // I assumed "status" would be "New" by default and change to "Working on" after 24 hours.
          if (Date.now() < this._oneDayLater && this._status != 'Finished') {
              this.status = 'New';
              return 'New'
          } else {
              if (Date.now() >= this._oneDayLater && this._status != 'Finished') {
                  this.status = 'Working on';
                  return 'Working on'    
              } else {
                  if (this._status === 'Finished') {
                      return 'Finished'
                  } else {
                      if (this._status === 'Deleted') {
                          return 'Deleted'
                      } else {
                          console.log('Error reading status')
                      }
                  }
              }
          }
      }
  }
}


// Guide:
  // To make a new To-Do list, use const toDoList = toDoListFactory(title,owner).
  // To create an item, use toDoList.createItem(itemTitle). Item properties and methods below (ind represents the index of the item starting at 1):
      // toDoList.setTitle(ind,title)...............sets the title of the 'ind'th item
      // toDoList.setDescription(ind,description)...gives the item a description
      // toDoList.setDueDate(ind,'YYYY-MM-DD')......gives the item a due date
      // toDoList.finishItem(ind)...................marks the item as 'Finished'
      // toDoList.deleteItem(ind,finished?).........deletes the item, set 'finished?' as 'true' or 'false' according to which array it is getting deleted from.
      // toDoList.moveItem(ind,newInd)..............moves the item from ind to newInd
  // Choose whether or not to show finished items with toDoList.showFinished = true/false
  // Show deleted items with toDoList.displayDeleted()

// Example:
const johns = toDoListFactory('General To-Dos','John Doe');
// creates a to do list for 'John Doe'
johns.createItem('Do Physics homework');
johns.setDescription(1,'This one is worth 25% of the grade!');
johns.setDueDate(1,'2021-11-16');
// The first item is 'Do Physics homework' with a description, 'This one is worth 25% of the grade!', and due date of November 16, 2021
johns.createItem('Water the garden');
johns.setDescription(2,'Weed as well');
johns.setDueDate(2,'2021-11-15');
// Similarly, a second item 'Water the garden' is created with title, description, and due date.
johns.createItem('Feed the dog');
// This third item has only a title
johns.setTitle(2,'Water the flowerbed');
// You can change the title of any item
johns.display;
// See all the items displayed formatted
johns.moveItem(3,1);
// The third item, 'Feed the dog,' is moved to the first position
johns.display;
// The change in position is displayed
johns.deleteItem(1,false);
johns.deleteItem(1,false);
// The first two items are deleted, leaving only 'Water the flowerbed' left
johns.display;
johns.displayDeleted;
// The deleted items can be displayed
