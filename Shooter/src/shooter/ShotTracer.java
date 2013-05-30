package shooter;

import shooter.gameObjects.Peewee;
import shooter.map.Map;
import shooter.map.TileType;
import geom.Point;

public class ShotTracer {
	private static final double accuracy = 0.03;
	private static final double enemyRadius = 0.4;

	public static RayTraceResult rayTraceEnemy(GameWorld world, double rotation, Point location, double maxDistance) {
		//unit direction vector
		double dx = Math.sin(Math.toRadians(rotation));
		double dy = Math.cos(Math.toRadians(rotation));
		
		dx *= accuracy;
		dy *= accuracy;
		
		double x = location.x;
		double y = location.y;
		
		double distancePerStep = Math.sqrt(dx*dx + dy*dy);
		
		Peewee[] enemies = getEnemyObjects(world);
		Point[] enemyLocations = new Point[enemies.length];
		
		for(int i = 0; i < enemies.length; i++) {
			enemyLocations[i] = enemies[i].getLocation();
		}
		
		for(double distanceCovered = 0; distanceCovered <= maxDistance; distanceCovered += distancePerStep) {
			if(isWallAt(world.map, x, y)) {
				return RayTraceResult.missResult(distanceCovered, x, y);
			}
			
			for(int i = 0; i < enemies.length; i++) {
				if(distanceTo(x, y, enemyLocations[i]) <= enemyRadius) {
					return RayTraceResult.hitResult(enemies[i], x, y, distanceCovered);
				}
			}
			
			x += dx;
			y += dy;
		}
		return RayTraceResult.missResult(maxDistance, x, y);
	}

	private static Peewee[] getEnemyObjects(GameWorld world) {
		GameObject[] enemyObjects = world.getGameObjectsByType(GameObjectType.PEEWEE);
		Peewee[] enemies = new Peewee[enemyObjects.length];
		
		for(int i = 0; i < enemies.length; i++) {
			enemies[i] = (Peewee) enemyObjects[i];
		}
		return enemies;
	}

	private static double distanceTo(double x, double y, Point destination) {
		double dx = x - destination.x;
		double dy = y - destination.y;
		
		return Math.sqrt(dx*dx + dy*dy);
	}

	private static boolean isWallAt(Map map, double x, double y) {
		int mapX = (int) Math.floor(x);
		int mapY = (int) Math.floor(y);
		return map.getTileAt(mapX, mapY) == TileType.WALL;
	}

}
