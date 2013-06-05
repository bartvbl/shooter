package shooter.gameObjects;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;

import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyContainerNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;

public class GameTerminator extends GameObject {

	private final String gameEndMessage;

	public GameTerminator(GameWorld world, String gameEndMessage) {
		super(GameObjectType.GAME_TERMINATOR, new EmptyContainerNode(), world);
		this.gameEndMessage = gameEndMessage;
	}

	public void update() {
		Display.destroy();
		JOptionPane.showMessageDialog(null, gameEndMessage + "\nYour final score is: " + world.player.getKillCount(), "The game has ended.", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

}
