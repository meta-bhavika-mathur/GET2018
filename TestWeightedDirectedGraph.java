package undirectedweightedgraph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestWeightedDirectedGraph 
{
    Graph graph = new Graph(4);   
    
    // Test to add edge to a graph
    @Test
    public void testToAddEdgeToGraph() 
    {
        assertEquals(true, graph.addEdgeToGraph(0, 1, 300));
        assertEquals(true, graph.addEdgeToGraph(0, 2, 800));
        assertEquals(true, graph.addEdgeToGraph(0, 3, 100));
    }   
    
    // Test to add edge to the graph with invalid source vertex
    @Test
    public void testToAddEdgetoGraphWithSourceVertexOutsideRange() 
    {
        assertEquals(false, graph.addEdgeToGraph(4, 1, 300));
    } 

    // Test to add edge to the graph with invalid destination vertex
    @Test
    public void testToAddEdgetoGraphWithDestinationVertexOutsideRange() 
    {
        assertEquals(false, graph.addEdgeToGraph(0, -1, 300));
    }
    
    // Test to find if a graph is connected with start vertex in connected graph
    @Test
    public void testIsConnectedFromStartNode() 
    {      
        try
        {
            graph.addEdgeToGraph(0, 1, 300);
            graph.addEdgeToGraph(0, 2, 800);
            graph.addEdgeToGraph(0, 3, 100); 
            assertEquals(true, graph.isConnected(0));
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
    
    // Test to check if a graph is connected with vertex other that the source vertex
    @Test
    public void testIsConnectedFromNodeOtherThanStartNode()
    {
        try
        {
            graph.addEdgeToGraph(0, 1, 300);
            graph.addEdgeToGraph(0, 2, 800);
            graph.addEdgeToGraph(0, 3, 100); 
            assertEquals(true, graph.isConnected(3));
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }       
    }
    
    // Test to check if a graph is connected with disconnected graph
    @Test
    public void testIsConnectedWithDisonnectedGraph() 
    {
        try
        {
            graph.addEdgeToGraph(0, 1, 300);
            graph.addEdgeToGraph(0, 2, 800);    
            assertEquals(false, graph.isConnected(3));
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
    
    // Test to find list of all reachable nodes from a given node in a connected graph
    @Test
    public void testToGetReachableNodes() 
    {
        graph.addEdgeToGraph(0, 1, 300);
        graph.addEdgeToGraph(0, 2, 800);
        graph.addEdgeToGraph(0, 3, 100); 
        
        try
        {
            List<Integer> actualListOfIntegers = graph.getReachableNodes(3);
            List<Integer> expecetedListOfIntegers = new ArrayList<Integer>();
            
            expecetedListOfIntegers.add(0);
            expecetedListOfIntegers.add(1);
            expecetedListOfIntegers.add(2);
            expecetedListOfIntegers.add(3);                
            assertEquals(actualListOfIntegers, expecetedListOfIntegers);
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }          
    }
    
    // Test to find list of all reachable nodes from a given node in a disconnected graph
    @Test
    public void testToGetReachableNodesWithDisconnectedGraph() 
    {
        graph.addEdgeToGraph(0, 1, 300);
        graph.addEdgeToGraph(0, 2, 800);
        
        try
        {
            List<Integer> actualListOfIntegers = graph.getReachableNodes(3);
            List<Integer> expecetedListOfIntegers = new ArrayList<Integer>();           
            
            expecetedListOfIntegers.add(3);       
            assertEquals(actualListOfIntegers, expecetedListOfIntegers);
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }          
    }
    
    // Test to find minimum spanning tree of a graph
    @Test
    public void testGetMinimumSpanningTree()
    {
        Graph graph = new Graph(6);
        graph.addEdgeToGraph(0, 1, 4);
        graph.addEdgeToGraph(0, 2, 3);
        graph.addEdgeToGraph(1, 3, 2);
        graph.addEdgeToGraph(1, 2, 5);
        graph.addEdgeToGraph(2, 3, 7);
        graph.addEdgeToGraph(3, 4, 2);
        graph.addEdgeToGraph(4, 0, 8);
        graph.addEdgeToGraph(4, 1, 4);
        graph.addEdgeToGraph(4, 5, 6);
        
        String[] expectedOutput = {"0 1 4", "0 2 3", "1 3 2", "3 4 2", "4 5 6"};                                      
        int i = 0;
        for(Edge edge: graph.getMinimumSpanningTree())
        {
            String actualOutput = edge.getSource() + " " + edge.getDestination() + " " + edge.getWeight();
            assertEquals(expectedOutput[i], actualOutput);
            i++;
        }
    }

    // Test to find shortest path in a graph between two vertices
    @Test
    public void testGetShortestPath()
    {
        Graph graph = new Graph(6);
        
        try
        {
        	graph.addEdgeToGraph(0, 1, 4);
        	graph.addEdgeToGraph(0, 2, 3);
            graph.addEdgeToGraph(1, 3, 2);
            graph.addEdgeToGraph(1, 2, 5);
            graph.addEdgeToGraph(2, 3, 7);
            graph.addEdgeToGraph(3, 4, 2);
            graph.addEdgeToGraph(4, 0, 8);
            graph.addEdgeToGraph(4, 1, 4);
            graph.addEdgeToGraph(4, 5, 6);
            
            String[] expectedOutput = {"1 2 0", "2 3 7",  "3 4 9", "4 5 15"};                              
            int i = 0;
            for(Edge edge: graph.getShortestPath(2, 5))
            {
                String actualOutput = edge.getSource() + " " + edge.getDestination() + " " + edge.getWeight();
                assertEquals(expectedOutput[i], actualOutput);
                i++;
            }
        } 
        catch(Exception exception)
        {
        	System.out.println(exception.getMessage());
        }
    }
    
    // Test to find shortest path with no path available between two vertices in a graph
    @Test
    public void testGetShortestPathWithNoPathAvailable()
    {
        Graph graph = new Graph(6);
        try
        {
        	 graph.addEdgeToGraph(0, 1, 4);
             graph.addEdgeToGraph(0, 2, 3);
             graph.addEdgeToGraph(1, 3, 2);
             graph.addEdgeToGraph(1, 2, 5);
             graph.addEdgeToGraph(2, 3, 7);
             graph.addEdgeToGraph(3, 4, 2);
             graph.addEdgeToGraph(4, 0, 8);
             graph.addEdgeToGraph(4, 1, 4);
             
             List<Edge> shortestPath = graph.getShortestPath(2, 5);
        }
        catch(Exception exception)
        {
        	System.out.println(exception.getMessage());
        }      
    }
}