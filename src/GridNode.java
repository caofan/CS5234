
/*
 * This class is used for the grid style node representation
 */
public class GridNode implements Node {
	final double x, y;
	final int index;
	
	public GridNode(double x1, double y2, int idx) {
		this.x = x1;
		this.y = y2;
		this.index = idx;
	}
	
	public double distanceTo(Node other) {
		if(! (other instanceof GridNode) )
			return -1;
		return Math.sqrt(Math.pow((Double)((GridNode)other).x -(Double) x, 2) + 
						 Math.pow((Double)((GridNode)other).y - (Double)y, 2));
		
	}
}
