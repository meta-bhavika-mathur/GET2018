package undirectedweightedgraph;

// Custom Exception thrown when an invalid vertex is accessed that is not part of the graph
public class InvalidVertexException extends Exception
{
       public InvalidVertexException(String errorMessage)
       {
           super(errorMessage);
       }
}
