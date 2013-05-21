package gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class FrameBufferUtils {

	public static int generateFrameBuffer() {
		int frameBufferID = glGenFramebuffers();
		return frameBufferID;
	}

	public static int generateShadowMapFrameBuffer(int shadowMapTextureID) {
		int frameBufferID = generateFrameBuffer();
		//configuring the frame buffer
		glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
		glReadBuffer(GL_NONE);
		glDrawBuffer(GL_NONE);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, shadowMapTextureID, 0);
		
		int isFramebufferOperational = glCheckFramebufferStatus(GL_FRAMEBUFFER);
		if(isFramebufferOperational != GL_FRAMEBUFFER_COMPLETE) {			
			System.out.println("ERROR: FrameBuffer is not operational!");
		}
		
		//resetting to the default frame buffer
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
		return frameBufferID;
	}

}
