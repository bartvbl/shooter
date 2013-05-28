package io;

import geom.Point;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import shooter.GameWorld;
import shooter.map.TileType;

public class InputHandler {
	private final GameWorld world;
	private double mapRotation = 0;
	
	private static final float MOVE_SPEED = 0.06f;
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
		double deltaRotation = (double) dx / 5d;
		this.mapRotation  += deltaRotation;
		world.controlledNode.setRotationZ(mapRotation);
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
			world.player.updateLegRotation(Math.toDegrees(angleRadians) + 180);
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
		
		Point location = this.world.controlledNode.getLocation();
		double currentX = -location.x;
		double currentY = -location.y;
		
		double newX = -location.x - dx;
		double newY = -location.y - dy;
		
//		//no collission with wall on x a
//		if(!isWallAt(newX - 0.1, newY - 0.1) && !isWallAt(newX + 0.1, newY + 0.1)){
//			
//		}
//		if(isWallAt(newX + 0.1, newY - 0.1)) return;
//		if return;
//		if(isWallAt(newX - 0.1, newY + 0.1)) return;
//		
		if(!isWallAt(newX, currentY)) {			
			this.world.controlledNode.translate(dx, 0, 0);
		}
		if(!isWallAt(currentX, newY)) {			
			this.world.controlledNode.translate(0, dy, 0);
		}
		
	}

	private boolean isWallAt(double x, double y) {
		int xCoord = (int) Math.floor(x);
		int yCoord = (int) Math.floor(y);
		return this.world.map.getTileAt(xCoord, yCoord) == TileType.WALL;
	}
}
