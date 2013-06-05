package shooter.gameObjects;

import org.lwjgl.opengl.Display;

import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import scene.sceneGraph.sceneNodes.EnemyNode;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.dialogue.DialogueSequence;

public class CommanderBoss extends Enemy {
private static final BlueprintModel bossModel = ModelLoader.loadModel("res/mesh/armcom.mdl", "peewee");

	private static final double firingRate = 0.35;
	private static final double rocketDamage = 0.2;
	private static final double rocketDistanceFromCenter = 0.25;

	public CommanderBoss(EnemyNode enemyNode, GameWorld world) {
		super(GameObjectType.BOSS, enemyNode, world, new EnemySettings(firingRate, rocketDamage, rocketDistanceFromCenter));
	}

	public static void spawn(GameWorld world) {
		EnemyNode enemyNode = new EnemyNode(bossModel, world);
		CommanderBoss boss = new CommanderBoss(enemyNode, world);
		boss.setLocation(1, 1);
	}

	protected void onKill() {
		world.dialogueHandler.showDialogueSequence(DialogueSequence.GAME_WIN);
		world.addGameObject(new GameTerminator(world));
	}
}
