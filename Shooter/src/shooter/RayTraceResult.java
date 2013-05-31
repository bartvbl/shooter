package shooter;

public class RayTraceResult {
	public final boolean hasHitEnemy;
	public final double distance;
	public final Damageable foundObject;
	public final double rayEndX;
	public final double rayEndY;
	public final boolean isOutOfRange;

	public static RayTraceResult hitResult(Damageable foundObject, double rayEndX, double rayEndY, double distance) {
		return new RayTraceResult(true, false, foundObject, rayEndX, rayEndY, distance);
	}
	
	public static RayTraceResult missResult(double distance, double rayEndX, double rayEndY) {
		return new RayTraceResult(false, false, null, rayEndX, rayEndY, distance);
	}
	
	public static RayTraceResult outOfRangeResult(double distance, double rayEndX, double rayEndY) {
		return new RayTraceResult(false, true, null, rayEndX, rayEndY, distance);
	}

	private RayTraceResult(boolean hasHitEnemy, boolean outOfRange, Damageable foundObject, double rayEndX, double rayEndY, double distance) {
		this.hasHitEnemy = hasHitEnemy;
		this.distance = distance;
		this.foundObject = foundObject;
		this.rayEndX = rayEndX;
		this.rayEndY = rayEndY;
		this.isOutOfRange = outOfRange;
	}

}
