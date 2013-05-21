package scene.sceneGraph;

import java.util.ArrayList;

import render.RenderContext;

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
	
	public ArrayList<SceneNode> getChildren() {
		return this.children;
	}
	

	public void clear() {
		for(SceneNode child : children) {
			child.destroy();
		}
		children.clear();
	}

	public abstract void render(RenderContext context);
	public abstract void destroy();
	
	public boolean isVisible() {
		return true;
	}
}

