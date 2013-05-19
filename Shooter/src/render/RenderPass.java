package render;

import java.util.ArrayList;

import scene.sceneGraph.SceneNode;
import util.Stack;

public class RenderPass {
	public static void render(SceneNode node) {
		ArrayList<SceneNode> children = node.getChildren();
		
		if(node.isVisible()) {			
			node.preRender();
			node.render();
			
			for(SceneNode child : children) {
				RenderPass.render(child);
			}
			node.postRender();
		}
	}
}
