package scene.sceneGraph.sceneNodes;

import geom.mesh.Mesh3D;
import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import render.RenderContext;

import static org.lwjgl.opengl.GL11.*;


public class PeeweeNode extends EmptyCoordinateNode {
	private static final BlueprintModel peeweeModel = ModelLoader.loadModel("res/mesh/peewee.mdl", "peewee");
	private Mesh3D peeweeMesh;

	public void preRender(RenderContext context) {
		super.preRender(context);
		glEnable(GL_NORMALIZE);
	}
	
	public void render(RenderContext context) {
		context.rotate(90, 1, 0, 0);
		context.translate(0, 0, -5);
		context.scale(0.03f, 0.03f, 0.03f);
	}
	
	public void postRender(RenderContext context) {
		super.postRender(context);
		glDisable(GL_NORMALIZE);
	}
	
	public PeeweeNode() {
		this.peeweeMesh = peeweeModel.createSceneNode();
		this.addChild(peeweeMesh);
	}
}
