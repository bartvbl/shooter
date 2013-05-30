package shooter.gameObjects;

import org.lwjgl.util.Timer;

import geom.Point;
import scene.sceneGraph.sceneNodes.PeeweeNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.RayTraceResult;
import shooter.ShotTracer;
import util.PlayerDistance;

public class Peewee extends GameObject {
	private static final double firingRange = 2.5;
	private static final double firingRate = 0.3;
	private static final double damage = 0.01;
	private double health = 1;
	private final Timer timer;
	
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
			//kind of abusing the class here. If the ray tracer can not find an enemy or wall in view and ends up with a case of out of range, it means the player was hit.
			if(timer.getTime() >= firingRate) {
				RayTraceResult result = ShotTracer.rayTraceEnemy(world, peeweeNode.getBodyRotation(), peeweeNode.getLocation(), playerDistance);
				if(result.isOutOfRange) {
					System.out.println("shot!");
					world.player.addHealth(-damage);
					timer.reset();					
				}
			}
			
			
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
