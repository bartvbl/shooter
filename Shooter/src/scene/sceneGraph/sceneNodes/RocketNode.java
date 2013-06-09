package scene.sceneGraph.sceneNodes;

import render.RenderContext;
import reused.geom.mesh.Mesh3D;
import reused.resources.loaders.obj.ModelLoader;
import reused.resources.twoStageLoadables.BlueprintModel;
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
