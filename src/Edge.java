

/*
 * This class assumes the vertices in the graph are stored in order.
 * Thus in the Edge class, only the indexes of the vertex in the original list
 * is store.
 * This Edge class does not allow self-looping edges.
 */
public class Edge implements Comparable<Edge> {
	
	private int u; //vertex 1
	private int v; //vertex 2
	private double weight;
	
	/*
	 * Constructor. The edge weight is 0.
	 * @param u Vertex 1.
	 * @param v Vertex 2.
	 */
	public Edge(int u, int v) {
		this(u,v,0);
	}
	
	/*
	 * Constructor
	 * @param u Vertex 1.
	 * @param v Vertex 2.
	 * @param weight The weight of the edge.
	 */
	public Edge(int u, int v, double weight){
		assert u >= 0;
		assert v >= 0;
		assert u != v;
		assert weight >= 0;
		this.u = u;
		this.v = v;
		this.weight = weight;
	}
	
	/*
	 * Get the weight of the edge.
	 * @return The weight of the edge.
	 */
	public double weight() {
		return this.weight;
	}
	
	/*
	 * Get one end of the edge.
	 * @return A vertex.
	 */
	public int getV() {
		return v;
	}
	
	public int getU() {
		return u;
	}
	
	/*
	 * Get the other end of the edge.
	 * @param t The current vertex.
	 * @return The other end of the edge.
	 */
	public int other(int t) {
		assert t == v || t == u;
		if(t == v)
			return u;
		else
			return v;
	}
	
	/*
	 * Whether the edge is adjacent to a vertex at some index.
	 * @param t The target vertex.
	 * @return True if is adjacent. Otherwise, false.
	 */
	public boolean isAdjacentTo( int t ) {
		return u == t || v == t;
	}
	
	public int compareTo( Edge other ){
		if( this.weight > other.weight )
			return 1;
		else if(this.weight == other.weight)
			return 0;
		else
			return -1;
	}
	
	public String toString() {
		return u + " " + v + " " + weight;
	}
}