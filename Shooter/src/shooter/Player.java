package shooter;

import scene.sceneGraph.sceneNodes.PlayerSceneNode;

public class Player extends GameObject {

	private final PlayerSceneNode playerNode;
	
	public static Player createInstance(GameWorld gameWorld) {
		return new Player(gameWorld, new PlayerSceneNode());
	}

	private Player(GameWorld world, PlayerSceneNode playerSceneNode) {
		super(GameObjectType.PLAYER, playerSceneNode, world);
		this.playerNode = playerSceneNode;
	}

	public void update() {
	}

	public void shoot() {
		
	}


}
