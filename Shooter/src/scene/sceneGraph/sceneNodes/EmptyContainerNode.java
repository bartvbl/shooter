package scene.sceneGraph.sceneNodes;

import render.RenderContext;
import scene.sceneGraph.ContainerNode;
import static org.lwjgl.opengl.GL11.*;

public class EmptyContainerNode extends StackingContainerNode {

	public void render(RenderContext context) {}

	public void destroy() {}


}
