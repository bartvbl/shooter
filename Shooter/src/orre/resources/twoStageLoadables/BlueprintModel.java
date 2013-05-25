package orre.resources.twoStageLoadables;

import geom.mesh.Mesh3D;

import java.util.ArrayList;
import java.util.HashMap;

import orre.resources.Finalizable;
import orre.resources.loaders.obj.StoredModelPart;

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
			mesh.addChild(part.createSceneNode());
		}
		return mesh;
	}

}
