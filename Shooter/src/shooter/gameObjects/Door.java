package shooter.gameObjects;

import geom.Point;
import scene.sceneGraph.sceneNodes.DoorSceneNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.map.Orientation;

public class Door extends GameObject {

	private int x;
	private int y;
	private Orientation orientation;
	private final DoorSceneNode doorNode;
	private final double activationRadius = 2;
	private final double moveSpeed = 0.01;
	private double height = 0;

	private Door(DoorSceneNode sceneNode, GameWorld world) {
		super(GameObjectType.DOOR, sceneNode, world);
		this.doorNode = sceneNode;
	}

	public void update() {
		if(isCloseToDoor()) {
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
		this.doorNode.setLocation(x, y, (float) height);
	}
	
	private boolean isCloseToDoor() {
		Point mapLocation = this.world.controlledNode.getLocation();
		//map scrolls in opposite direction of camera
		double dx = -mapLocation.x - x;
		double dy = -mapLocation.y - y;
		double distanceToCameraCenter = Math.sqrt(dx*dx + dy*dy);
		return distanceToCameraCenter <= activationRadius;
	}

	public static Door createInstance(GameWorld world) {
		return new Door(new DoorSceneNode(), world);
	}

	public void setLocation(int x, int y, Orientation orientation) {
		this.x = x;
		this.y = y;
		this.doorNode.setLocation(x, y, 0);
		
		this.orientation = orientation;
		
	}

}
