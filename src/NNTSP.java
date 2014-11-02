import java.util.Random;


public class NNTSP {
	private AdjacencyListGraph g;
	private int[] nodes; //The order.
	private int[] order; //From order to nodes;
	private double[] dists;
	
	public NNTSP( AdjacencyListGraph g ) {
		this.g = g;
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
		while(!done) {
			
			double minWeight = Double.POSITIVE_INFINITY;
			int minWeightId = -1;
			for(int curr = 0; curr < nodes.length; curr++) {
				if(nodes[curr] >=0){
					for( Edge e: g.adj(curr) ) {
						int other = e.other(curr);
						if( nodes[other] < 0 && e.weight() < minWeight ) {
							minWeightId = other;
							minWeight = e.weight();
						}
					}
				}
			}
			if(minWeightId >= 0) {
				nodes[minWeightId] = o;
				order[o] = minWeightId;
				dists[minWeightId] = minWeight;
				o++;
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
	
	public static void main(String[] args) {
		Random random = new Random();
		AdjacencyListGraph g = new AdjacencyListGraph(10);
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j <= i; j++)
				System.out.print("\t");
			for(int j = i+1; j < 10; j++) {
				double w = random.nextDouble()*10;
				g.addEdge(new Edge(i,j, w));
				System.out.printf("%.2f\t" ,w);
			}
			System.out.println();
		}
		NNTSP tsp = new NNTSP(g);
		int[] order= tsp.getOrder();
		for(int i: order) {
			System.out.print(i + " " );
			System.out.println();
		}
		System.out.println(tsp.getWeight());
	}
}
