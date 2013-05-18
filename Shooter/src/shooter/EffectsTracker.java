package shooter;

import scene.sceneGraph.sceneNodes.EffectsSceneNode;

public class EffectsTracker extends GameObject {

	private final EffectsSceneNode effectsNode;

	public static EffectsTracker createInstance(GameWorld world) {
		return new EffectsTracker(world, new EffectsSceneNode());
	}
	
	private EffectsTracker(GameWorld world, EffectsSceneNode effectsNode) {
		super(GameObjectType.EFFECT_TRACKER, effectsNode, world);
		this.effectsNode = effectsNode;
	}

	public void update() {
		
	}

}
