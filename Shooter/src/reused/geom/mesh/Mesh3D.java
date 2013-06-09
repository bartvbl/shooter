package reused.geom.mesh;

import java.util.HashMap;

import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;

public class Mesh3D extends EmptyCoordinateNode implements SceneNode {
	private HashMap<String, ModelPart> parts = new HashMap<String, ModelPart>();
	
	public ModelPart getModelPartByName(String name) {
		return this.parts.get(name);
	}

	public void registerPart(String name, ModelPart partNode) {
		this.parts.put(name, partNode);
	}

}
