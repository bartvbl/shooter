package render;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import reused.util.Stack;
import scene.sceneGraph.SceneNode;

public class RenderPass {
	private static int numRenders = 0;
	
	public static void render(SceneNode node, RenderContext context) {
		doRender(node, context, 0);
		
	}

	public static void doRender(SceneNode node, RenderContext context, int level) {
		ArrayList<SceneNode> children = node.getChildren();
		if(node.isVisible()) {			
//			for(int i = 0; i <= level; i++) {
//				System.out.print('\t');
//			}
//			System.out.print(context.stackLevel() + " ("+(level+1)+") : " + node);
//			System.out.print("\n");
			node.preRender(context);
			node.render(context);
			for(SceneNode child : children) {
				RenderPass.doRender(child, context, level + 1);
			}
			node.postRender(context);
			
//			for(int i = 0; i <= level; i++) {
//				System.out.print('\t');
//			}
//			System.out.print(context.stackLevel() + " ("+(level+1)+") <ended> " + node);
//			System.out.print("\n");
		}
	}
}
