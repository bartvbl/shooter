package shooter.map.generator;

import scene.sceneGraph.FrustrumCullingNode;
import shooter.GameWorld;
import shooter.gameObjects.Door;
import shooter.map.TileType;

public class DoorSpawner {

	private static Orientation parseOrientation(TileType[][] tileMap, int x, int y) {
		if((tileMap[x][y-1] == TileType.WALL) && (tileMap[x][y+1] == TileType.WALL)) {
			return Orientation.VERTICAL;
		} else {
			return Orientation.HORIZONTAL;
		}
	}

	public static void spawnDoors(FrustrumCullingNode chunkRootNode, ChunkDimension dimension, TileType[][] tileMap, GameWorld world) {
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				if(tileMap[i][j] == TileType.DOOR) {
					Orientation orientation = parseOrientation(tileMap, i, j);
					Door door = Door.createInstance(world, i, j, orientation);
					chunkRootNode.addChild(door.sceneNode);
					world.addGameObject(door);
				}
			}
		}
	}

}
