import java.util.ArrayList;
import java.util.HashMap;


public class FibonacciHeap<T> {
	private FibNode<T> minNode = null;

	private int nTrees = 0;
	private int nNodes = 0;
	private int nMarked = 0;
	
	public void enqueue( T ele ) {
		FibNode<T> newNode = new FibNode(ele);
		this.minNode = mergeTwoLists(this.minNode, newNode);
		
		nTrees ++;
		nNodes ++;
	}
	
	/*
	 * Add the elements of other to the current root list. And update this.minNode;
	 * @param other The node of other elements
	 * @return the minimum Node<T>
	 */
	private FibNode<T> mergeTwoLists(FibNode<T> one, FibNode<T> other ) {
		if( one == null && other == null)
			return null;
		else if( one != null && other == null )
			return one;
		else if( other != null && one == null )
			return other;
		else{
			FibNode<T> temp = one.getRight();
			one.setRight(other.getRight());
			other.getRight().setLeft(one);
			other.setRight( temp );
			temp.setLeft( other );
			if(other.compareTo(one) < 0) {
				return other;
			}
			else {
				return one;
			}
		}
	}
	
	public FibNode<T> min() {
		return this.minNode;
	}
	
	public boolean isEmpty() {
		return minNode == null;
	}
	
	public FibNode<T> deleteMin() {
		if(this.minNode == null )
			return null;
		FibNode<T> minNode = this.minNode;
		//Remove minNode from list
		if( this.minNode.getRight() == this.minNode ){
			this.minNode = null;
			if( minNode.getChild() == null )
				return minNode;
		}
		else{
			minNode.getLeft().setRight(minNode.getRight());
			minNode.getRight().setLeft(minNode.getLeft());
			this.minNode = minNode.getRight();//This is arbitrary
		}
		/*
		if(nNodes <= 5){
			System.out.println("START1");
			printRoot();
			System.out.println("END1");
		}
		*/
			
		nTrees --;
		//Add all children of the minNode to the root list.
		if( minNode.getChild() != null ){
			FibNode<T> c = minNode.getChild();
			do {
				c.setParent( null );
				c = c.getRight();
				nTrees ++;
			} while(c != minNode.getChild());
			this.minNode = mergeTwoLists( this.minNode, minNode.getChild() );
		}
		/*
		if(nNodes <= 5){
			System.out.println("\nSTART2");
			printRoot();
			System.out.println("END2");
		}
		*/
		ArrayList<FibNode<T>> trees = new ArrayList(2*nTrees);
		HashMap<Integer,FibNode<T>> degreeMap = new HashMap(nTrees);
		for(FibNode<T> t = this.minNode; trees.isEmpty() || t != this.minNode; t = t.getRight())
			trees.add(t);
		//System.out.println(nTrees == trees.size());
		int i = 0;
		while( i < trees.size() ) {
			FibNode<T> currT = trees.get(i);
			int currDeg = currT.getDegree();
			if( degreeMap.get( currDeg ) == null) {
				degreeMap.put(currDeg, trees.get(i));
			}
			else{
				while( degreeMap.containsKey(currDeg)) {
					
					FibNode<T> otherT = degreeMap.remove( currDeg );
					FibNode<T> large = currT.compareTo(otherT) > 0 ? currT : otherT;
					FibNode<T> small = currT.compareTo(otherT) > 0 ? otherT : currT;
					//if(nNodes == 5)
					//	System.out.println("large: " + large.getKey() + " small: " + small.getKey());
					//large is about to be removed from root list
					large.getLeft().setRight(large.getRight());
					large.getRight().setLeft(large.getLeft());
					large.setLeft(large);
					large.setRight(large);
					small.setChild(mergeTwoLists(small.getChild(), large));
					large.setParent(small);
					small.incrementDegree(1);
					nTrees --;
					currT = small;
					currDeg = currT.getDegree();
					//System.out.println("AA");
				}
				trees.add( currT );
			}
			//When the minNode and the currT are equal, the currT should be made
			//the minNode as the minNode might not be at the root.
			if(currT.compareTo(this.minNode) <= 0 )
				this.minNode = currT;
			i++;
		}
		/*
		if(nNodes <= 5){
			System.out.println("START3");
			printRoot();
			System.out.println("END3");
		}
		*/
		nNodes --;
		return minNode;
	}

	
	//Helper methods
	public void printRoot() {
		int indent = 0;
		if( isEmpty() )
			System.out.println("Empty");
		else {
			printNode(this.minNode,0);
		}
	}
	
	public void printNode(FibNode<T> n, int indent) {
		if( n != null) {
			FibNode<T> curr = n;
			do {
				for( int i = 0; i < indent; i++ )
					System.out.print("\t");
				System.out.println(curr.getKey());
				printNode(curr.getChild(), indent + 1);
				curr = curr.getRight();
			}while(curr != n);
		}
			
	}
	
	public int getNumNodes() {
		return this.nNodes;
	}
	
}
