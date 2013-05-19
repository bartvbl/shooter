package core;

import java.nio.FloatBuffer;

import io.InputHandler;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import scene.Scene;
import shooter.GameWorld;

public class GameMain {
	private GameWorld gameWorld;
	private InputHandler inputHandler;
	private Scene scene;
	private FloatBuffer buffer;

	public void init() {
		this.scene = new Scene();
		this.gameWorld = new GameWorld(scene);
		this.inputHandler = new InputHandler(gameWorld);
		this.buffer = BufferUtils.createFloatBuffer(4);
	}
	
	public void mainLoop() {
		Mouse.setGrabbed(true);
		while(!Display.isCloseRequested()) {
			FrameUtils.newFrame();
			FrameUtils.set3DMode();
		
			
			glEnable(GL_LIGHTING);
			glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)buffer.put(new float[]{0.1f, 0.1f, 0.1f, 1}).rewind());
			glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)buffer.put(new float[]{0.8f, 0.8f, 0.8f, 1}).rewind());
			glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)buffer.put(new float[]{0.5f, 0.5f, 0.5f, 1}).rewind());
			
			glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)buffer.put(new float[]{0, 0, 0, 1}).rewind());
			glLight(GL_LIGHT0, GL_SPOT_CUTOFF, (FloatBuffer)buffer.put(new float[]{30}).rewind());
			glLight(GL_LIGHT0, GL_SPOT_DIRECTION, (FloatBuffer)buffer.put(new float[]{1, 0, 0}).rewind());

			glTranslated(0, 0, -10);
			inputHandler.handleInput();
			gameWorld.update();
			scene.render();
			
			Display.update();
			Display.sync(1000);
		}
	}
}
