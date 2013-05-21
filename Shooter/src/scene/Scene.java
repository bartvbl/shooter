package scene;

import render.RenderContext;
import render.RenderPass;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;
import scene.sceneGraph.sceneNodes.ShadowMappedLightNode;


public class Scene {
	private final ShadowMappedLightNode rootNode;
	private final RenderContext renderContext = new RenderContext();
	private final EmptyCoordinateNode contentRootNode;
	
	public Scene() {
		this.rootNode = new ShadowMappedLightNode();
		this.contentRootNode = new EmptyCoordinateNode();
		this.rootNode.addChild(contentRootNode);
		contentRootNode.translate(0, 0, 10);
	}

	public void render() {
		renderContext.setIdentity();
		RenderPass.render(rootNode, renderContext);
	}

	public void addSceneNode(SceneNode sceneNode) {
		this.contentRootNode.addChild(sceneNode);
	}

	public void buildScene(SceneNode playerNode, SceneNode mapNode, SceneNode hudNode) {
		contentRootNode.addChild(hudNode);
		contentRootNode.addChild(playerNode);
		contentRootNode.addChild(mapNode);
	}

}
