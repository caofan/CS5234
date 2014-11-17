
public class Test {
	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();
		double[] a = new double[2000000];
		for(int i = 0; i < a.length; i++)
			a[i] = Double.POSITIVE_INFINITY;
		System.out.println("" + (System.currentTimeMillis() - time1) / 1000.0);
	}
}
