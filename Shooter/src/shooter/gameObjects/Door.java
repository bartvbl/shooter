package shooter.gameObjects;

import geom.Point;
import scene.sceneGraph.sceneNodes.DoorSceneNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.map.Orientation;

public class Door extends Trigger {

	private int x;
	private int y;
	private Orientation orientation;
	private final DoorSceneNode doorNode;
	private final double activationRadius = 2;
	private final double moveSpeed = 0.025;
	private double height = 0;

	private Door(DoorSceneNode sceneNode, int x, int y, GameWorld world) {
		super(GameObjectType.DOOR, sceneNode, world);
		this.doorNode = sceneNode;
		this.x = x;
		this.y = y;
		super.setActivationRadius(activationRadius);
		super.setTriggerLocation(x, y);
	}

	public void update() {
		if(isTriggered()) {
			height -= moveSpeed;
			if(height <= -0.99) {
				height = -0.99;//.01 to avoid z fighting
			}
		} else {
			height += moveSpeed;
			if(height >= 0d) {
				height = 0.0;
			}
		}
		this.doorNode.setLocation(0, 0, (float) height);
	}
	
	

	public static Door createInstance(GameWorld world, int x, int y, Orientation orientation) {
		return new Door(new DoorSceneNode(x, y), x, y, world);
	}

}
