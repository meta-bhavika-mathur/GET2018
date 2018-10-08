// Node of a linked list
function Node(data) 
{
    this.data = data;
    this.next = null;
	this.previous = null;
}
// Structure of a doubly linked list
function DoublyLinkedList()
{
    this.lengthOfLinkedList = 0;
    this.head = null;
	this.tail = null;
}
// To add nodes to a linked list
DoublyLinkedList.prototype.add = function(value) 
{
    var node = new Node(value);
 
    if (this.head) 
    {
        this.tail.next = node;
		node.previous = this.tail;
		this.tail = node;
    } 
    else 
    {
		this.head = node;
		this.tail = node;
	}
    this.lengthOfLinkedList++; 
    return node;
};
// To remove element at specific position
DoublyLinkedList.prototype.remove = function(position)
{
    var currentNode = this.head,
    count = 1,
    message = 'Invalid node position',
    beforeNodeToDelete = null,
	afterNodeToDelete = null,
    nodeToDelete = null;
 
    if (this.size === 0 || position < 0 || position > this.lengthOfLinkedList)
    {
        throw new Error(message);
    }
    else 
    {
        if (position === 1) 
        {
            this.head = currentNode.next;

            if(!this.head) 
            {
                this.tail = null;
            } 
            else 
            {
                this.head.previous = null;
            }
        } 
        else 
            if (position == this.lengthOfLinkedList)
            {
                this.tail = this.tail.previous;
                this.tail.next = null;
	        }
            else 
            {
		         while (count < position) 
                 {
                     currentNode = currentNode.next;
                     count++;
		         }
                 beforeNodeToDelete = currentNode.previous;
                 afterNodeToDelete = currentNode.next;
                 beforeNodeToDelete.next = afterNodeToDelete;
                 afterNodeToDelete.previous = beforeNodeToDelete;
                 nodeToDelete = currentNode;
                 currentNode = null;
	       }
 	       this.size--;
    }    
}; 
// To print elements of linked list
DoublyLinkedList.prototype.printList = function() 
{
	var currentNode = this.head; 
    var str = ""; 
    while (currentNode)
    { 
        str += currentNode.data + " "; 
        currentNode = currentNode.next; 
    } 
    console.log(str);
};

var newDoublyLinkedList = new DoublyLinkedList();
newDoublyLinkedList.add(5);
newDoublyLinkedList.add(10);
newDoublyLinkedList.add(15);
newDoublyLinkedList.add(20);
newDoublyLinkedList.add(25);
newDoublyLinkedList.printList();
newDoublyLinkedList.remove(1);
newDoublyLinkedList.remove(2);
newDoublyLinkedList.printList();
