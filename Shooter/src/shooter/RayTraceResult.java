package shooter;

import shooter.gameObjects.Peewee;

public class RayTraceResult {
	public final boolean hasHitEnemy;
	public final double distance;
	public final Peewee foundObject;
	public final double rayEndX;
	public final double rayEndY;

	public static RayTraceResult hitResult(Peewee foundObject, double rayEndX, double rayEndY, double distance) {
		return new RayTraceResult(true, foundObject, rayEndX, rayEndY, distance);
	}
	
	public static RayTraceResult missResult(double distance, double rayEndX, double rayEndY) {
		return new RayTraceResult(false, null, rayEndX, rayEndY, distance);
	}
	
	private RayTraceResult(boolean hasHitEnemy, Peewee foundObject, double rayEndX, double rayEndY, double distance) {
		this.hasHitEnemy = hasHitEnemy;
		this.distance = distance;
		this.foundObject = foundObject;
		this.rayEndX = rayEndX;
		this.rayEndY = rayEndY;
	}
}
