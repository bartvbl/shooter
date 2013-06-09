package scene.sceneGraph;

import render.RenderContext;

public abstract class StackingContainerNode extends ContainerNode implements SceneNode {
	
	public void preRender(RenderContext context) {
		context.pushMatrix();
	}
	
	public void postRender(RenderContext context) {
		context.popMatrix();
	}
}
