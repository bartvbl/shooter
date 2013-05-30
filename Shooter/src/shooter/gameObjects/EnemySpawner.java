package shooter.gameObjects;

import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;
import shooter.GameObjectType;
import shooter.GameWorld;

public class EnemySpawner extends Trigger {

	private static final double spawnActivationRadius = 20;
	
	private int spawnLocationX;
	private int spawnLocationY;
	
	public static EnemySpawner createSpawner(int mapX, int mapY, GameWorld world) {
		EnemySpawner spawner = new EnemySpawner(new EmptyCoordinateNode(), world);
		spawner.setSpawnLocation(mapX, mapY);
		return spawner;
	}

	private EnemySpawner(SceneNode sceneNode, GameWorld world) {
		super(GameObjectType.ENEMY_SPAWNER, sceneNode, world);
		super.setActivationRadius(spawnActivationRadius);
	}

	public void update() {
		if(super.isTriggered()) {
			this.spawnEnemy();
			//this should disable and garbage collect this object
			this.world.removeGameObject(this);
		}
	}
	
	private void setSpawnLocation(int mapX, int mapY) {
		super.setTriggerLocation(mapX, mapY);
		this.spawnLocationX = mapX;
		this.spawnLocationY = mapY;
	}

	private void spawnEnemy() {
		Peewee.spawn(spawnLocationX, spawnLocationY, world);
	}

}
