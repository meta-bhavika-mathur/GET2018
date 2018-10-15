// Class to define structure of a stack
function Stack()
{
    this.sizeOfStack = 0;
    this.stackStorage = {};
}

// To push data into stack
Stack.prototype.push = function(data) 
{
    var size = ++this.sizeOfStack;
    this.stackStorage[size] = data;
};

// To pop element from the stack
Stack.prototype.pop = function() 
{
    var size = this.sizeOfStack,
               deletedData;
    if(!this.isEmpty()) 
    {
        deletedData = this.stackStorage[size];
        delete this.stackStorage[size];
        this.sizeOfStack--;
        
        return deletedData;
    }
};

// To get top element of the stack
Stack.prototype.peek = function()
{
    return this.stackStorage[this.sizeOfStack];
}

// To check if the stack is empty
Stack.prototype.isEmpty = function() 
{
    if(this.sizeOfStack == 0) 
        return true;
    else
        return false;
};

// To display all the elements of the stack
Stack.prototype.showStackElements = function()
{
    for(var i = 0; i <= this.sizeOfStack; i++)
        console.log(this.stackStorage[i]);
}

var newStack = new Stack();
newStack.push(10);
newStack.push(20);
newStack.push(30);
newStack.push(40);

console.log("Stack elements are:- \n");
newStack.showStackElements();

console.log("Element popped: " + newStack.pop());
console.log("Element popped: " + newStack.pop());
console.log("Is empty: " + newStack.isEmpty());
console.log("Element at top: " + newStack.peek());
console.log("Element popped: " + newStack.pop());
console.log("Element at top: " + newStack.peek());
console.log("Element popped: " + newStack.pop());
console.log("Is empty: " + newStack.isEmpty());