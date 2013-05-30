package shooter.map;

import geom.AxisAlignedUnitPlane;
import gl.material.Material;
import gl.texture.Texture;
import gl.texture.TextureLoader;
import gl.vbo.BufferDataFormatType;
import gl.vbo.GeometryBuffer;
import gl.vbo.GeometryBufferGenerator;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Rectangle;

import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.MapSceneNode;
import shooter.GameWorld;

public class MapBuilder {
	
	private static final int trianglesPerPolygon = 2;
	private static final int verticesPerTriangle = 3;
	private static final int coordinatesPerVertex = 3 + 3 + 2;//3 coordinates, 3 normal, 2 texture
	private static final int CHUNK_WIDTH = 25;
	private static final int CHUNK_HEIGHT = 25;

	public static void buildMap(MapSceneNode mapNode, TileType[][] tileMap, GameWorld world) {
		mapNode.clear();
		
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		
		int chunksX = (int) Math.ceil((double)mapWidth / (double) CHUNK_WIDTH);
		int chunksY = (int) Math.ceil((double)mapHeight / (double) CHUNK_HEIGHT);
		
		SceneNode[][] chunkMap = new SceneNode[chunksX][chunksY];
		
		for(int i = 0; i < chunksX; i ++ ) {
			for(int j = 0; j < chunksY; j ++) {
				int chunkWidth = Math.min(mapWidth - i*CHUNK_WIDTH, CHUNK_WIDTH);
				int chunkHeight = Math.min(mapHeight - j*CHUNK_HEIGHT, CHUNK_HEIGHT);
				Rectangle chunkDimension = new Rectangle(i*CHUNK_WIDTH, j*CHUNK_HEIGHT, chunkWidth, chunkHeight);
				chunkMap[i][j] = buildChunk(tileMap, chunkDimension, world);
				mapNode.addChild(chunkMap[i][j]);
			}
		}
		
	}

	private static SceneNode buildChunk(TileType[][] tileMap, Rectangle chunkDimension, GameWorld world) {
		double chunkRadius = Math.sqrt(CHUNK_WIDTH*CHUNK_WIDTH + CHUNK_HEIGHT*CHUNK_HEIGHT);
		double chunkCenterX = chunkDimension.getX() + ((double) CHUNK_WIDTH / 2d);
		double chunkCenterY = chunkDimension.getY() + ((double) CHUNK_HEIGHT / 2d);
		MapFrustrumCullingNode chunkRootNode = new MapFrustrumCullingNode(world, chunkRadius, chunkCenterX, chunkCenterY);
		
		ChunkDimension dimension = new ChunkDimension(chunkDimension);
		
		buildTerrain(chunkRootNode, tileMap, dimension);
		DoorSpawner.spawnDoors(chunkRootNode, dimension, tileMap, world);
		MonsterSpawner.spawnMonsters(chunkRootNode, dimension, tileMap, world);
		return chunkRootNode;
	}

	private static void buildTerrain(SceneNode chunkRootNode, TileType[][] tileMap, ChunkDimension dimension) {
		CountedPolygons polyCount = calculatePolycount(tileMap, dimension);
		buildChunkGeometry(chunkRootNode, tileMap, polyCount, dimension);
	}

	private static void buildChunkGeometry(SceneNode chunkRootNode, TileType[][] tileMap, CountedPolygons polyCount, ChunkDimension dimension) {
		IntBuffer wallIndexBuffer = BufferUtils.createIntBuffer(polyCount.wallPolygons * trianglesPerPolygon * verticesPerTriangle);
		IntBuffer groundIndexBuffer = BufferUtils.createIntBuffer(polyCount.groundPolygons * trianglesPerPolygon * verticesPerTriangle);
		DoubleBuffer geometryDataBuffer = BufferUtils.createDoubleBuffer((polyCount.wallPolygons + polyCount.groundPolygons) * trianglesPerPolygon * verticesPerTriangle * coordinatesPerVertex);
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				if(isWallAt(tileMap, i, j)) {
					wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position() / coordinatesPerVertex));
					geometryDataBuffer.put(AxisAlignedUnitPlane.createTopPlane(i, j, 1));
					
					if(!isWallAt(tileMap, i - 1, j)) {
						wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position() / coordinatesPerVertex));
						geometryDataBuffer.put(AxisAlignedUnitPlane.createLeftPlane(i, j, 0));
					}
					if(!isWallAt(tileMap, i + 1, j)) {
						wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position() / coordinatesPerVertex));
						geometryDataBuffer.put(AxisAlignedUnitPlane.createRightPlane(i, j, 0));
					}
					if(!isWallAt(tileMap, i, j - 1)) {
						wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position() / coordinatesPerVertex));
						geometryDataBuffer.put(AxisAlignedUnitPlane.createFrontPlane(i, j, 0));
					}
					if(!isWallAt(tileMap, i, j + 1)) {
						wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position() / coordinatesPerVertex));
						geometryDataBuffer.put(AxisAlignedUnitPlane.createBackPlane(i, j, 0));
					}
				} else {
					groundIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position() / coordinatesPerVertex));
					geometryDataBuffer.put(AxisAlignedUnitPlane.createTopPlane(i, j, 0));
				}
			}
		}
		
		if(wallIndexBuffer.capacity() != 0) { //check if buffer has contents			
			GeometryBuffer wallBuffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryDataBuffer, wallIndexBuffer);
			Material wallMaterial = new Material("wallMaterial");
			wallMaterial.addChild(wallBuffer);
			Texture texture = TextureLoader.loadTextureFromFile("res/textures/wall.png");
			wallMaterial.setDiffuseTexture(texture);
			chunkRootNode.addChild(wallMaterial);
		}
		
		if(groundIndexBuffer.capacity() != 0) { //check if buffer has contents
			GeometryBuffer groundBuffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryDataBuffer, groundIndexBuffer);
			Material groundMaterial = new Material("groundMaterial");
			groundMaterial.addChild(groundBuffer);
			Texture groundTexture = TextureLoader.loadTextureFromFile("res/textures/ground.png");
			groundMaterial.setDiffuseTexture(groundTexture);
			chunkRootNode.addChild(groundMaterial);
		}
	}

	private static CountedPolygons calculatePolycount(TileType[][] tileMap, ChunkDimension dimension) {
		int wallPolycount = 0;
		int groundPolycount = 0;
		
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				boolean centerIsWall = isWallAt(tileMap, i, j);
				
				if(centerIsWall) { 
					wallPolycount++;
					//to ensure each polygon being counted once, we only count "downhill" polygons.
					if(!isWallAt(tileMap, i - 1, j)) {
						wallPolycount++;
					}
					if(!isWallAt(tileMap, i + 1, j)) {
						wallPolycount++;
					}
					if(!isWallAt(tileMap, i, j - 1)) {
						wallPolycount++;
					}
					if(!isWallAt(tileMap, i, j + 1)) {
						wallPolycount++;
					}
				} else {
					groundPolycount++;
				}
			}
		}
		return new MapBuilder.CountedPolygons(groundPolycount, wallPolycount);
	}
	
	private static boolean isWallAt(TileType[][] tileMap, int x, int y) {
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		
		if((x < 0) || (x >= mapWidth) || (y < 0) || (y >= mapHeight)) {
			return false;
		}
		
		return tileMap[x][y] == TileType.WALL;
	}
	
	private static class CountedPolygons {
		public final int wallPolygons;
		public final int groundPolygons;

		public CountedPolygons(int groundPolygons, int wallPolygons) {
			this.wallPolygons = wallPolygons;
			this.groundPolygons = groundPolygons;
		}
	}
}
