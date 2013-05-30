package shooter.gameObjects;

import geom.Point;
import scene.sceneGraph.sceneNodes.PeeweeNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import util.PlayerDistance;

public class Peewee extends GameObject {
	private static final double firingRange = 2.5;
	private double health = 1;
	
	public static Peewee spawn(int x, int y, GameWorld world) {
		Peewee peewee = new Peewee(new PeeweeNode(), world);
		peewee.setLocation((double) x + 0.5d, (double) y + 0.5d);
		world.addGameObject(peewee);
		world.scene.addMapSceneNode(peewee.sceneNode);
		return peewee;
	}

	private PeeweeNode peeweeNode;

	private Peewee(PeeweeNode sceneNode, GameWorld world) {
		super(GameObjectType.PEEWEE, sceneNode, world);
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
		}
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
