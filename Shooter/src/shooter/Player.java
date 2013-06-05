package shooter;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import geom.Point;
import scene.sceneGraph.sceneNodes.PlayerSceneNode;
import shooter.dialogue.DialogueSequence;
import shooter.gameObjects.CommanderBoss;

public class Player extends GameObject implements Damageable {
	private static final double laserDistanceFromCenter = 0.25;
	private static final double rocketSpeed = 0.03;

	private boolean fireFromLeftSide = true;
	private final PlayerSceneNode playerNode;
	private double health = 1;
	private int kills;

	
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
	
		double offset = fireFromLeftSide ? -laserDistanceFromCenter : laserDistanceFromCenter;
		double dx = Math.cos(Math.toRadians(-playerRotation)) * offset;
		double dy = Math.sin(Math.toRadians(-playerRotation)) * offset;
		
		fireFromLeftSide = !fireFromLeftSide;
		Point rocketOrigin = new Point(playerLocation.x + dx, playerLocation.y + dy);
		
		RocketSpawner.spawnRocket(world, rocketOrigin, playerRotation, rocketSpeed, 0.09);
	}

	public void updateLegRotation(double degrees) {
		this.playerNode.updateLegRotation(degrees);
	}

	public double getHealth() {
		return health;
	}
	
	public void damage(double amount) {
		this.health -= amount;
		if(health <= 0) {
			Display.destroy();
			JOptionPane.showMessageDialog(null, "You were killed!", "The game has ended", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		if(health >= 1) {
			health = 1;
		}
	}

	public boolean hasFullHealth() {
		return health == 1;
	}

	public void notifyPeeweeKill() {
		this.kills++;
		if(kills == 1) {
			this.world.dialogueHandler.showDialogueSequence(DialogueSequence.BOSS_ENTER);
			CommanderBoss.spawn(world);
		}
	}


}
