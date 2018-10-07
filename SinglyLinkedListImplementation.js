// Node of a linked list
function Node(data) 
{
    this.data = data;
    this.next = null;
}

// Structure of a linked list
function SinglyList()
{
    this.lengthOfLinkedList = 0;
    this.head = null;
}

// To add nodes to a linked list
SinglyList.prototype.add = function(value)
{
    var node = new Node(value),
    currentNode = this.head;
 
    // When list is empty 
    if (!currentNode) 
    {
        this.head = node;
        this.lengthOfLinkedList++;     
        return node;
    }
 
    // When list is not empty
    while (currentNode.next) 
    {
        currentNode = currentNode.next;
    }
    currentNode.next = node;
    this.lengthOfLinkedList++;
    
    return node;
};

// To remove element at specific position
SinglyList.prototype.remove = function(position)
{
    var currentNode = this.head,
    length = this.lengthOfLinkedList,
    count = 1,
    message = {failure: 'Invalid node position'},
    beforeNodeToDelete = null,
    nodeToDelete = null,
    deletedNode = null;
 
     // When an invalid position is provided as input
    if (position < 0 || position > length) 
    {
        throw new Error(message.failure);
    }
 
    // When first node is removed
    if (position === 1) {
        this.head = currentNode.next;
        deletedNode = currentNode;
        currentNode = null;
        this.lengthOfLinkedList--;
         
        return deletedNode;
    }
    // When any other node is removed
    while (count < position)
    {
        beforeNodeToDelete = currentNode;
        nodeToDelete = currentNode.next;
        currentNode = currentNode.next;
        count++;
    }
    beforeNodeToDelete.next = nodeToDelete.next;
    deletedNode = nodeToDelete;
    nodeToDelete = null;
    this.lengthOfLinkedList--;
 
    return deletedNode;
};

// To search a node at specified position 
SinglyList.prototype.searchNodeAt = function(position)
{
    var currentNode = this.head,
    length = this.lengthOfLinkedList,
    count = 1,
    message = {failure: 'Failure: Invalid node position'};
 
    // When an invalid position is provided as input
    if (length === 0 || position < 1 || position > length) 
    {
        throw new Error(message.failure);
    }
 
    // When a valid position is provided as input
    while (count < position) 
    {
        currentNode = currentNode.next;
        count++;
    }
    return currentNode;
};

// To print elements of linked list
SinglyList.prototype.print = function() 
{
   var currentNode = this.head;
   var start = "";
   while(currentNode)
   {
       start += currentNode.data + " ";
       currentNode = currentNode.next;
   }
   console.log(start);
}

var linkedList = new SinglyList();
linkedList.add(10);
linkedList.add(20);
linkedList.add(30);
linkedList.add(40);
linkedList.print();
console.log(linkedList.searchNodeAt(3));
linkedList.remove(3);
console.log("Linked List after removal of 3rd element:\n");
linkedList.print();