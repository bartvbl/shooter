package scene.sceneGraph.sceneNodes;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;
import static org.lwjgl.opengl.GL11.*;

public class MapSceneNode extends CoordinateNode implements SceneNode {

	private final FloatBuffer position = BufferUtils.createFloatBuffer(4);
	
	public void render() {
	}

	public void destroy() {
		
	}


}
