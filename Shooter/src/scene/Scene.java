package scene;

import render.RenderContext;
import render.RenderPass;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;
import scene.sceneGraph.sceneNodes.ShadowMappedLightNode;


public class Scene {
	private final EmptyCoordinateNode rootNode;
	private final RenderContext renderContext = new RenderContext();
	private final ShadowMappedLightNode contentRootNode;
	
	public Scene() {
		this.contentRootNode = new ShadowMappedLightNode();
		this.rootNode = new EmptyCoordinateNode();
		this.rootNode.addChild(contentRootNode);
		rootNode.translate(0, 0, -10);
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

	public void translate(double dx, double dy) {
		this.rootNode.translate(dx, dy, 0);
	}

	public void setRotation(double rotation) {
		this.rootNode.setRotation(rotation);
	}

}
