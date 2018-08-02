package Session1;

public class NodeOfLinkedList<T> {
    
    protected T data;
    protected NodeOfLinkedList<T> next;
   
    public NodeOfLinkedList()
    {   
        next = null;
    }
    
    public NodeOfLinkedList(T value, NodeOfLinkedList<T> node)
    {
        data = value;
        next = node;
    }
    
    public void setData(T value)
    {
        data = value;
    }
    
    public void setNextNode(NodeOfLinkedList<T> node)
    {
        next = node;
    }
    
    public T getData()
    {
        return data;
    }
    
    public NodeOfLinkedList<T> getNextNode()
    {
        return next;
    }
}
    

