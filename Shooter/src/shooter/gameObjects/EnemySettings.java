package shooter.gameObjects;

public class EnemySettings {
	public final double firingRate;
	public final double firingRange = 5;
	public final double rocketSpeed = 0.023;
	public final double rocketDamage;
	public final double launchDistanceFromCenter;
	
	public EnemySettings(double firingRate, double rocketDamage, double distanceFromCenter) {
		this.firingRate = firingRate;
		this.rocketDamage = rocketDamage;
		this.launchDistanceFromCenter = distanceFromCenter;
	}
	
	
}
