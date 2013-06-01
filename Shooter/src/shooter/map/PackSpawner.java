package shooter.map;

import shooter.GameWorld;
import shooter.gameObjects.EnemySpawner;
import shooter.gameObjects.HealthPack;

public class PackSpawner {

	public static void spawnHealthPacks(MapFrustrumCullingNode chunkRootNode, ChunkDimension dimension, TileType[][] tileMap, GameWorld world) {
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				if(tileMap[i][j] == TileType.HEALTH) {
					HealthPack.spawn(i, j, world, chunkRootNode);
				}
			}
		}
	}

}
