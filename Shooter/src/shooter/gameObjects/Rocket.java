package shooter.gameObjects;

import geom.Point;
import scene.sceneGraph.sceneNodes.RocketNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.RayTraceResult;

public abstract class Rocket extends GameObject {
	private final RocketNode rocketNode;
	private double dx;
	private double dy;
	protected double speed;
	protected double heading;
	protected double damage;

	protected Rocket(RocketNode rocketNode, GameWorld world) {
		super(GameObjectType.ROCKET, rocketNode, world);
		this.rocketNode = rocketNode;
		world.addGameObject(this);
		world.scene.addMapSceneNode(rocketNode);
	}

	public void update() {
		this.rocketNode.translate(dx * speed, dy * speed, 0);
		RayTraceResult result = doObstacleRayTrace();
		if(result.hasHitEnemy) {
			//result.foundObject.damage(damage);
			destroy();
		}
		if(!result.isOutOfRange) {
			destroy();
		}
	}

	protected abstract RayTraceResult doObstacleRayTrace();

	private void destroy() {
		world.removeGameObject(this);
		world.scene.removeMapSceneNode(rocketNode);
	}
	
	protected void setLocation(Point rocketOrigin) {
		rocketNode.setLocation((float) rocketOrigin.x, (float) rocketOrigin.y, 0.3f);
	}
	
	protected void setHeading(double rocketHeading) {
		rocketNode.setRotationZ(-rocketHeading + 180);
		this.dx = Math.sin(Math.toRadians(rocketHeading));
		this.dy = Math.cos(Math.toRadians(rocketHeading));
		this.heading = rocketHeading;
	}
	
	protected void setSpeed(double rocketSpeed) {
		this.speed = rocketSpeed;
	}
	
	protected void setDamage(double rocketDamage) {
		this.damage = rocketDamage;
	}
	
	protected Point getRocketLocation() {
		return rocketNode.getLocation();
	}
}
