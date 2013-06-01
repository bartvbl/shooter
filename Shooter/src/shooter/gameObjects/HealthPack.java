package shooter.gameObjects;

import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.HealthPackNode;
import shooter.GameObjectType;
import shooter.GameWorld;
import shooter.map.MapFrustrumCullingNode;

public class HealthPack extends Trigger {
	private static final double pickupRadius = 0.6;
	private static final double healthBonus = 0.1;

	private final HealthPackNode packNode;
	private final MapFrustrumCullingNode chunkRootNode;

	private HealthPack(HealthPackNode sceneNode, MapFrustrumCullingNode chunkRootNode, GameWorld world) {
		super(GameObjectType.HEALTH_PACK, sceneNode, world);
		this.chunkRootNode = chunkRootNode;
		this.setActivationRadius(pickupRadius);
		this.packNode = sceneNode;
	}

	public void update() {
		this.packNode.progressAnimation();
		if(super.isTriggered()) {
			if(!this.world.player.hasFullHealth()) {				
				this.world.player.damage(-healthBonus);
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

	public static void spawn(int x, int y, GameWorld world, MapFrustrumCullingNode chunkRootNode) {
		HealthPack pack = new HealthPack(new HealthPackNode(), chunkRootNode, world);
		pack.setLocation(x, y);
		world.addGameObject(pack);
		chunkRootNode.addChild(pack.sceneNode);
	}

}
