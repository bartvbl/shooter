package shooter;

import java.util.ArrayList;

import scene.Scene3D;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;
import shooter.dialogue.DialogueHandler;
import shooter.dialogue.DialogueSequence;
import shooter.map.Map;

public class GameWorld {
	
	public final Map map;
	public final Player player;
	public final EmptyCoordinateNode controlledNode;
	public final Scene3D scene;
	public final DialogueHandler dialogueHandler;
	
	private final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private final ArrayList<GameObject> gameObjectRemovalQueue = new ArrayList<GameObject>();
	private final ArrayList<GameObject> gameObjectAdditionQueue = new ArrayList<GameObject>();

	public GameWorld(Scene3D scene) {
		this.scene = scene;
		this.player = Player.createInstance(this);
		this.controlledNode = new EmptyCoordinateNode();
		this.controlledNode.setLocation(-1.5f, -1.5f, 0);
		this.controlledNode.setPivot(-1.5f, -1.5f, 0);
		this.map = Map.createInstance(this);
		this.dialogueHandler = new DialogueHandler();
		
		this.gameObjects.add(player);
		this.gameObjects.add(map);
		
		scene.buildScene(player.sceneNode, map.sceneNode, controlledNode);

		this.dialogueHandler.showDialogueSequence(DialogueSequence.INTRO);
	}
	
	public void addGameObject(GameObject object) {
		gameObjectAdditionQueue.add(object);
	}
	
	public void removeGameObject(GameObject object) {
		gameObjectRemovalQueue.add(object);
	}
	
	public GameObject[] getGameObjectsByType(GameObjectType type) {
		ArrayList<GameObject> foundGameObjects = new ArrayList<GameObject>();
		for(GameObject object : gameObjects) {
			if(object.type == type) {
				foundGameObjects.add(object);
			}
		}
		return foundGameObjects.toArray(new GameObject[foundGameObjects.size()]);
	}

	public void update() {
		if(this.dialogueHandler.isActive()) {
			this.dialogueHandler.update();
		} else {			
			for(GameObject gameObject : gameObjects) {
				gameObject.update();
			}
		}
		flushQueues();
	}

	private void flushQueues() {
		//had to queue up destroyed game objects to avoid concurrent modification exceptions.
		for(GameObject object : gameObjectRemovalQueue) {
			gameObjects.remove(object);
			object.sceneNode.destroy();			
		}
		for(GameObject object : gameObjectAdditionQueue) {
			gameObjects.add(object);
		}
		gameObjectAdditionQueue.clear();
		gameObjectRemovalQueue.clear();
	}


}
