package scene.sceneGraph.sceneNodes;

import render.RenderContext;
import reused.geom.mesh.Mesh3D;
import reused.resources.loaders.obj.ModelLoader;
import reused.resources.twoStageLoadables.BlueprintModel;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

public class ShieldPackNode extends CoordinateNode implements SceneNode {
	private static final BlueprintModel plusModel = ModelLoader.loadModel("res/mesh/spark.mdl", "spark");
	
	private final Mesh3D sparkMesh;

	private double counter = 0;
	
	public ShieldPackNode() {
		this.sparkMesh = plusModel.createSceneNode();
		this.addChild(sparkMesh);
	}

	public void render(RenderContext context) {

	}

	public void destroy() {

	}

	public void progressAnimation() {
		counter++;
		double heightFactor = 0.5 * Math.sin(counter / 30d) + 0.5;
		sparkMesh.setLocation(0, 0, (float)(heightFactor * 0.6 + 0.2));
		sparkMesh.rotate(0.4);
	}
}
