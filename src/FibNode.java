

public class FibNode<T> implements Comparable<FibNode<T>>{
		private T key;
		private int degree;
		private boolean marked = false;
		private FibNode<T> parent;
		private FibNode<T> child; //The minimum child
		private FibNode<T> left;
		private FibNode<T> right;
		
		public FibNode(T ele) {
			key = ele;
			degree = 0;
			left = right = this;
		}
		
		public void setKey( T newKey ){
			key = newKey; //Used in decreaseKek.
		}
		
		public T getKey() {
			return key;
		}
		
		public int getDegree() {
			return degree;
		}
		
		public boolean isMakred() {
			return marked;
		}
		
		public FibNode<T> getParent() {
			return parent;
		}
		
		public FibNode<T> getChild() {
			return child;
		}
		
		public FibNode<T> getLeft() {
			return left;
		}
		
		public FibNode<T> getRight() {
			return right;
		}
		
		public void setParent( FibNode<T> p ) {
			this.parent = p;
		}
		
		public void setChild( FibNode<T> c ) {
			this.child = c;
		}
		
		public void setLeft( FibNode<T> l ) {
			this.left = l;
		}
		
		public void setRight( FibNode<T> r ) {
			this.right = r;
		}
		
		public void setDegree( int d ) {
			this.degree = d;
		}
		
		public void incrementDegree( int i ) {
			this.degree += i;
		}
		
		public void setMarked( boolean m ) {
			this.marked = m;
		}
		

		public int compareTo(FibNode<T> other) {
			return ((Comparable<T>) this.key).compareTo(other.getKey());
		}
	}