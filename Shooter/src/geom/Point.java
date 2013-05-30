package geom;

public class Point {
	public final double x;
	public final double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double distanceTo(Point point) {
		double dx = point.x - x;
		double dy = point.y - y;
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public String toString() {
		return "("+x+", "+y+")";
	}

	public Point negate() {
		return new Point(-x, -y);
	}
}
