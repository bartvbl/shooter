package shooter.gameObjects;

import scene.sceneGraph.SceneNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import util.PlayerDistance;

public abstract class Trigger extends GameObject {
	private double triggerX = 0;
	private double triggerY = 0;
	private double activationRadius = 0;

	public Trigger(GameObjectType type, SceneNode sceneNode, GameWorld world) {
		super(type, sceneNode, world);
	}

	public abstract void update();
	
	protected boolean isTriggered() {
		double distanceToPlayer = PlayerDistance.toPoint(world, triggerX, triggerY);
		return distanceToPlayer <= activationRadius;
	}

	public void setActivationRadius(double activationRadius) {
		this.activationRadius = activationRadius;
	}

	public void setTriggerLocation(int x, int y) {
		this.triggerX = x;
		this.triggerY = y;
	}

}
