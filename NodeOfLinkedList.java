package Session1;

// Class to define structure and operations of linked list
public class NodeOfLinkedList<T> 
{
    protected T data;
    protected NodeOfLinkedList<T> pointerToNextNode;
   
    // Constructor of linked list
    public NodeOfLinkedList()
    {   
    	pointerToNextNode = null;
    }
    
    // Parameterized constructor of linked list
    public NodeOfLinkedList(T value, NodeOfLinkedList<T> node)
    {
        data = value;
        pointerToNextNode = node;
    }
    
    /**
     * To add data into the node of linked list
     * @param value, data that is required to be assigned to node
     */
    public void setData(T value)
    {
        data = value;
    }
    
    /**
     * To set address of next node in the pointer of current node
     * @param node, node that is required to be pointed by current node
     */
    public void setNextNode(NodeOfLinkedList<T> node)
    {
    	pointerToNextNode = node;
    }
    
    /**
     * To get data in current node
     * @return data, data of node
     */
    public T getData()
    {
        return data;
    }
    
    /**
     * To get pointer to the next node i.e address of the node to which current node is pointing
     * @return pointerToNextNode, address of next node
     */
    public NodeOfLinkedList<T> getNextNode()
    {
        return pointerToNextNode;
    }
}