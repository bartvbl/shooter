package orre.resources.twoStageLoadables;

import geom.mesh.Mesh3D;
import geom.mesh.ModelPart;

import java.util.ArrayList;
import java.util.HashMap;

import orre.resources.Finalizable;
import orre.resources.loaders.obj.StoredModelPart;
import scene.sceneGraph.SceneNode;

public class BlueprintModel extends Finalizable {
	private ArrayList<StoredModelPart> topLevelNodeList = new ArrayList<StoredModelPart>();
	private HashMap<String, StoredModelPart> modelParts = new HashMap<String, StoredModelPart>();
	public final String name;
	
	public BlueprintModel(String name)
	{
		this.name = name;
	}

	public void addTopLevelPart(StoredModelPart currentPart) {
		this.topLevelNodeList.add(currentPart);
	}

	public void registerPart(StoredModelPart part) {
		this.modelParts.put(part.name, part);
	}
	
	public void linkGeometryPartToModelPart(String partName, PartiallyLoadableModelPart partToLink) {
		StoredModelPart part = this.modelParts.get(partName);
		partToLink.setDestinationPart(part);
	}

	public void finalizeResource() {}

	public Mesh3D createSceneNode() {
		Mesh3D mesh = new Mesh3D();
		for(StoredModelPart part : this.topLevelNodeList) {
			ModelPart partNode = part.createSceneNode();
			mesh.addChild(partNode);
			mesh.registerPart(part.name, partNode);
			parseNodeChildren(part, partNode, mesh);
		}
		return mesh;
	}

	private void parseNodeChildren(StoredModelPart part, ModelPart partNode, Mesh3D mesh) {
		for(StoredModelPart child : part.getChildren()) {
			ModelPart childPart = child.createSceneNode();
			partNode.addChild(childPart);
			mesh.registerPart(child.name, childPart);
			parseNodeChildren(child, childPart, mesh);
		}
	}

}
