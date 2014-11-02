import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Random;
public class BinaryHeap<T extends Comparable<T>> {
	private int maxSize;
	private T[] data;
	private int[] heap;
	private int[] index; //The array such that index[i] is the location of item of index i in heap.
	private int size;
	
	public BinaryHeap() {
		this( 1000 );
		
	}
	
	public BinaryHeap(int initCapacity) {
		data = (T[]) new Comparable[initCapacity];
		size = 0;
		maxSize = initCapacity;
		index = new int[maxSize];
		heap = new int[maxSize];
		for( int i = 0; i < maxSize; i++)
			index[i] = -1;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void insert(int i, T ele) {
		checkI( i );
		if( contains(i) )
			return;
		data[i] = ele;
		heap[size] = i;
		index[i] = size;
		size ++;
		siftUp( size -1 );
	}
	
	/*
	 * Whether the heap has index i.
	 */
	public boolean contains( int i ) {
		checkI( i );
		return index[i] >= 0;
	}
	
	private void checkI( int i ) {
		if( i < 0 || i >= maxSize ) {
			System.err.println("Index not valid.");
			System.exit(1);
		}
			
	}
	
	/*
	 * Return the index of the item.
	 * @return the index of the item as it was added.
	 */
	public int pop() {
		if( size <= 0)
			return -1;
		int toRet = heap[0];
		delete( heap[0] );
		return toRet;
	}
	
	public T delete(int i){
		checkI( i );
		int idxHeap = index[i];
		T ele = data[heap[idxHeap]];
		swapNodes(idxHeap, size-1);
		size --;
		siftUp( idxHeap );
		siftDown( idxHeap );
		index[i] = -1;
		data[i] = null;
		return ele;
	}
	
	private void siftUp( int end ) {
		int parent = getParentIdx( end );
		
		while( parent >= 0 && largerThan( parent, end ) ){
			//System.out.print(parent + " ");
			swapNodes( parent, end );
			end = parent;
			parent = getParentIdx( end );
		}
		//System.out.println();
	}
	
	
	private void siftDown(int start) {
		int root = start;
		while( getLhsIdx( root ) < size){
			int swap = root;
			int child = getLhsIdx( root );
			if( largerThan( root, child )) {
				swap = child;
			}
			child = getRhsIdx(root);
			if( child  < size && largerThan( swap, child )) {
				swap = child;
			}
			if( swap != root ) {
				swapNodes(swap, root);
				root = swap;
			} else{
				return;
			}

		}
	}
	
	public void updateKey( int idx, T newValue ) {
		checkI( idx );
		int idxHeap = index[idx];
		data[idx] = newValue;
		siftUp( idxHeap );
		siftDown( idxHeap );
	}
	
	public void decreaseKey( int idx, T newValue ) {
		checkI(idx);
		
		if(data[idx].compareTo(newValue) <= 0) {
			System.err.println("New value larger than previous value. Nothing will be done");
			return;
		}
		data[idx] = newValue;
		int idxHeap = index[idx];
		siftUp( idxHeap );
	}
	
	public static void main(String[] args) {
		Random random = new Random();
		BinaryHeap<Integer> h = new BinaryHeap();
		int iters = 10;
		int[] values = new int[iters];
		for( int i = 0; i < iters; i++){
			//System.out.println(i);
			h.insert(i, random.nextInt(1000));
		}
		
		
		h.traverseHeap(0,0);
		for( int i = 0; i < iters; i++){
			
			h.updateKey(i, i);
			System.out.println("\n\n++++++++++++++");
			h.traverseHeap(0,0);
			//System.out.println(h.dequeue());
		}
		
		h.traverseHeap(0,0);
		for( int i = 0; i < iters; i++){
			
			h.pop();
			System.out.println("\n\n++++++++++++++");
			h.traverseHeap(0,0);
			//System.out.println(h.dequeue());
		}
	}
	
	private boolean largerThan( int a, int b ) {
		return data[heap[a]].compareTo(data[heap[b]]) > 0;
	}
	
	private void swapNodes( int a, int b) {
		index[ heap[a] ] = b;
		index[ heap[b] ] = a;
		int temp = heap[a];
		heap[a] = heap[b];
		heap[b] = temp;
		
	}
	
	private void traverseHeap(int start, int indent) {
		if( start >= size )
			return;
		for(int i = 0; i < indent; i++)
			System.out.print("\t");
		System.out.println(data[heap[start]]);
		traverseHeap(getLhsIdx(start), indent+1);
		traverseHeap(getRhsIdx(start), indent+1);
		
	}
	
	/*
	 * Get index of parent.
	 * @param currIdx The index of the current node.
	 * @return The index of the parent in the array.
	 */
	private int getParentIdx( int currIdx ) {
		return (currIdx-1) / 2;
	}
	
	/*
	 * Get the index of the left hand side child
	 * @param currIdx The index of the current node.
	 * @return The index of the left hand side child.
	 */
	private int getLhsIdx( int currIdx ) {
		return currIdx * 2 + 1;
	}
	
	/*
	 * Get the index of the right hand side child
	 * @param currIdx The index of the current node.
	 * @return The index of the right hand side child.
	 */
	private int getRhsIdx( int currIdx ) {
		return currIdx * 2 + 2;
	}
	
	/*
	 * Compare two elements of type T. Will return -1 if one < two, 0 if one = two,
	 * and 1 if one > two.
	 * @param one The first element of type T.
	 * @param two The second element.
	 * @return An integer of the result of comparison.
	 */
	private int compareNode( T one, T two ) {
		return ((Comparable<T>) one).compareTo(two);
	}
}

