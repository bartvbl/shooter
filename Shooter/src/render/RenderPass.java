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
			System.out.println("<node> " + node);
			for(SceneNode child : children) {
				RenderPass.render(child, context);
			}
			System.out.println("</node> " + node);
			node.postRender(context);
		}
	}
}
