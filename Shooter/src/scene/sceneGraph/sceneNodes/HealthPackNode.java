package scene.sceneGraph.sceneNodes;

import render.RenderContext;
import reused.geom.mesh.Mesh3D;
import reused.resources.loaders.obj.ModelLoader;
import reused.resources.twoStageLoadables.BlueprintModel;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

public class HealthPackNode extends CoordinateNode implements SceneNode {
	private static final BlueprintModel plusModel = ModelLoader.loadModel("res/mesh/health.mdl", "plus");
	
	private final Mesh3D plusMesh;

	private double counter = 0;
	
	public HealthPackNode() {
		this.plusMesh = plusModel.createSceneNode();
		this.addChild(plusMesh);
	}

	public void render(RenderContext context) {

	}

	public void destroy() {

	}

	public void progressAnimation() {
		counter++;
		double heightFactor = 0.5 * Math.sin(counter / 30d) + 0.5;
		plusMesh.setLocation(0, 0, (float)(heightFactor * 0.6 + 0.2));
		plusMesh.rotate(0.4);
	}

}
