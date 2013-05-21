package scene.sceneGraph.sceneNodes;

import render.RenderContext;
import scene.sceneGraph.ContainerNode;
import scene.sceneGraph.SceneNode;

public abstract class StackingContainerNode extends ContainerNode implements SceneNode {
	
	public void preRender(RenderContext context) {
		context.pushMatrix();
	}
	
	public void postRender(RenderContext context) {
		context.popMatrix();
	}
}
