import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

public class Main {
	public static void main(String[] args ) {
		Random random = new Random();
		PriorityQueue<Integer>  pq = new PriorityQueue();
		FibonacciHeap<Integer> h = new FibonacciHeap();
		BinaryHeap<Integer> bh;
		int[] v1 =   {1,1,1,2,2,2,3,3,3};
		int[] v2 =   {2,3,4,4,3,1,4,5,6};
		int[] w = {5,4,3,2,1,4,5,6,7};
		int iters = 100000;
		int rounds = 10;
		int rounds2 = 20;
		long time1 = System.currentTimeMillis();
		for(int j = 0; j < rounds; j++ ) {
			for(int i = 0; i< iters/rounds; i++ ){
				//Edge newEdge = new Edge(i,i,i/2);
				h.enqueue(random.nextInt(1000));
			}
			for(int i = 0; i<iters/rounds2; i++){
				//System.out.println("Number of nodes: " + h.getNumNodes());
				//System.out.println("RootList");
				//h.printRoot();
				//System.out.println("++++ENDRootList");
				h.deleteMin();
				//System.out.print(h.deleteMin().getKey() + " ");
				//System.out.println(min.getKey().toString());
				
			}
		}
		//System.out.println();
		long time2 = System.currentTimeMillis();
		for(int j = 0; j < rounds; j++ ) {
			for(int i = 0; i < iters/rounds; i ++ )
				pq.add(random.nextInt(1000));
			for(int i = 0; i < iters/rounds2; i ++ )
				pq.remove();
		}
		long time3 = System.currentTimeMillis();
		
		bh = new BinaryHeap(iters);
		for(int j = 0; j<rounds; j++) {
		for(int i = 0; i < iters/rounds; i ++ )
			bh.insert(i,random.nextInt(1000));
		for(int i = 0; i < iters/rounds2; i ++ )
			bh.pop();
		}
			//System.out.print(bh.dequeue() + " ");
		//System.out.println();
		long time4 = System.currentTimeMillis();
		
		
		System.out.println((time2-time1)*1.0/1000);
		System.out.println((time3-time1)*1.0/1000);
		System.out.println((time4-time3)*1.0/1000);
		HashMap<Integer, FibNode<Integer>> map = new HashMap();
		map.put(10, new FibNode(10));
		map.put(20, new FibNode(20));
		FibNode<Integer> removed = map.remove(10);
		System.out.println(removed.getKey());
	}
}
