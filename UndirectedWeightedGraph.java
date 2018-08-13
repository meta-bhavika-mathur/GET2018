package undirectedweightedgraph;

import java.util.List;

public interface UndirectedWeightedGraph 
{
	boolean isConnected(int startVertex) throws InvalidVertexException;
	List<Integer> getReachableNodes(int startVertex) throws InvalidVertexException;
	List<Edge> getShortestPath(int source, int destination) throws PathNotConnectedException;
	List<Edge> getMinimumSpanningTree();
}
