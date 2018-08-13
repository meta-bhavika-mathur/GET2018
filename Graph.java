package undirectedweightedgraph;

import java.util.*;

/**
 * A class representing a undirected weighted graph with n number of vertices. Each edge of the graph 
 * is assigned some weight. The graph is stored in the form of adjacency list, where the index of list
 * represents the source vertex and the list represented by it contains the edges emerging from it.
 */
class Graph implements UndirectedWeightedGraph
{
    final String ERROR_MESSAGE = "Invalid Vertex Exception!";
    final String DISCONNECTED_PATH_ERROR_MESSAGE = "Path is not connected!";
    int numberOfVertices;                   // Number of vertices in the graph
    LinkedList<Edge>[] adjacencylist;       // List of edges from each vertex
    private Edge edge;
    
    // Parameterized constructor of graph
    public Graph(int numberOfVertices)
    {
        this.numberOfVertices = numberOfVertices;
        adjacencylist = new LinkedList[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++)        // Create adjacency list for each vertex
             adjacencylist[i] = new LinkedList<>();
    }
    
    /**
     * To add a new edge to the graph, as the graph is undirected, when an edge is added from source 
     * vertex to destination vertex, it is also added for destination vertex to source vertex.
     * @param source, source vertex of the graph
     * @param destination, destination vertex of the graph
     * @param weightOfEdge, weight of the edge
     */
    public boolean addEdgeToGraph(int source, int destination, int weightOfEdge)
    {
        boolean result = true;
        
        if(!((source >= 0 && source < numberOfVertices ) && (destination >= 0 && destination < numberOfVertices)))
            result = false;
        else
        {
            Edge newEdgeOne = new Edge(source, destination, weightOfEdge);
            adjacencylist[source].addFirst(newEdgeOne);                      // Add edge from source to destination            
            Edge newEdgeTwo = new Edge(destination, source, weightOfEdge);
            adjacencylist[destination].addFirst(newEdgeTwo);                 // Add edge from destination to source
        }
        return result;
    }
    
    /**
     * To find whether the graph is connected or not. A graph is said to be connected if every vertex
     * of the graph is reachable from every vertex i.e if there is a path from every vertex to every other
     * vertex of the graph.
     * @param startVertex, the start vertex of the graph
     * @return true, if the graph is a connected graph otherwise false
     */
    public boolean isConnected(int startVertex) throws InvalidVertexException
    {
        boolean connected = true;
        boolean visited[] = new boolean[numberOfVertices];
        
        if(!(startVertex >= 0 && startVertex < numberOfVertices ))
            throw new InvalidVertexException(ERROR_MESSAGE);
        
        DepthFirstSearch( startVertex , visited);      
        for(int i = 0; i < numberOfVertices; i++ )
        {
            if(!visited[i])
            {
                connected = false;
                break;
            }
        }
        return connected;   
    }
    
    /**
     * Utility method to perform depth first traversal of a graph 
     * @param vertex, veretx in the graph
     * @param visited, an array representing traversed nodes in the graph as boolean true
     */
    private void DepthFirstSearch(int vertex , boolean[] visited)
    {
         visited[vertex] = true;
         
         for(Edge edge : adjacencylist[vertex])
         {
             int destinationVertex = edge.destination;
              if (!visited[destinationVertex])
                  DepthFirstSearch(destinationVertex, visited);
         }
    }
    
    /**
     * To find list of reachable vertices from a given vertex of a graph. Reachable vertex from a given vertex are vertices for which there exists some path between
     * them and the source vertex.
     * @param startVertex, vertex of a graph
     * @return reachableVertices, list of all the reachable nodes from a given vertex
     */
    public List<Integer> getReachableNodes(int startVertex) throws InvalidVertexException
    {
        if(!(startVertex >= 0 && startVertex < numberOfVertices ))
            throw new InvalidVertexException(ERROR_MESSAGE);
        
        boolean visited[] = new boolean[numberOfVertices];
        List<Integer> reachableVertices = new ArrayList<Integer>();
        DepthFirstSearch(startVertex , visited);
        
        for(int i = 0; i < numberOfVertices; i++)
        {
            if(visited[i])
                reachableVertices.add(i);
        }
        Collections.sort(reachableVertices);
        return reachableVertices; 
    }
    
    /**
     * To get shortest path between given source to destination in the graph
     * @param source, source from which shortest path has to be found
     * @param destination, destination vertex to which shortest path has to be found
     * @return shortestPath, list containing the set of edges in shortest path from source to vertex
     * @throws AssertionError
     */
    public List<Edge> getShortestPath(int source, int destination) throws PathNotConnectedException
    {
        List<Edge> shortestPath;                                            // List for storing edges in shortest path
        List<Edge> edgesList;                   

        int distance[] = new int[numberOfVertices];                         // To store shortest distance from source to every vertex i in graph
        Boolean currentShortestDistance[] = new Boolean[numberOfVertices];  // To keep track of vertices included in shortest path

        for (int i = 0; i < numberOfVertices; i++)                          
        {
            distance[i] = Integer.MAX_VALUE;                                // Initialize all distances with a large infinite value
            currentShortestDistance[i] = false;                             // Initialize with false, as no vertex is not yet included
        }
        distance[source] = 0;                                               // Distance from source vertex to itself is always zero

        // Find shortest path for all vertices
        for (int count = 0; count < numberOfVertices - 1; count++) 
        {
            int minimumDistanceVertex = getMinimumKeyIndex(distance, currentShortestDistance);  // Pick the minimum distance vertex among all the vertices
            currentShortestDistance[minimumDistanceVertex] = true;                              // Mark it as true to include it in shortest path
            edgesList = adjacencylist[minimumDistanceVertex];

            for (Edge edge : edgesList) 
            {
                int destinationVertex = edge.destination;
                if (!currentShortestDistance[destinationVertex] && edge.weight != 0  && distance[minimumDistanceVertex] != Integer.MAX_VALUE 
                    && distance[minimumDistanceVertex] + edge.weight < distance[destinationVertex]) 
                {
                    distance[destinationVertex] = distance[minimumDistanceVertex] + edge.weight; // Update distance with new distance
                }               
            }
        }
        shortestPath = new ArrayList<>();   
        if(distance[destination] == Integer.MAX_VALUE)
            throw new PathNotConnectedException(DISCONNECTED_PATH_ERROR_MESSAGE);
             
        for (int i = source; i < destination + 1; i++) 
        {
            edge = new Edge(i - 1, i, distance[i]);
            shortestPath.add(edge);
        }
        return shortestPath;
    }
    /**
     * To find index of vertex with minimum weight and not yet included in the path
     */
    public int getMinimumKeyIndex(int minimumWeight[], Boolean setOfIncludedVertices[]) 
    {
        int minimumValue = Integer.MAX_VALUE, minimumIndex = -1;

        for (int i = 0; i < numberOfVertices; i++)
        {     
            if (setOfIncludedVertices[i] == false && minimumWeight[i] < minimumValue) 
            {
                minimumValue = minimumWeight[i];
                minimumIndex = i;
            }
        }
        return minimumIndex;
    }
    
    /**
     * To find minimum spanning tree of a graph
     */
    public List<Edge> getMinimumSpanningTree()
    {
        List<Edge> minSpanningtree;
        List<Edge> edges;

        int parent[] = new int[numberOfVertices];                           // To store constructed minimum spanning tree
        int minimumWeight[] = new int[numberOfVertices];                    // Key values used to pick minimum weight edge
        Boolean spanningTreeSet[] = new Boolean[numberOfVertices];          // To represent set of vertices not yet included in MST

        for (int i = 0; i < numberOfVertices; i++) 
        {
            minimumWeight[i] = Integer.MAX_VALUE;                           // Initialize all distances with a large infinite value
            spanningTreeSet[i] = false;
        }
        minimumWeight[0] = 0;                                               // Make key 0 so that this vertex is picked as first vertex
        parent[0] = -1;                                                     // First node is always root of MST
        
        for (int count = 0; count < numberOfVertices - 1; count++) 
        { 
            int index = getMinimumKeyIndex(minimumWeight, spanningTreeSet); // Pick the minimum key vertex from the set of vertices not yet included in MST
            spanningTreeSet[index] = true;                                  // Add the picked vertex to the MST Set
            edges = adjacencylist[index];
            
            for (Edge adjacentEdge : edges)                                 // Updates key value and parent index of the adjacent vertices that are not yet included in MST
            {
                int v = adjacentEdge.destination;                
                if (adjacentEdge.weight != 0 && !spanningTreeSet[v]         // Update the key, if weight(u,v) is smaller than key[v]
                    && adjacentEdge.weight < minimumWeight[v]) 
                {
                    parent[v] = index;
                    minimumWeight[v] = adjacentEdge.weight;  
                }
            }
        }

        minSpanningtree = new ArrayList<>();

        for (int i = 1; i < numberOfVertices; i++) 
        {
            edges = adjacencylist[i];
            for (Edge edgeConnected : edges) 
            {
                if (edgeConnected.destination == parent[i]) 
                {
                    edge = new Edge(parent[i], i, edgeConnected.weight);                          
                    minSpanningtree.add(edge);
                }
            }
        }
        return minSpanningtree;
    } 

    /**
     * To print all the edges of the graph with their source and destination vertex
     */
    public void printGraph()
    {
        for (int i = 0; i < numberOfVertices; i++) 
        {
            LinkedList<Edge> list = adjacencylist[i];
            for (int j = 0; j < list.size() ; j++) 
            {
                System.out.println("vertex-" + (i + 1) + " is connected to " + ( list.get(j).destination + 1) + " with weight " +  list.get(j).weight);          
            }
        }
    }
} 
