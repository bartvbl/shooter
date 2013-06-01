package shooter.gameObjects;

import org.lwjgl.util.Timer;

import geom.Point;
import scene.sceneGraph.sceneNodes.PeeweeNode;
import shooter.Damageable;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.RocketSpawner;
import util.PlayerDistance;

public class Peewee extends GameObject implements Damageable {
	private static final double firingRate = 0.55;
	private static final double firingRange = 5;
	private static final double rocketSpeed = 0.023;
	private static final double rocketDamage = 0.1;
	private static final double rocketDistanceFromCenter = 0.25;
	
	private final Timer timer;
	private final PeeweeNode peeweeNode;

	private double health = 1;
	private boolean fireFromLeftSide = true;
	
	public static Peewee spawn(int x, int y, GameWorld world) {
		Peewee peewee = new Peewee(new PeeweeNode(), world);
		peewee.setLocation((double) x + 0.5d, (double) y + 0.5d);
		world.addGameObject(peewee);
		world.scene.addMapSceneNode(peewee.sceneNode);
		
		return peewee;
	}


	private Peewee(PeeweeNode sceneNode, GameWorld world) {
		super(GameObjectType.PEEWEE, sceneNode, world);
		this.timer = new Timer();
		timer.resume();
		this.peeweeNode = sceneNode;
	}
	
	private void setLocation(double x, double y) {
		peeweeNode.setLocation((float) x, (float) y, 0);
	}

	public void update() {
		Point peeweeLocation = peeweeNode.getLocation();
		double playerDistance = PlayerDistance.toPoint(world, peeweeLocation.x, peeweeLocation.y);
		if(playerDistance <= firingRange) {
			Point playerLocation = world.controlledNode.getLocation();
			this.peeweeNode.pointBodyAt(-playerLocation.x, -playerLocation.y);
			Timer.tick();
			if(timer.getTime() >= firingRate) {
				fireRocket();
				timer.reset();
				timer.resume();
			}
		}
	}

	private void fireRocket() {
		double offset = fireFromLeftSide ? -rocketDistanceFromCenter : rocketDistanceFromCenter;
		double dx = Math.cos(Math.toRadians(peeweeNode.getBodyRotation())) * offset;
		double dy = Math.sin(Math.toRadians(peeweeNode.getBodyRotation())) * offset;
		
		fireFromLeftSide = !fireFromLeftSide;

		Point peeweeLocation = peeweeNode.getLocation();
		
		RocketSpawner.spawnEnemyRocket(world, new Point(peeweeLocation.x + dx, peeweeLocation.y + dy), -peeweeNode.getBodyRotation(), rocketSpeed, rocketDamage);
	}

	public Point getLocation() {
		return peeweeNode.getLocation();
	}

	public void damage(double damage) {
		health -= damage;
		if(health <= 0) {
			kill();
		}
	}

	private void kill() {
		this.world.scene.removeMapSceneNode(this.sceneNode);
		this.world.removeGameObject(this);
	}

}
