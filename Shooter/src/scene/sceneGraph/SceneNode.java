package scene.sceneGraph;

import java.util.ArrayList;

public interface SceneNode {
	public void preRender();
	public void render();
	public void postRender();
	
	public void addChild(SceneNode node);
	public void removeChild(SceneNode node);
	public ArrayList<SceneNode> getChildren();
	
	public void destroy();
	
	public boolean isVisible();
}
