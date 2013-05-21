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
	private double variable = 0;
	private FloatBuffer buffer;
	
	public Scene() {
		this.rootNode = new EmptyCoordinateNode();
		//rootNode.translate(0, 0, -10);
		this.buffer = BufferUtils.createFloatBuffer(4);
	}

	public void render() {
		variable += (double)Mouse.getDY() / 5d;
		renderContext.setIdentity();
		
		
		glEnable(GL_LIGHTING);
		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)buffer.put(new float[]{0.1f, 0.1f, 0.1f, 1}).rewind());
		glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)buffer.put(new float[]{0.5f, 0.5f, 0.5f, 1}).rewind());
		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)buffer.put(new float[]{0.2f, 0.2f, 0.2f, 1}).rewind());
		
		glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{0, 0.3f, 1f, 1}).rewind());
		glLight(GL_LIGHT0, GL_SPOT_CUTOFF, (FloatBuffer)buffer.put(new float[]{30}).rewind());
		glLight(GL_LIGHT0, GL_SPOT_DIRECTION, (FloatBuffer)buffer.put(new float[]{0, 1, 0}).rewind());
		
		renderContext.translate(0, 0, -10);
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
