package shooter.gameObjects.rocket;

import geom.Point;
import scene.sceneGraph.sceneNodes.RocketNode;
import shooter.Damageable;
import shooter.GameWorld;

public class PlayerRocket extends Rocket {
	public PlayerRocket(RocketNode rocketNode, GameWorld world) {
		super(rocketNode, world);
	}

	public static void spawn(GameWorld world, Point rocketOrigin, double rocketHeading, double rocketSpeed, double rocketDamage) {
		PlayerRocket rocket = new PlayerRocket(new RocketNode(), world);
		rocket.setHeading(rocketHeading);
		rocket.setLocation(rocketOrigin);
		rocket.setSpeed(rocketSpeed);
		rocket.setDamage(rocketDamage);
	}

	protected RayTraceResult doObstacleRayTrace() {
		return ShotTracer.rayTraceEnemy(world, heading, getRocketLocation(), speed);
	}
}
