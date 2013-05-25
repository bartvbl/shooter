package scene.sceneGraph.sceneNodes;

import render.RenderContext;
import scene.sceneGraph.CoordinateNode;

public class EmptyCoordinateNode extends CoordinateNode {

	public void destroy() {
		
	}

	public void postRender(RenderContext context) {
		context.popMatrix();
	}

	public void render(RenderContext context) {
		
	}


}
