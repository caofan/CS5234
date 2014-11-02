import java.util.LinkedList;


/*
 * A Graph class implemented using Adjacency List.
 */
public class AdjacencyListGraph implements Graph{
	private final int N; //The number of vertices.
	private int M; //The number of edges.
	private LinkedList<Edge>[] adj;
	
	public AdjacencyListGraph(int N) {
		this.N = N;
		this.M = 0;
		this.adj = new LinkedList[this.N];
		for(int i = 0; i < this.N; i++ )
			this.adj[i] = new LinkedList<Edge>();
	}
	
	public void addEdge( Edge e ) {
		assert e.getV() >= 0 && e.getV() < N;
		assert e.getU() >= 0 && e.getV() < N;
		adj[ e.getV() ].add( e );
		adj[ e.getU() ].add( e );
	}
	
	public int getN() {
		return this.N;
	}
	
	public int getM() {
		return this.M;
	}
	
	public Iterable<Edge> adj( int v ) {
		assert v >= 0 && v < this.N;
		return this.adj[v];
	}
	
	public Iterable<Edge> getEdges() {
		LinkedList<Edge> edges = new LinkedList();
		for( int i = 0; i < N; i++ ){
			for( Edge e: adj[i] ) {
				if( e.other( i ) > i) {
					edges.add( e );
				}
			}
		}
		return edges;
	}
	
	public int getNumberEdgesOn(int v) {
		return adj[v].size();
	}
	
	
}
