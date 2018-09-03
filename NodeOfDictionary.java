package dsassignment5;

// A class to store words of a dictionary where each word represents a node of Binary Search
public class NodeOfDictionary 
{
    String key;                             // This represents word of a dictionary
    String value;                           // This represents meaning of the word in dictionary
    NodeOfDictionary leftPointer;           // Pointer to left node in Binary Search Tree
    NodeOfDictionary rightPointer;          // Pointer to right node in Binary Search Tree
    
    // Constructor for Binary Search Tree
    public NodeOfDictionary()
    {
        key = "";
        value = "";
        leftPointer = null;
        rightPointer = null;        
    }
    
    // Parameterized Constructor for BinarySearchTree
    public NodeOfDictionary(String key, String value)
    {
        this.key = key;
        this.value = value;
        leftPointer = null;
        rightPointer = null;        
    }
    
    // To set left child
    public void setLeft(NodeOfDictionary newNode)
    {
        leftPointer = newNode;
    }
    
    // To set right child 
    public void setRight(NodeOfDictionary newNode)
    {
        rightPointer = newNode;
    }
    
    // To get left child
    public NodeOfDictionary getLeftChild()
    {
        return leftPointer;
    }
    
    // To get right child
    public NodeOfDictionary getRightChild()
    {
        return rightPointer;
    }
    
    // To set data in a new a new node
    public void setData(String key, String value)
    {
       this.key = key;
       this.value = value;
    }
    
    // To get value of  key
    public String getkey()
    {
        return key;
    } 
    
    // To get value of value stored in node
    public String getValue()
    {
        return value;
    }  
}