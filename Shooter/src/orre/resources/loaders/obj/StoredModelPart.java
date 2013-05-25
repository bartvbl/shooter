package orre.resources.loaders.obj;

import geom.mesh.ModelPart;
import gl.material.Material;
import gl.vbo.GeometryBuffer;

import java.util.ArrayList;

import orre.resources.twoStageLoadables.BlueprintMaterial;
import orre.resources.twoStageLoadables.UnpackedGeometryBuffer;
import scene.sceneGraph.SceneNode;

public class StoredModelPart {
	
	public final ModelPartType partType;
	public final String name;
	private ArrayList<BlueprintMaterial> materials = new ArrayList<BlueprintMaterial>();
	private ArrayList<GeometryBuffer> geometryBuffers = new ArrayList<GeometryBuffer>();
	
	private ArrayList<StoredModelPart> childList = new ArrayList<StoredModelPart>();

	public StoredModelPart(ModelPartType partType, String name)
	{
		this.partType = partType;
		this.name = name;
	}
	
	public void addChild(StoredModelPart child)
	{
		this.childList.add(child);
	}
	
	public ArrayList<StoredModelPart> getChildren()
	{
		return this.childList;
	}

	public void addBufferCombo(BlueprintMaterial blueprintMaterial, GeometryBuffer geometryBuffer) {
		this.materials.add(blueprintMaterial);
		this.geometryBuffers.add(geometryBuffer);
	}

	public SceneNode createSceneNode() {
		ModelPart part = new ModelPart();
		for(int i = 0; i < this.materials.size(); i++) {
			Material material = this.materials.get(i).convertToMaterial();
			material.addChild(this.geometryBuffers.get(i));
			part.addMaterialAndGeometryBufferCombo(material, this.name);
		}
		for(StoredModelPart child : this.childList) {
			part.addChild(child.createSceneNode());
		}
		return part;
	}
}
