package reused.resources.loaders.obj;


import java.util.ArrayList;

import reused.geom.mesh.ModelPart;
import reused.gl.material.Material;
import reused.gl.vbo.GeometryBuffer;
import reused.resources.twoStageLoadables.BlueprintMaterial;
import reused.resources.twoStageLoadables.UnpackedGeometryBuffer;
import scene.sceneGraph.SceneNode;

public class StoredModelPart {
	
	public final ModelPartType partType;
	public final String name;
	private ArrayList<BlueprintMaterial> materials = new ArrayList<BlueprintMaterial>();
	private ArrayList<GeometryBuffer> geometryBuffers = new ArrayList<GeometryBuffer>();
	
	private ArrayList<StoredModelPart> childList = new ArrayList<StoredModelPart>();
	private float pivotX, pivotY, pivotZ;

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
	
	public void setPivotLocation(float x, float y, float z) {
		this.pivotX = x;
		this.pivotY = y;
		this.pivotZ = z;
	}

	public void addBufferCombo(BlueprintMaterial blueprintMaterial, GeometryBuffer geometryBuffer) {
		this.materials.add(blueprintMaterial);
		this.geometryBuffers.add(geometryBuffer);
	}

	public ModelPart createSceneNode() {
		ModelPart part = new ModelPart();
		part.setPivot(pivotX, pivotY, pivotZ);
		for(int i = 0; i < this.materials.size(); i++) {
			Material material = this.materials.get(i).convertToMaterial();
			material.addChild(this.geometryBuffers.get(i));
			part.addMaterialAndGeometryBufferCombo(material, this.name);
		}
		
		return part;
	}
}
