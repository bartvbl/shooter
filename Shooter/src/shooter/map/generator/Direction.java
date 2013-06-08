package shooter.map.generator;

public enum Direction {
	NORTH(0, 1, 0), SOUTH(0, -1, 180), EAST(1, 0, 90), WEST(-1, 0, 270), ;

	public final int dx;
	public final int dy;
	public final double rotation;

	private Direction(int dx, int dy, double rotation) {
		this.dx = dx;
		this.dy = dy;
		this.rotation = rotation;
	}
}
