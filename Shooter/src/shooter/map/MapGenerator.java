package shooter.map;

import java.util.Arrays;
import java.util.Random;

public class MapGenerator {
	private static final double NOISE_LEVEL = 0.02;

	public static TileType[][] generateMap(int width, int height, long seed) {
		TileType[][] tiles = new TileType[width][height];
		Random random = new Random(seed);
		
		initializeMap(tiles);
		createMapBorders(tiles);
		generateRandomNoise(tiles, random);
		
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
	
	private static void generateRandomNoise(TileType[][] tiles, Random random) {
		for(int i = 1; i < tiles.length - 2; i++) {
			for(int j = 1; j < tiles[0].length - 2; j++) {
				double randomValue = random.nextDouble();
				if(randomValue <= NOISE_LEVEL) {
					tiles[i][j] = TileType.WALL;
				}
			}
		}
	}

}
