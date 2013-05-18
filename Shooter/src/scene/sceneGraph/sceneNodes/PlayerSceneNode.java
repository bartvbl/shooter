package scene.sceneGraph.sceneNodes;

import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

import static org.lwjgl.opengl.GL11.*;

public class PlayerSceneNode extends CoordinateNode implements SceneNode {

	public void render() {
		glColor4d(1, 1, 0, 1);
		glBegin(GL_QUADS);
		glVertex2d(-0.03, -0.03);
		glVertex2d(0.03, -0.03);
		glVertex2d(0.03, 0.03);
		glVertex2d(-0.03, 0.03);
		glEnd();
	}

	public void destroy() {
		
	}

}
