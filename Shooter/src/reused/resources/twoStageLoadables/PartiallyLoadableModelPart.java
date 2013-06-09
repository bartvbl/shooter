package reused.resources.twoStageLoadables;

import reused.gl.vbo.BufferDataFormatType;
import reused.resources.Finalizable;
import reused.resources.loaders.obj.StoredModelPart;
import scene.sceneGraph.SceneNode;

public class PartiallyLoadableModelPart extends Finalizable {
	private BlueprintMaterial material;
	private final UnpackedGeometryBuffer geometryBuffer;
	
	public final String name;
	private StoredModelPart destinationPart;

	public PartiallyLoadableModelPart(String name, int numVertices, BufferDataFormatType bufferDataFormatType) {
		this.geometryBuffer = new UnpackedGeometryBuffer(bufferDataFormatType, numVertices);
		this.name = name;
	}
		
	public void setMaterial(BlueprintMaterial newMaterial) {
		this.material = newMaterial;
	}
	public void addVertex(double[] vertex) {
		this.geometryBuffer.addVertex(vertex); 
	}
	
	public void finalizeResource() {
		if(this.destinationPart == null) {
			System.out.println("ERROR: missing part in OBJ file: " + name);
		}
		this.material.finalizeResource();
		this.geometryBuffer.finalizeResource();
		this.destinationPart.addBufferCombo(this.material, this.geometryBuffer.convertToGeometryBuffer());
	}

	public SceneNode createSceneNode() {return null;}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		geometryBuffer.setBufferDataFormat(dataType);
	}

	public void setDestinationPart(StoredModelPart part) {
		this.destinationPart = part;
	}
	
	public String toString() {
		return "PartiallyLoadableModelPart@" + name;
	}

}
