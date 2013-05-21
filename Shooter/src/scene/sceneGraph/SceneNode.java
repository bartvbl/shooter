package scene.sceneGraph;

import java.util.ArrayList;

import render.RenderContext;

public interface SceneNode {
	public void preRender(RenderContext context);
	public void render(RenderContext context);
	public void postRender(RenderContext context);
	
	public void addChild(SceneNode node);
	public void removeChild(SceneNode node);
	public ArrayList<SceneNode> getChildren();
	
	public void destroy();
	
	public boolean isVisible();
}
