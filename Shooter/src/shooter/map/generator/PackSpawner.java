package shooter.map.generator;

import scene.sceneGraph.sceneNodes.FrustrumCullingNode;
import shooter.GameWorld;
import shooter.gameObjects.EnemySpawner;
import shooter.gameObjects.HealthPack;
import shooter.gameObjects.ShieldPack;
import shooter.map.TileType;

public class PackSpawner {
	public static void spawnPowerups(FrustrumCullingNode chunkRootNode, ChunkDimension dimension, TileType[][] tileMap, GameWorld world) {
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				if(tileMap[i][j] == TileType.HEALTH) {
					HealthPack.spawn(i, j, world, chunkRootNode);
				}
				if(tileMap[i][j] == TileType.SHIELD) {
					ShieldPack.spawn(i, j, world, chunkRootNode);
				}
			}
		}
	}

}
