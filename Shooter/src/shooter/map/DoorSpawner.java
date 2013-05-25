package shooter.map;

import shooter.GameWorld;
import shooter.gameObjects.Door;

public class DoorSpawner {

	private static Orientation parseOrientation(TileType[][] tileMap, int x, int y) {
		if((tileMap[x][y-1] == TileType.WALL) && (tileMap[x][y+1] == TileType.WALL)) {
			return Orientation.VERTICAL;
		} else {
			return Orientation.HORIZONTAL;
		}
	}

	public static void spawnDoors(MapFrustrumCullingNode chunkRootNode, ChunkDimension dimension, TileType[][] tileMap, GameWorld world) {
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				if(tileMap[i][j] == TileType.DOOR) {
					Door door = Door.createInstance(world);
					chunkRootNode.addChild(door.sceneNode);
					Orientation orientation = parseOrientation(tileMap, i, j);
					door.setLocation(i, j, orientation);
					world.addGameObject(door);
				}
			}
		}
	}

}
