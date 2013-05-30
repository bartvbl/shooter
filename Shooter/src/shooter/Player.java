package shooter;

import scene.sceneGraph.sceneNodes.PlayerSceneNode;

public class Player extends GameObject {

	private final PlayerSceneNode playerNode;
	private boolean isAnimationPlaying = false;
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
