package scene.sceneGraph.sceneNodes;

import java.util.ArrayList;

import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import render.RenderContext;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

import static org.lwjgl.opengl.GL11.*;

public class PlayerSceneNode extends StackingContainerNode implements SceneNode {
	public PlayerSceneNode() {
		BlueprintModel model = ModelLoader.loadModel("res/mesh/ak.mdl", "AK");
		model.finalizeResource();
		SceneNode node = model.createSceneNode();
		this.addChild(node);
	}

	public void destroy() {
		
	}

	public void render(RenderContext context) {
		context.rotate(90, 1, 0, 0);
		context.scale(0.03f, 0.03f, 0.03f);
	}

}
