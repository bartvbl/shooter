package io;

import geom.Point;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Timer;

import shooter.GameWorld;
import shooter.map.TileType;

public class InputHandler {
	private final GameWorld world;
	private double mapRotation = 0;
	private final Timer timer;
	
	private static final float SHOOTING_FREQUENCY = 0.05f;
	private static final float MOVE_SPEED = 0.06f;
	private static final int LEFT_MOUSE_BUTTON = 0;

	public InputHandler(GameWorld world) {
		this.world = world;
		timer = new Timer();
		timer.resume();
		world.controlledNode.setRotationZ(0);
	}
	
	public void handleInput() {
		handleMouse();
		handleKeyboard();
	}

	private void handleMouse() {
		while(Mouse.next()) {
			handleMapRotationEvent();	
		}
		handlePlayerShoot();
	}

	private void handleMapRotationEvent() {
		int dx = Mouse.getEventDX();
		double deltaRotation = (double) dx / 5d;
		this.mapRotation  += deltaRotation;
		world.controlledNode.setRotationZ(mapRotation);
	}
	
	private void handlePlayerShoot() {
		if(Mouse.isButtonDown(LEFT_MOUSE_BUTTON)) {
			Mouse.setGrabbed(true);
			Timer.tick();
			if(timer.getTime() >= SHOOTING_FREQUENCY) {
				timer.reset();
				timer.resume();
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
		
		Point location = this.world.controlledNode.getLocation().negate();
		
		double newX = location.x - dx;
		double newY = location.y - dy;
		
		boolean hasNotCollidedX = testLocation(newX, location.y);
		boolean hasNotCollidedY = testLocation(location.x, newY);
		
		if(hasNotCollidedX) {			
			this.world.controlledNode.translate(dx, 0, 0);
			this.world.controlledNode.translatePivot(dx, 0, 0);
		}
		if(hasNotCollidedY) {			
			this.world.controlledNode.translate(0, dy, 0);
			this.world.controlledNode.translatePivot(0, dy, 0);
		}
	}

	private boolean testLocation(double newX, double newY) {
		boolean hasNotCollided = true;
		final double rayRadius = 0.3;
		
		for(double rayAngle = 0; rayAngle < 360; rayAngle += 5) {
			double rayX = Math.sin(Math.toRadians(rayAngle)) * rayRadius;
			double rayY = Math.cos(Math.toRadians(rayAngle)) * rayRadius;
			hasNotCollided = hasNotCollided && !isWallAt(newX + rayX, newY + rayY);
		}
		return hasNotCollided;
	}

	private boolean isWallAt(double x, double y) {
		int xCoord = (int) Math.floor(x);
		int yCoord = (int) Math.floor(y);
		return this.world.map.getTileAt(xCoord, yCoord) == TileType.WALL;
	}
}
