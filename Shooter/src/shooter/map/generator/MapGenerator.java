package shooter.map.generator;

import geom.Point;

import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import shooter.map.TileType;

public class MapGenerator {
	private static final double monsterSpawnProbability = 0.05;
	private static final double healthSpawnProbability = 0.02;
	private static final double shieldSpawnProbability = 0.02;
	private TileType[][] tiles;
	private Random random;

	public TileType[][] generateMap(int width, int height, long seed) {
		this.tiles = new TileType[width][height];
		this.random = new Random(seed);
		
		initializeMap();
		createMapBorders();
		spawnShieldPacks();
		spawnHealthPacks();
		spawnMonsters();
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
	
	private void spawnShieldPacks() {
		placeRandomTiles(TileType.SHIELD, shieldSpawnProbability);
	}
	
	private void spawnHealthPacks() {
		placeRandomTiles(TileType.HEALTH, healthSpawnProbability);
	}
	
	private void spawnMonsters() {
		placeRandomTiles(TileType.MONSTER, monsterSpawnProbability);
	}

	private void placeRandomTiles(TileType type, double probability) {
		int right = tiles.length - 1;
		int top = tiles[0].length - 1;
		
		for(int x = 1; x < right; x++) {
			for(int y = 1; y < top; y++) {
				if(random.nextDouble() <= probability) {
					tiles[x][y] = type;
				}
			}
		}
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
			int x = (int) point.x;
			int y = (int) point.y;
			fillFromTile(x, y);
		}
	}
	
	private void fillFromTile(int x, int y) {
		boolean[][] neighbourhood5x5 = TileNeighbourhood.generate5x5Neighbourhood(tiles, x, y);
		if(TileNeighbourhood.isNeighbourhoodFree(neighbourhood5x5)) {
			if(shouldPlaceWall() && !willHitDoor(x, y, Direction.NORTH)) {				
				fillInDirection(x, y, Direction.NORTH);
			}
			if(shouldPlaceWall() && !willHitDoor(x, y, Direction.EAST)) {
				fillInDirection(x, y, Direction.EAST);
			}
			if(shouldPlaceWall() && !willHitDoor(x, y, Direction.SOUTH)) {
				fillInDirection(x, y, Direction.SOUTH);
			}
			if(shouldPlaceWall() && !willHitDoor(x, y, Direction.WEST)) {
				fillInDirection(x, y, Direction.WEST);
			}
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
			if(currentIsFree) {
				possibleDoorLocations.add(new Point(x, y));
			} else {
				placeDoor(possibleDoorLocations);
			}
			previousWasFree = currentIsFree;
			x += direction.dx;
			y += direction.dy;
		} while(tiles[x][y] != TileType.WALL);
		placeDoor(possibleDoorLocations);
	}

	private void placeDoor(ArrayList<Point> possibleDoorLocations) {
		if(!possibleDoorLocations.isEmpty() && (possibleDoorLocations.size() > 1)) {
			int chosenIndex = random.nextInt(possibleDoorLocations.size() - 1);
			Point doorLocation = possibleDoorLocations.get(chosenIndex);
			tiles[(int) doorLocation.x][(int) doorLocation.y] = TileType.DOOR;
			possibleDoorLocations.clear();
		}
	}

	private boolean willHitDoor(int x, int y, Direction direction) {
		while(tiles[x][y] != TileType.WALL) {
			if(tiles[x][y] == TileType.DOOR) {
				return true;
			}
			x += direction.dx;
			y += direction.dy;
		}
		return false;
	}
	
	private boolean shouldPlaceWall() {
		return random.nextBoolean();
	}
}
