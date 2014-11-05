import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;


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
	
	/*
	 * Depth-first traversal of the graph.
	 * Will report all components.
	 */
	public ArrayList<ArrayList<Integer>> dfs() {
		ArrayList<ArrayList<Integer>> order = new ArrayList<ArrayList<Integer>>();
		boolean[] traveled = new boolean[this.N];
		for(int i = 0; i < traveled.length; i++ ) {
			traveled[i] = false;
		}
		for(int i = 0; i < traveled.length; i++) {
			ArrayList<Integer> currOrder = new ArrayList<Integer>(this.N);
			if(!traveled[i]) {
				dfsNode( i, traveled, currOrder );
				order.add(currOrder);
			}
			
		};
		return order;
	}
	
	public void dfsNode( int i, boolean[] traveled, ArrayList<Integer> currOrder ) {
		
		Stack<Integer> stack = new Stack<Integer>();
		int[] parent = new int[traveled.length];
		for(int j =0; j< parent.length; j++ )
			parent[j] = -1;
		stack.push(i);
		while( !stack.isEmpty()) {
			int curr = stack.pop();
			currOrder.add( curr );
			
			if(!traveled[curr]) {
				if(parent[curr] != -1 ) 
					stack.push( parent[curr] );
				traveled[curr] = true;
				for(Edge e: adj(curr)) {
					int other = e.other(curr);
					if( other != parent[curr] ) 
						stack.push( other );		
					if( !traveled[other] ) {
						parent[other] = curr;
					}
				}
				
			}

		}
	}
	
	
}
