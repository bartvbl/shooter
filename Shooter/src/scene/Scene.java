package scene;

import render.RenderContext;
import render.RenderPass;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.ShadowMappedLightNode;


public class Scene {
	private final ShadowMappedLightNode rootNode;
	private final RenderContext renderContext = new RenderContext();
	
	public Scene() {
		this.rootNode = new ShadowMappedLightNode();
	}

	public void render() {
		renderContext.setIdentity();
		RenderPass.render(rootNode, renderContext);
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
