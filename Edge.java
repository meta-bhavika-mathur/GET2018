package undirectedweightedgraph;

/**
 * A class to define an edge of a weighted undirected graph. An edge is defined with source, destination 
 * and weight. Source and destination are vertices between which the edge lies.
 */
public class Edge 
{
    int source;             // Source of edge
    int destination;        // Destination of edge
    int weight;             // Weight of 

    // Constructor for Edge class
    public Edge(int source, int destination, int weight) 
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
    // To get source vertex of the edge
    public int getSource()
    {
        return source;
    }
    
    // To get destination vertex of the edge
    public int getDestination()
    {
        return destination;
    }
    
    // To get weight of the edge of the graph
    public int getWeight()
    {
        return weight;
    }
}
