package geom.mesh;

import gl.material.Material;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;

public class ModelPart extends EmptyCoordinateNode implements SceneNode {
	
	private String name;

	public void addMaterialAndGeometryBufferCombo(Material material, String name) {
		this.addChild(material);
		this.name = name;
	}
	public void destroy() {}
	
	public String toString() {
		return "ModelPart " + this.name;
	}
	
}
