package geom;

public class AxisAlignedUnitPlane {
	public static double[] createFrontPlane(double x, double y, double z) {
		return new double[]{
			x, y, z, 				0, 0,		0, -1, 0,
			x + 1, y, z, 			1, 0,		0, -1, 0,
			x + 1, y, z + 1,		1, 1,		0, -1, 0,
			
			x, y, z, 				0, 0,		0, -1, 0,
			x + 1, y, z + 1, 		1, 1,		0, -1, 0,
			x, y, z + 1, 			0, 1,		0, -1, 0
		};
	}
	
	public static double[] createBackPlane(double x, double y, double z) {
		return new double[]{
			x + 1, y + 1, z,		0, 0,		0, 1, 0,
			x, y + 1, z,			1, 0,		0, 1, 0,
			x, y + 1, z + 1,		1, 1,		0, 1, 0,
			
			x + 1, y + 1, z,		0, 0,		0, 1, 0,
			x, y + 1, z + 1,		1, 1,		0, 1, 0,
			x + 1, y + 1, z + 1,	0, 1,		0, 1, 0
		};
	}
	
	public static double[] createRightPlane(double x, double y, double z) {
		return new double[]{
			x + 1, y, z,			0, 0,		1, 0, 0,
			x + 1, y + 1, z,		1, 0,		1, 0, 0,
			x + 1, y + 1, z + 1,	1, 1,		1, 0, 0,
			
			x + 1, y, z,			0, 0,		1, 0, 0,
			x + 1, y + 1, z + 1,	1, 1,		1, 0, 0,
			x + 1, y, z + 1,		0, 1,		1, 0, 0
		};
	}
	
	public static double[] createLeftPlane(double x, double y, double z) {
		return new double[]{
			x, y + 1, z,			0, 0,		-1, 0, 0,
			x, y, z,				1, 0,		-1, 0, 0,
			x, y, z + 1,			1, 1,		-1, 0, 0,
			
			x, y + 1, z,			0, 0,		-1, 0, 0,
			x, y, z + 1,			1, 1,		-1, 0, 0,
			x, y + 1, z + 1,		0, 1,		-1, 0, 0
		};
	}
	
	public static double[] createTopPlane(double x, double y, double z) {
		return new double[]{
			x, y, z, 				0, 0,		0, 0, -1,
			x + 1, y, z, 			1, 0,		0, 0, -1,
			x + 1, y + 1, z,		1, 1,		0, 0, -1,
			
			x, y, z, 				0, 0,		0, 0, -1,
			x + 1, y + 1, z, 		1, 1,		0, 0, -1,
			x, y + 1, z, 			0, 1,		0, 0, -1
		};
	}
	
	public static double[] createBottomPlane(double x, double y, double z) {
		return new double[]{
			//bottom
			x, y + 1, z, 			0, 0,		0, 0, -1,
			x + 1, y + 1, z, 		1, 0,		0, 0, -1,
			x + 1, y, z, 			1, 1,		0, 0, -1,
			
			x, y + 1, z, 			0, 0,		0, 0, -1,
			x + 1, y, z, 			1, 1,		0, 0, -1,
			x, y, z, 				0, 1,		0, 0, -1
		};
	}

	
	public static int[] generateIndices(int startIndex) {
		int[] indices = new int[6];
		for(int i = 0; i < 6; i++) {
			indices[i] = i + startIndex;
		}
		return indices;
	}
}
