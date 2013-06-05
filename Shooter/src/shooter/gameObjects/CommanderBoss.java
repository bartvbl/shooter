package shooter.gameObjects;

import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;
import scene.sceneGraph.sceneNodes.EnemyNode;
import shooter.GameObjectType;
import shooter.GameWorld;

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
}
