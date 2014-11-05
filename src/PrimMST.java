import java.util.ArrayList;
import java.util.Random;


/*
 * An implementation of the Prim's algorithm for finding the Minimal spanning tree of a connected graph.
 */
public class PrimMST {
	private Edge[] edges; //The edge that connects each node to the MST
	private double[] dists; //Distancance 
	private boolean[] visited; //Keep track of nodes of whether they are visited
	private Graph g;
	private BinaryHeap<Double> vheap; //Heap to store the distance to nodes.
	private double totalWeight;
	
	public PrimMST( Graph g ) {
		this.g = g;
		this.totalWeight = -1;
		edges = new Edge[g.getN()];
		dists = new double[g.getN()];
		visited = new boolean[g.getN()];
		for(int i = 0; i < g.getN(); i++ ) {
			visited[i] = false;
			dists[i] = Double.POSITIVE_INFINITY;
		}
		vheap = new BinaryHeap<Double>(g.getN());
		getMST();
	}
	
	/*
	 * To run the Prim's algorithm and populate edges[], dists[].
	 */
	private void getMST() {
		
		for(int i=0; i < g.getN(); i++ ) {
			if( !visited[i] ) {
				//System.out.println(i);
				primNode(i);
			}
		}
		getWeight();
		System.out.println(totalWeight);
		
	}
	
	/*
	 * Get the total weight of the minimal spannint tree/forest.
	 * @return the total weight in a double type.
	 */
	public double getWeight() {
		if( this.totalWeight < 0)
		{
			for( int i = 0 ; i < dists.length; i++ )
				totalWeight += dists[i];
		}
		return this.totalWeight;
	}
	
	/*
	 * Start from a node, run Prim's algorithm
	 * @param i The vertex
	 */
	private void primNode(int i) {
		if( visited[i] ) 
			return;
		dists[i] = 0.0;
		vheap.insert(i, dists[i]);
		while(!vheap.isEmpty()) {
			int curr = vheap.pop();
			visited[curr] = true;
			for( Edge e: g.adj(curr)) {
				int other = e.other(curr);
				if( ! visited[other] && e.weight() < dists[other] ) {
					if( vheap.contains(other))
						vheap.decreaseKey(other, e.weight());
					else
						vheap.insert(other, e.weight());
					edges[ other ] = e;
					dists[ other ]= e.weight();
				}
			}
		}
	}
	
	public ArrayList<Edge> getEdges() {
		ArrayList<Edge> nonNullEdges = new ArrayList<Edge>(edges.length);
		for(int i = 0; i < edges.length; i++) {
			Edge e = edges[i];
			if(e!=null)
				nonNullEdges.add(e);
		}
		return nonNullEdges;
	}
	
	
	public static void main(String[] args) {
		Random random = new Random();
		AdjacencyListGraph g = new AdjacencyListGraph(10);
		for( int i = 0; i < 8; i ++ ) {
			for( int j = i+1; j < 8; j++ )
				g.addEdge(new Edge(i,j,random.nextDouble()*10));
			
		}
		g.addEdge(new Edge(8,9,random.nextDouble()*10));
		ArrayList<ArrayList<Integer>> dfs = g.dfs();
		for(ArrayList<Integer> subOrder: dfs ) {
			for(Integer i: subOrder) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
		PrimMST mst = new PrimMST(g);
		ArrayList<Edge> edges = mst.getEdges();
		for(Edge e: edges) {
			if(e != null)
				System.out.println(e);
		}
	}
	
	
	
}
