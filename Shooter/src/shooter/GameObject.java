package shooter;

import scene.sceneGraph.SceneNode;

public abstract class GameObject {
	public final GameObjectType type;
	public final SceneNode sceneNode;
	public final GameWorld world;

	public GameObject(GameObjectType type, SceneNode sceneNode, GameWorld world) {
		this.type = type;
		this.sceneNode = sceneNode;
		this.world = world;
	}
	
	public abstract void update();
}
