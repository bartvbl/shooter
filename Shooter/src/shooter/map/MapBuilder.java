package shooter.map;

import geom.AxisAlignedUnitPlane;
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
import scene.sceneGraph.sceneNodes.DisplayListNode;
import scene.sceneGraph.sceneNodes.MapSceneNode;

public class MapBuilder {
	
	private static final int trianglesPerPolygon = 2;
	private static final int verticesPerTriangle = 3;
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
		
		for(int i = 0; i < chunksX; i ++ ) {
			for(int j = 0; j < chunksY; j ++) {
				int chunkWidth = Math.min(mapWidth - i*CHUNK_WIDTH, CHUNK_WIDTH);
				int chunkHeight = Math.min(mapHeight - j*CHUNK_HEIGHT, CHUNK_HEIGHT);
				Rectangle chunkDimension = new Rectangle(i*CHUNK_WIDTH, j*CHUNK_HEIGHT, chunkWidth, chunkHeight);
				chunkMap[i][j] = buildChunk(tileMap, chunkDimension);
				mapNode.addChild(chunkMap[i][j]);
			}
		}
	}

	private static SceneNode buildChunk(TileType[][] tileMap, Rectangle chunkDimension) {
		double chunkRadius = Math.sqrt(CHUNK_WIDTH*CHUNK_WIDTH + CHUNK_HEIGHT*CHUNK_HEIGHT);
		double chunkCenterX = chunkDimension.getX() + ((double) CHUNK_WIDTH / 2d);
		double chunkCenterY = chunkDimension.getY() + ((double) CHUNK_HEIGHT / 2d);
		MapFrustrumCullingNode chunkRootNode = new MapFrustrumCullingNode(chunkRadius, chunkCenterX, chunkCenterY, 0);
		
		DisplayListNode chunkContentsNode = new DisplayListNode();
		chunkRootNode.addChild(chunkContentsNode);
		
		buildTerrain(chunkContentsNode, tileMap, chunkDimension);
		
		return chunkRootNode;
	}

	private static void buildTerrain(DisplayListNode chunkContentsNode, TileType[][] tileMap, Rectangle chunkDimension) {
		int chunkLeft = chunkDimension.getX();
		int chunkBottom = chunkDimension.getY();
		int chunkRight = chunkLeft + chunkDimension.getWidth();
		int chunkTop = chunkBottom + chunkDimension.getHeight();
		ChunkDimension dimension = new ChunkDimension(chunkLeft, chunkRight, chunkBottom, chunkTop);
		
		int polyCount = calculatePolycount(tileMap, dimension);
		
		buildChunkGeometry(chunkContentsNode, tileMap, polyCount, dimension);
	}

	private static void buildChunkGeometry(DisplayListNode chunkContentsNode, TileType[][] tileMap, int polyCount, ChunkDimension dimension) {
		IntBuffer wallIndexBuffer = BufferUtils.createIntBuffer(polyCount * trianglesPerPolygon * verticesPerTriangle);
		IntBuffer groundIndexBuffer = BufferUtils.createIntBuffer(polyCount * trianglesPerPolygon * verticesPerTriangle);
		DoubleBuffer geometryDataBuffer = BufferUtils.createDoubleBuffer(polyCount * trianglesPerPolygon * verticesPerTriangle * coordinatesPerVertex);
		System.out.println("("+dimension.chunkLeft+", " + dimension.chunkRight + "), ("+dimension.chunkBottom+", "+dimension.chunkTop+")");
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				if(isWallAt(tileMap, i, j)) {
					wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position()));
					geometryDataBuffer.put(AxisAlignedUnitPlane.createTopPlane(i, j, 1));
					
					if(!isWallAt(tileMap, i - 1, j)) {
						wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position()));
						geometryDataBuffer.put(AxisAlignedUnitPlane.createLeftPlane(i, j, 0));
					}
					if(!isWallAt(tileMap, i + 1, j)) {
						wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position()));
						geometryDataBuffer.put(AxisAlignedUnitPlane.createRightPlane(i, j, 0));
					}
					if(!isWallAt(tileMap, i, j - 1)) {
						wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position()));
						geometryDataBuffer.put(AxisAlignedUnitPlane.createFrontPlane(i, j, 0));
					}
					if(!isWallAt(tileMap, i, j + 1)) {
						wallIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position()));
						geometryDataBuffer.put(AxisAlignedUnitPlane.createBackPlane(i, j, 0));
					}
					
					
				} else {
					groundIndexBuffer.put(AxisAlignedUnitPlane.generateIndices(geometryDataBuffer.position()));
					geometryDataBuffer.put(AxisAlignedUnitPlane.createTopPlane(i, j, 0));
				}
				System.out.println(i +", " + j + " " + geometryDataBuffer.position() + " out of " + (polyCount * trianglesPerPolygon * verticesPerTriangle * coordinatesPerVertex) + " (reamining: " + geometryDataBuffer.remaining() + ")");
			}
		}
		
		GeometryBuffer wallBuffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryDataBuffer, wallIndexBuffer);
		Material wallMaterial = new Material("wallMaterial");
		wallMaterial.addChild(wallBuffer);
		Texture texture = TextureLoader.loadTextureFromFile("res/textures/wall.png");
		wallMaterial.setDiffuseTexture(texture);
		chunkContentsNode.addChild(wallMaterial);
		
		GeometryBuffer groundBuffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryDataBuffer, groundIndexBuffer);
		Material groundMaterial = new Material("groundMaterial");
		groundMaterial.addChild(groundBuffer);
		Texture groundTexture = TextureLoader.loadTextureFromFile("res/textures/ground.png");
		groundMaterial.setDiffuseTexture(groundTexture);
		chunkContentsNode.addChild(groundMaterial);
	}

	private static int calculatePolycount(TileType[][] tileMap, ChunkDimension dimension) {
		int polycount = 0;
		
		for(int i = dimension.chunkLeft; i < dimension.chunkRight; i++) {
			for(int j = dimension.chunkBottom; j < dimension.chunkTop; j++) {
				boolean centerIsWall = isWallAt(tileMap, i, j);
				polycount++; //the square itself is a polygon
				
				if(centerIsWall) { 
					//to ensure each polygon being counted once, we only count "downhill" polygons.
					if(!isWallAt(tileMap, i - 1, j)) {
						polycount++;
					}
					if(!isWallAt(tileMap, i + 1, j)) {
						polycount++;
					}
					if(!isWallAt(tileMap, i, j - 1)) {
						polycount++;
					}
					if(!isWallAt(tileMap, i, j + 1)) {
						polycount++;
					}
				}
			}
		}
		return polycount;
	}
	
	private static boolean isWallAt(TileType[][] tileMap, int x, int y) {
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		
		if((x < 0) || (x >= mapWidth) || (y < 0) || (y >= mapHeight)) {
			return false;
		}
		
		return tileMap[x][y] == TileType.WALL;
	}
}
