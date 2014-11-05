import java.util.ArrayList;
import java.util.Random;


/*
 * An implementation of the 2-approximation algorithm based on MST.
 */
public class TwoApproxTSP {
	AdjacencyListGraph g;
	
	public TwoApproxTSP( AdjacencyListGraph gr ) {
		this.g = gr;
		PrimMST mst = new PrimMST(this.g);
		System.out.println("Done prim");
		ArrayList<Edge> mstEdges = mst.getEdges();
		AdjacencyListGraph treeGraph = new AdjacencyListGraph(this.g.getN());
		for(Edge e: mstEdges) {
			treeGraph.addEdge(e);
			//System.out.println(e);
		}
		
		/*
		ArrayList<ArrayList<Integer>> dfs = g.dfs();
		for(ArrayList<Integer> subOrder: dfs ) {
			for(Integer i: subOrder) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
		*/
		
		ArrayList<ArrayList<Integer>> mstdfs = treeGraph.dfs();
		for(ArrayList<Integer> subOrder: mstdfs ) {
			for(Integer i: subOrder) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		Random random = new Random();
		int iters = 10000;
		AdjacencyListGraph g = new AdjacencyListGraph(iters);
		for( int i = 0; i < iters; i ++ ) {
			for( int j = i+1; j < iters; j+=1 )
				g.addEdge(new Edge(i,j,random.nextDouble()*10));
			if( i%1000 == 0 )
				System.out.println(i);
			
		}
		System.out.println("Done creating graph.");
		//g.addEdge(new Edge(8,9,random.nextDouble()*10));
		TwoApproxTSP tsp = new TwoApproxTSP(g);
	}
}
