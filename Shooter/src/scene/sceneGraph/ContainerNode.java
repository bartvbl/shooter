package scene.sceneGraph;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;

import java.util.ArrayList;

public abstract class ContainerNode implements SceneNode {
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();

	public void addChild(SceneNode node) 
	{
		this.children.add(node);
	}

	public void removeChild(SceneNode node) 
	{
		this.children.remove(node);
	}

	public void setVisibility(boolean isVisible) 
	{
		this.visible = isVisible;
	}
	
	protected void renderChildren()
	{
		for(SceneNode child : this.children)
		{
			child.render();
		}
	}

	public ArrayList<SceneNode> getChildren() {
		return this.children;
	}
	

	public void clear() {
		for(SceneNode child : children) {
			child.destroy();
		}
		children.clear();
	}

	public abstract void render();
	public abstract void destroy();
	
	public boolean isVisible() {
		return true;
	}
}

