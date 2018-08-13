package undirectedweightedgraph;

public class PathNotConnectedException extends Exception
{
    public PathNotConnectedException(String errorMessage)
    {
        super(errorMessage);
    }
}
