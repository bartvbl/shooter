package scene;

import static org.lwjgl.opengl.GL11.glRotated;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import render.RenderContext;
import render.RenderPass;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;
import scene.sceneGraph.sceneNodes.ShadowMappedLightNode;


public class Scene {
	private final EmptyCoordinateNode rootNode;
	private final RenderContext renderContext = new RenderContext();
	
	public Scene() {
		this.rootNode = new EmptyCoordinateNode();
		rootNode.translate(0, 0, -10);
		
	}

	public void render() {
		renderContext.setIdentity();
		RenderPass.render(rootNode, renderContext);
	}

	public void addSceneNode(SceneNode sceneNode) {
		this.rootNode.addChild(sceneNode);
	}

	public void buildScene(SceneNode playerNode, SceneNode mapNode, SceneNode controlledNode) {
		rootNode.addChild(controlledNode);
		rootNode.addChild(playerNode);
		ShadowMappedLightNode shadowNode = new ShadowMappedLightNode();
		controlledNode.addChild(shadowNode);
		shadowNode.addChild(mapNode);
	}

}
