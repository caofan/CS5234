import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;


/*
 * A Graph class implemented using Adjacency List.
 */
public class AdjacencyListGraph implements Graph{
	private final int N; //The number of vertices.
	private int M; //The number of edges.
	private Node[] nodes;
	private ArrayList<Edge>[] adj;
	
	public AdjacencyListGraph(int N) {
		this.N = N;
		this.M = 0;
		nodes = new Node[this.N];
		this.adj = new ArrayList[this.N];
		for(int i = 0; i < this.N; i++ )
			this.adj[i] = new ArrayList<Edge>();
	}
	
	public void addNode(Node n, int i) {
		assert i >=0 && i < this.N;
		nodes[i] = n;
	}
	
	public Node getNode(int i ) {
		assert i >=0 && i < this.N;
		return nodes[i];
	}
	
	public void addEdge( Edge e ) {
		assert e.getV() >= 0 && e.getV() < N;
		assert e.getU() >= 0 && e.getV() < N;
		adj[ e.getV() ].add( e );
		adj[ e.getU() ].add( e );
		this.M ++;
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
		int[] parent = new int[this.N];
		for(int i = 0; i < traveled.length; i++ ) {
			traveled[i] = false;
			parent[i] = -1;
		}
		int traveledSize = 0;
		
		for(int i = 0; i < traveled.length; i++) {
			if(!traveled[i] && this.adj[i].size() > 0) {
				//System.out.println("now starting: " + i);
				ArrayList<Integer> currOrder = new ArrayList<Integer>();
				dfsNode( i, traveled, parent, currOrder );
				order.add(currOrder);
				traveledSize += currOrder.size();
				//System.out.println("last " + currOrder.get(currOrder.size() -1));
			}
			
		};
		return order;
	}
	
	public void dfsNode( int i, boolean[] traveled, int[] parent, ArrayList<Integer> currOrder ) {
		int count = 0;
		double totalWeight = 0;
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(i);
		while( !stack.isEmpty()) {
			int curr = stack.pop();
			currOrder.add( curr );
			
			if(!traveled[curr]) {
				if(parent[curr] >= 0 ) 
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
			//if(count % 100000 == 0) 
			//	System.out.println(count);
			count ++;
			

		}
	}
	
	/*
	 * Calculate the shortest path between nodes a and b by their index.
	 * @param a Index of first node
	 * @param b Index of second node
	 */
	public Iterable<Edge> shortestPath(int a, int b) {
		assert a>=0 && a < this.N && b>=0 && b < this.N;
		ArrayList<Edge> edges = new ArrayList<Edge>();
		Edge[] fromEdge = new Edge[this.N];
		double[] currDists = new double[this.N];
		Arrays.fill(currDists, Double.POSITIVE_INFINITY);
		dijstra_helper(fromEdge, currDists, a, b);
		int tmp = b;
		while( tmp != a) {
			edges.add(fromEdge[tmp]);
			tmp = fromEdge[tmp].other(tmp);
		}
		return edges;
	}
	
	public double shortestPathWeight(int a, int b) {
		assert a>=0 && a < this.N && b>=0 && b < this.N;
		ArrayList<Edge> edges = new ArrayList<Edge>();
		Edge[] fromEdge = new Edge[this.N];
		double[] currDists = new double[this.N];
		Arrays.fill(currDists, Double.POSITIVE_INFINITY);
		dijstra_helper(fromEdge, currDists, a, b);
		//System.out.println(currDists[b]);
		return currDists[b];
	}
	
	public double[] shortestPathAllWeight(int a) {
		assert a>=0 && a < this.N;
		Edge[] fromEdge = new Edge[this.N];
		double[] currDists = new double[this.N];
		Arrays.fill(currDists, Double.POSITIVE_INFINITY);
		dijstra_helper(fromEdge, currDists, a, -1);
		
		return currDists;
	}
	
	private void dijstra_helper(Edge[] fromEdge, double[] dists, int a, int b ) {
		BinaryHeap<Double> heap = new BinaryHeap<Double>(this.N);
		heap.insert(a, 0.0);
		int curr;
		boolean found = false;
		dists[a] = 0;
		while(!heap.isEmpty() && !found) {
			curr = heap.pop();
			if(b >= 0 && curr == b) {
				break;
			}
				
			for(Edge e:adj(curr)) {
			
				int other = e.other(curr);
				double tmpDist = dists[curr] + e.weight();
				if(tmpDist < dists[other]) {
					dists[other] = tmpDist;
					fromEdge[other ] = e;
					if(heap.contains(other)) {
						heap.decreaseKey(other, dists[other]);
					}else
						heap.insert(other, dists[other]);
				}
				if(other == b) {
					found = true;
					//System.out.println("before return " + dists[b]);
					break; 
				}
				
			}
		}
	}
	
}
