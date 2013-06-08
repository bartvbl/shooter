package gui;

import java.awt.Color;

import geom.Point;
import core.FrameUtils;
import shooter.GameWorld;

import static org.lwjgl.opengl.GL11.*;

public class HUD {
	
	private final GameWorld world;
	private static final Point healthLocation = new Point(0.02, 0.02);
	private static final Point shieldLocation = new Point(0.02, 0.07);
	private static final double maxWidth = 0.4;

	public HUD(GameWorld world) {
		this.world = world;
	}

	public void render() {
		FrameUtils.set2DMode();
		
		double shieldWidth = world.player.getShield();
		Color shieldColour = Color.blue;
		renderBar(shieldLocation, shieldWidth, shieldColour);
		
		double healthWidth = world.player.getHealth();
		Color healthColour = Color.red;
		renderBar(healthLocation, healthWidth, healthColour);
	}

	private void renderBar(Point location, double percentage, Color barColour) {
		//by far not the best performing way of doing stuff, but it gets the job done.
		double x = location.x;
		double y = location.y;
		
		double width = percentage * maxWidth;
		double height = 0.03;
		
		glDisable(GL_TEXTURE_2D);
		
		glBegin(GL_QUADS);
		glColor4d(0.3, 0.3, 0.3, 1);
		glVertex2d(x, y);
		glVertex2d(x + maxWidth, y);
		glVertex2d(x + maxWidth, y + height);
		glVertex2d(x, y + height);
		
		glColor4d(barColour.getRed(), barColour.getGreen(), barColour.getBlue(), 1);
		glVertex2d(x, y);
		glVertex2d(x + width, y);
		glVertex2d(x + width, y + height);
		glVertex2d(x, y + height);
		glEnd();
		
		glColor4d(0.01, 0.01, 0.01, 1);
		glLineWidth(3);
		
		glBegin(GL_LINES);
		glVertex2d(x, y);
		glVertex2d(x + maxWidth, y);
		glVertex2d(x + maxWidth, y);
		glVertex2d(x + maxWidth, y + height);
		glVertex2d(x + maxWidth, y + height);
		glVertex2d(x, y + height);
		glVertex2d(x, y + height);
		glVertex2d(x, y);
		glEnd();
	}

}
