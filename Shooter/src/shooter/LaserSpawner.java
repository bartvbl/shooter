package shooter;

import org.lwjgl.util.ReadableColor;

import scene.sceneGraph.sceneNodes.LaserNode;

import geom.Point;

public class LaserSpawner {

	public static void spawnLaser(GameWorld world, ReadableColor laserColour, Point location, double rotation) {
		LaserNode laser = new LaserNode(world.scene, laserColour);
		//laser.setLocation((float)location.x, (float)location.y, 0.3f);
		//laser.setRotationZ(rotation);
	}

}
