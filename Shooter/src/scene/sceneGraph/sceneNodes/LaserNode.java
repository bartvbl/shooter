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

	private double distance = 0;

	public LaserNode(Scene scene, ReadableColor laserColour) {
		this.scene = scene;
		timer = new Timer();
		timer.resume();
		this.laserColour = laserColour;
		scene.addMapSceneNode(this);
	}
	
	public void render(RenderContext context) {
		Timer.tick();
		if(timer.getTime() <= laserDisplayTime) {
			laserTexture.bind();
			glBegin(GL_QUADS);
			glTexCoord2d(0, 0);
			glVertex3d(-0.1, 0, 0);
			glTexCoord2d(1, 0);
			glVertex3d(0.1, 0, 0);
			glTexCoord2d(1, 1);
			glVertex3d(0.1, distance, 0);
			glTexCoord2d(0, 1);
			glVertex3d(-0.1, distance, 0);
			glEnd();
			
		} else {
			scene.removeMapSceneNode(this);
		}
	}

}
