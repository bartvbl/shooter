package scene;

import render.RenderPass;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EffectsSceneNode;
import scene.sceneGraph.sceneNodes.EmptyContainerNode;
import scene.sceneGraph.sceneNodes.MapSceneNode;
import scene.sceneGraph.sceneNodes.PlayerSceneNode;
import scene.sceneGraph.sceneNodes.ShadowMappedLightNode;

public class Scene {
	
	private final SceneNode rootNode;

	public Scene() {
		this.rootNode = new ShadowMappedLightNode();
	}

	public void render() {
		RenderPass.render(rootNode);
	}

	public void addSceneNode(SceneNode sceneNode) {
		this.rootNode.addChild(sceneNode);
	}

	public void buildScene(SceneNode playerNode, SceneNode mapNode, SceneNode hudNode) {
		rootNode.addChild(hudNode);
		rootNode.addChild(playerNode);
		rootNode.addChild(mapNode);
	}

}
