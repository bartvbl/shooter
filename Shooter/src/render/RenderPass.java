package render;

import java.util.ArrayList;

import scene.sceneGraph.SceneNode;
import util.Stack;

public class RenderPass {
	public static void render(SceneNode node, RenderContext context) {
		ArrayList<SceneNode> children = node.getChildren();
		if(node.isVisible()) {			
			node.preRender(context);
			node.render(context);
			for(SceneNode child : children) {
				RenderPass.render(child, context);
			}
			node.postRender(context);
		}
	}
}
