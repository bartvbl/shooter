package scene.sceneGraph.sceneNodes;

import java.util.ArrayList;

import render.RenderPass;
import scene.sceneGraph.ContainerNode;
import scene.sceneGraph.SceneNode;
import static org.lwjgl.opengl.GL11.*;

public class DisplayListNode extends StackingContainerNode implements SceneNode {

	private final ArrayList<SceneNode> emptyChildList = new ArrayList<SceneNode>();
	private boolean isBuilt = false;
	private int listID = -1;
	
	public void render() {
		if(!isBuilt) {
			buildDisplayList();
		}
		glCallList(listID);
	}
	
	public void rebuild() {
		isBuilt = false;
	}

	private void buildDisplayList() {
		if(listID != -1) {
			destroy();
		}
		this.listID = glGenLists(1);
		glNewList(this.listID, GL_COMPILE);
		for(SceneNode child : this.children) {				
			RenderPass.render(child);
		}
		glEndList();
	}

	public void destroy() {
		glDeleteLists(listID, 1);
	}
	
	public ArrayList<SceneNode> getChildren() {
		return emptyChildList;
	}

}
