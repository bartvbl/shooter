package scene.sceneGraph.sceneNodes;

import gl.texture.Texture;
import gl.texture.TextureLoader;

import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.Timer;
import static org.lwjgl.opengl.GL11.*;

import render.RenderContext;
import scene.Scene;

public class LaserNode extends EmptyCoordinateNode {
	private static final Texture laserTexture = TextureLoader.loadTextureFromFile("res/textures/laser.png");
	private static final float laserDisplayTime = 0.3f;
	private final Timer timer;
	private final Scene scene;
	private final ReadableColor laserColour;
	private final double distance;


	public LaserNode(Scene scene, ReadableColor laserColour, double distance) {
		this.scene = scene;
		timer = new Timer();
		timer.resume();
		this.laserColour = laserColour;
		scene.addMapSceneNode(this);
		this.distance = distance;
	}
	
	public void render(RenderContext context) {
		Timer.tick();
		if(timer.getTime() <= laserDisplayTime) {
			glColor4d(1, 0, 0, 1);
			glLineWidth(3);
			glBegin(GL_LINES);
			glVertex3d(0, 0, 0.3);
			glVertex3d(0, distance, 0.3);
			glEnd();
		} else {
			scene.removeMapSceneNode(this);
		}
	}

}
