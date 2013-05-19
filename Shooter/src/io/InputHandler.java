package io;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import shooter.GameWorld;

public class InputHandler {
	private final GameWorld world;
	private double mapRotation = 0;
	
	private static final float MOVE_SPEED = 0.03f;
	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final boolean MOUSE_BUTTON_UP = false;

	public InputHandler(GameWorld world) {
		this.world = world;
	}
	
	public void handleInput() {
		handleMouse();
		handleKeyboard();
	}

	private void handleMouse() {
		while(Mouse.next()) {
			handleMapRotationEvent();	
			handlePlayerShoot();
		}
	}

	private void handleMapRotationEvent() {
		int dx = Mouse.getEventDX();
		double deltaRotation = (double) dx / -5d;
		this.mapRotation  += deltaRotation;
		world.map.setRotation(mapRotation);
	}
	
	private void handlePlayerShoot() {
		if(Mouse.getEventButton() == LEFT_MOUSE_BUTTON ) {
			if(Mouse.getEventButtonState() == MOUSE_BUTTON_UP ) {
				world.player.shoot();
			}
		}
	}
	
	private void handleKeyboard() {
		double stepsDX = 0;
		double stepsDY = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			stepsDY++;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			stepsDX++;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			stepsDY--;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			stepsDX--;
		}
		
		if((stepsDX != 0) || (stepsDY != 0)) {
			double angleRadians = calculateDirectionAngleRadians(stepsDX, stepsDY);
			moveMapInDirection(Math.toDegrees(angleRadians));
		}
		
	}

	private double calculateDirectionAngleRadians(double stepsDX, double stepsDY) {
		double angleRadians = Math.atan2(stepsDY, stepsDX);
		
		angleRadians += Math.PI/2d;
		
		if(angleRadians < 0) {
			angleRadians += 2d*Math.PI;
		}
		return angleRadians;
	}
	
	private void moveMapInDirection(double angle) {
		double dx = Math.sin(Math.toRadians(mapRotation + angle)) * MOVE_SPEED;
		double dy = Math.cos(Math.toRadians(mapRotation + angle)) * MOVE_SPEED;
		this.world.map.translate(dx, dy);
	}
}
