package shooter.gameObjects.rocket;

import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.gameObjects.enemy.Enemy;
import shooter.gameObjects.enemy.Peewee;
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
		
		Enemy[] enemies = getEnemyObjectsByType(world, GameObjectType.PEEWEE);
		Point[] enemyLocations = new Point[enemies.length];
		Enemy[] boss = getEnemyObjectsByType(world, GameObjectType.BOSS);
		boolean bossHasSpawned = boss.length == 1;
		Point bossLocation = null;
		if(bossHasSpawned) {
			bossLocation = boss[0].getLocation();
		}
		
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
			if(bossHasSpawned) {
				if(distanceTo(x, y, bossLocation) <= enemyRadius) {
					return RayTraceResult.hitResult(boss[0], x, y, distanceCovered);
				}
			}
			
			x += dx;
			y += dy;
		}
		return RayTraceResult.outOfRangeResult(maxDistance, x, y);
	}

	private static Enemy[] getEnemyObjectsByType(GameWorld world, GameObjectType enemyObjectType) {
		GameObject[] enemyObjects = world.getGameObjectsByType(enemyObjectType);
		Enemy[] enemies = new Enemy[enemyObjects.length];
		
		for(int i = 0; i < enemies.length; i++) {
			enemies[i] = (Enemy) enemyObjects[i];
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

	public static RayTraceResult rayTracePlayer(GameWorld world, double rotation, Point location, double maxDistance) {
		//unit direction vector
		double dx = Math.sin(Math.toRadians(rotation));
		double dy = Math.cos(Math.toRadians(rotation));
		
		dx *= accuracy;
		dy *= accuracy;
		
		double x = location.x;
		double y = location.y;
		
		double distancePerStep = Math.sqrt(dx*dx + dy*dy);
		
		Point playerLocation = world.controlledNode.getLocation().negate();
		
		for(double distanceCovered = 0; distanceCovered <= maxDistance; distanceCovered += distancePerStep) {
			if(isWallAt(world.map, x, y)) {
				return RayTraceResult.missResult(distanceCovered, x, y);
			}
			
			if(distanceTo(x, y, playerLocation) <= enemyRadius) {
				return RayTraceResult.hitResult(world.player, x, y, distanceCovered);
			}
			
			x += dx;
			y += dy;
		}
		return RayTraceResult.outOfRangeResult(maxDistance, x, y);
	}

}
