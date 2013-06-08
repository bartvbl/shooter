package shooter.gameObjects;

import java.util.Random;

import org.lwjgl.util.Timer;

import orre.resources.loaders.obj.ModelLoader;
import orre.resources.twoStageLoadables.BlueprintModel;

import geom.Point;
import scene.sceneGraph.sceneNodes.EnemyNode;
import shooter.Damageable;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.gameObjects.rocket.RocketSpawner;
import shooter.map.TileType;
import shooter.map.generator.Direction;
import shooter.map.generator.DistanceSorter;
import util.PlayerDistance;

public class Peewee extends Enemy {
	private static final BlueprintModel peeweeModel = ModelLoader.loadModel("res/mesh/peewee.mdl", "peewee");
	
	private static final double firingRate = 0.55;
	private static final double rocketDamage = 0.1;
	private static final double rocketDistanceFromCenter = 0.25;
	private static final double peeweeHealth = 1;
	
	public static Peewee spawn(int x, int y, GameWorld world) {
		EnemyNode enemyNode = new EnemyNode(peeweeModel, world);
		Peewee peewee = new Peewee(enemyNode, world);
		peewee.setLocation(x, y);
		
		return peewee;
	}


	private Peewee(EnemyNode sceneNode, GameWorld world) {
		
		
		super(GameObjectType.PEEWEE, sceneNode, world, new EnemySettings(firingRate, rocketDamage, rocketDistanceFromCenter, peeweeHealth));
		
	}
	protected void onKill() {}
}
