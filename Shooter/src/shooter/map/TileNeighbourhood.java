package shooter.map;

public class TileNeighbourhood {
	public static boolean[][] generate3x3Neighbourhood(TileType[][] tiles, int x, int y) {
		boolean[][] neighbourhood3x3 = new boolean[3][3];
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				neighbourhood3x3[i + 1][j + 1] = isWall(tiles, x, y);
			}
		}
		return neighbourhood3x3;
	}
	
	private static boolean isWall(TileType[][] tiles, int x, int y) {
		if((x < 0) || (x >= tiles.length) || (y < 0) || (y >= tiles[0].length)) {
			return true;
		}
		return tiles[x][y] == TileType.WALL;
	}

	public static boolean isNorthFree(boolean[][] neighbourhood3x3) {
		return !neighbourhood3x3[0][2] && !neighbourhood3x3[1][2] && !neighbourhood3x3[2][2];
	}

	public static boolean isEastFree(boolean[][] neighbourhood3x3) {
		return !neighbourhood3x3[2][2] && !neighbourhood3x3[2][1] && !neighbourhood3x3[2][0];
	}

	public static boolean isSouthFree(boolean[][] neighbourhood3x3) {
		return !neighbourhood3x3[0][0] && !neighbourhood3x3[1][0] && !neighbourhood3x3[2][0];
	}

	public static boolean isWestFree(boolean[][] neighbourhood3x3) {
		return !neighbourhood3x3[0][2] && !neighbourhood3x3[0][1] && !neighbourhood3x3[0][0];
	}

	public static boolean isSideFree(TileType[][] tiles, int x, int y, Direction direction) {
		switch(direction) {
		case EAST:
			return isHorizontalFree(tiles, x, y);
		case NORTH:
			return isVerticalFree(tiles, x, y);
		case SOUTH:
			return isVerticalFree(tiles, x, y);
		case WEST:
			return isHorizontalFree(tiles, x, y);
		default:
			return false;
		}
	}

	private static boolean isHorizontalFree(TileType[][] tiles, int x, int y) {
		return !isWall(tiles, x, y - 1) && !isWall(tiles, x, y + 1);
	}
	
	private static boolean isVerticalFree(TileType[][] tiles, int x, int y) {
		return !isWall(tiles, x - 1, y) && !isWall(tiles, x + 1, y);
	}
}
