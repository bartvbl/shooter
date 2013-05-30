package shooter;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import geom.Point;
import scene.sceneGraph.sceneNodes.PlayerSceneNode;

public class Player extends GameObject {
	private static final double PLAYER_SHOOTING_RANGE = 3;
	private static final double PLAYER_SHOOTING_DAMAGE = 1;
	
	private static final double laserDistanceFromCenter = 0.22;
	private static final double laserHeight = 0.3;
	private static final ReadableColor playerLaserColour = Color.RED;

	private boolean fireFromLeftSide = true;
	private final PlayerSceneNode playerNode;
	private double health = 1;
	
	public static Player createInstance(GameWorld gameWorld) {
		return new Player(gameWorld, new PlayerSceneNode());
	}

	private Player(GameWorld world, PlayerSceneNode playerSceneNode) {
		super(GameObjectType.PLAYER, playerSceneNode, world);
		this.playerNode = playerSceneNode;
	}

	public void update() {
		this.playerNode.updateAnimation();
	}

	public void shoot() {
		double playerRotation = world.controlledNode.getRotationZ();
		Point playerLocation = world.controlledNode.getLocation().negate();
		RayTraceResult result = ShotTracer.rayTraceEnemy(world, playerRotation, playerLocation, PLAYER_SHOOTING_RANGE);
		
		if(result.hasHitEnemy) {
			result.foundObject.damage(PLAYER_SHOOTING_DAMAGE);
		}
		
		double xOffset = fireFromLeftSide ? -laserDistanceFromCenter : laserDistanceFromCenter;
		fireFromLeftSide = !fireFromLeftSide;
		Point laserOrigin = new Point(playerLocation.x + xOffset, playerLocation.y);
		
		LaserSpawner.spawnLaser(world, playerLaserColour, laserOrigin, playerRotation);
	}

	public void updateLegRotation(double degrees) {
		this.playerNode.updateLegRotation(degrees);
	}

	public double getHealth() {
		return health;
	}
	
	public void addHealth(double amount) {
		this.health += amount;
	}


}
