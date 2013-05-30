package shooter.gameObjects;

import scene.sceneGraph.sceneNodes.PeeweeNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;

public class Peewee extends GameObject {
	public static Peewee spawn(int x, int y, GameWorld world) {
		Peewee peewee = new Peewee(new PeeweeNode(), world);
		peewee.setLocation((double) x + 0.5d, (double) y + 0.5d);
		world.scene.addSceneNodeToMap(peewee.sceneNode);
		return peewee;
	}

	private PeeweeNode peeweeNode;

	private Peewee(PeeweeNode sceneNode, GameWorld world) {
		super(GameObjectType.PEEWEE, sceneNode, world);
		this.peeweeNode = sceneNode;
	}
	
	private void setLocation(double x, double y) {
		peeweeNode.setLocation((float) x, (float) y, 0);
	}

	public void update() {

	}

}
