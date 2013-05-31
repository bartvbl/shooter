package shooter;

import shooter.gameObjects.EnemyRocket;
import shooter.gameObjects.PlayerRocket;
import geom.Point;

public class RocketSpawner {
	public static void spawnRocket(GameWorld world, Point rocketOrigin, double rocketHeading, double rocketSpeed, double rocketDamage) {
		PlayerRocket.spawn(world, rocketOrigin, rocketHeading, rocketSpeed, rocketDamage);
	}

	public static void spawnEnemyRocket(GameWorld world, Point rocketOrigin, double rocketHeading, double rocketSpeed, double rocketDamage) {
		EnemyRocket.spawn(world, rocketOrigin, rocketHeading, rocketSpeed, rocketDamage);
	}
}
