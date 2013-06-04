package shooter.gameObjects;

import java.util.Random;

import org.lwjgl.util.Timer;

import geom.Point;
import scene.sceneGraph.sceneNodes.PeeweeNode;
import shooter.Damageable;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.RocketSpawner;
import shooter.map.Direction;
import shooter.map.TileType;
import util.PlayerDistance;

public class Peewee extends GameObject implements Damageable {
	private static final double firingRate = 0.55;
	private static final double firingRange = 5;
	private static final double rocketSpeed = 0.023;
	private static final double rocketDamage = 0.1;
	private static final double rocketDistanceFromCenter = 0.25;
	
	private final Timer timer;
	private final PeeweeNode peeweeNode;
	private static final Random random = new Random(System.currentTimeMillis());

	private double health = 1;
	private boolean fireFromLeftSide = true;
	private int transitionDestinationX;
	private int transitionDestinationY;
	
	public static Peewee spawn(int x, int y, GameWorld world) {
		Peewee peewee = new Peewee(new PeeweeNode(world), world);
		peewee.setLocation(x, y);
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
	
	private void setLocation(int x, int y) {
		this.transitionDestinationX = x;
		this.transitionDestinationY = y;
		peeweeNode.setLocation((float) x + 0.5f, (float) y + 0.5f, 0);
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
		if(peeweeNode.hasFinishedTransition()) {
			this.chooseNextTransition();
		}
	}

	private void chooseNextTransition() {
		int chosenDirection = random.nextInt(4);
		if(chosenDirection == 0) {
			generateTransitionInDirection(Direction.EAST);
		} else if(chosenDirection == 1) {
			generateTransitionInDirection(Direction.SOUTH);
		} else if(chosenDirection == 2) {
			generateTransitionInDirection(Direction.WEST);
		} else if(chosenDirection == 3) {
			generateTransitionInDirection(Direction.NORTH);
		}
	}

	private void generateTransitionInDirection(Direction direction) {
		int currentX = this.transitionDestinationX;
		int currentY = this.transitionDestinationY;
		
		while(world.map.getTileAt(currentX + direction.dx, currentY + direction.dy) != TileType.WALL){
			currentX += direction.dx;
			currentY += direction.dy;
			if(random.nextDouble() < 0.3) {
				break;
			}
		}
		
		peeweeNode.transitionTo(currentX - transitionDestinationX, currentY - transitionDestinationY, direction);
		this.transitionDestinationX = currentX;
		this.transitionDestinationY = currentY;
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
