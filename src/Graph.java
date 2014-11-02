/*
 * An interface for the graphs.
 */
public interface Graph {
	/*
	 * Get the number of nodes in the graph.
	 * @return The number of nodes. An integer.
	 */
	public int getN();
	
	/*
	 * Get the number of edges in the graph.
	 * @return The number of edges. An integer.
	 */
	public int getM();
	
	/*
	 * Get an Iterable<Edge> instance of the edges incident to a vertex.
	 */
	public Iterable<Edge> adj( int v );
	
	/*
	 * Get an Iterable<Edge> instance of all edges in the graph.
	 */
	public Iterable<Edge> getEdges();
	
	/*
	 * Add an edge to the graph.
	 * @param e The edge instance.
	 */
	public void addEdge( Edge e);
	
}
