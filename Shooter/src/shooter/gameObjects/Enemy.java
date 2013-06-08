package shooter.gameObjects;

import java.util.Random;

import org.lwjgl.util.Timer;

import geom.Point;
import scene.sceneGraph.sceneNodes.EnemyNode;
import shooter.Damageable;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.gameObjects.rocket.RocketSpawner;
import shooter.map.TileType;
import shooter.map.generator.Direction;
import util.PlayerDistance;

public abstract class Enemy extends GameObject implements Damageable {
	private final Timer timer;
	private final EnemyNode enemyNode;
	private final EnemySettings settings;
	private static final Random random = new Random(System.currentTimeMillis());
	
	private double health;
	private boolean fireFromLeftSide = true;
	private int transitionDestinationX;
	private int transitionDestinationY;
	
	public Enemy(GameObjectType type, EnemyNode sceneNode, GameWorld world, EnemySettings settings) {
		super(type, sceneNode, world);
		world.addGameObject(this);
		world.scene.addMapSceneNode(this.sceneNode);
		this.enemyNode = sceneNode;
		this.timer = new Timer();
		timer.resume();
		this.settings = settings;
		this.health = settings.enemyStartingHealth;
	}
	
	public Point getLocation() {
		return enemyNode.getLocation();
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
		this.world.player.notifyPeeweeKill();
		this.onKill();
	}
	
	protected abstract void onKill();

	protected void setLocation(int x, int y) {
		this.transitionDestinationX = x;
		this.transitionDestinationY = y;
		enemyNode.setLocation((float) x + 0.5f, (float) y + 0.5f, 0);
	}

	public void update() {
		Point peeweeLocation = enemyNode.getLocation();
		double playerDistance = PlayerDistance.toPoint(world, peeweeLocation.x, peeweeLocation.y);
		if(playerDistance <= settings.firingRange) {
			Point playerLocation = world.controlledNode.getLocation();
			this.enemyNode.pointBodyAt(-playerLocation.x, -playerLocation.y);
			Timer.tick();
			if(timer.getTime() >= settings.firingRate) {
				fireRocket();
				timer.reset();
				timer.resume();
			}
		}
		if(enemyNode.hasFinishedTransition()) {
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
		
		enemyNode.transitionTo(currentX - transitionDestinationX, currentY - transitionDestinationY, direction);
		this.transitionDestinationX = currentX;
		this.transitionDestinationY = currentY;
	}

	private void fireRocket() {
		double offset = fireFromLeftSide ? -settings.launchDistanceFromCenter : settings.launchDistanceFromCenter;
		double dx = Math.cos(Math.toRadians(enemyNode.getBodyRotation())) * offset;
		double dy = Math.sin(Math.toRadians(enemyNode.getBodyRotation())) * offset;
		
		fireFromLeftSide = !fireFromLeftSide;

		Point peeweeLocation = enemyNode.getLocation();
		
		RocketSpawner.spawnEnemyRocket(world, new Point(peeweeLocation.x + dx, peeweeLocation.y + dy), -enemyNode.getBodyRotation(), settings.rocketSpeed, settings.rocketDamage);
	}


}
