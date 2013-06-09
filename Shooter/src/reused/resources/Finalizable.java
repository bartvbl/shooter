package reused.resources;

import scene.sceneGraph.SceneNode;

public abstract class Finalizable {
	public void finalizeAndSendToCache()
	{
		this.finalizeResource();
	}
	
	public abstract void finalizeResource();
	public abstract SceneNode createSceneNode();
}
