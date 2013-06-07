package scene.sceneGraph.sceneNodes;

import static org.lwjgl.opengl.GL11.*;
import geom.Point;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.Sphere;

import render.RenderContext;
import scene.sceneGraph.ContainerNode;
import scene.sceneGraph.CoordinateNode;
import scene.sceneGraph.SceneNode;

public class MapSceneNode implements SceneNode {
	
	private final CoordinateNode mapContentsNode;
	private final ArrayList<SceneNode> childList = new ArrayList<SceneNode>();

	public MapSceneNode(CoordinateNode mapContentsNode) {
		this.mapContentsNode = mapContentsNode;
		this.childList.add(mapContentsNode);
		
		
	}
	
	public void destroy() {}

	public void preRender(RenderContext context) {
		context.pushMatrix();
		context.translate(0, 0, -0.5f);
		
		
		context.popMatrix();
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
