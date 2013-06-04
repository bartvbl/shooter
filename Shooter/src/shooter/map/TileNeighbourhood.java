package shooter.map;

public class TileNeighbourhood {

	public static boolean[][] generate5x5Neighbourhood(TileType[][] tiles, int x, int y) {
		boolean[][] neighbourhood5x5 = new boolean[5][5];
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				neighbourhood5x5[i + 2][j + 2] = isWall(tiles, x + i, y + j);
			}
		}
		return neighbourhood5x5;
	}
	
	private static boolean isWall(TileType[][] tiles, int x, int y) {
		if((x < 0) || (x >= tiles.length) || (y < 0) || (y >= tiles[0].length)) {
			return true;
		}
		return tiles[x][y] == TileType.WALL;
	}

	public static boolean isNorthFree(boolean[][] neighbourhood5x5) {
		return !neighbourhood5x5[1][3] && !neighbourhood5x5[2][3] && !neighbourhood5x5[3][3];
	}

	public static boolean isEastFree(boolean[][] neighbourhood5x5) {
		return !neighbourhood5x5[3][3] && !neighbourhood5x5[3][2] && !neighbourhood5x5[3][1];
	}

	public static boolean isSouthFree(boolean[][] neighbourhood5x5) {
		return !neighbourhood5x5[1][1] && !neighbourhood5x5[2][1] && !neighbourhood5x5[3][1];
	}

	public static boolean isWestFree(boolean[][] neighbourhood5x5) {
		return !neighbourhood5x5[1][3] && !neighbourhood5x5[1][2] && !neighbourhood5x5[1][1];
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

	public static boolean isNeighbourhoodFree(boolean[][] neighbourhood5x5) {
		boolean isFree = true;
		for(int i = 0; i <= 4; i++) {
			for(int j = 0; j <= 4; j++) {
				isFree = isFree && !neighbourhood5x5[i][j];
			}
		}
		return isFree;
	}

}
