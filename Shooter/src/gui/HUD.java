package gui;

import scene.sceneGraph.sceneNodes.HUDSceneNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;

public class HUD extends GameObject {
	
	private final HUDSceneNode hudNode;

	public static HUD createInstance(GameWorld gameWorld) {
		return new HUD(gameWorld, new HUDSceneNode());
	}

	public HUD(GameWorld world, HUDSceneNode hudSceneNode) {
		super(GameObjectType.HUD, hudSceneNode, world);
		this.hudNode = hudSceneNode;
	}

	public void update() {
		
	}

}
