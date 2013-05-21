package shooter.map;

import java.util.ArrayList;

import geom.Point;

public class DistanceSorter {

	public static ArrayList<Point> sortCoordinatesByDistance(ArrayList<Point> randomNoisePoints, Point center) {
		if(randomNoisePoints.size() <= 1) {
			return randomNoisePoints; //already sorted
		}
		
		int pivotIndex = (int) Math.floor((double) randomNoisePoints.size() / 2d);
		Point pivot = randomNoisePoints.remove(pivotIndex);
		double pivotDistance = pivot.distanceTo(center);
		
		ArrayList<Point> smaller = new ArrayList<Point>();
		ArrayList<Point> greater = new ArrayList<Point>();
		
		for(Point point : randomNoisePoints) {
			if(point.distanceTo(center) <= pivotDistance) {
				smaller.add(point);
			} else {
				greater.add(point);
			}
		}
		
		smaller = sortCoordinatesByDistance(smaller, center);
		greater = sortCoordinatesByDistance(greater, center);
		
		smaller.add(pivot);
		smaller.addAll(greater);
		
		return smaller;
	}

}
