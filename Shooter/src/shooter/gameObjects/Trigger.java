package shooter.gameObjects;

import geom.Point;
import scene.sceneGraph.SceneNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;

public abstract class Trigger extends GameObject {
	private double triggerX = 0;
	private double triggerY = 0;
	private double activationRadius = 0;

	public Trigger(GameObjectType type, SceneNode sceneNode, GameWorld world) {
		super(type, sceneNode, world);
	}

	public abstract void update();
	
	protected boolean isTriggered() {
		Point mapLocation = this.world.controlledNode.getLocation();
		//map scrolls in opposite direction of camera
		double dx = -mapLocation.x - (double) triggerX - 0.5d;
		double dy = -mapLocation.y - (double) triggerY - 0.5d;
		double distanceToCameraCenter = Math.sqrt(dx*dx + dy*dy);
		return distanceToCameraCenter <= activationRadius;
	}

	public void setActivationRadius(double activationRadius) {
		this.activationRadius = activationRadius;
	}

	public void setTriggerLocation(int x, int y) {
		this.triggerX = x;
		this.triggerY = y;
	}

}
