package core;

import io.InputHandler;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import scene.Scene;
import shooter.GameWorld;

public class GameMain {
	private GameWorld gameWorld;
	private InputHandler inputHandler;
	private Scene scene;

	public void init() {
		this.scene = new Scene();
		this.gameWorld = new GameWorld(scene);
		this.inputHandler = new InputHandler(gameWorld);
		
	}
	
	public void mainLoop() {
		Mouse.setGrabbed(true);
		while(!Display.isCloseRequested()) {
			FrameUtils.newFrame();
			FrameUtils.set3DMode();
			
			inputHandler.handleInput();
			gameWorld.update();
			scene.render();
			
			Display.update();
			Display.sync(60);
		}
	}
}
