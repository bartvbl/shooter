package scene.sceneGraph.sceneNodes;

import render.RenderContext;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

import static org.lwjgl.opengl.GL11.*;

public class PlayerSceneNode extends StackingContainerNode implements SceneNode {
	public void destroy() {
		
	}

	public void render(RenderContext context) {
		glColor4d(1, 1, 0, 1);
		glBegin(GL_QUADS);
		glVertex3d(-0.03, -0.03, 0.01);
		glVertex3d(0.03, -0.03, 0.01);
		glVertex3d(0.03, 0.03, 0.01);
		glVertex3d(-0.03, 0.03, 0.01);
		glEnd();
	}

}
