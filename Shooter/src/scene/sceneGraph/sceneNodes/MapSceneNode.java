package scene.sceneGraph.sceneNodes;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import render.RenderContext;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

public class MapSceneNode extends CoordinateNode implements SceneNode {

	private final FloatBuffer position = BufferUtils.createFloatBuffer(4);

	public void postRender(RenderContext context) {
		context.popMatrix();
	}
	
	public void render(RenderContext context) {
		
	}
		
	public void destroy() {

	}
}
