import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/*
 * This class is an implementation of a random (1+e)-approximation algorithm
 * by 
 */
public class RPM {
	public static void main(String[] args ) throws Exception{
		runRPM();
	}
	public static void runRPM() throws Exception {
		ArrayList<Edge> M = new ArrayList<Edge>();
		double E = 0.0002;
		final int k = (int)(1/E);
		int N = 0;
		int nnodes = 10000;
		boolean[] used = new boolean[nnodes];
		//ArrayList<GridNode> W = new ArrayList<GridNode>(nnodes);
		//HashSet<Integer> W = new HashSet<Integer>();
		GridNode[] V;
		V = generateNodes(nnodes, 1000);
		for(int i = 0; i < used.length; i++){
			used[i] = false;
			//W.add(i);
		}
		boolean done = false;
		int remain = nnodes;
		Random random = new Random();
		System.out.println(k);
		for(int i = 0; i < k && remain > 0; i++) {
			if(i%5000 == 0)
				System.out.println("iter " + i);
			int tmpidx = random.nextInt(nnodes-1);
			while(used[tmpidx]) {
				tmpidx = random.nextInt(nnodes-1);
			}

			GridNode curr = V[tmpidx];
			int nnc = getNearNode(curr.index, V, used, true);
			int nn;
			if(used[nnc]) {
				nn = getNearNode(curr.index, V, used, false);
			}else
				nn = nnc;
			int nnnn = getNearNode(nn, V, used, true);
			if(curr.distanceTo(V[nn]) <= (1+E)*Math.max(curr.distanceTo(V[nnc]), V[nn].distanceTo(V[nnnn]))){
				M.add(new Edge(curr.index, nn, curr.distanceTo(V[nn])));
				used[curr.index] = true;
				used[nn] = true;
				N++;
				remain -= 2;
			}
			
		}
		System.out.println(N + " " + done);
		
		
		while(remain > 0) {
			
			int tmpidx = random.nextInt(nnodes);
			while(used[tmpidx]) {
				tmpidx = random.nextInt(nnodes-1);
			}

			GridNode curr = V[tmpidx];
			int nnc = getNearNode(curr.index, V, used, true);
			int nn;
			if(used[nnc]) {
				nn = getNearNode(curr.index, V, used, false);
			}else
				nn = nnc;
			int nnnn = getNearNode(nn, V, used, true);
			M.add(new Edge(curr.index, nn, curr.distanceTo(V[nn])));
			used[curr.index] = true;
			used[nn] = true;
			if(curr.distanceTo(V[nn]) <= (1+E)*Math.max(curr.distanceTo(V[nnc]), V[nn].distanceTo(V[nnnn]))){
				N++;
			}
			remain -= 2;
		}
		System.out.println(N + " " + done + " " + M.size());
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gridmatching.txt")));
		double totalWeight = 0;
		for(Edge e: M) {
			//System.out.println(e.getU() + " " + e.getV() + " " + e.weight());
			out.println(e.getU() + " " + e.getV() );
			totalWeight += e.weight();
			
		}
		System.out.println("Total weight: " + totalWeight);
		out.close();
	}
	
	public static int getNearNode(int v, GridNode[] nodes, boolean[] used, boolean careless) {
		double minWeight = Double.POSITIVE_INFINITY;
		int minWeightId = -1;
		for(int i = 0; i < nodes.length; i++ ){
			if((careless || !used[i]) && i != v ) {
				double currDist = nodes[v].distanceTo(nodes[i]);
				if(currDist < minWeight) {
					minWeight = currDist;
					minWeightId = i;
				}
			}
		}
		return minWeightId;
	}
	
	public static GridNode[] generateNodes(int n, int D) throws Exception{
		if(n % 2 != 0)
			return null;
		Random random = new Random();
		GridNode[] nodes = new GridNode[n];
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gridnodes.txt")));
		for(int i = 0; i<n; i++) {
			int a = random.nextInt(D);
			int b = random.nextInt(D);
			System.out.println(a + " " + b);
			out.println(i + " " + a + " " + b);
			nodes[i]=(new GridNode(a,b, i));
		}
		out.close();
		/*
		out = new PrintWriter(new BufferedWriter(new FileWriter("gridedges.txt")));
		for(int i=0; i<n; i++ ) {
			for(int j = i+1; j <n;j++){
				out.println(i + " " + j + " " + nodes[i].distanceTo(nodes[j]));
			}
		}
		out.close();
		*/
		return nodes;
	}
}
