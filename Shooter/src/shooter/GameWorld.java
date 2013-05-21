package shooter;

import gui.HUD;

import java.util.ArrayList;

import scene.Scene;
import shooter.map.Map;

public class GameWorld {
	
	public final Map map;
	public final Player player;
	public final EffectsTracker effects;
	public final HUD hud;
	public final Scene scene;
	
	private final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	public GameWorld(Scene scene) {
		this.player = Player.createInstance(this);
		this.map = Map.createInstance(this);
		this.effects = EffectsTracker.createInstance(this);
		this.hud = HUD.createInstance(this);
		
		this.gameObjects.add(hud);
		this.gameObjects.add(player);
		this.gameObjects.add(map);
		this.gameObjects.add(effects);

		this.scene = scene;
		scene.buildScene(player.sceneNode, map.sceneNode, hud.sceneNode);
	}
	
	public void addGameObject(GameObject object) {
		gameObjects.add(object);
		scene.addSceneNode(object.sceneNode);
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
