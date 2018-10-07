// Structure of queue
function Queue()
{
    this.front = -1;
    this.rear = -1;
    this.queueStorage = {};
}
// To insert element into queue
Queue.prototype.enqueue = function(data) 
{
    if(this.rear == -1)
        this.front = this.rear = 0;
    else
        this.rear++;
    
    this.queueStorage[this.rear] = data;
};
// To delete element from the queue
Queue.prototype.dequeue = function() 
{
    var deletedData;
    message = {failure: 'Empty queue!'};
 
    // When an invalid position is provided as input
    if(this.front == -1)
        throw new Error(message.failure);
    else
    { 
        deletedData = this.queueStorage[this.front]; 
            
        if(this.front == this.rear)
            this.front = this.rear = -1;
        else 
            this.front++;
        
        return deletedData; 
    }   
};

// To print elements of queue
Queue.prototype.print = function() 
{
   
   for(var i = this.front; i <= this.rear; i++)
       console.log(this.queueStorage[i]);
}

var newQueue = new Queue();
newQueue.enqueue(5);
newQueue.enqueue(50);
newQueue.enqueue(500);
newQueue.enqueue(10);
newQueue.enqueue(100);
newQueue.enqueue(1000);
newQueue.print();
newQueue.dequeue();
newQueue.print();