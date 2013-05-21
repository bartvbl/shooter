package scene.sceneGraph.sceneNodes;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import scene.sceneGraph.ContainerNode;
import scene.sceneGraph.SceneNode;

public abstract class StackingContainerNode extends ContainerNode implements SceneNode {
	
	public void preRender() {
		glPushMatrix();
	}
	
	public void postRender() {
		glPopMatrix();
	}
}
