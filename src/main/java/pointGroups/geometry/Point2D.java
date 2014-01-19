package pointGroups.geometry;

/**
 * 
 * The 2D point is only for representation used und not for rotation.
 * Therefore it is not a Quanternion.
 * 
 * @author max
 */
public class Point2D implements Point {

	private double[] comp;
	
	public Point2D(double x, double y){
		comp = new double[2];
		comp[0] = x;
		comp[1] = y;
	}
	
	public Point2D(double[] comp){
		this.comp = comp;
	}
	
	@Override
	public double[] getComponents() {
		return comp;
	}

}
