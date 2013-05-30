package shooter.map;

import java.util.Random;

import shooter.GameWorld;
import shooter.gameObjects.Door;
import shooter.gameObjects.EnemySpawner;

public class MonsterSpawner {
	
	
	public static void spawnMonsters(MapFrustrumCullingNode chunkRootNode, ChunkDimension dimension, TileType[][] tileMap, GameWorld world) {
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				if(tileMap[i][j] == TileType.MONSTER) {
					EnemySpawner spawner = EnemySpawner.createSpawner(i, j, world);
					world.addGameObject(spawner);
				}
			}
		}
	}

}
