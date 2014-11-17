import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;


/*
 * An implementation of the 2-approximation algorithm based on MST.
 */
public class TwoApproxTSP {
	AdjacencyListGraph g;
	
	public TwoApproxTSP( AdjacencyListGraph gr, String outfile ) throws Exception {
		this.g = gr;
		PrimMST mst = new PrimMST(this.g);
		//System.out.println("Done prim");
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
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
		ArrayList<ArrayList<Integer>> mstdfs = treeGraph.dfs();
		double dfsCost = 0;
		for(ArrayList<Integer> subOrder: mstdfs ) {
			int last = -1;
			if( subOrder.size() > 0 ) {
				for(Integer i: subOrder) {
					out.println(i );
					if(last != -1) {
						for(Edge e: this.g.adj(last)) {
							if(e.other(last)== i) {
								dfsCost += e.weight();
								last = i;
							}
						}
					} else{
						last = i;
					}
				}
				out.println("\n");
			}
			System.out.println("2-approx Weight: " + dfsCost);
		}
		out.close();
	}
	
	public static void main(String[] args) throws Exception{
		
		/*
		//Random random = new Random();
		int iters = 10000;
		AdjacencyListGraph g = new AdjacencyListGraph(iters);
		for( int i = 0; i < iters; i ++ ) {
			for( int j = i+1; j < iters; j+=1 )
				g.addEdge(new Edge(i,j,j-i));
			if( i%1000 == 0 )
				System.out.println(i);
			
		}
		System.out.println("Done creating graph.");
		//g.addEdge(new Edge(8,9,random.nextDouble()*10));
		TwoApproxTSP tsp = new TwoApproxTSP(g, "test.txt");
		*/
		//runFromSNAPData("roadNet-CA-2.txt", "roadNet-CA-2_out.txt");
		
		runFromSNAPData("roadNet-PA.txt", "roadNet-PA_out.txt");
		runFromSNAPData("roadNet-TX.txt", "roadNet-TX_out.txt");
		//runFromTSPLIB("att48.tsp","att48_out.txt", "att48.opt.tour");
		//runFromTSPLIB("berlin52-2.tsp","berlin52_out.txt", "berlin52.opt.tour");
		//runFromTSPLIB("usa13509.tsp","usa13509_out.txt", "berlin52.opt.tour");
		
	}
	
	public static void runFromSNAPData( String filename, String outfile ) throws Exception {
		long time1 = System.currentTimeMillis();
		BufferedReader in = new BufferedReader(new FileReader(filename));
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
		//g.addEdge(new Edge(8,9,random.nextDouble()*10));
		TwoApproxTSP tsp = new TwoApproxTSP(g, outfile);
		long time3 = System.currentTimeMillis();
		System.out.println("Time taken for 2-approx: " + (time3 - time2) / 1000.0);
		System.out.println("Total time: " + (time3 - time1) / 1000.0 );
		if(filename.contains("PA")) {
			;
		//getTrajectorySize(g, "data/real_road/Trajectory_pa");
		//getTrajectorySize(g, "data/real_road/Trajectory_after_improvement_pa");
		} else if (filename.contains("TX")) {
			getTrajectorySize(g, "data/real_road/Trajectory_texas");
			getTrajectorySize(g, "data/real_road/Trajectory_after_improvement_texas");
		}
	}
	
	public static void getTrajectorySize(AdjacencyListGraph g, String filename ) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		int last = -1;
		int curr = -1;
		int first = -1;
		int count = 0;
		String inStr = in.readLine();
		double totalSize = 0;
		while(inStr != null && !inStr.trim().equals("")) {
			count += 1;
			if(count%10000 == 0)
				System.out.println(count);
			curr = Integer.parseInt(inStr);
			if(last != -1) {
				double currValue = g.shortestPathWeight(last,curr);
				if(currValue != Double.POSITIVE_INFINITY)
					totalSize += currValue;
				else
					System.out.println("Infinity: " + last + " " + curr);
			}
			else
				first = curr;
			last = curr;
			inStr = in.readLine();
		}
		double currValue = g.shortestPathWeight(last,curr);
		if(currValue != Double.POSITIVE_INFINITY)
			totalSize += currValue;
		else
			System.out.println("Infinity: " + last + " " + curr);
		in.close();
		System.out.println(filename + " weight: " + totalSize);
	}
	
	public static void runFromTSPLIB(String filename, String outfile, String optFile) throws Exception {
		long time1 = System.currentTimeMillis();
		class TempNode {
			int id;
			double x;
			double y;
			
			protected TempNode( int i, double x, double y) {
				this.id = i;
				this.x = x;
				this.y = y;
			}
		}
		BufferedReader in = new BufferedReader(new FileReader(filename));
		AdjacencyListGraph g;
		String inStr = in.readLine();
		ArrayList<TempNode> nodes = new ArrayList<TempNode>();
		int nNodes = -1;
		int count = 0;
		while(inStr != null && ! inStr.trim().equals("")){
			if(inStr.startsWith("DIMENSION")) {
				StringTokenizer st = new StringTokenizer(inStr);
				st.nextToken();
				
				try {
					nNodes = Integer.parseInt(st.nextToken());
				} catch( NumberFormatException e)  {
					nNodes = Integer.parseInt(st.nextToken());
				}
				
			} else{
				StringTokenizer st = new StringTokenizer(inStr);
				String token1 = st.nextToken();
				try{
					int idx = Integer.parseInt( token1 );
					double x = Double.parseDouble(st.nextToken());
					double y = Double.parseDouble(st.nextToken());
					nodes.add(new TempNode(idx, x, y));
					count ++;
				} catch(NumberFormatException e) {
					
				}
			}
			inStr = in.readLine();
		}
		in.close();
		g = new AdjacencyListGraph(count +1);
		for( int i = 1; i< count +1; i++) {
			TempNode a = nodes.get(i-1);
			for(int j = i+1; j < count+1; j ++) {
				TempNode b = nodes.get(j-1);
				double dist = Math.sqrt(Math.pow(a.x - b.x,2) + Math.pow(a.y-b.y, 2));
				g.addEdge(new Edge(a.id, b.id, dist));
			}
		}
		long time2 = System.currentTimeMillis();
		System.out.println("Done creating graph.");
		System.out.println("Time taken for graph creationg: " + (time2 - time1)/1000);
		//g.addEdge(new Edge(8,9,random.nextDouble()*10));
		TwoApproxTSP tsp = new TwoApproxTSP(g, outfile);
		
		in = new BufferedReader(new FileReader(optFile));
		ArrayList<Integer> order = new ArrayList<Integer>();
		inStr = in.readLine();
		while(inStr != null){
			StringTokenizer st = new StringTokenizer(inStr);
			String token1 = st.nextToken();
			try{
				int idx = Integer.parseInt( token1 );
				if(idx < 0 )
					idx = idx * -1;
				order.add(idx);
			} catch(NumberFormatException e) {
					
			}
			inStr = in.readLine();
		}
		in.close();
		double optWeight = 0;
		for(int i = 0; i < order.size() - 1; i++ ){
			TempNode a = nodes.get( order.get(i) - 1);
			TempNode b = nodes.get( order.get(i + 1) - 1);
			double dist =  Math.sqrt(Math.pow(a.x - b.x,2) + Math.pow(a.y-b.y, 2));
			System.out.println(a.id + " " + b.id + " " + dist);
			optWeight += dist;
		}
		System.out.println("Opt weight: " + optWeight);
		long time3 = System.currentTimeMillis();
		System.out.println("Time taken for 2-approx: " + (time3 - time2) / 1000);
		System.out.println("Total time: " + (time3 - time1) / 1000 );
	}
}
