package shooter.map;

import gl.BufferDataFormatType;
import gl.GeometryBuffer;
import gl.GeometryBufferGenerator;
import gl.material.Material;
import gl.texture.Texture;
import gl.texture.TextureLoader;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Rectangle;

import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.MapSceneNode;

public class MapBuilder {
	
	private static final int polygonsPerObject = 6;
	private static final int verticesPerPolygon = 3 + 3; //two triangles
	private static final int coordinatesPerVertex = 3 + 3 + 2;//3 coordinates, 3 normal, 2 texture
	private static final int CHUNK_WIDTH = 100;
	private static final int CHUNK_HEIGHT = 100;

	public static void buildMap(MapSceneNode mapNode, TileType[][] tileMap) {
		mapNode.clear();
		
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		
		int chunksX = (int) Math.ceil((double)mapWidth / (double) CHUNK_WIDTH);
		int chunksY = (int) Math.ceil((double)mapHeight / (double) CHUNK_HEIGHT);
		
		SceneNode[][] chunkMap = new SceneNode[chunksX][chunksY];
		
		for(int i = 0; i < mapWidth; i ++ ) {
			for(int j = 0; j < mapHeight; j ++) {
				int chunkWidth = Math.min(mapWidth - i*CHUNK_WIDTH, CHUNK_WIDTH);
				int chunkHeight = Math.min(mapHeight - j*CHUNK_HEIGHT, CHUNK_HEIGHT);
				Rectangle chunkDimension = new Rectangle(i*CHUNK_WIDTH, j*CHUNK_HEIGHT, chunkWidth, chunkHeight);
				chunkMap[i][j] = buildChunk(tileMap, chunkDimension);
			}
		}
		
		
		HashMap<TileType, Integer> tileCounts = countTiles(tileMap);
		GeometryBuffer wallBuffer = buildWallBuffer(tileMap, tileCounts.get(TileType.WALL));
		Material material = new Material("mapMaterial");
		material.addChild(wallBuffer);
		Texture texture = TextureLoader.loadTextureFromFile("res/textures/wall.png");
		material.setDiffuseTexture(texture);
		
		GeometryBuffer floorBuffer = buildFloorBuffer(tileMap, tileCounts.get(TileType.GROUND) + tileCounts.get(TileType.DOOR));
		Texture groundTexture = TextureLoader.loadTextureFromFile("res/textures/ground.png");
		Material groundMaterial = new Material("mapMaterial");
		groundMaterial.addChild(floorBuffer);
		groundMaterial.setDiffuseTexture(groundTexture);
		//mapNode.addChild(groundMaterial);
		
		mapNode.addChild(material);
	}

	private static SceneNode buildChunk(TileType[][] tileMap, Rectangle chunkDimension) {
		return null;
	}

	private static GeometryBuffer buildFloorBuffer(TileType[][] tileMap, int tileCount) {
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		
		final int coordinateCount = verticesPerPolygon * tileCount;
		
		IntBuffer indexBuffer = generateIndexBuffer(coordinateCount);
		
		DoubleBuffer geometryBuffer = BufferUtils.createDoubleBuffer(coordinateCount * coordinatesPerVertex);
		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				if((tileMap[i][j] == TileType.DOOR) || (tileMap[i][j] == TileType.GROUND)) {
					drawFloorTileAt(geometryBuffer, i, j, 0);
				}
				
			}
		}
		return GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryBuffer, indexBuffer, coordinateCount);
	}

	private static GeometryBuffer buildWallBuffer(TileType[][] tileMap, int wallTileCount) {
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		
		final int coordinateCount = polygonsPerObject * verticesPerPolygon * wallTileCount;
		
		//4 vertices per quad, 2 coordinates per square
		IntBuffer indexBuffer = generateIndexBuffer(coordinateCount);
		
		DoubleBuffer vertexBuffer = BufferUtils.createDoubleBuffer(coordinateCount * coordinatesPerVertex);
		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				if(tileMap[i][j] == TileType.WALL) {
					drawCubeAt(i, j, 0, vertexBuffer);
				}
				
			}
		}
		return GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, vertexBuffer, indexBuffer, coordinateCount);
	}

	private static IntBuffer generateIndexBuffer(final int coordinateCount) {
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(coordinateCount);
		for(int i = 0; i < coordinateCount; i++) {
			indexBuffer.put(i);
		}
		return indexBuffer;
	}
	
	private static void drawFloorTileAt(DoubleBuffer geometryBuffer, double x, double y, double z) {
		geometryBuffer.put(new double[]{
		x, y, z + 1, 			0, 0,		0, 0, 1,
		x + 1, y, z + 1, 		1, 0,		0, 0, 1,
		x + 1, y + 1, z + 1,	1, 1,		0, 0, 1,
		
		x, y, z + 1, 			0, 0,		0, 0, 1,
		x + 1, y + 1, z + 1, 	1, 1,		0, 0, 1,
		x, y + 1, z + 1, 		0, 1,		0, 0, 1});
	}

	private static void drawCubeAt(int x, int y, int z, DoubleBuffer geometryBuffer) {
		//I tried turning this somehow into an algorithm. It seems this is the easiest way, unfortunately.
		geometryBuffer.put(new double[]{
				//coordinate			texture		normal
				//front
				x, y, z, 				0, 0,		0, -1, 0,
				x + 1, y, z, 			1, 0,		0, -1, 0,
				x + 1, y, z + 1,		1, 1,		0, -1, 0,
				
				x, y, z, 				0, 0,		0, -1, 0,
				x + 1, y, z + 1, 		1, 1,		0, -1, 0,
				x, y, z + 1, 			0, 1,		0, -1, 0,
				
				//right side
				x + 1, y, z,			0, 0,		1, 0, 0,
				x + 1, y + 1, z,		1, 0,		1, 0, 0,
				x + 1, y + 1, z + 1,	1, 1,		1, 0, 0,
				
				x + 1, y, z,			0, 0,		1, 0, 0,
				x + 1, y + 1, z + 1,	1, 1,		1, 0, 0,
				x + 1, y, z + 1,		0, 1,		1, 0, 0,
				
				//left side
				x, y + 1, z,			0, 0,		-1, 0, 0,
				x, y, z,				1, 0,		-1, 0, 0,
				x, y, z + 1,			1, 1,		-1, 0, 0,
				
				x, y + 1, z,			0, 0,		-1, 0, 0,
				x, y, z + 1,			1, 1,		-1, 0, 0,
				x, y + 1, z + 1,		0, 1,		-1, 0, 0,
				
				//top
				x, y, z + 1, 			0, 0,		0, 0, 1,
				x + 1, y, z + 1, 		1, 0,		0, 0, 1,
				x + 1, y + 1, z + 1,	1, 1,		0, 0, 1,
				
				x, y, z + 1, 			0, 0,		0, 0, 1,
				x + 1, y + 1, z + 1, 	1, 1,		0, 0, 1,
				x, y + 1, z + 1, 		0, 1,		0, 0, 1,
				
				//bottom
				x, y + 1, z, 			0, 0,		0, 0, -1,
				x + 1, y + 1, z, 		1, 0,		0, 0, -1,
				x + 1, y, z, 			1, 1,		0, 0, -1,
				
				x, y + 1, z, 			0, 0,		0, 0, -1,
				x + 1, y, z, 			1, 1,		0, 0, -1,
				x, y, z, 				0, 1,		0, 0, -1,
				
				//back
				x + 1, y + 1, z,		0, 0,		0, 1, 0,
				x, y + 1, z,			1, 0,		0, 1, 0,
				x, y + 1, z + 1,		1, 1,		0, 1, 0,
				
				x + 1, y + 1, z,		0, 0,		0, 1, 0,
				x, y + 1, z + 1,		1, 1,		0, 1, 0,
				x + 1, y + 1, z + 1,	0, 1,		0, 1, 0});
	}

	private static HashMap<TileType, Integer> countTiles(TileType[][] tileMap) {
		HashMap<TileType, Integer> tileCount = new HashMap<TileType, Integer>();
		for(TileType type : TileType.values()){
			tileCount.put(type, 0);
		}
		
		for(TileType[] row : tileMap) {
			for(TileType field : row) {
				int currentTileCount = tileCount.get(field);
				tileCount.put(field, currentTileCount + 1);
			}
		}
		return tileCount;
	}

}
