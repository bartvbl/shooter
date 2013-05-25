package scene.sceneGraph.sceneNodes;

import geom.AxisAlignedUnitPlane;
import gl.material.Material;
import gl.texture.Texture;
import gl.texture.TextureLoader;
import gl.vbo.BufferDataFormatType;
import gl.vbo.GeometryBuffer;
import gl.vbo.GeometryBufferGenerator;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import scene.sceneGraph.SceneNode;

public class DoorSceneNode extends EmptyCoordinateNode implements SceneNode {
	private static final SceneNode doorBlockNode = createDoorNode();
	private final EmptyCoordinateNode door;
	
	public DoorSceneNode() {
		this.door = new EmptyCoordinateNode();
		
		this.addChild(door);
		
		this.door.addChild(doorBlockNode);
	}


	private static SceneNode createDoorNode() {
		final int numFaces = 5;
		final int numVertices = 2*3;
		final int coordinatesPerVertex = 8;
		DoubleBuffer geometryData = BufferUtils.createDoubleBuffer(numFaces * numVertices * coordinatesPerVertex);
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(numFaces * numVertices);
		
		geometryData.put(AxisAlignedUnitPlane.createFrontPlane(0, 0, 0));
		geometryData.put(AxisAlignedUnitPlane.createBackPlane(0, 0, 0));
		geometryData.put(AxisAlignedUnitPlane.createLeftPlane(0, 0, 0));
		geometryData.put(AxisAlignedUnitPlane.createRightPlane(0, 0, 0));
		geometryData.put(AxisAlignedUnitPlane.createTopPlane(0, 0, 1));
		
		for(int i = 0; i < numFaces; i++) {			
			indexBuffer.put(AxisAlignedUnitPlane.generateIndices(indexBuffer.position()));
		}
		
		GeometryBuffer geometryBuffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryData, indexBuffer);
		
		Material material = new Material("doorMaterial");
		Texture doorTexture = TextureLoader.loadTextureFromFile("res/textures/door.png");
		material.setDiffuseTexture(doorTexture);
		material.addChild(geometryBuffer);
		
		return material;
	}

}
