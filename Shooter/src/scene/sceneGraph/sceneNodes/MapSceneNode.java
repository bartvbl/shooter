package scene.sceneGraph.sceneNodes;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;
import static org.lwjgl.opengl.GL11.*;

public class MapSceneNode extends CoordinateNode implements SceneNode {

	private final FloatBuffer position = BufferUtils.createFloatBuffer(4);
	
	public void render() {
		position.put(0).put(0).put(0.5f).put(1).flip();
		glLight(GL_LIGHT0, GL_POSITION, position);
	}

	public void destroy() {
		
	}


}
