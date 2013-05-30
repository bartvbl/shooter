package shooter;

import java.util.ArrayList;

import scene.Scene;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;
import shooter.map.Map;

public class GameWorld {
	
	public final Map map;
	public final Player player;
	public final EmptyCoordinateNode controlledNode;
	public final Scene scene;
	
	private final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	public GameWorld(Scene scene) {
		this.scene = scene;
		this.player = Player.createInstance(this);
		this.map = Map.createInstance(this);
		this.controlledNode = new EmptyCoordinateNode();
		this.controlledNode.setLocation(-1.5f, -1.5f, 0);
		
		this.gameObjects.add(player);
		this.gameObjects.add(map);

		scene.buildScene(player.sceneNode, map.sceneNode, controlledNode);
	}
	
	public void addGameObject(GameObject object) {
		gameObjects.add(object);
	}
	
	public void removeGameObject(GameObject object) {
		gameObjects.remove(object);
		object.sceneNode.destroy();
	}

	public void update() {
		for(GameObject gameObject : gameObjects) {
			gameObject.update();
		}
	}


}
