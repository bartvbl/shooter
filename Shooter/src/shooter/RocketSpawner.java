package shooter;

import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import shooter.gameObjects.Rocket;
import geom.Point;

public class RocketSpawner {
	

	public static void spawnRocket(GameWorld world, Point rocketOrigin, double rocketHeading, double rocketSpeed, double rocketDamage) {
		Rocket.spawn(world, rocketOrigin, rocketHeading, rocketSpeed, rocketDamage);
	}

}
