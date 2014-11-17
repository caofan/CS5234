import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
public class RunTest {
	public static void main(String[] args) throws Exception{
		String type = "medium";
		Collection<File> files = FileUtils.listFiles(new File("data/"+type), null, false);
		ArrayList<Long> time = new ArrayList<Long>();
		int iters = 1;
		for(int i = 0; i<iters; i++ ){
			int count = 0;
			for(File f : files) {
				if(f.getName().startsWith("Edges")) {
					Scanner sc = new Scanner(f.getName().substring(f.getName().indexOf("node")+4));
					sc.useDelimiter("-");
					int nnodes = sc.nextInt();
					double p = Double.parseDouble(sc.next().substring(1));
					System.out.println(f.getName());
					AdjacencyListGraph g = GraphLoader.loadFromEdge(f.getAbsolutePath(), nnodes);
					long time1 = System.currentTimeMillis();
					TwoApproxTSP tsp = new TwoApproxTSP(g, "data/"+type+"/Trajectory_2_approx_node" + nnodes + "-p" + p);
					long time2 = System.currentTimeMillis();
					if(time.size() <= count)
						time.add(count, (Long)(long)0);
					time.set(count, time.get(count) + time2-time1);
					if(i == iters-1) {
						System.out.println("Time: " + time.get(count)/(iters*1000.0));
					}
					count ++;
				}
			}
		}
		//AdjacencyListGraph g = GraphLoader.loadFromEdge("data/medium/, nnodes)
		//AdjacencyListGraph g = GraphLoader.loadFromEdge("gridedges.txt", 50);
		//TwoApproxTSP tsp = new TwoApproxTSP(g, "CF_grid.out");
	}
}
