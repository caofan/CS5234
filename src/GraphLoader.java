import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;


public class GraphLoader {
	public static AdjacencyListGraph loadFromEdge(String filename, int nnodes) throws Exception {
		AdjacencyListGraph g = new AdjacencyListGraph(nnodes);
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String inStr = in.readLine();
		StringTokenizer st;
		int a, b;
		double w;
		while(inStr != null) {
			st = new StringTokenizer(inStr);
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			w = Double.parseDouble(st.nextToken());
			g.addEdge(new Edge(a,b,w));
			inStr = in.readLine();
		}
		in.close();
		return g;
	}
}
