
/*
 * An implementation of the Prim's algorithm for finding the Minimal spanning tree of a connected graph.
 */
public class PrimMST {
	private Edge[] edges; //The edge that connects each node to the MST
	private double[] dists; //Distancance 
	private boolean[] visited; //Keep track of nodes of whether they are visited
	private Graph g;
	private BinaryHeap<Double> vheap; //Heap to store the distance to nodes.
	
	public PrimMST( Graph g ) {
		this.g = g;
		edges = new Edge[g.getN()];
		dists = new double[g.getN()];
		visited = new boolean[g.getN()];
		for(int i = 0; i < g.getN(); i++ ) {
			visited[i] = false;
			dists[i] = Double.POSITIVE_INFINITY;
		}
		vheap = new BinaryHeap<Double>();
		getMST();
	}
	
	/*
	 * To run the Prim's algorithm and populate edges[], dists[].
	 */
	private void getMST() {
		
		for(int i=0; i < g.getN(); i++ ) {
			primNode(i);
		}
	}
	
	/*
	 * Start from a node, run Prim's algorithm
	 * @param i The vertex
	 */
	private void primNode(int i) {
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
	
	public Edge[] getEdges() {
		return edges;
	}
	
	public static void main(String[] args) {
		AdjacencyListGraph g = new AdjacencyListGraph(10);
		for( int i = 0; i < 8; i ++ ) {
			for( int j = i+1; j < 8; j++ )
				g.addEdge(new Edge(i,j,20-i-j*1.0));
			
		}
		PrimMST mst = new PrimMST(g);
		Edge[] edges = mst.getEdges();
		for(Edge e: edges) {
			if(e != null)
				System.out.println(e);
		}
	}
	
}
