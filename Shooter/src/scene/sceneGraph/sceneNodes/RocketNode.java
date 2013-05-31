package scene.sceneGraph.sceneNodes;

import geom.mesh.Mesh3D;
import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import render.RenderContext;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

public class RocketNode extends CoordinateNode implements SceneNode {

	private static BlueprintModel rocketModel = ModelLoader.loadModel("res/mesh/missile.mdl", "rocket");
	private final Mesh3D rocketMesh;
	
	public RocketNode() {
		this.rocketMesh = rocketModel.createSceneNode();
		this.addChild(rocketMesh);
	}
	
	public void render(RenderContext context) {
		
	}

	public void destroy() {
		
	}

}
