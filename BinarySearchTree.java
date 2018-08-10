package dsassignment5;

import java.util.*;

/*
 * A class to represent a binary search tree(BST). A binary search tree is a data structure in which for each node, all the nodes to its left are less
 * than it and all the nodes to the right are greater than it.
 * Root of the tree always point to first node of the tree.
 * Each node represents an entry in the dictionary.
 */
public class BinarySearchTree 
{
    private NodeOfDictionary root;
    private Map<String, String> listOfEntries = new LinkedHashMap<String, String>();
    
    // Constructor for Binary Search Tree(BST)
    public BinarySearchTree()
    {
        root = null;
    }
    
    // Parameterized constructor to create dictionary with initial entries from text file using JSON
    public BinarySearchTree(String initialWordList)
    {
        JSONParser parser = new JSONParser();
        root = null;

        try 
        {
            Object obj = parser.parse(new FileReader(initialWordList));
            JSONObject jsonObject = (JSONObject) obj;

            for (Object object : jsonObject.keySet()) 
            {
                insert(object.toString(), jsonObject.get(object).toString());             
            }
        } 
        catch (Exception exception) 
        {
            System.out.println(exception.getMessage());
        }
    }
    
    /**
     * To insert a new entry to the binary search tree
     * @param key, representing word of the dictionary
     * @param value, represent meaning of the word
     */
    void insert(String key, String value) 
    {
        root = insertRecursively(root, key, value);
    }
    
    /**
     * Recursive method to insert a node in the BST so the the order of tree is maintained.
     * @param root, root of the tree
     * @param key, representing word of the dictionary
     * @param value, represent meaning of the word
     * @return root.
     */
    public NodeOfDictionary insertRecursively(NodeOfDictionary root, String key, String value)
    {
        if (root == null)							// If tree is empty
        {
            root = new NodeOfDictionary(key, value);
            return root;
        }
        if(key.compareToIgnoreCase(root.key) < 0) // If new node is less that root node search in left subtree to insert
            root.leftPointer = insertRecursively(root.leftPointer, key, value);
        else if (key.compareToIgnoreCase(root.key) > 0) // If new node is less that root node search in right subtree to insert
            root.rightPointer = insertRecursively(root.rightPointer, key, value);

        return root;
    }
    
    /**
     * To search a value in the dictionary corresponding to the key
     * @param key, whose corresponding value has to be found
     */
    public String searchValue(String key)
    {
        NodeOfDictionary resultNode = search(root, key);
        return resultNode.getValue();
    }
    
    /**
     * To search a node representing a word in the dictionary
     * @param root, root of the dictionary tree
     * @param key, representing key of dictionary entry
     */
    public NodeOfDictionary search(NodeOfDictionary root, String key)
    {   
        if ( root == null || root.key == key )
            return root;
        if ( root.key.compareToIgnoreCase(key) > 0 )
            return search(root.leftPointer, key);
        return search(root.rightPointer, key);
    }

    /**
     * To perform recursive inorder traversal of a dictionary tree 
     */
     void inorderTraversal()  
     {
         inorderRecursive(root);
     }
 
     /**
      * To perform recursive inorder traversal of a dictionary tree
      * @param root, root of the dictionary tree
      */
     void inorderRecursive(NodeOfDictionary root) 
     {
         if (root != null) 
         {
             inorderRecursive(root.leftPointer);
             System.out.println(root.getkey());
             inorderRecursive(root.rightPointer);
         }
     }
     
     /**
      * To get all entries in dictionary
      * @param root
      */  
     void getNodeInInorderRecursive(NodeOfDictionary root) 
     {      
         if (root != null) 
         {
             getNodeInInorderRecursive(root.leftPointer);
             listOfEntries.put(root.key, root.value);
             getNodeInInorderRecursive(root.rightPointer);
         }
     }
     
     Map<String, String> getAllEntriesOfDictionary()
     {
         getNodeInInorderRecursive(root);
         return listOfEntries;
     }
           
     /**
      * To get all entries of dictionary in given range specified by startKy and endKey
      * @param startKey, starting entry to store range
      * @param endKey,  ending entry to store range
      * @return sortedListInARange, sorted list of all entries in the given range
      */
     Map<String, String> getAllEntriesInGivenRange(String startKey, String endKey)
     {
         Map<String, String> sortedList = new LinkedHashMap<String, String>();
         Map<String, String> sortedListInARange = new LinkedHashMap<String, String>();
         
         sortedList = getAllEntriesOfDictionary();
         
         Iterator iterator = sortedList.entrySet().iterator();         
         while(iterator.hasNext())
         {
            Map.Entry record = (Map.Entry)iterator.next(); 
            
            if(((String)record.getKey()).compareToIgnoreCase(startKey) >= 0 && ((String) record.getKey()).compareToIgnoreCase(endKey) <= 0 )
                sortedListInARange.put((String)record.getKey(), (String)record.getValue());
         }
         
         return sortedListInARange;      
     }
     
     // To delete entry specified by key as input. Calls deleteRecursively, a recursive method.
     void deleteSpecifiedEntry(String key)
     {
         root = deleteRecursively(root, key);
     }
     
     /**
      * To delete a particular entry from the dictionary
      * @param root, root of the dictionary tree
      * @param key, key specifying the entry to be deleted
      * @return, root of the dictionary tree
      */
     NodeOfDictionary deleteRecursively(NodeOfDictionary root, String key)
     {
    	 // If the tree is empty.
         if (root == null)   
             return root;
         // If key to be deleted is less than root
         if (key.compareToIgnoreCase(root.getkey()) < 0)
             root.leftPointer = deleteRecursively(root.leftPointer, key);
         // If key to be deleted is greater than root
         else 
             if ((key.compareToIgnoreCase(root.key) > 0))
                 root.rightPointer = deleteRecursively(root.rightPointer, key);
         // When key equals the root node, node to be deletd is found
         else
         {       
             if (root.leftPointer == null) 		 // If node has one child or no child
                 return root.rightPointer;
             else if (root.rightPointer == null) // If node has two children
                 return root.leftPointer;
             
            // Delete the inorder successor
             root.key = minimumValue(root.rightPointer);
             root.rightPointer = deleteRecursively(root.rightPointer, root.key);
         }
         return root;
     }
     
     // Utility method to find minimum value in BST
     String minimumValue(NodeOfDictionary root)
     {
         String val = root.getkey();
         while (root.leftPointer != null)
         {
        	 val = root.leftPointer.getkey();
             root = root.leftPointer;
         }
         return val;
     }  
}
