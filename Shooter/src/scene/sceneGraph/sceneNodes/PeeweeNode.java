package scene.sceneGraph.sceneNodes;

import geom.mesh.Mesh3D;
import geom.mesh.ModelPart;
import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import render.RenderContext;

import static org.lwjgl.opengl.GL11.*;


public class PeeweeNode extends EmptyCoordinateNode {
	private static final BlueprintModel peeweeModel = ModelLoader.loadModel("res/mesh/peewee.mdl", "peewee");
	private Mesh3D peeweeMesh;
	
	private final ModelPart body;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	
	public PeeweeNode() {
		this.peeweeMesh = peeweeModel.createSceneNode();
		this.addChild(peeweeMesh);
		
		this.body = peeweeMesh.getModelPartByName("body");
		this.leftLeg = peeweeMesh.getModelPartByName("leftLeg");
		this.rightLeg = peeweeMesh.getModelPartByName("rightLeg");
	}
	
	public void pointBodyAt(double x, double y) {
		double dx = this.x - x;
		double dy = this.y - y;
		if(dx != 0) {			
			double angle = Math.atan(dy/dx);
			angle = Math.toDegrees(angle);
			angle -= 90;
			if(dx > 0) {
				angle += 180;
			}
			this.body.setRotationZ(angle);
		}
		
		
	}
}
