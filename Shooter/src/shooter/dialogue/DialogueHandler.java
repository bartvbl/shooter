package shooter.dialogue;

import org.lwjgl.input.Keyboard;

import reused.gl.texture.Texture;
import reused.gl.texture.TextureLoader;
import static org.lwjgl.opengl.GL11.*;

import core.FrameUtils;

public class DialogueHandler {
	private boolean isActive = false;
	private static final Texture actorTexture = TextureLoader.loadTextureFromFile("res/textures/corcom.png");
	private boolean previousSpacebarState = false;
	private int sequencePlayhead = 0;
	private DialogueSequence sequence;
	
	public void showDialogueSequence(DialogueSequence sequence) {
		isActive = true;
		this.sequencePlayhead = 0;
		this.sequence = sequence;
	}
	
	public void update() {
		boolean currentSpacebarState = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		if((previousSpacebarState == true) && (currentSpacebarState == false)) {
			this.advanceDialogueSequence();
		}
		previousSpacebarState = currentSpacebarState;
	}
	
	private void advanceDialogueSequence() {
		sequencePlayhead++;
		if(sequencePlayhead == this.sequence.dialogueTextures.length) {
			this.isActive = false;
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void render() {
		FrameUtils.set2DMode();
		
		glDisable(GL_TEXTURE_2D);
		
		drawBackground();
		drawDialogueBackground();
		
		glEnable(GL_TEXTURE_2D);
		glColor4f(1, 1, 1, 1);
		
		drawActorTexture();
		drawDialogueTexture();
	}


	private void drawBackground() {
		double aspectRatio = FrameUtils.calculateAspectRatio();
		glColor4f(0, 0, 0, 1);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex2d(0, 0);
		glTexCoord2d(1, 0);
		glVertex2d(aspectRatio, 0);
		glTexCoord2d(1, 1);
		glVertex2d(aspectRatio, 1);
		glTexCoord2d(0, 1);
		glVertex2d(0, 1);
		glEnd();
	}

	private void drawDialogueBackground() {
		double aspectRatio = FrameUtils.calculateAspectRatio();
		double centerX = aspectRatio / 2;
		double xOffset = aspectRatio / 4;
		
		glColor4f(1, 1, 1, 1);
		
		glBegin(GL_QUADS);
		glVertex2d(centerX - xOffset, 0.65);
		glVertex2d(centerX + xOffset, 0.65);
		glVertex2d(centerX + xOffset, 0.85);
		glVertex2d(centerX - xOffset, 0.85);
		glEnd();
	}

	private void drawActorTexture() {
		double aspectRatio = FrameUtils.calculateAspectRatio();
		double centerX = aspectRatio / 2;
		double xOffset = aspectRatio / 4;
		
		actorTexture.bind();
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex2d(centerX - xOffset, 0.1);
		glTexCoord2d(1, 0);
		glVertex2d(centerX + xOffset, 0.1);
		glTexCoord2d(1, 1);
		glVertex2d(centerX + xOffset, 0.6);
		glTexCoord2d(0, 1);
		glVertex2d(centerX - xOffset, 0.6);
		glEnd();
	}


	private void drawDialogueTexture() {
		double aspectRatio = FrameUtils.calculateAspectRatio();
		double centerX = aspectRatio / 2;
		double xOffset = aspectRatio / 4;

		this.sequence.dialogueTextures[this.sequencePlayhead].bind();
		
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex2d(centerX - xOffset, 0.65);
		glTexCoord2d(1, 0);
		glVertex2d(centerX + xOffset, 0.65);
		glTexCoord2d(1, 1);
		glVertex2d(centerX + xOffset, 0.85);
		glTexCoord2d(0, 1);
		glVertex2d(centerX - xOffset, 0.85);
		glEnd();
	}
}
