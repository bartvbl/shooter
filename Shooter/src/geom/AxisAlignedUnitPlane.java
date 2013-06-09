package geom;

public class AxisAlignedUnitPlane {
	//this is the class that creates arrays of geometry and indices for the game's map.
	//just for the sake of having it down on paper:
	//to calculate the normal of a plane, first divide it into two triangles.
	//Then, take an origin of these triangles and construct two vectors starting at that origin vertex and ending at the other two vertices.
	//Calculating the vector product of these vectors will yield a surface normal for that triangle. Do the same for the other triangle, 
	//and you have a surface quad for your terrain.
	
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
			x, y, z, 				0, 0,		0, 0, 1,
			x + 1, y, z, 			1, 0,		0, 0, 1,
			x + 1, y + 1, z,		1, 1,		0, 0, 1,
			
			x, y, z, 				0, 0,		0, 0, 1,
			x + 1, y + 1, z, 		1, 1,		0, 0, 1,
			x, y + 1, z, 			0, 1,		0, 0, 1
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
