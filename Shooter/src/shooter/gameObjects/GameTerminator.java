package shooter.gameObjects;

import org.lwjgl.opengl.Display;

import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyContainerNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;

public class GameTerminator extends GameObject {

	public GameTerminator(GameWorld world) {
		super(GameObjectType.GAME_TERMINATOR, new EmptyContainerNode(), world);
	}

	public void update() {
		Display.destroy();
		System.exit(0);
	}

}
