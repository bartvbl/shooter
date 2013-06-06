package scene.sceneGraph.sceneNodes;

import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import geom.Point;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import render.RenderContext;
import scene.sceneGraph.ContainerNode;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

public class MapSceneNode implements SceneNode {
	private FloatBuffer buffer;
	private final CoordinateNode mapContentsNode;
	private final ArrayList<SceneNode> childList = new ArrayList<SceneNode>();

	public MapSceneNode(CoordinateNode mapContentsNode) {
		this.mapContentsNode = mapContentsNode;
		this.childList.add(mapContentsNode);
		this.buffer = BufferUtils.createFloatBuffer(4);
	}
	
	public void destroy() {}

	public void preRender(RenderContext context) {
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)buffer.put(new float[]{0.0f, 0.0f, 0.0f, 1}).rewind());
		glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)buffer.put(new float[]{0.5f, 0.5f, 0.5f, 1}).rewind());
		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)buffer.put(new float[]{0.8f, 0.8f, 0.8f, 1}).rewind());
		glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{0, 0, 0, 1}).rewind());
	}
	
	public void render(RenderContext context) {}
	public void postRender(RenderContext context) {}

	public void setRotationZ(double rotation) {
		this.mapContentsNode.setRotationZ(rotation);
	}

	public void translate(double x, double y, double z) {
		this.mapContentsNode.translate(x, y, z);
	}

	public Point getLocation() {
		return this.mapContentsNode.getLocation();
	}

	public void clear() {
		this.mapContentsNode.clear();
	}

	public void addChild(SceneNode node) {
		mapContentsNode.addChild(node);
	}

	public void removeChild(SceneNode node) {
		mapContentsNode.removeChild(node);
	}

	public ArrayList<SceneNode> getChildren() {
		return childList;
	}

	public boolean isVisible() {
		return true;
	}
}
