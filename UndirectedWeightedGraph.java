package undirectedweightedgraph;

import java.util.List;

// Interface to define methods for Undirected Weighted Graph
public interface UndirectedWeightedGraph 
{
	boolean isConnected(int startVertex) throws InvalidVertexException;
	List<Integer> getReachableNodes(int startVertex) throws InvalidVertexException;
	List<Edge> getShortestPath(int source, int destination) throws PathNotConnectedException;
	List<Edge> getMinimumSpanningTree();
}
