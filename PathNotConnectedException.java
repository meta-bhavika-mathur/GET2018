package undirectedweightedgraph;

// Custom Exception thrown when disconnected path is found
public class PathNotConnectedException extends Exception
{
    public PathNotConnectedException(String errorMessage)
    {
        super(errorMessage);
    }
}
