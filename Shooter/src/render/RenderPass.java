package render;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import scene.sceneGraph.SceneNode;
import util.Stack;

public class RenderPass {
	public static void render(SceneNode node, RenderContext context) {
		doRender(node, context, 0);
		
	}

	public static void doRender(SceneNode node, RenderContext context, int level) {
		ArrayList<SceneNode> children = node.getChildren();
		if(node.isVisible()) {			
			node.preRender(context);
			node.render(context);
			for(SceneNode child : children) {
				RenderPass.doRender(child, context, level + 1);
			}
			node.postRender(context);
		}
	}
}
