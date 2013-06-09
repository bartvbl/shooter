package scene.sceneGraph.sceneNodes;

import geom.AxisAlignedUnitPlane;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import reused.gl.material.Material;
import reused.gl.texture.Texture;
import reused.gl.texture.TextureLoader;
import reused.gl.vbo.BufferDataFormatType;
import reused.gl.vbo.GeometryBuffer;
import reused.gl.vbo.GeometryBufferGenerator;
import scene.sceneGraph.SceneNode;

public class DoorSceneNode extends EmptyCoordinateNode implements SceneNode {
	private final EmptyCoordinateNode door;
	
	public DoorSceneNode(int x, int y) {
		this.door = new EmptyCoordinateNode();
		
		this.addChild(door);
		
		this.door.addChild(createDoorNode(x, y));
	}


	private static SceneNode createDoorNode(int x, int y) {
		final int numFaces = 5;
		final int numVertices = 2*3;
		final int coordinatesPerVertex = 8;
		DoubleBuffer geometryData = BufferUtils.createDoubleBuffer(numFaces * numVertices * coordinatesPerVertex);
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(numFaces * numVertices);
		
		geometryData.put(AxisAlignedUnitPlane.createFrontPlane(x, y, 0));
		geometryData.put(AxisAlignedUnitPlane.createBackPlane(x, y, 0));
		geometryData.put(AxisAlignedUnitPlane.createLeftPlane(x, y, 0));
		geometryData.put(AxisAlignedUnitPlane.createRightPlane(x, y, 0));
		geometryData.put(AxisAlignedUnitPlane.createTopPlane(x, y, 1));
		
		for(int i = 0; i < numFaces; i++) {			
			indexBuffer.put(AxisAlignedUnitPlane.generateIndices(indexBuffer.position()));
		}
		
		geometryData.rewind();
		indexBuffer.rewind();
		
		GeometryBuffer geometryBuffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryData, indexBuffer);
		
		Material material = new Material("doorMaterial");
		Texture doorTexture = TextureLoader.loadTextureFromFile("res/textures/door.png");
		material.setDiffuseTexture(doorTexture);
		material.addChild(geometryBuffer);
		
		return material;
	}

}
