package shooter.gameObjects;

import scene.sceneGraph.sceneNodes.FrustrumCullingNode;
import scene.sceneGraph.sceneNodes.HealthPackNode;
import scene.sceneGraph.sceneNodes.ShieldPackNode;
import shooter.GameObject;
import shooter.GameObjectType;
import shooter.GameWorld;

public class ShieldPack extends Trigger {

	private static final double pickupRadius = 0.6;
	private static final double shieldBonus = 0.15;

	private final ShieldPackNode packNode;
	private final FrustrumCullingNode chunkRootNode;

	private ShieldPack(ShieldPackNode sceneNode, FrustrumCullingNode chunkRootNode, GameWorld world) {
		super(GameObjectType.SHIELD_PACK, sceneNode, world);
		this.chunkRootNode = chunkRootNode;
		this.setActivationRadius(pickupRadius);
		this.packNode = sceneNode;
	}

	public void update() {
		this.packNode.progressAnimation();
		if(super.isTriggered()) {
			if(!this.world.player.hasFullShield()) {				
				this.world.player.addShield(shieldBonus);
				//this should disable and garbage collect this object
				this.world.removeGameObject(this);
				chunkRootNode.removeChild(sceneNode);
			}
		}
	}
	
	public void setLocation(int x, int y) {
		this.setTriggerLocation(x, y);
		packNode.setLocation((float) x + 0.5f, (float) y + 0.5f, 0);
	}

	public static void spawn(int x, int y, GameWorld world, FrustrumCullingNode chunkRootNode) {
		ShieldPack pack = new ShieldPack(new ShieldPackNode(), chunkRootNode, world);
		pack.setLocation(x, y);
		world.addGameObject(pack);
		chunkRootNode.addChild(pack.sceneNode);
	}

}
