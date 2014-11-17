import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.StringTokenizer;

/*
 * This is only for the stability test.
 * Assuming a grid.
 */
public class NNTSP {
	private AdjacencyListGraph g;
	private int[] nodes; //The order.
	private int[] order; //From order to nodes;
	private double[] dists;
	private boolean isGrid;
	
	public NNTSP( AdjacencyListGraph g, boolean isGrid ) {
		this.g = g;
		this.isGrid = isGrid;
		nodes = new int[this.g.getN()];
		dists = new double[this.g.getN()];
		order = new int[this.g.getN()];
		for( int i = 0; i < nodes.length; i ++ ) {
			nodes[i] = -1;
			dists[i] = Double.POSITIVE_INFINITY;
		}
		NN();
	}
	
	public void NN() {
		for( int i = 0; i< g.getN(); i++ ) {
			if(nodes[i] < 0)
				nnNode(i);
		}
	}
	
	private void nnNode(int i) {
		dists[i] = 0.0;
		boolean done = false;
		nodes[i] = 0;
		order[0] = i;
		int o = 1;
		int curr = i;
		while(!done) {
			
			double minWeight = Double.POSITIVE_INFINITY;
			int minWeightId = -1;
			if(!isGrid) {
				double[] currDists = this.g.shortestPathAllWeight(curr);
				for(int j = 0; j < currDists.length; j++ )
				{
					if(nodes[j] <0 && currDists[j] < minWeight) 
					{
						minWeight = currDists[j];
						minWeightId = j;
					}
				}
				
			} else{
				for(int j = 0 ; j < g.getN(); j++ ) {
					if(nodes[j] <0) {
						double tmpDist = g.getNode(j).distanceTo(g.getNode(curr));
						if(tmpDist < minWeight) {
							minWeightId = j;
							minWeight = tmpDist;
						}
					}
				}
				
			}
			if(minWeightId >= 0) {
				nodes[minWeightId] = o;
				order[o] = minWeightId;
				dists[minWeightId] = minWeight;
				o++;
				curr = minWeightId;
			}else
				done = true;
		}
		
	}
	
	public int[] getOrder() {
		return order;
	}
	
	public double getWeight() {
		double sum = 0;
		for(int i = 0; i < dists.length; i++ ) {
			assert dists[i] >=0;
			sum += dists[i];
		}
		return sum;
	}
	
	public static void main(String[] args) throws Exception{
		long time1 = System.currentTimeMillis();
		BufferedReader in = new BufferedReader(new FileReader("roadNet-PA.txt"));
		AdjacencyListGraph g = null;
		String inStr = in.readLine();
		int lineCount = 1;
		while(inStr != null) {
			if( inStr.startsWith("#") && lineCount == 3) {
				StringTokenizer st = new StringTokenizer(inStr);
				st.nextToken();
				st.nextToken();
				int numNodes = Integer.parseInt(st.nextToken());
				System.out.println("Number of nodes: " + numNodes);
				g = new AdjacencyListGraph( numNodes * 2 );
			} else if( ! inStr.startsWith("#")) {
				StringTokenizer st = new StringTokenizer(inStr);
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				if( a < b){
					g.addEdge(new Edge(a,b,1));
				}
			}
			lineCount ++;
			inStr = in.readLine();
		}
		in.close();
		long time2 = System.currentTimeMillis();
		System.out.println("Done creating graph.");
		System.out.println("Time taken for graph creationg: " + (time2 - time1)/1000.0);
		NNTSP tsp = new NNTSP(g, false);
		System.out.println(tsp.getWeight());
		//g.addEdge(new Edge(8,9,random.nextDouble()*10));
		long time3 = System.currentTimeMillis();
		System.out.println("Time taken for 2-approx: " + (time3 - time2) / 1000.0);
		System.out.println("Total time: " + (time3 - time1) / 1000.0 );
		//getTrajectorySize(g, "data/real_road/Trajectory_pa");
		//getTrajectorySize(g, "data/real_road/Trajectory_after_improvement_pa");
	}
	
	
}
