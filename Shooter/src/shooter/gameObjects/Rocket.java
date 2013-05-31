package shooter.gameObjects;

import geom.Point;
import scene.sceneGraph.sceneNodes.RocketNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.RayTraceResult;
import shooter.ShotTracer;

public class Rocket extends GameObject {

	private final RocketNode rocketNode;
	private double dx;
	private double dy;
	private double speed;
	private double heading;
	private double damage;

	private Rocket(RocketNode sceneNode, GameWorld world) {
		super(GameObjectType.ROCKET, sceneNode, world);
		this.rocketNode = sceneNode;
	}

	public void update() {
		this.rocketNode.translate(dx * speed, dy * speed, 0);
		RayTraceResult result = ShotTracer.rayTraceEnemy(world, heading, rocketNode.getLocation(), speed);
		if(result.hasHitEnemy) {
			result.foundObject.damage(damage);
			destroy();
		}
		if(!result.isOutOfRange) {
			destroy();
		}
	}

	private void destroy() {
		world.removeGameObject(this);
		world.scene.removeMapSceneNode(rocketNode);
	}
	
	private void setLocation(Point rocketOrigin) {
		rocketNode.setLocation((float) rocketOrigin.x, (float) rocketOrigin.y, 0.3f);
	}
	
	private void setHeading(double rocketHeading) {
		rocketNode.setRotationZ(-rocketHeading + 180);
		this.dx = Math.sin(Math.toRadians(rocketHeading));
		this.dy = Math.cos(Math.toRadians(rocketHeading));
		this.heading = rocketHeading;
	}
	
	private void setSpeed(double rocketSpeed) {
		this.speed = rocketSpeed;
	}
	
	private void setDamage(double rocketDamage) {
		this.damage = rocketDamage;
	}

	public static Rocket spawn(GameWorld world, Point rocketOrigin, double rocketHeading, double rocketSpeed, double rocketDamage) {
		Rocket rocket = new Rocket(new RocketNode(), world);
		world.addGameObject(rocket);
		world.scene.addMapSceneNode(rocket.sceneNode);
		rocket.setHeading(rocketHeading);
		rocket.setLocation(rocketOrigin);
		rocket.setSpeed(rocketSpeed);
		rocket.setDamage(rocketDamage);
		return rocket;
	}

}
