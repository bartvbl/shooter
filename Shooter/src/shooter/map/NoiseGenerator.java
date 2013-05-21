package shooter.map;

import geom.Point;

import java.util.ArrayList;
import java.util.Random;

public class NoiseGenerator {
	private static final double NOISE_LEVEL = 0.05;
	
	public static ArrayList<Point> generateSortedRandomNoise(Random random, int mapWidth, int mapHeight) {
		ArrayList<Point> randomNoisePoints = generateRandomNoise(random, mapWidth, mapHeight);
		ArrayList<Point> sortedNoisePoints = sortNoisePoints(randomNoisePoints, mapWidth, mapHeight);
		return sortedNoisePoints;
	}

	private static ArrayList<Point> sortNoisePoints(ArrayList<Point> randomNoisePoints, int mapWidth, int mapHeight) {
		double centerX = (double)mapWidth / 2d;
		double centerY = (double)mapHeight / 2d;
		Point center = new Point(centerX, centerY);
		
		return DistanceSorter.sortCoordinatesByDistance(randomNoisePoints, center);
	}
	
	private static ArrayList<Point> generateRandomNoise(Random random, int width, int height) {
		int numPointsToGenerate = (int) (Math.floor(NOISE_LEVEL*width*height));
		ArrayList<Point> noiseCoordinates = new ArrayList<Point>();
		noiseCoordinates.ensureCapacity(numPointsToGenerate);
		
		//note integer division -> floor(width/2)
		int halfWidth = width / 2;
		int halfHeight = height / 2;
		
		for(int i = 0; i < numPointsToGenerate; i++) {
			int randomX = random.nextInt(width - 1) - halfWidth;
			int randomY = random.nextInt(height - 1) - halfHeight;
			noiseCoordinates.add(new Point(randomX, randomY));
		}
		
		return noiseCoordinates;
	}
}
