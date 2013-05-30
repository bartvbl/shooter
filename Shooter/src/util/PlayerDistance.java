package util;

import shooter.GameWorld;
import geom.Point;

public class PlayerDistance {
	public static double toPoint(GameWorld world, double x, double y) {
		Point mapLocation = world.controlledNode.getLocation();
		//map scrolls in opposite direction of camera
		double dx = -mapLocation.x - (double) x - 0.5d;
		double dy = -mapLocation.y - (double) y - 0.5d;
		double distanceToCameraCenter = Math.sqrt(dx*dx + dy*dy);
		return distanceToCameraCenter;
	}
}
