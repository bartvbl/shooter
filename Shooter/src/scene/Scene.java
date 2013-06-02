package scene;

import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_SPOT_CUTOFF;
import static org.lwjgl.opengl.GL11.GL_SPOT_DIRECTION;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glRotated;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import core.FrameUtils;

import render.RenderContext;
import render.RenderPass;
import scene.sceneGraph.SceneNode;
import scene.sceneGraph.sceneNodes.EmptyCoordinateNode;
import scene.sceneGraph.sceneNodes.ShadowMappedLightNode;


public class Scene {
	private final EmptyCoordinateNode rootNode;
	private final RenderContext renderContext = new RenderContext();
	private SceneNode mapNode;
	private FloatBuffer buffer;
	private ArrayList<SceneNode> mapAdditionQueue = new ArrayList<SceneNode>();
	private ArrayList<SceneNode> mapRemovalQueue = new ArrayList<SceneNode>();
	
	public Scene() {
		this.rootNode = new EmptyCoordinateNode();
		this.buffer = BufferUtils.createFloatBuffer(4);
	}

	public void render() {
		renderContext.setIdentity();
		
//		glEnable(GL_LIGHTING);
//		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)buffer.put(new float[]{0.0f, 0.0f, 0.0f, 1}).rewind());
//		glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)buffer.put(new float[]{0.5f, 0.5f, 0.5f, 1}).rewind());
//		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)buffer.put(new float[]{0.8f, 0.8f, 0.8f, 1}).rewind());
//		
//		glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{0, 0.3f, 1f, 1}).rewind());
//		glLight(GL_LIGHT0, GL_SPOT_CUTOFF, (FloatBuffer)buffer.put(new float[]{30}).rewind());
//		glLight(GL_LIGHT0, GL_SPOT_DIRECTION, (FloatBuffer)buffer.put(new float[]{0, 1, 0}).rewind());
		
		renderContext.translate(0, 0, -10);
		
		RenderPass.render(rootNode, renderContext);
		
		flushMapNodeQueues();
	}

	
	private void flushMapNodeQueues() {
		for(SceneNode node : mapAdditionQueue) {
			this.mapNode.addChild(node);
		}
		for(SceneNode node : mapRemovalQueue) {
			this.mapNode.removeChild(node);
		}
		mapAdditionQueue.clear();
		mapRemovalQueue.clear();
	}

	public void addMapSceneNode(SceneNode sceneNode) {
		this.mapAdditionQueue.add(sceneNode);
	}
	
	public void removeMapSceneNode(SceneNode sceneNode) {
		this.mapRemovalQueue.add(sceneNode);
	}

	public void buildScene(SceneNode playerNode, SceneNode mapNode, EmptyCoordinateNode controlledNode) {
		rootNode.addChild(controlledNode);
		rootNode.addChild(playerNode);
		ShadowMappedLightNode shadowNode = new ShadowMappedLightNode(controlledNode);
		controlledNode.addChild(shadowNode);
		shadowNode.addChild(mapNode);
		this.mapNode = mapNode;
	}

}
