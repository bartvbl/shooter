package shooter.map;

import geom.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MapGenerator {
	public static TileType[][] generateMap(int width, int height, long seed) {
		TileType[][] tiles = new TileType[width][height];
		Random random = new Random(seed);
		
		initializeMap(tiles);
		createMapBorders(tiles);
		createRooms(tiles, random);
		
		return tiles;
	}

	private static void initializeMap(TileType[][] tiles) {
		for(int i = 0; i < tiles.length; i++) {
			Arrays.fill(tiles[i], TileType.GROUND);
		}
	}
	
	private static void createMapBorders(TileType[][] tiles) {
		int numColumns = tiles[0].length - 1;
		for(int i = 0; i < tiles.length; i++) {
			tiles[i][0] = TileType.WALL;
			tiles[i][numColumns] = TileType.WALL;
		}
		
		Arrays.fill(tiles[0], TileType.WALL);
		Arrays.fill(tiles[tiles.length - 1], TileType.WALL);
	}
	
	private static void createRooms(TileType[][] tiles, Random random) {
		int mapWidth = tiles.length;
		int mapHeight = tiles[0].length;
		ArrayList<Point> sortedNoisePoints = NoiseGenerator.generateSortedRandomNoise(random, mapWidth, mapHeight);
		visitPoint(tiles, sortedNoisePoints, random);
	}

	private static void visitPoint(TileType[][] tiles, ArrayList<Point> sortedNoisePoints, Random random) {
		for(int i = 0; i < sortedNoisePoints.size(); i++) {
			Point point = sortedNoisePoints.get(i);
			fillFromTile(tiles, random, (int)point.x, (int)point.y);
		}
	}
	
	private static void fillFromTile(TileType[][] tiles, Random random, int x, int y) {
		if(horizontalNeighbourhoodEmpty(tiles, x, y)) {
			fillVertical(tiles, random, x, y);
		}
		if(verticalNeighbourhoodEmpty(tiles, x, y)) {
			fillHorizontal(tiles, random, x, y);
		}
	}


	private static boolean horizontalNeighbourhoodEmpty(TileType[][] tiles, int x, int y) {
		boolean isNotWall = true;
		for(int i = -2; i <= 2; i++) {
			isNotWall = isNotWall && isNotWall(tiles, x + i, y);
		}
		return isNotWall;
	}

	private static boolean verticalNeighbourhoodEmpty(TileType[][] tiles, int x, int y) {
		boolean isNotWall = true;
		for(int i = -2; i <= 2; i++) {
			isNotWall = isNotWall && isNotWall(tiles, x, y + i);
		}
		return isNotWall;
	}

	private static boolean isNotWall(TileType[][] tiles, int x, int y) {
		if((x < 0) || (x >= tiles.length) || (y < 0) || (y >= tiles[0].length)) {
			return false;
		}
		return tiles[x][y] != TileType.WALL;
	}

	private static void fillVertical(TileType[][] tiles, Random random, int x, int y) {
		int currentY = y + 1;
		if(random.nextBoolean()) {
			tiles[x][y] = TileType.WALL;
			while(tiles[x][currentY] != TileType.WALL) {
				tiles[x][currentY] = TileType.WALL;
				currentY++;
			}	
		}
		if(random.nextBoolean()) {	
			tiles[x][y] = TileType.WALL;
			currentY = y - 1;
			while(tiles[x][currentY] != TileType.WALL) {
				tiles[x][currentY] = TileType.WALL;
				currentY--;
			}
		}
	}

	private static void fillHorizontal(TileType[][] tiles, Random random, int x, int y) {
		int currentX = x + 1;
		if(random.nextBoolean()) {	
			tiles[x][y] = TileType.WALL;
			while(tiles[currentX][y] != TileType.WALL) {
				tiles[currentX][y] = TileType.WALL;
				currentX++;
			}
		}
		if(random.nextBoolean()) {	
			tiles[x][y] = TileType.WALL;
			currentX = x - 1;
			while(tiles[currentX][y] != TileType.WALL) {
				tiles[currentX][y] = TileType.WALL;
				currentX--;
			}
		}
	}
}
