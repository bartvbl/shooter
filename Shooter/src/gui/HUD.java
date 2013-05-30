package gui;

import core.FrameUtils;
import shooter.GameWorld;

import static org.lwjgl.opengl.GL11.*;

public class HUD {
	
	private final GameWorld world;

	public HUD(GameWorld world) {
		this.world = world;
	}

	public void render() {
		FrameUtils.set2DMode();
		renderHealthBar();
	}

	private void renderHealthBar() {
		//by far not the best performing way of doing stuff, but it gets the job done.
		
		double x = 0.02;
		double y = 0.02;
		double width = 0.4 * world.player.getHealth();
		double maxWidth = 0.4;
		double height = 0.03;
		
		glDisable(GL_TEXTURE_2D);
		
		glBegin(GL_QUADS);
		glColor4d(0.3, 0.3, 0.3, 1);
		glVertex2d(x, y);
		glVertex2d(x + maxWidth, y);
		glVertex2d(x + maxWidth, y + height);
		glVertex2d(x, y + height);
		
		glColor4d(1, 0, 0, 1);
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
