package shooter.map;

import geom.Point;

import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MapGenerator {
	private TileType[][] tiles;
	private Random random;
	private boolean[][] neighbourhood3x3;

	public TileType[][] generateMap(int width, int height, long seed) {
		this.tiles = new TileType[width][height];
		this.random = new Random(seed);
		
		initializeMap();
		createMapBorders();
		createRooms();
		
		return tiles;
	}

	private void initializeMap() {
		for(int i = 0; i < tiles.length; i++) {
			Arrays.fill(tiles[i], TileType.GROUND);
		}
	}
	
	private void createMapBorders() {
		int numColumns = tiles[0].length - 1;
		for(int i = 0; i < tiles.length; i++) {
			tiles[i][0] = TileType.WALL;
			tiles[i][numColumns] = TileType.WALL;
		}
		
		Arrays.fill(tiles[0], TileType.WALL);
		Arrays.fill(tiles[tiles.length - 1], TileType.WALL);
	}
	
	private void createRooms() {
		int mapWidth = tiles.length;
		int mapHeight = tiles[0].length;
		ArrayList<Point> sortedNoisePoints = NoiseGenerator.generateSortedRandomNoise(random, mapWidth, mapHeight);
		visitPoints(sortedNoisePoints);
	}

	private void visitPoints(ArrayList<Point> sortedNoisePoints) {
		for(int i = 0; i < sortedNoisePoints.size(); i++) {
			Point point = sortedNoisePoints.get(i);
			fillFromTile((int)point.x, (int)point.y);
		}
	}
	
	private void fillFromTile(int x, int y) {
		this.neighbourhood3x3 = TileNeighbourhood.generate3x3Neighbourhood(tiles, x, y);
		if(TileNeighbourhood.isNorthFree(neighbourhood3x3) && shouldPlaceWall()) {
			fillInDirection(x, y, Direction.NORTH);
		}
		if(TileNeighbourhood.isEastFree(neighbourhood3x3) && shouldPlaceWall()) {
			fillInDirection(x, y, Direction.EAST);
		}
		if(TileNeighbourhood.isSouthFree(neighbourhood3x3) && shouldPlaceWall()) {
			fillInDirection(x, y, Direction.SOUTH);
		}
		if(TileNeighbourhood.isWestFree(neighbourhood3x3) && shouldPlaceWall()) {
			fillInDirection(x, y, Direction.WEST);
		}
	}

	private void fillInDirection(int x, int y, Direction direction) {
		boolean previousWasFree = false;
		boolean currentIsFree = false;
		boolean nextIsFree = false;
		ArrayList<Point> possibleDoorLocations = new ArrayList<Point>();
		do {
			currentIsFree = TileNeighbourhood.isSideFree(tiles, x, y, direction);
			nextIsFree = TileNeighbourhood.isSideFree(tiles, x + direction.dx, y + direction.dy, direction);
			if(currentIsFree) {
				tiles[x][y] = TileType.WALL;
			}
			if(previousWasFree && currentIsFree && nextIsFree) {
				possibleDoorLocations.add(new Point(x, y));
			}
			previousWasFree = currentIsFree;
			x += direction.dx;
			y += direction.dy;
		} while(tiles[x][y] != TileType.WALL);
		if(!possibleDoorLocations.isEmpty() && (possibleDoorLocations.size() > 1)) {
			int chosenIndex = random.nextInt(possibleDoorLocations.size() - 1);
			Point doorLocation = possibleDoorLocations.get(chosenIndex);
			tiles[(int) doorLocation.x][(int) doorLocation.y] = TileType.DOOR;
		}
	}

	private boolean shouldPlaceWall() {
		return random.nextBoolean();
	}
}
