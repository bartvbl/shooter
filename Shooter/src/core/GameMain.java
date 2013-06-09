package core;

import io.InputHandler;

import org.lwjgl.opengl.Display;

import scene.Scene2D;
import scene.Scene3D;
import shooter.GameWorld;

public class GameMain {
	private GameWorld gameWorld;
	private InputHandler inputHandler;
	private Scene3D scene3D;
	private Scene2D scene2D;

	public void init() {
		this.scene3D = new Scene3D();
		this.gameWorld = new GameWorld(scene3D);
		this.inputHandler = new InputHandler(gameWorld);
		this.scene2D = new Scene2D(gameWorld);
	}
	
	public void mainLoop() {
		while(!Display.isCloseRequested()) {
			FrameUtils.newFrame();
			
			FrameUtils.set3DMode();
			inputHandler.handleInput();
			if(!GameSettings.maxFramerateEnabled) {	//for the demo of the game. Stops all world activity to show framerate of rendering the world
				gameWorld.update();
			}
			scene3D.render();
			scene2D.render();
			
			Display.update();
			
			if(GameSettings.maxFramerateEnabled) { //increases the max framerate (for the demo)
				Display.sync(1000);
			} else {
				Display.sync(100);
			}
		}
	}
}
